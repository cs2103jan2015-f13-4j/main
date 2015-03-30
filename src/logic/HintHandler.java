package logic;

import utility.MessageList;
import utility.CommandType;
import utility.CommandListHelp;

public class HintHandler {

	/**
	 * This method will execute the hint
	 * 
	 * @param userCmd
	 * @return
	 */
	public static String executeHint(String userCmd) {

		getMessageTyping(userCmd);

		return null;
	}

	/**
	 * This method will get what the user typed
	 * 
	 * @param userCmd
	 * @return
	 */
	public static String getMessageTyping(String userCmd) {

		String listOfHint = "";

		if (isCommandInvalid(userCmd)) {
			return MessageList.MESSAGE_HINT_INVALID;
		}

		if (userCmd.length() < 1) {
			return MessageList.MESSAGE_HELP;
		}

		for (CommandType.Command_Types aCmd : CommandType.Command_Types
				.values()) {
			if (aCmd.name().startsWith(userCmd.toUpperCase())) {
				listOfHint += getCommandType(aCmd) + "\n";
			}
		}

		return listOfHint;
	}

	/**
	 * This method will get the command type
	 * 
	 * @param aCmd
	 * @return
	 */
	private static String getCommandType(CommandType.Command_Types aCmd) {
		switch (aCmd) {

		case ADD: {
			return MessageList.MESSAGE_ADD_HELP;
		}
		case DISPLAY: {
			return MessageList.MESSAGE_DISPLAY_HELP;
		}
		case DELETE: {
			return MessageList.MESSAGE_DELETE_HELP;
		}
		case CLEAR: {
			return MessageList.MESSAGE_CLEAR_HELP;
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
		default: {
			return MessageList.MESSAGE_INVAILD;
		}
		}
	}

	/**
	 * This method is use to check if the command is valid or invalid
	 * 
	 * @param userCmd
	 * @return
	 */
	public static boolean isCommandInvalid(String userCmd) {

		boolean isCommandNull = (userCmd == null);
		boolean isCommandEmpty = !isCommandNull && userCmd.trim().isEmpty();
		boolean isCommandInvalid = isCommandNull || isCommandEmpty;

		return isCommandInvalid;
	}
}
