package logic;

//import java.util.ArrayList;
import java.util.Map;

import utility.CommandType;
import utility.MessageList;

//import commonUsage.KeyFieldPair;

/**
 * 
 * @author Baoyi
 * 
 */
public class HelpCommandListHandler {

	public static String getCommandHelp(Map<String, String> keyFieldsList) {
		if (keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (keyFieldsList.size() != 1) {
			return MessageList.MESSAGE_INVAILD;
		}

		if (keyFieldsList.get(CommandType.Command_Types.HELP.name()).isEmpty()) {
			return MessageList.MESSAGE_HELP;
		}

		keyFieldsList.remove(CommandType.Command_Types.HELP.name());// remove
																	// this pair

		for (String key : keyFieldsList.keySet()) {
			CommandType.Command_Types getType = CommandType.getType(key
					.split(" "));

			return getCommandType(getType);
		}
		return "";
	}

	/**
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
			return MessageList.MESSAGE_INVAILD;
		}
		}
	}
}