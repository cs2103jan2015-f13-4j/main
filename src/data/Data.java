//@author A0111935L
package data;

import java.util.ArrayList;

import org.joda.time.DateTime;

import storage.FileStorage;
import utility.IndicatorMessagePair;
import utility.MessageList;

/**
 * This class stores the list of task, the last unused index and also the list
 * of blocked dates
 * 
 */
public class Data {
	private ArrayList<Task> tasksList;
	private ArrayList<DateTime> blockedDateTimeList;
	private Integer lastUnUsedIndex;

	public Data() {
		int initialIndex = 1;
		tasksList = new ArrayList<Task>();
		blockedDateTimeList = new ArrayList<DateTime>();
		lastUnUsedIndex = initialIndex;
	}

	/**
	 * getListTask method will return the list of tasks
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getListTask() {
		return tasksList;
	}

	/**
	 * getSize method returns the size of the list
	 * 
	 * @return
	 */
	public int getSize() {
		return tasksList.size();
	}

	public void clearTaskList() {
		tasksList.clear();
	}

	/**
	 * setListTask method overwrite the list of tasks
	 * 
	 * @param receivedListTask
	 */
	public void setListTask(ArrayList<Task> receivedListTask) {
		tasksList.clear();
		int startIndex = 0;
		Task newTask;
		for (int i = startIndex; i < receivedListTask.size(); i++) {
			newTask = new Task();
			newTask.setTaskId(receivedListTask.get(i).getTaskId());
			newTask.setTaskDescription(receivedListTask.get(i)
					.getTaskDescription());
			newTask.setTaskStartDateTime(receivedListTask.get(i)
					.getTaskStartDateTime());
			newTask.setTaskEndDateTime(receivedListTask.get(i)
					.getTaskEndDateTime());
			newTask.setTaskStatus(receivedListTask.get(i).getTaskStatus());
			newTask.setWeeklyDay(receivedListTask.get(i).getWeeklyDay());
			newTask.setDeadLineStatus(receivedListTask.get(i)
					.getDeadLineStatus());
			tasksList.add(newTask);
		}
	}

	/**
	 * getLastUnUsedIndex method return the last unused index
	 * 
	 * @return last unused index
	 */
	public Integer getLastUnUsedIndex() {
		return lastUnUsedIndex;
	}

	/**
	 * setLastUnUsedIndex method sets the last unused index
	 * 
	 * @param receivedLastUnusedIndex
	 *            the received unused index
	 */
	public void setLastUnUsedIndex(Integer receivedLastUnusedIndex) {
		lastUnUsedIndex = receivedLastUnusedIndex;
	}

	/**
	 * getATask will return a task from the list
	 * 
	 * @param index
	 *            which is the position inside the list
	 * @return a task object
	 */
	public Task getATask(int index) {
		int startIndex = 0;
		if (index >= tasksList.size() || index < startIndex) {
			return null;
		}
		return tasksList.get(index);
	}

	/**
	 * addATaskToList method add a new Task to the List
	 * 
	 * @param newTask
	 *            a new task object
	 * @return an indicator whether the task object were added successfully
	 */
	public IndicatorMessagePair addATaskToList(Task newTask) {
		if (newTask == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_TASK_ADDED_TO_DATA);
		}

