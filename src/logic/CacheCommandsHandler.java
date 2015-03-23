package logic;

import java.util.Stack;

import utility.IndicatorMessagePair;
import utility.MessageList;
import data.Data;

public class CacheCommandsHandler {

	private static Stack<Data> current = new Stack<Data>();
	private static Stack<Data> aheadCmds = new Stack<Data>();

	/**
	 * This method will do an undo operation
	 * @param fileName will get this fileName from the menu class
	 * @param listTask contains the list of current tasks
	 * @param msgPair to indicate the message type
	 * @return message depending on situation met 
	 */
	public static String executeUndo(Data smtData) {

		if (checkUndoEmpty()) {
			return MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
		}
		
		// pop out the latest operation from current and push to aheadCmds
		aheadCmds.push(current.pop());
		
		return updateTaskList(smtData);
	}
	
	/**
	 * This method will check whether undo can be done
	 * @return true if current stack is empty, return false if current stack is not empty
	 */
	private static boolean checkUndoEmpty() {
		return current.isEmpty();
	}

	/**
	 * This method will update the task list
	 * @param smtData is a variable of object type Data class
	 * @return
	 */
	private static String updateTaskList(Data smtData) {

		smtData.getListTask().clear();
		Data prevList = current.peek();
		
		for(int i = 0; i < prevList.getSize(); i++){
			smtData.addATaskToList(prevList.getATask(i));
		}
		
		// this will call updateLastUnUsedIndex to update the last unused index
		updateLastUnUsedIndex(smtData);

		// write to file the updated task list to file
		IndicatorMessagePair indicMsg = smtData.writeTaskListToFile();
		

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		return MessageList.MESSAGE_UNDO_SUCCESS;
	}
	
	/**
	 * This method will update the latest unused index
	 * @param smtData is a variable of object type Data class
	 */
	private static void updateLastUnUsedIndex(Data smtData){
		
		Data prevIndex = current.peek();
		
		for(int i=0; i < prevIndex.getSize(); i++){
			smtData.setLastUnUsedIndex(prevIndex.getLastUnUsedIndex());
		}
		
		// write to file the updated last unused index to file
		IndicatorMessagePair indicIndex = smtData.writeLastUnUsedIndexToFile();
		
		if (!indicIndex .isTrue()) {
			indicIndex.getMessage();
		}
	}
	
	/**
	 * This method will add a new history to current stack and clear the aheadCmds stack
	 * @param smtData is a variable of object type Data class
	 */
	public static void newHistory(Data smtData){
		current.push(smtData);
		aheadCmds.clear();
	}
}
