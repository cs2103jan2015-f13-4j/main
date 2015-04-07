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
		case "a":
			return Command_Types.ADD;
		case "delete": 
		case "del":
			return Command_Types.DELETE;
		case "display": 
		case "dis":
			return Command_Types.DISPLAY;
		case "update": 
		case "u":
			return Command_Types.UPDATE;
		case "exit":
			return Command_Types.EXIT;
		case "search":
		case "sh":
			return Command_Types.SEARCH;
		case "sort": 
		case "st":
			return Command_Types.SORT;
		case "help":
		case "h":
			return Command_Types.HELP;
		case "undo":
		case "ud":
			return Command_Types.UNDO;
		case "redo":
		case "rd":
			return Command_Types.REDO;
		case "block":
		case "b": 
			return Command_Types.BLOCK;
		case "unblock":
		case "ub": 
			return Command_Types.UNBLOCK;
		default: 
			return Command_Types.INVALID;
		}
	}

}
