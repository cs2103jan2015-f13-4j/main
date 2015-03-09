import java.util.ArrayList;

public class Menu {
	private static ArrayList<Task> listTask;
	private static int lastUnusedIndex = 1;	//stub
	public Menu() {
		listTask = new ArrayList<Task>();
	}

	public static String commandExecution(String fileName, String input) {
		ArrayList<KeyParamPair> keyParamList = new ArrayList<KeyParamPair>();
		CommandType.Command_Types cmd = InputStringParser.processString(input,
				keyParamList);

		switch (cmd) {

		case ADD: {
			return AddHandler.executeAdd(fileName, keyParamList, listTask, lastUnusedIndex);
		}
		case DISPLAY: {
			//return DisplayHandler.executeDisplay(fileName, input, listTask);
		}
		case DELETE: {
			//return DeleteHandler.executedelete(fileName, input, listTask);
		}
		case CLEAR: {
			//return ClearHandler.executeClear(fileName, input, listTask);
		}
		case INVALID: {
			return MessageList.MESSAGE_INVAILD;
		}
		case SEARCH: {
			//return ExecuteHandler.executeSearch(input);
		}
		case SORT: {
			//return SortHandler.executeSort(fileName);
		}
		case UPDATE: {
			return UpdateHandler.executeUpdate(fileName, keyParamList, listTask);
		}
		case EXIT: {
			System.exit(0);
			break;
		}
		default: {

			return MessageList.MESSAGE_MENU_COMMAND_UNRECOGNISED;
		}
		}

		return MessageList.MESSAGE_MENU_NO_COMMAND;
	}

}
