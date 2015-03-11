import java.util.ArrayList;

public class Menu {
	private static ArrayList<Task> listTask;
	private static int lastUnusedIndex = 1;	//stub
	private static String fileName = "tasklist.txt";
	private static String lastUsedIndexFileName = "lastUsedIndex.txt";
	
	public Menu() {
		listTask = new ArrayList<Task>();
	}
	
	public void retrieveTasksAndLastUsedIndex(IndicatorMessagePair msgPair){
		//IndicatorMessagePair msgPair = new IndicatorMessagePair();
		FileHandler.checkAndLoadTaskFile(fileName, listTask, msgPair);
		if(!msgPair.isTrue){
			return;
		}
		lastUnusedIndex = FileHandler.checkAndLoadLastTaskIndexFile(lastUsedIndexFileName, msgPair);
		if(!msgPair.isTrue){
			return;
		}
	}

	public String commandExecution(String input) {
		ArrayList<KeyParamPair> keyParamList = new ArrayList<KeyParamPair>();
		CommandType.Command_Types cmd = InputStringParser.processString(input,
				keyParamList);
		

		switch (cmd) {

		case ADD: {
			return AddHandler.executeAdd(fileName, keyParamList, listTask, lastUnusedIndex);
		}
		case DISPLAY: {
			return DisplayHandler.executeDisplay(fileName, keyParamList, listTask);
		}
		case DELETE: {
			return DeleteHandler.executeDelete(fileName, keyParamList, listTask);
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
