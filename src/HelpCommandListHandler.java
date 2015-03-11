import java.util.ArrayList;


public class HelpCommandListHandler {

	public static String getCommandHelp(ArrayList<KeyParamPair> keyParamList) {
		if (keyParamList == null || keyParamList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}
		
		if(keyParamList.size() != 1){
			return MessageList.MESSAGE_INVAILD;
		}

		if (keyParamList.get(0).getParam().isEmpty()) {
			return MessageList.MESSAGE_HELP;
		}
		
		CommandType.Command_Types getType = CommandType.getType(keyParamList.get(0).getParam().split(" "));
		
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