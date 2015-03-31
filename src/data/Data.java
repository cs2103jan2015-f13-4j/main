package data;
import java.util.ArrayList;

import org.joda.time.DateTime;

import storage.FileStorage;
import utility.IndicatorMessagePair;

public class Data {
	private ArrayList<Task> tasksList;
	private ArrayList<DateTime> blockedDateTimeList;
	private Integer lastUnUsedIndex;

	public Data(){
		tasksList = new ArrayList<Task>();
		blockedDateTimeList = new ArrayList<DateTime>();
		lastUnUsedIndex = 1;
	}
	
	/**
	 * getListTask method will return the list of tasks
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getListTask(){
		return tasksList;
	}
	
	/**
	 * getSize method returns the size of the list
	 * @return
	 */
	public int getSize(){
		return tasksList.size();
	}
	
	public void clearTaskList(){
		tasksList.clear();
	}
	
	/**
	 * setListTask method overwrite the list of tasks
	 * @param receivedListTask
	 */
	public void setListTask(ArrayList<Task> receivedListTask){
		tasksList.clear();
		Task newTask;
		for(int i = 0; i < receivedListTask.size(); i++){
			newTask = new Task();
			newTask.setTaskId(receivedListTask.get(i).getTaskId());
			newTask.setTaskDescription(receivedListTask.get(i).getTaskDescription());
			newTask.setTaskStartDateTime(receivedListTask.get(i).getTaskStartDateTime());
			newTask.setTaskEndDateTime(receivedListTask.get(i).getTaskEndDateTime());
			newTask.setTaskStatus(receivedListTask.get(i).getTaskStatus());
			newTask.setWeeklyDay(receivedListTask.get(i).getWeeklyDay());
			tasksList.add(newTask);
		}
	}
	
	/**
	 * getLastUnUsedIndex method return the last unused index
	 * @return last unused index
	 */
	public Integer getLastUnUsedIndex(){
		return lastUnUsedIndex;
	}
	
	/**
	 * setLastUnUsedIndex method sets the last unused index
	 * @param receivedLastUnusedIndex the received unused index
	 */
	public void setLastUnUsedIndex(Integer receivedLastUnusedIndex){
		lastUnUsedIndex = receivedLastUnusedIndex;
	}
	
	/**
	 * getATask will return a task from the list
	 * @param index which is the position inside the list
	 * @return a task object
	 */
	public Task getATask(int index){
		if(index >= tasksList.size() || index < 0){
			return null;
		}
		return tasksList.get(index);
	}
	
