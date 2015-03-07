
public class Menu {
	
	public static String commandExecution(String fileName,String input) {
		CommandType.Command_Types cmd = CommandType.getType(input.split(" "));

		switch (cmd) {
		
		case ADD: {
			return AddHandler.executeAdd(fileName,input,listTask);
		}
		case DISPLAY: {
			return DisplayHandler.executeDisplay(fileName, input, listTask);
		}
		case DELETE: {
			return DeleteHandler.delete(fileName, input, listTask);
		}
		case CLEAR: {
			return ClearHandler.executeClear(fileName, input, listTask);
		}
		case INVALID: {
			return "Invalid Command!";
		}
		case SEARCH: {
			return ExecuteHandler.executeSearch(input);
		}
		case SORT: {
			return SortHandler.executeSort(fileName);
		}
		case EXIT: {
			System.exit(0);
			break;
		}
		default: {

			return "Command Unrecognized!";
		}
		}

		return "No command entered";
	}

}

