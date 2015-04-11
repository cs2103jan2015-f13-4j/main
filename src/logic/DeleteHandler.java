//@author A0112502A
package logic;

import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

import storage.FileStorage;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.MessageList;
import utility.TaskLogging;
import data.Data;
import data.Task;

/**
 * This class is doing the delete operation
 *
 */

public class DeleteHandler {

	/**
	 * Declaration for constant variable and Logger
	 */
    private static Logger taskLogger = TaskLogging.getInstance();
    private static int length = 0;
	
    /**
     * This method is to check if the input is integer
     * @param text input that user have typed in
     * @return true if input is integer, false if input is not integer
     */
	private static boolean checkInteger(String text){
		try{
			Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method is use to execute the delete operation
	 * @param keyFieldsList contains the list of keyword and the data it has
	 * @param smtData contains the whole information including the task list
	 * @return accordingly to the condition met
	 */
	public static String executeDelete(Map<String, String> keyFieldsList, Data smtData) {

		// Local declaration
		int i;
		int index;
		
		if(smtData == null){
			assert false : "Data object is null";
		}
		
		if(keyFieldsList == null){
			assert false : "Map object is null";
		}
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()){
			return MessageList.MESSAGE_INVALID_DELETE;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()){
			return MessageList.MESSAGE_NO_FILE_DELETED;
		}
		
		if(!(keyFieldsList.size() == 1) || !(checkInteger(keyFieldsList.get(CommandType.Command_Types.DELETE.name())))){
			return MessageList.MESSAGE_INVALID_DELETE;
		}
		
		// converting command type from string into integer
		index = Integer.parseInt(keyFieldsList.get(CommandType.Command_Types.DELETE.name()));
		
		// will search the task id from the list and delete the task
		for(i = 0; i < smtData.getSize(); i++){
			if(smtData.getListTask().get(i).getTaskId() == index){
				break;
			}
		}
		
		// will execute the checkTaskIDSize
		return checkTaskIDSize(smtData, i);
	}

	/**
	 * This method is to check if user input's task id to be delete is more than or equal to 0, 
	 * and check if it is lesser than the list task size
	 * @param smtData contains the whole information including the task list
	 * @param taskId number assigned to each task 
	 * @return delete success message if taskId is lesser than 0 and list task size
	 */
	private static String checkTaskIDSize(Data smtData, int taskId) {
		Task removedText;
		
		if(taskId >= length && taskId < smtData.getListTask().size())
		{
			IndicatorMessagePair indicMsg = new IndicatorMessagePair();
			removedText = smtData.removeATaskFromList(taskId, indicMsg);
			
			if(!indicMsg.isTrue()){
				return indicMsg.getMessage();
			}
			
			indicMsg = new IndicatorMessagePair();
			indicMsg = smtData.writeTaskListToFile();
			
			if(!indicMsg.isTrue()){
				return indicMsg.getMessage();
			}
			
			// this will call the CacheCommandsHandler and update the history of the list
			CacheCommandsHandler.newHistory(smtData);
			
			// to log delete operation
            taskLogger.log(Level.INFO, "Task ID deleted: " + removedText.getTaskId());
			
            return String.format(removedText.toString() + "\nDeleted");
            //return String.format(MessageList.MESSAGE_DELETE_SUCCESS, FileStorage.getFileNameForTasksList(), removedText.getTaskDescription());
		}
			
		return MessageList.MESSAGE_NO_FILE_DELETED;
	}
}
