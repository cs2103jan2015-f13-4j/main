import java.util.ArrayList;
import java.util.Stack;

public class CacheCommandsHandler {

	private static Stack<ArrayList<Task>> current = new Stack<ArrayList<Task>>();
	private static Stack<ArrayList<Task>> aheadCmds = new Stack<ArrayList<Task>>();

	/**
	 * This method will do an undo operation
	 * @param fileName will get this fileName from the menu class
	 * @param listTask contains the list of current tasks
	 * @param msgPair to indicate the message type
	 * @return message depending on situation met 
	 */
	public static String executeUndo(String fileName, ArrayList<Task> listTask, IndicatorMessagePair msgPair) {

		if (checkUndoEmpty()) {
			return MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
		}
		
		// pop out the latest operation from current and push to aheadCmds
		aheadCmds.push(current.pop());

		return updateTaskList(fileName, listTask);
	}
	
	/**
	 * This method will check whether undo can be done
	 * @return true if current stack is empty, return false if current stack is not empty
	 */
	private static boolean checkUndoEmpty() {
		return current.isEmpty();
	}

	private static String updateTaskList(String fileName, ArrayList<Task> listTask) {

		listTask.clear();
		ArrayList<Task> prevList = current.peek();
		
		for(int i = 0; i < prevList.size(); i++){
			listTask.add(prevList.get(i));
		}

		// write to file
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		FileHandler.writeToFile(fileName, listTask, indicMsg);

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		return MessageList.MESSAGE_UNDO_SUCCESS;
	}
	
	/**
	 * This method will add a new history to current stack and clear the aheadCmds stack
	 * @param listTask
	 */
	public static void newHistory(ArrayList<Task> listTask){
		current.push(listTask);
		aheadCmds.clear();
	}
}
