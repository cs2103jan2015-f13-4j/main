import java.util.ArrayList;

public class Menu {
	private static ArrayList<Task> listTask;
	private static String fileName = "tasklist.txt";
	private static String lastUsedIndexFileName = "lastUsedIndex.txt";

	public Menu() {
		listTask = new ArrayList<Task>();
	}

	public void retrieveTasksAndLastUsedIndex(IndicatorMessagePair msgPair) {
		// IndicatorMessagePair msgPair = new IndicatorMessagePair();
		FileHandler.checkAndLoadTaskFile(fileName, listTask, msgPair);
		if (!msgPair.isTrue) {
			return;
		}
	}

	public String commandExecution(String input) {
		ArrayList<KeyFieldPair> keyFieldsList = new ArrayList<KeyFieldPair>();
		CommandType.Command_Types cmd = CommandStringParser.processString(input,
				keyFieldsList);

		switch (cmd) {

		case ADD: {
			return AddHandler.executeAdd(fileName, lastUsedIndexFileName,
					keyFieldsList, listTask);
		}
		case DISPLAY: {
			return DisplayHandler.executeDisplay(fileName, keyFieldsList,
					listTask);
		}
		case DELETE: {
			return DeleteHandler
					.executeDelete(fileName, keyFieldsList, listTask);
		}
		case CLEAR: {
			// return ClearHandler.executeClear(fileName, input, listTask);
		}
		case INVALID: {
			return MessageList.MESSAGE_INVAILD;
		}
		case SEARCH: {
			 return SearchHandler.executeSearch(keyFieldsList, listTask);
		}
		case SORT: {
			 return SortHandler.executeSort(fileName, keyFieldsList, listTask);
		}
		case HELP:{
			return HelpCommandListHandler.getCommandHelp(keyFieldsList);
		}
		case UPDATE: {
			return UpdateHandler
					.executeUpdate(fileName, keyFieldsList, listTask);
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
