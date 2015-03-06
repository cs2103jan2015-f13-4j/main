
public class CommandType {
	public enum Command_Types {
		ADD, DELETE, DISPLAY, CLEAR, EXIT, UPDATE, INVALID, SEARCH, SORT
	}
	
	public static Command_Types getType(String[] commandTypeString){
		if (commandTypeString == null || commandTypeString.length == 0) {
			return Command_Types.INVALID;
		}

		if (commandTypeString[0].equalsIgnoreCase("add")) {
			return Command_Types.ADD;
		} else if (commandTypeString[0].equalsIgnoreCase("delete")) {
			return Command_Types.DELETE;
		} else if (commandTypeString[0].equalsIgnoreCase("display")) {
			return Command_Types.DISPLAY;
		} else if (commandTypeString[0].equalsIgnoreCase("update")) {
			return Command_Types.UPDATE;
		} else if (commandTypeString[0].equalsIgnoreCase("clear")) {
			return Command_Types.CLEAR;
		} else if (commandTypeString[0].equalsIgnoreCase("exit")) {
			return Command_Types.EXIT;
		} else if (commandTypeString[0].equalsIgnoreCase("search")) {
			return Command_Types.SEARCH;
		} else if (commandTypeString[0].equalsIgnoreCase("sort")) {
			return Command_Types.SORT;
		} else {
			return Command_Types.INVALID;
		}
	}
	
	
}
