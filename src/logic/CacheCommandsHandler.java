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

	private static String updateTaskList(Data smtData) {

		smtData.getListTask().clear();
		Data prevList = current.peek();
		
		for(int i = 0; i < prevList.getSize(); i++){
			smtData.addATaskToList(prevList.getATask(i));
		}

		// write to file
		IndicatorMessagePair indicMsg = smtData.writeTaskListToFile();
		

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		return MessageList.MESSAGE_UNDO_SUCCESS;
	}
	
	/**
	 * This method will add a new history to current stack and clear the aheadCmds stack
	 * @param listTask
	 */
	public static void newHistory(Data smtData){
		current.push(smtData);
		aheadCmds.clear();
	}
}
