import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CacheCommandsHandler {

	private static Stack<ArrayList<Task>> prevStack = new Stack<ArrayList<Task>>();
	private static Stack<ArrayList<Task>> currStack = new Stack<ArrayList<Task>>();

	/**
	 * This method will check whether undo can be done
	 * 
	 * @return
	 */
	private static boolean checkUndo() {
		return prevStack.isEmpty();
	}

	public static String undo(String fileName, ArrayList<Task> listTask, IndicatorMessagePair msgPair) {
		
		ArrayList<Task> updatedTask = new ArrayList<Task>();

		if (!checkUndo()) {
			
			// clear the currStack and let it be empty 
			currStack.clear();
			
			// pop out the latest operation
			prevStack.pop();
			
			while(!prevStack.isEmpty())
			{
				currStack.push(prevStack.pop());
			}
			
			for (int i = 0; i < listTask.size(); i++) {
				
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
		
		return MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
	}
}
