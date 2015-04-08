//@A0112502A
package logic;

import utility.CommandType.Command_Types;
import utility.MessageList;
import utility.CommandType;

/**
 * This class is doing the hint operation, which act as the command assistance to the user.
 * @author SHUNA
 *
 */

public class HintHandler {

	/**
	 * This method will execute the hint
	 * 
	 * @param userCmd
	 * @return
	 */
	public static String executeHint(String userCmd) {
		return getMessageTyping(userCmd);
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

		String[] userCmdList = userCmd.split(" ");
	
		for (Command_Types aCmd : CommandType.Command_Types.values()) {
			if (aCmd.name().startsWith(userCmdList[0].toUpperCase())) {
				listOfHint += HelpCommandListHandler.getCommandType(aCmd) + "\n";
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
