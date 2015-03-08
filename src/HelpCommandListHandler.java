
public class HelpCommandListHandler {

	public static String getCommandHelp(String helpCommand) {
		CommandListHelp.CommandListHelp_Type commandList = CommandListHelp
				.getType(helpCommand.split(" "));

		if (helpCommand.equalsIgnoreCase("help")) {
			return MessageList.MESSAGE_HELP;
		}
		switch (commandList) {

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
}