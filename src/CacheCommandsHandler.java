import java.util.ArrayList;
import java.util.Stack;

public class CacheCommandsHandler {
	
	private static Stack <ArrayList<Task>> undoStack = new Stack <ArrayList<Task>>();
	
	/**
	 * This method will check whether undo can be done
	 * @return
	 */
	private static boolean checkUndo() {
		return !undoStack.isEmpty();
	}
	
	private static String undo() {
		if(!checkUndo()){
			return MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
		}
		
		return MessageList.MESSAGE_UNDO_SUCCESS;
	}
	
}
