import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CacheCommandsHandler {

	private static Stack<Task> current = new Stack<Task>();
	private static Stack<Task> aheadCmds = new Stack<Task>();
	private static ArrayList<Task> updatedTask = new ArrayList<Task>();

	/**
	 * This method will do an undo operation
	 * @param fileName will get this fileName from the menu class
	 * @param listTask contains the list of current tasks
	 * @param msgPair to indicate the message type
	 * @return message depending on situation met 
	 */
	public static String executeUndo(String fileName, ArrayList<Task> listTask, IndicatorMessagePair msgPair) {

		// adding all the elements into current stack
		current.addAll(listTask);

		if (checkUndoEmpty()) {
			return MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
		}
		
		// pop out the latest operation from current and push to aheadCmds
		aheadCmds.push(current.pop());
			
		checkStackEmptyForUndo();

		return updateTaskForUndo(fileName, listTask, updatedTask);
	}
	
	/**
	 * This method will check whether undo can be done
	 * @return true if current stack is empty, return false if current stack is not empty
	 */
	private static boolean checkUndoEmpty() {
		return current.isEmpty();
	}

	/**
	 * This method will check whether current stack is empty, 
	 * if it is not, will add each elements into updatedTask
	 */
	private static void checkStackEmptyForUndo() {
		while(!current.empty())
		{
			updatedTask.add(current.pop());
		}
	}

	private static String updateTaskForUndo(String fileName, ArrayList<Task> listTask, ArrayList<Task> updatedTask) {

		for (int i = 0; i < updatedTask.size(); i++) {

			// updating main array list
			listTask.set(i, updatedTask.get(i));

			// write to file
			IndicatorMessagePair indicMsg = new IndicatorMessagePair();
			FileHandler.writeToFile(fileName, listTask, indicMsg);

			if (!indicMsg.isTrue()) {
				return indicMsg.getMessage();
			}
		}

		return MessageList.MESSAGE_UNDO_SUCCESS;
	}
}
