//@author A0112501E
package logic;

import java.util.Map;

import utility.CommandType;
import utility.MessageList;

//This class is use to print out the hint for hint handler, when user enter a 
//specific character or command. It also use to print out the Help command list
public class HelpCommandListHandler {

	// Declare global variable
	private static final int KEYWORD = 1;

	/**
	 * This method is to check if there is a keyword. If there is only help but
	 * no keyword given, will display help commandlist
	 */
	public static String getCommandHelp(Map<String, String> keyFieldsList) {
		if (keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (keyFieldsList.size() != KEYWORD) {
			return MessageList.MESSAGE_INVALID;
		}

		if (keyFieldsList.get(CommandType.Command_Types.HELP.name()).isEmpty()) {
			return MessageList.MESSAGE_HELP;
		}

		keyFieldsList.remove(CommandType.Command_Types.HELP.name());

		for (String key : keyFieldsList.keySet()) {
			CommandType.Command_Types getType = CommandType.getType(key
					.split(" "));

			return getCommandType(getType);
		}
		return "";
	}

	/**
	 * This method is to print out the help message for the hintHandler
	 * accordingly to the keyword input.
	 * 
	 * @param getType
	 * @return
	 */
	public static String getCommandType(CommandType.Command_Types getType) {
		switch (getType) {

		case ADD: {
			return MessageList.MESSAGE_ADD_HELP;
		}
		case DISPLAY: {
			return MessageList.MESSAGE_DISPLAY_HELP;
		}
		case DELETE: {
			return MessageList.MESSAGE_DELETE_HELP;
		}
		case SEARCH: {
			return MessageList.MESSAGE_SEARCH_HELP;
		}
		case SORT: {
			return MessageList.MESSAGE_SORT_HELP;
		}
		case UPDATE: {
			return MessageList.MESSAGE_UPDATE_HELP;
		}
		case EXIT: {
			return MessageList.MESSAGE_EXIT_HELP;
		}
		case UNDO: {
			return MessageList.MESSAGE_UNDO_HELP;
		}
		case REDO: {
			return MessageList.MESSAGE_REDO_HELP;
		}
		case BLOCK: {
			return MessageList.MESSAGE_BLOCK_HELP;
		}
		case UNBLOCK: {
			return MessageList.MESSAGE_UNBLOCK_HELP;
		}
		default: {
			return MessageList.MESSAGE_INVALID;
		}
		}
	}
}