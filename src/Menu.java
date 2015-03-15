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
		ArrayList<KeyFieldPair> KeyFieldsList = new ArrayList<KeyFieldPair>();
		CommandType.Command_Types cmd = CommandStringParser.processString(input,
				KeyFieldsList);

		switch (cmd) {

		case ADD: {
			return AddHandler.executeAdd(fileName, lastUsedIndexFileName,
					KeyFieldsList, listTask);
		}
		case DISPLAY: {
			return DisplayHandler.executeDisplay(fileName, KeyFieldsList,
					listTask);
		}
		case DELETE: {
			return DeleteHandler
					.executeDelete(fileName, KeyFieldsList, listTask);
		}
		case CLEAR: {
			// return ClearHandler.executeClear(fileName, input, listTask);
		}
		case INVALID: {
			return MessageList.MESSAGE_INVAILD;
		}
		case SEARCH: {
			 //return SearchHandler.executeSearch(ArrayList<KeyFieldPair> KeyFieldsList,
						//ArrayList<Task> listTask);
		}
		case SORT: {
			 return SortHandler.executeSort(fileName, KeyFieldsList, listTask);
		}
		case HELP:{
			return HelpCommandListHandler.getCommandHelp(KeyFieldsList);
		}
		case UPDATE: {
			return UpdateHandler
					.executeUpdate(fileName, KeyFieldsList, listTask);
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
