package logic;

import utility.CommandType.Command_Types;
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

		for (Command_Types aCmd : CommandType.Command_Types.values()) {
			if (aCmd.name().startsWith(userCmd.toUpperCase())) {
				listOfHint += CommandListHelp.getType(aCmd.name().split("")) + "\n";
			}
		}

		return listOfHint;
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
