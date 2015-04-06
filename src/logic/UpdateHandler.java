package logic;



import java.util.Map;
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
	public static String executeUpdate(Map<String, String> keyFieldsList, Data smtData){
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
		
		if(keyFieldsList.get(CommandType.Command_Types.UPDATE.name()).split(" ").length > 1){
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Update");
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
	private static String updateContents(Map<String, String> keyFieldsList, int index, Data smtData){
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		indicMsg.setTrue(true);
		KeywordType.List_Keywords getKey;
		
		if(restrictOnlyUnqiueKeyWord(keyFieldsList) > 1){
			return MessageList.MESSAGE_NO_WEEKLY_DEADLINE;
		}
		
		//first check if command contains from and to keywords and process them first
		if(checkFromTimeToTimeBothExist(keyFieldsList)){
			indicMsg = processBothTimes(keyFieldsList, index, smtData);
		}
		
		if(indicMsg != null && !indicMsg.isTrue()){
				return indicMsg.getMessage();
		}
		
		for (String key : keyFieldsList.keySet()) {
			getKey = KeywordType.getKeyword(key);
			switch(getKey){
			case BY:
			case ON:
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
		taskLogger.log(Level.INFO, MessageList.MESSAGE_UPDATE_SUCCESS, smtData.getATask(index));
		return String.format(MessageList.MESSAGE_UPDATE_SUCCESS, smtData.getATask(index));
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
		
		DateTime newStartDateTime = null; 
		DateTime newEndDateTime = null;
		
		if(smtData.getATask(index).getTaskStartDateTime() != null){
			newStartDateTime = new DateTime(endDate.getYear(), endDate.getMonthOfYear(), endDate.getDayOfMonth(), smtData.getATask(index).getTaskStartDateTime().getHourOfDay(), smtData.getATask(index).getTaskStartDateTime().getMinuteOfHour());
		}
		
		if(smtData.getATask(index).getTaskEndDateTime() != null){
			newEndDateTime = new DateTime(endDate.getYear(), endDate.getMonthOfYear(), endDate.getDayOfMonth(), smtData.getATask(index).getTaskEndDateTime().getHourOfDay(), smtData.getATask(index).getTaskEndDateTime().getMinuteOfHour());
		}
		
		
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskStartDateTime(newStartDateTime);
		tempTask.setTaskEndDateTime(newEndDateTime);
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
		DateTime newStartTime = null; 
		DateTime newEndTime = null;
		
		if(smtData.getATask(index).getTaskStartDateTime() != null){
			newStartTime = new DateTime(weeklyDate.getYear(), weeklyDate.getMonthOfYear(), weeklyDate.getDayOfMonth(), smtData.getATask(index).getTaskStartDateTime().getHourOfDay(), smtData.getATask(index).getTaskStartDateTime().getMinuteOfHour());
		}
		
		if(smtData.getATask(index).getTaskEndDateTime() != null){
			newEndTime = new DateTime(weeklyDate.getYear(), weeklyDate.getMonthOfYear(), weeklyDate.getDayOfMonth(), smtData.getATask(index).getTaskEndDateTime().getHourOfDay(), smtData.getATask(index).getTaskEndDateTime().getMinuteOfHour());
		}
	
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskStartDateTime(newStartTime);
		tempTask.setTaskEndDateTime(newEndTime);
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
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_TIME_GIVEN);
		}

		DateTime startOrEndTime = DateTimeParser.generateTime(keyFields);
		if(startOrEndTime == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_INCORRECT_TIME_FORMAT));
		}
		
		Task tempTask = smtData.getATask(index);
		
		if(direction == KeywordType.List_Keywords.FROM){
			DateTime newStartTime = updateTimeToExistingDateTime(smtData.getATask(index), startOrEndTime);
			if(tempTask.getTaskEndDateTime() != null && !checkFromTimeToTimeBothValid(newStartTime, tempTask.getTaskEndDateTime())){
				return new IndicatorMessagePair(false, MessageList.MESSAGE_TIME_WRONG_FLOW);
			}
			tempTask.setTaskStartDateTime(newStartTime);
		}
		else if(direction == KeywordType.List_Keywords.TO){
			DateTime newEndTime = updateTimeToExistingDateTime(smtData.getATask(index), startOrEndTime);
			if(tempTask.getTaskStartDateTime() != null && !checkFromTimeToTimeBothValid(tempTask.getTaskStartDateTime(), newEndTime)){
				return new IndicatorMessagePair(false, MessageList.MESSAGE_TIME_WRONG_FLOW);
			}
			tempTask.setTaskEndDateTime(newEndTime);
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
	
	/**
	 * determineBothTimes method checks if both times are valid and proceed to update if possible
	 * @param keyFieldsList the input command
	 * @param index the current task location
	 * @param smtData the data object which contains the whole data
	 * @return IndicatorMessagePair which states whether the times can be update
	 */
	private static IndicatorMessagePair processBothTimes(Map<String, String> keyFieldsList, int index, Data smtData){
		
		if(!checkFromTimeToTimeBothField(keyFieldsList)){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INCORRECT_TIME_FORMAT);
		}
		
		DateTime startTime = DateTimeParser.generateTime(keyFieldsList.get(KeywordType.List_Keywords.FROM.name()));
		DateTime endTime = DateTimeParser.generateTime(keyFieldsList.get(KeywordType.List_Keywords.TO.name()));
		if(!checkFromTimeToTimeBothValid(startTime, endTime)){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_TIME_WRONG_FLOW);
		}
		if(smtData.getATask(index).getTaskStartDateTime() == null && smtData.getATask(index).getTaskEndDateTime() == null){
			startTime = new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth(), startTime.getHourOfDay(), startTime.getMinuteOfHour());
			endTime = new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth(), endTime.getHourOfDay(), endTime.getMinuteOfHour());
		}
		
		updateBothTimes(index, smtData, startTime, endTime);
		
		keyFieldsList.remove(KeywordType.List_Keywords.FROM.name());
		keyFieldsList.remove(KeywordType.List_Keywords.TO.name());
		return new IndicatorMessagePair(true, "");
	}


	/**
	 * updateBothTimes method will update both times to a task
	 * @param index the index where the existing task is
	 * @param smtData the data object which contains all the data
	 * @param startTime the start time to update
	 * @param endTime the end time to update
	 */
	private static void updateBothTimes(int index, Data smtData,
			DateTime startTime, DateTime endTime) {
		DateTime newStartTime = updateTimeToExistingDateTime(smtData.getATask(index), startTime);
		DateTime newEndTime = updateTimeToExistingDateTime(smtData.getATask(index), endTime);
		
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskStartDateTime(updateTimeToExistingDateTime(smtData.getATask(index), newStartTime));
		tempTask.setTaskEndDateTime(updateTimeToExistingDateTime(smtData.getATask(index), newEndTime));
		smtData.updateTaskList(index, tempTask);
	}
	
	/**
	 * checkFromTimeToTimeBothExist method checks if both from and to keyword exists
	 * @param keyFieldsList the command input 
	 * @return true if it contains, else false
	 */
	private static boolean checkFromTimeToTimeBothExist(Map<String, String> keyFieldsList){
		if(keyFieldsList.containsKey(KeywordType.List_Keywords.FROM.name()) && keyFieldsList.containsKey(KeywordType.List_Keywords.TO.name())){
			return true;
		}
		return false;
	}
	
	/**
	 * checkFromTimeToTimeBothField method checks if it receives from and to time
	 * @param keyFieldsList the command input
	 * @return true if both fields have some info, else false
	 */
	private static boolean checkFromTimeToTimeBothField(Map<String, String> keyFieldsList){
		if(keyFieldsList.get(KeywordType.List_Keywords.FROM.name()) != null && keyFieldsList.get(KeywordType.List_Keywords.FROM.name()).isEmpty() && keyFieldsList.get(KeywordType.List_Keywords.TO.name()) != null && keyFieldsList.get(KeywordType.List_Keywords.TO.name()).isEmpty()){
			return false;
		}
		return true;
	}
	
	
	/**
	 * checkFromTimeToTimeBothValid method checks if the time from both sides are valid to proceed
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return true if possible, else false
	 */
	private static boolean checkFromTimeToTimeBothValid(DateTime startTime, DateTime endTime){
		if(startTime == null){
			return false;
		}
		
		if(endTime == null){
			return false;
		}
		
		if(startTime.isAfter(endTime)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * updateTimeToExistingDateTime method updates the current task start or end time with the existing time
	 * @param currentTask the current task
	 * @param existingDateTime the existing time to update
	 * @return
	 */
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
	
	private static int restrictOnlyUnqiueKeyWord(Map<String, String> keyFieldsList){
		int count = 0;
		if(keyFieldsList.containsKey(KeywordType.List_Keywords.ON.name())){
			count++;
		}
		if(keyFieldsList.containsKey(KeywordType.List_Keywords.BY.name())){
			count++;
		}
		if(keyFieldsList.containsKey(KeywordType.List_Keywords.EVERY.name())){
			count++;
		}
		return count;
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
