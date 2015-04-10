//@A0112502A
package utility;

/**
 * This class is use to indicate the command types
 *
 */

public class CommandType {
	public enum Command_Types {
		ADD, DELETE, DISPLAY, EXIT, UPDATE, INVALID, SEARCH, SORT, HELP, UNDO, REDO, BLOCK, UNBLOCK
	}

	/**
	 * This method will check the commands input by the user and go to the necessary case
	 * @param commandTypeString is the command type that user has input 
	 * @return the corresponding command type with the commandTypeString received
	 */
	public static Command_Types getType(String[] commandTypeString) {

		if (commandTypeString == null) {
			assert false : "Invalid command types entered";
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
		case "up":
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
