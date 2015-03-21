package logic;


import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

import parser.DateParser;
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
	 * This method search for a task in the ArrayList
	 * @param taskId the task id for searching
	 * @param listTask contains the list of current tasks
	 * @return message
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
	
	
	
	
	
	
	
	
	
	//===================================================================================
	//====================================================================================
	
	
	
	/**
	 * This method handle the update execution and update the contents to the ArrayList of tasks
	 * @param keyPFieldsList contains the list of keyword and the data it has
	 * @param listTask contains the list of current tasks
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
		
		if(index < minTaskListSize){
			return MessageList.MESSAGE_NO_SUCH_TASK;
		}
		keyFieldsList.remove(CommandType.Command_Types.UPDATE.name());////remove the update key pair as it has the index extracted
		return updateContents(keyFieldsList, index, smtData);
	}
	
	
	/**
	 * This method will update the contents based on the key word
	 * @param keyFieldsList contains the list of keyword and the data it has
	 * @param index indicate the location of the intended task to be updated
	 * @param listTask contains the list of current tasks
	 * @return message
	 */
	private static String updateContents(HashMap<String, String> keyFieldsList, int index, Data smtData){
		IndicatorMessagePair indicMsg;
		KeywordType.List_Keywords getKey;
		for (String key : keyFieldsList.keySet()) {
			getKey = KeywordType.getKeyword(key);
			switch(getKey){
			case BY:
				indicMsg = updateTaskByOrEndWhen(smtData, index, keyFieldsList.get(key));
				break;
			case TASKDESC:
				indicMsg = updateTaskDesc(smtData, index, keyFieldsList.get(key));
				break;
			case TASKSTART:
				indicMsg = updateTaskStartWhen(smtData, index, keyFieldsList.get(key));
				break;
			case TASKEND:
				indicMsg = updateTaskByOrEndWhen(smtData, index, keyFieldsList.get(key));
				break;
			case COMPLETED:
				indicMsg = updateTaskStatus(smtData, index, keyFieldsList.get(key), true);
				break;
			case PENDING:
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
		taskLogger.log(Level.INFO, MessageList.MESSAGE_UPDATE_SUCCESS);
		return MessageList.MESSAGE_UPDATE_SUCCESS;
	}
	
	/**
	 * This method will update the task end date and will determine whether it can be updated as well.
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskByOrEndWhen(Data smtData, int index, String keyFields){
		if(smtData == null){
			assert false : "System error";
		}
		
		if(keyFields == null || keyFields.isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateTime endDate = DateParser.generateDate(keyFields);
		if(endDate == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "End"));
		}
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskEndDateTime(endDate);
		smtData.updateTaskList(index, tempTask);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method will update the task description and will determine whether it can be updated as well.
	 * @param listTask contains the list of current tasks
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
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskStartWhen(Data smtData, int index, String keyFields){
		if(smtData == null){
			assert false : "System error";
		}
		
		if(keyFields == null || keyFields.isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		
		DateTime startDate = DateParser.generateDate(keyFields);
		if(startDate == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start"));
		}
		Task tempTask = smtData.getATask(index);
		tempTask.setTaskStartDateTime(startDate);
		smtData.updateTaskList(index, tempTask);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method update the status of the task to completed or pending.
	 * @param listTask contains the list of current tasks
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

}
