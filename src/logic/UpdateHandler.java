package logic;


import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

import parser.DateTimeParser;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import utility.TaskLogging;
import data.Data;
import data.Task;

public class UpdateHandler {
	
	private static Logger taskLogger = TaskLogging.getInstance();

	
	/**
	 * This method handle the update execution and update the contents to the ArrayList of tasks
	 * @param keyPFieldsList contains the list of keyword and the data it has
	 * @param smtData contains the whole information including the task list
	 * @return message
	 */
	public static String executeUpdate(HashMap<String, String> keyFieldsList, Data smtData){
		int minTaskListSize = 0;
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()){
			return MessageList.MESSAGE_NULL;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()){
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(!keyFieldsList.containsKey(CommandType.Command_Types.UPDATE.name())){
			assert false : "Either Update is missing or the word update is in upper case: ";
		}
		
		if(!isStringAnInteger(keyFieldsList.get(CommandType.Command_Types.UPDATE.name()))){
			return String.format(MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Update");
		}
		
		int index = searchTaskIndexStored(Integer.parseInt(keyFieldsList.get(CommandType.Command_Types.UPDATE.name())), smtData);
		
		if(index < minTaskListSize || index >= smtData.getSize()){
			return MessageList.MESSAGE_NO_SUCH_TASK;
		}
		keyFieldsList.remove(CommandType.Command_Types.UPDATE.name());////remove the update key pair as it has the index extracted
		return updateContents(keyFieldsList, index, smtData);
	}
	
	
	/**
	 * This method will update the contents based on the key word
	 * @param keyFieldsList contains the list of keyword and the data it has
	 * @param index indicate the location of the intended task to be updated
	 * @param smtData contains the whole information including the task list
	 * @return message
	 */
	private static String updateContents(HashMap<String, String> keyFieldsList, int index, Data smtData){
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		indicMsg.setTrue(true);
		KeywordType.List_Keywords getKey;
		
		if(checkFromTimeToTimeBothExist(keyFieldsList)){
			indicMsg = determineBothTimes(keyFieldsList);
		}
		
		if(indicMsg != null && !indicMsg.isTrue()){
				return indicMsg.getMessage();
		}
		
		for (String key : keyFieldsList.keySet()) {
			getKey = KeywordType.getKeyword(key);
			switch(getKey){
			case BY:
				indicMsg = updateTaskByOrEndWhen(smtData, index, keyFieldsList.get(key));
				break;
			case TASKDESC:
				indicMsg = updateTaskDesc(smtData, index, keyFieldsList.get(key));
				break;
			case FROM:
				indicMsg = updateTaskFromOrToTimeWhen(smtData, index, keyFieldsList.get(key), KeywordType.List_Keywords.FROM);
				break;
			case TO:
				indicMsg = updateTaskFromOrToTimeWhen(smtData, index, keyFieldsList.get(key), KeywordType.List_Keywords.TO);
				break;
			case EVERY:
				indicMsg = updateRecurringWeek(smtData, index, keyFieldsList.get(key));
				break;
			case COMPLETE:
				indicMsg = updateTaskStatus(smtData, index, keyFieldsList.get(key), true);
				break;
			case INCOMPLETE:
				indicMsg = updateTaskStatus(smtData, index, keyFieldsList.get(key), false);
				break;
			default:
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Update");
			}
			
			if(!indicMsg.isTrue()){
				return indicMsg.getMessage();
			}
		}
		indicMsg = new IndicatorMessagePair();
		
		indicMsg = smtData.writeTaskListToFile();
		
		if(!indicMsg.isTrue()){
			return indicMsg.getMessage();
		}
		CacheCommandsHandler.newHistory(smtData);
		taskLogger.log(Level.INFO, MessageList.MESSAGE_UPDATE_SUCCESS);
		return MessageList.MESSAGE_UPDATE_SUCCESS;
	}
	

	/**
	 * updateTaskByOrEndWhen method will update the task end date and will determine whether it can be updated as well.
	 * @param smtData contains the whole information including the task list
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskByOrEndWhen(Data smtData, int index, String keyFields){
		if(smtData == null){
			assert false : "System error";
		}
		
		if(keyFields == null || keyFields.isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateTime endDate = DateTimeParser.generateDate(keyFields);
		if(endDate == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "End"));
		}
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskEndDateTime(endDate);
		smtData.updateTaskList(index, tempTask);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * updateRecurringWeek method will update the task to be happening in every week
	 * @param smtData contains the whole information including the task list
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateRecurringWeek(Data smtData, int index, String keyFields){
		if(keyFields == null || keyFields.isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		

		DateTime weeklyDate = DateTimeParser.generateDate(keyFields);
		
		if(weeklyDate == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Weekly"));
		}
		
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskStartDateTime(null);
		tempTask.setTaskEndDateTime(null);
		tempTask.setWeeklyDay(keyFields);
		smtData.updateTaskList(index, tempTask);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method will update the task description and will determine whether it can be updated as well.
	 * @param smtData contains the whole information including the task list
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @return true if success, false if the parameter and message
	 */
	private static IndicatorMessagePair updateTaskDesc(Data smtData, int index, String keyFields){
		if(smtData == null){
			assert false : "System error";
		}
		
		if(keyFields == null || keyFields.isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_DESCRIPTION_EMPTY);
		}
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskDescription(keyFields);
		smtData.updateTaskList(index, tempTask);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method will update the task start date and will determine whether it can be updated as well.
	 * @param smtData contains the whole information including the task list
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskFromOrToTimeWhen(Data smtData, int index, String keyFields, KeywordType.List_Keywords direction){
		if(smtData == null){
			assert false : "System error";
		}
		
		if(keyFields == null || keyFields.isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}

		DateTime startOrEndTime = DateTimeParser.generateTime(keyFields);
		if(startOrEndTime == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start"));
		}
		
		Task tempTask = smtData.getATask(index);
		
		if(direction == KeywordType.List_Keywords.FROM){
			tempTask.setTaskStartDateTime(updateTimeToExistingDateTime(smtData.getATask(index), startOrEndTime));
		}
		else if(direction == KeywordType.List_Keywords.TO){
			tempTask.setTaskEndDateTime(updateTimeToExistingDateTime(smtData.getATask(index), startOrEndTime));
		}
		
		//tempTask.setTaskStartDateTime(startTime);
		smtData.updateTaskList(index, tempTask);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method update the status of the task to completed or pending.
	 * @param smtData contains the whole information including the task list
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @param status the task status in boolean
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskStatus(Data smtData, int index, String keyFields, boolean status){
		if(smtData == null){
			assert false : "System error";
		}
		
		if(keyFields == null || !keyFields.isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_UPDATE_STATUS_EXTRA_FIELD);
		}
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskStatus(status);
		smtData.updateTaskList(index, tempTask);
		return new IndicatorMessagePair(true, "");
	}

	
	/**
	 * searchTaskIndexStored method search for a task in the ArrayList
	 * @param taskId taskId the task id for searching
	 * @param smtData the data contains all the information such as task list
	 * @return
	 */
	private static int searchTaskIndexStored(int taskId, Data smtData){
		for(int i = 0; i < smtData.getSize(); i++){
			if(taskId == smtData.getATask(i).getTaskId()){
				return i;
			}
		}
		return -1;
	}
	
	private static IndicatorMessagePair determineBothTimes(HashMap<String, String> keyFieldsList){
		
		if(!checkFromTimeToTimeBothField(keyFieldsList)){
			return new IndicatorMessagePair(false, "Time is not entered correctly");
		}
		
		if(!checkFromTimeToTimeBothValid(keyFieldsList)){
			return new IndicatorMessagePair(false, "Start Time is before End Time");
		}
		
		return new IndicatorMessagePair(true, "");
	}
	
	private static boolean checkFromTimeToTimeBothExist(HashMap<String, String> keyFieldsList){
		if(keyFieldsList.containsKey(KeywordType.List_Keywords.FROM.name()) && keyFieldsList.containsKey(KeywordType.List_Keywords.TO.name())){
			return true;
		}
		return false;
	}
	
	private static boolean checkFromTimeToTimeBothField(HashMap<String, String> keyFieldsList){
		if(keyFieldsList.get(KeywordType.List_Keywords.FROM.name()) != null && keyFieldsList.get(KeywordType.List_Keywords.FROM.name()).isEmpty() && keyFieldsList.get(KeywordType.List_Keywords.TO.name()) != null && keyFieldsList.get(KeywordType.List_Keywords.TO.name()).isEmpty()){
			return false;
		}
		return true;
	}
	
	private static boolean checkFromTimeToTimeBothValid(HashMap<String, String> keyFieldsList){
		DateTime startTime = DateTimeParser.generateTime(keyFieldsList.get(KeywordType.List_Keywords.FROM.name()));
		if(startTime == null){
			return false;
		}
		
		DateTime endTime = DateTimeParser.generateTime(keyFieldsList.get(KeywordType.List_Keywords.TO.name()));
		if(endTime == null){
			return false;
		}
		
		if(startTime.isAfter(endTime)){
			return false;
		}
		
		return true;
	}
	
	private static DateTime updateTimeToExistingDateTime(Task currentTask, DateTime existingDateTime){
		if(currentTask.getTaskStartDateTime() == null && currentTask.getTaskEndDateTime() == null){
			//do nothing
		}
		else if(currentTask.getTaskStartDateTime() == null){
			existingDateTime = new DateTime(currentTask.getTaskEndDateTime().getYear(), currentTask.getTaskEndDateTime().getMonthOfYear(), currentTask.getTaskEndDateTime().getDayOfMonth(), existingDateTime.getHourOfDay(), existingDateTime.getMinuteOfHour());
		}
		else{
			existingDateTime = new DateTime(currentTask.getTaskStartDateTime().getYear(), currentTask.getTaskStartDateTime().getMonthOfYear(), currentTask.getTaskStartDateTime().getDayOfMonth(), existingDateTime.getHourOfDay(), existingDateTime.getMinuteOfHour());
		}
		
		return existingDateTime;
	}
	
	/**
	 * This method check if the given string can be converted to integer
	 * 
	 * @param inputStr
	 *            the string to be converted
	 * @return true if it is a integer, else false
	 */
	private static boolean isStringAnInteger(String inputStr) {
		try {
			Integer.parseInt(inputStr);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
