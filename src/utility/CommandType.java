package utility;
public class CommandType {
	public enum Command_Types {
		ADD, DELETE, DISPLAY, CLEAR, EXIT, UPDATE, INVALID, SEARCH, SORT, HELP, UNDO, REDO, BLOCK, UNBLOCK
	}

	public static Command_Types getType(String[] commandTypeString) {

		if (commandTypeString == null) {
			return Command_Types.INVALID;
		}
		
		switch (commandTypeString[0].toLowerCase()) {
		case "add": 
			return Command_Types.ADD;
		case "delete": 
			return Command_Types.DELETE;
		case "display": 
			return Command_Types.DISPLAY;
		case "update": 
			return Command_Types.UPDATE;
		case "clear": 
			return Command_Types.CLEAR;
		case "exit": 
			return Command_Types.EXIT;
		case "search": 
			return Command_Types.SEARCH;
		case "sort": 
			return Command_Types.SORT;
		case "help":
			return Command_Types.HELP;
		case "undo": 
			return Command_Types.UNDO;
		case "redo":
			return Command_Types.REDO;
		case "block": 
			return Command_Types.BLOCK;
		case "unblock":
			return Command_Types.UNBLOCK;
		default: 
			return Command_Types.INVALID;
		}
	}

}
