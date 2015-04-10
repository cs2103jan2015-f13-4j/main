//@A0112502A
package logic;

import utility.CommandType.Command_Types;
import utility.MessageList;
import utility.CommandType;

/**
 * This class will be executing the hint operation, which will act as the command assistance to the user.
 *
 */

public class HintHandler {

	/**
	 * This method will execute the hint
	 * @param userCmd command that user have typed in so far
	 * @return 
	 */
	public static String executeHint(String userCmd) {
		return getMessageTyping(userCmd);
	}

	/**
	 * This method will get what the user has typed in so far
	 * @param userCmd
	 * @return
	 */
	public static String getMessageTyping(String userCmd) {

		String listOfHint = "";
		
		// if command entered by the user is null, do assertion
		if(userCmd == null){
			assert false : "No command has been entered";
		}

		// if there is no command entered by the user, return invalid message
		if (isCommandInvalid(userCmd)) {
			return MessageList.MESSAGE_HINT_INVALID;
		}

		String[] userCmdList = userCmd.split(" ");
	
		// check if commands entered by the user starts with any of the command type
		for (Command_Types aCmd : CommandType.Command_Types.values()) {
			if (aCmd.name().startsWith(userCmdList[0].toUpperCase())) {
				listOfHint += HelpCommandListHandler.getCommandType(aCmd) + "\n";
			}
		}

		return listOfHint;
	}

	/**
	 * This method is use to check if the command is valid or invalid
	 * @param userCmd command that user have typed in so far
	 * @return true if command is invalid, otherwise return false
	 */
	public static boolean isCommandInvalid(String userCmd) {

		boolean isCommandNull = (userCmd == null);
		boolean isCommandEmpty = !isCommandNull && userCmd.trim().isEmpty();
		boolean isCommandInvalid = isCommandNull || isCommandEmpty;

		return isCommandInvalid;
	}
}