
public class Menu {

	public static String commandExecution(String fileName,String input) {
		CommandType.Command_Types cmd = CommandType.getType(input.split(" "));

		switch (cmd) {
		
		case ADD: {
			return AddHandler.executeAdd(fileName,input,listTask);
		}
		case DISPLAY: {
			return executeDisplay(fileName, input);
		}
		case DELETE: {
			return executeDelete(fileName,input);
		}
		case CLEAR: {
			return executeClear(fileName, input);
		}
		case INVALID: {
			return "Invalid Command!";
		}
		case SEARCH: {
			return executeSearch(input);
		}
		case SORT: {
			return executeSort(fileName);
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



}
