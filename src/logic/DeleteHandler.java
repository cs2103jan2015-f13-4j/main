package logic;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;
import storage.FileStorage;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.MessageList;
import utility.TaskLogging;
import data.Data;
import data.Task;

public class DeleteHandler {

    private static Logger taskLogger = TaskLogging.getInstance();
	
	private static boolean checkInteger(String text){
		try{
			Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	
	
	
	//======================================================================================
	//=====================================================================================
	
	public static String executeDelete(HashMap<String, String> keyFieldsList, Data smtData) {

		int i;
		int index;
		Task removedText;
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()){
			return MessageList.MESSAGE_INVALID_DELETE;
		}
		
		
		if(smtData == null || smtData.getListTask().isEmpty()){
			return MessageList.MESSAGE_NO_FILE_DELETED;
		}
		
		if(!(keyFieldsList.size() == 1) || !(checkInteger(keyFieldsList.get(CommandType.Command_Types.DELETE.name())))){
			return MessageList.MESSAGE_INVALID_DELETE;
		}
		
		
		index = Integer.parseInt(keyFieldsList.get(CommandType.Command_Types.DELETE.name()));
		
		// will search the task id from the list and delete the task
		for(i = 0; i < smtData.getSize(); i++){
			if(smtData.getListTask().get(i).getTaskId() == index){
				break;
			}
		}
		
		if(i >= 0 && i < smtData.getListTask().size())
		{
			IndicatorMessagePair indicMsg = new IndicatorMessagePair();
			removedText = smtData.removeATaskFromList(i, indicMsg);
			if(!indicMsg.isTrue()){
				return indicMsg.getMessage();
			}
			indicMsg = new IndicatorMessagePair();
			indicMsg = smtData.writeTaskListToFile();
			
			if(!indicMsg.isTrue()){
				return indicMsg.getMessage();
			}
			
            taskLogger.log(Level.INFO, "Task ID deleted: " + removedText.getTaskId());
			return String.format(MessageList.MESSAGE_DELETE_SUCCESS, FileStorage.getFileNameForTasksList(), removedText.getTaskDescription());
		}
			
		return MessageList.MESSAGE_NO_FILE_DELETED;
	}
}