		tasksList.add(newTask);
		lastUnUsedIndex++;
		return new IndicatorMessagePair(true, "");
	}

	/**
	 * removeATaskFromList method removes a task from the list and return the
	 * removed Task back
	 * 
	 * @param index
	 *            the index where the intended task is
	 * @param msgPair
	 *            an indicator whether the task object were removed successfully
	 * @return a Task
	 */
	public Task removeATaskFromList(int index, IndicatorMessagePair msgPair) {
		if (!checkTasksListForValidIndex(index, msgPair)) {
			msgPair.setTrue(false);
			msgPair.setMessage(MessageList.MESSAGE_INVALID_TASK_TO_REMOVE);
			return null;
		}

		Task removedTask = tasksList.remove(index);
		msgPair.setTrue(true);
		return removedTask;
	}

	/**
	 * updateTaskList method will update the task in the list
	 * 
	 * @param index
	 *            the index where the intended task is
	 * @param updatedTask
	 *            the updated Task which is to put into the list
	 * @return an indicator whether the task list were updated successfully
	 */
	public IndicatorMessagePair updateTaskList(int index, Task updatedTask) {
		IndicatorMessagePair msgPair = new IndicatorMessagePair();
		if (!checkTasksListForValidIndex(index, msgPair)) {
			return msgPair;
		}

		tasksList.set(index, updatedTask);
		msgPair.setTrue(true);
		msgPair.setMessage(MessageList.MESSAGE_SUCCESS_UPDATE_TASK_TO_ARRAYLIST);
		return msgPair;
	}

	/**
	 * getBlockedDateTimeList method will return the list of blocked date time
	 * 
	 * @return ArrayList<DateTime>
	 */
	public ArrayList<DateTime> getBlockedDateTimeList() {
		return this.blockedDateTimeList;
	}

	/**
	 * setBlockedDateTimeList method overwrite the list of blocked dates
	 * 
	 * @param receivedBlockedDateTimeList
	 */
	public void setBlockedDateTimeList(
			ArrayList<DateTime> receivedBlockedDateTimeList) {
		if (receivedBlockedDateTimeList == null) {
			return;
		}

		blockedDateTimeList.clear();
		for (int i = 0; i < receivedBlockedDateTimeList.size(); i++) {
			blockedDateTimeList.add(receivedBlockedDateTimeList.get(i));
		}
	}

	/**
	 * addBlockedDateTime method add a new blocked date time
	 * 
	 * @param receivedBlockedDateTime
	 *            the new date time to add
	 * @return an indication whether the date time is added successfully
	 */
	public IndicatorMessagePair addBlockedDateTime(
			DateTime receivedBlockedDateTime) {
		if (receivedBlockedDateTime == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_BLOCK_DATE_ADDED_TO_ARRAYLIST);
		}

		blockedDateTimeList.add(receivedBlockedDateTime);
		return new IndicatorMessagePair(true, "");
	}

	/**
	 * removeBlockedDateTime remove a blocked date based on the index
	 * 
	 * @param index
	 *            an index to indicate where is the blocked date
	 * @param msgPair
	 *            an indicator whether the date time were removed successfully
	 * @return the removed date time
	 */
	public DateTime removeBlockedDateTime(int index,
			IndicatorMessagePair msgPair) {
		if (!checkBlockedDateTimeListForValidIndex(index, msgPair)) {
			msgPair.setTrue(false);
			msgPair.setMessage(MessageList.MESSAGE_INVALID_BLOCKED_DATE_TO_REMOVE);
			return null;
		}

		DateTime removedTask = blockedDateTimeList.remove(index);
		msgPair.setTrue(true);
		return removedTask;
	}

	/**
	 * getABlockedDateTime method will return a blocked date with a specific
	 * location
	 * 
	 * @param index
	 *            the location of that particular date time
	 * @return the date time object
	 */
	public DateTime getABlockedDateTime(int index) {
		int startIndex = 0;
		if (index >= blockedDateTimeList.size() || index < startIndex) {
			return null;
		}
		return blockedDateTimeList.get(index);

	}

	/**
	 * checkForValidIndex method checks for the index given is within the range
	 * of the task list
	 * 
	 * @param index
	 *            an index to check
	 * @param msgPair
	 *            an indicator whether the index is valid
	 * @return a true if valid, else false
	 */
	private boolean checkTasksListForValidIndex(int index,
			IndicatorMessagePair msgPair) {
		int startIndex = 0;
		if (index < startIndex || index >= tasksList.size()) {
			msgPair.setTrue(false);
			msgPair.setMessage(MessageList.MESSAGE_INDEX_OUT_OF_RANGE);
			return false;
		}
		return true;
	}

	/**
	 * checkBlockedDateTimeListForValidIndex method checks for the index given
	 * is within the range of the blocked date time list
	 * 
	 * @param index
	 *            an index to check
	 * @param msgPair
	 *            an indicator whether the index is valid
	 * @return a true if valid, else false
	 */
	private boolean checkBlockedDateTimeListForValidIndex(int index,
			IndicatorMessagePair msgPair) {
		int startIndex = 0;
		if (index < startIndex || index >= blockedDateTimeList.size()) {
			msgPair.setTrue(false);
			msgPair.setMessage(MessageList.MESSAGE_INDEX_OUT_OF_RANGE);
			return false;
		}
		return true;
	}

	/**
	 * checkIfTaskIdExistWithLastUnusedIndex method check if task id conflicts
	 * with the last unused index
	 * 
	 * @return true if conflicts, else false
	 */
	private boolean checkIfTaskIdExistWithLastUnusedIndex() {
		int startIndex = 0;
		for (int i = startIndex; i < tasksList.size(); i++) {
			if (tasksList.get(i).getTaskId() == lastUnUsedIndex) {
				return true;
			}
		}
		return false;
	}

	/**
	 * writeTaskListToFile method send the task list to the file storage to
	 * writes to the text file
	 * 
	 * @return indicatormessagepair to indicate whether it has been written
	 *         successfully
	 */
	public IndicatorMessagePair writeTaskListToFile() {
		return FileStorage.writeToFile(tasksList);

	}

	/**
	 * writeLastUnUsedIndexToFile method send the last unused index to the file
	 * storage to writes to the text file
	 * 
	 * @return indicatormessagepair to indicate whether it has been written
	 *         successfully
	 */
	public IndicatorMessagePair writeLastUnUsedIndexToFile() {
		return FileStorage.writeToFile(lastUnUsedIndex);
	}

	/**
	 * writeBlockedDateTimeListToFile method send the blocked date list to the
	 * file storage to writes to the text file
	 * 
	 * @return indicatormessagepair to indicate whether it has been written
	 *         successfully
	 */
	public IndicatorMessagePair writeBlockedDateTimeListToFile() {
		return FileStorage.writeBlockedDateTimeToFile(blockedDateTimeList);
	}

	/**
	 * loadEveryThingFromFile method loads the data (task list, last unused
	 * index and blocked out dates list from the text file
	 * 
	 * @return indicator message pair which to indicate whether it is loaded
	 *         successfully with a message
	 */
	public IndicatorMessagePair loadEveryThingFromFile() {
		IndicatorMessagePair msgPair = new IndicatorMessagePair();
		ArrayList<Task> tempTaskList = FileStorage
				.checkAndLoadTaskFile(msgPair);

		if (!msgPair.isTrue()) {
			return msgPair;
		}

		setListTask(tempTaskList);

		int tempLastUnusedIndex = FileStorage
				.checkAndLoadLastTaskIndexFile(msgPair);

		if (!msgPair.isTrue()) {
			return msgPair;
		}

		setLastUnUsedIndex(tempLastUnusedIndex);

		if (checkIfTaskIdExistWithLastUnusedIndex()) {
			msgPair.setTrue(false);
			msgPair.setMessage(MessageList.MESSAGE_TASKID_ERROR);
			return msgPair;
		}

		ArrayList<DateTime> tempBlockedDateList = FileStorage
				.checkAndLoadBlockedDateFile(msgPair);

		if (!msgPair.isTrue()) {
			return msgPair;
		}

		setBlockedDateTimeList(tempBlockedDateList);

		return msgPair;
	}
}
