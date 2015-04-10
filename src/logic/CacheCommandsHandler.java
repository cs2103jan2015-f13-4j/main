//@A0112502A
package logic;

import java.util.Stack;
import utility.IndicatorMessagePair;
import utility.MessageList;
import data.Data;

/**
 * This class is doing the undo and re-do operation
 *
 */

public class CacheCommandsHandler {

	private static Stack<Data> current = new Stack<Data>();
	private static Stack<Data> aheadCmds = new Stack<Data>();
	private static String cacheCommandStatus = "";
	private static String result = "";

	/**
	 * This method will do an undo operation
	 * @param fileName will get this fileName from the menu class
	 * @param listTask contains the list of current tasks
	 * @param msgPair to indicate the message type
	 * @return message depending on situation met
	 */
	public static String executeUndo(Data smtData) {
		
		// check if smtData is null or empty
		if(smtData == null){
			assert false : "Data is null";
		}
		
		if (isStackContainsOneItem()) {
			return MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
		}

		// pop out the latest operation from current and push to aheadCmds
		aheadCmds.push(current.pop());
		cacheCommandStatus = "undo";

		return updateTaskList(smtData);
	}
	
	public static String executeRedo(Data smtData) {
		
		if(isStackContainsLastItem()) {
			return MessageList.MESSAGE_LAST_COMMAND;
		}
		
		current.push(aheadCmds.pop());
		cacheCommandStatus = "redo";
		
		return updateTaskList(smtData);
	}
	
	private static boolean isStackContainsLastItem() {
		if(aheadCmds.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * This method will check whether stack contains one item
	 * @return true if current stack contains one item, return false if current stack contains more or less than one
	 */
	private static boolean isStackContainsOneItem() {
		if(current.size() == 1){
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method will update the task list
	 * @param smtData contains the whole information including the task list
	 * @return result to indicate undo and re-do successfully
	 */
	private static String updateTaskList(Data smtData) {
		if(current.isEmpty()){
			return MessageList.MESSAGE_ERROR;
		}
			
		smtData.setListTask(current.peek().getListTask());

		// this will call updateLastUnUsedIndex and updatedBlockedOutDates to update the last unused index and the dates blocked
		updateLastUnUsedIndex(smtData);
		updatedBlockedOutDates(smtData);
		
		// write to file the updated task list to file
		IndicatorMessagePair indicMsg = smtData.writeTaskListToFile();

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		if(cacheCommandStatus.equals("undo")) {
			result = MessageList.MESSAGE_UNDO_SUCCESS;
		} else if(cacheCommandStatus.equals("redo")) {
			result = MessageList.MESSAGE_REDO_SUCCESS;
		}
		
		return result;
	}

	/**
	 * This method will update the latest unused index
	 * @param smtData contains the whole information including the task list
	 */
	private static void updateLastUnUsedIndex(Data smtData) {
		Data prevIndex;

		prevIndex = current.peek();
		smtData.setLastUnUsedIndex(prevIndex.getLastUnUsedIndex());

		// write to file the updated last unused index to file
		smtData.writeLastUnUsedIndexToFile();
	}
	
	/**
	 * This method will update the blocked out dates
	 * @param smtData contains the whole information including the task list
	 */
	private static void updatedBlockedOutDates(Data smtData){
		smtData.setBlockedDateTimeList(current.peek().getBlockedDateTimeList());
		smtData.writeBlockedDateTimeListToFile();
	}

	/**
	 * This method will add a new history to current stack and clear the aheadCmds stack
	 * @param smtData contains the whole information including the task list
	 */
	public static void newHistory(Data smtData) {
		Data newData = createData(smtData);
		current.push(newData);
		aheadCmds.clear();
	}

	/**
	 * This method will create a new data for set the blocked date, last unused index and list task
	 * @param smtData contains the whole information including the task list
	 * @return newData created
	 */
	private static Data createData(Data smtData) {
		Data newData = new Data();
		newData.setBlockedDateTimeList(smtData.getBlockedDateTimeList());
		newData.setLastUnUsedIndex(smtData.getLastUnUsedIndex());
		newData.setListTask(smtData.getListTask());
		return newData;
	}
}