	/**
	 * addATaskToList method add a new Task to the List
	 * @param newTask a new task object
	 * @return an indicator whether the task object were added successfully
	 */
	public IndicatorMessagePair addATaskToList(Task newTask){
		if(newTask == null){
			return new IndicatorMessagePair(false, "No Task added");
		}
		
		tasksList.add(newTask);
		lastUnUsedIndex++;
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * removeATaskFromList method removes a task from the list and return the removed Task back
	 * @param index the index where the intended task is
	 * @param msgPair an indicator whether the task object were removed successfully
	 * @return a Task
	 */
	public Task removeATaskFromList(int index, IndicatorMessagePair msgPair){
		if(!checkTasksListForValidIndex(index, msgPair)){
			msgPair.setTrue(false);
			msgPair.setMessage("Invalid task");
			return null;
		}
		
		Task removedTask = tasksList.remove(index);
		msgPair.setTrue(true);
		return removedTask;
	}
	
	/**
	 * updateTaskList method will update the task in the list
	 * @param index the index where the intended task is
	 * @param updatedTask the updated Task which is to put into the list
	 * @return an indicator whether the task list were updated successfully
	 */
	public IndicatorMessagePair updateTaskList(int index, Task updatedTask){
		IndicatorMessagePair msgPair = new IndicatorMessagePair();
		if(!checkTasksListForValidIndex(index, msgPair)){
			return msgPair;
		}
		
		tasksList.set(index, updatedTask);
		msgPair.setTrue(true);
		msgPair.setMessage("Update Success");
		return msgPair;
	}
	
	/**
	 * getBlockedDateTimeList method will return the list of blocked date time
	 * @return ArrayList<DateTime>
	 */
	public ArrayList<DateTime> getBlockedDateTimeList(){
		return this.blockedDateTimeList;
	}
	
	/**
	 * setBlockedDateTimeList method overwrite the list of blocked dates
	 * @param receivedBlockedDateTimeList
	 */
	public void setBlockedDateTimeList(ArrayList<DateTime> receivedBlockedDateTimeList){
		blockedDateTimeList.clear();
		for(int i = 0; i < receivedBlockedDateTimeList.size(); i++){
			blockedDateTimeList.add(receivedBlockedDateTimeList.get(i));
		}
	}
	
	/**
	 * addBlockedDateTime method add a new blocked date time
	 * @param receivedBlockedDateTime the new date time to add
	 * @return an indication whether the date time is added successfully
	 */
	public IndicatorMessagePair addBlockedDateTime(DateTime receivedBlockedDateTime){
		if(receivedBlockedDateTime == null){
			return new IndicatorMessagePair(false, "No blocked date added");
		}
		
		blockedDateTimeList.add(receivedBlockedDateTime);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * removeBlockedDateTime remove a blocked date based on the index
	 * @param index an index to indicate where is the blocked date 
	 * @param msgPair an indicator whether the date time were removed successfully
	 * @return the removed date time
	 */
	public DateTime removeBlockedDateTime(int index, IndicatorMessagePair msgPair){
		if(!checkBlockedDateTimeListForValidIndex(index, msgPair)){
			msgPair.setTrue(false);
			msgPair.setMessage("Invalid blocked date");
			return null;
		}
		
		DateTime removedTask = blockedDateTimeList.remove(index);
		msgPair.setTrue(true);
		return removedTask;
	}
	
	/**
	 * checkForValidIndex method checks for the index given is within the range of the task list
	 * @param index an index to check
	 * @param msgPair an indicator whether the index is valid
	 * @return a true if valid, else false
	 */
	private boolean checkTasksListForValidIndex(int index, IndicatorMessagePair msgPair){
		if(index < 0 || index > tasksList.size()){
			msgPair.setTrue(false);
			msgPair.setMessage("Index Out of Range.");
			return false;
		}
		return true;
	}
	
	/**
	 * checkBlockedDateTimeListForValidIndex method checks for the index given is within the range of the blocked date time list
	 * @param index an index to check
	 * @param msgPair an indicator whether the index is valid
	 * @return a true if valid, else false
	 */
	private boolean checkBlockedDateTimeListForValidIndex(int index, IndicatorMessagePair msgPair){
		if(index < 0 || index > blockedDateTimeList.size()){
			msgPair.setTrue(false);
			msgPair.setMessage("Index Out of Range.");
			return false;
		}
		return true;
	}
	
	
	public IndicatorMessagePair writeTaskListToFile(){
		return FileStorage.writeToFile(tasksList);
		
	}
	
	public IndicatorMessagePair writeLastUnUsedIndexToFile(){
		return FileStorage.writeToFile(lastUnUsedIndex);
	}
	
	public IndicatorMessagePair writeBlockedDateTimeListToFile(){
		return FileStorage.writeBlockedDateTimeToFile(blockedDateTimeList);
	}
	
	public IndicatorMessagePair loadEveryThingFromFile(){
		IndicatorMessagePair msgPair = new IndicatorMessagePair();
		setListTask(FileStorage.checkAndLoadTaskFile(msgPair));
		if(!msgPair.isTrue()){
			return msgPair;
		}
		
		setBlockedDateTimeList(FileStorage.checkAndLoadBlockedDateFile(msgPair));
		if(!msgPair.isTrue()){
			return msgPair;
		}
		
		setLastUnUsedIndex(FileStorage.checkAndLoadLastTaskIndexFile(msgPair));
		
		return msgPair;
	}
}
