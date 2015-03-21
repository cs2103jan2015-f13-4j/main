import java.util.ArrayList;

/**
 * 
 * @author Baoyi
 *
 */
public class HelpCommandListHandler {

	public static String getCommandHelp(ArrayList<KeyFieldPair> keyFieldsList) {
		if (keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}
		
		if(keyFieldsList.size() != 1){
			return MessageList.MESSAGE_INVAILD;
		}

		if (keyFieldsList.get(0).getFields().isEmpty()) {
			return MessageList.MESSAGE_HELP;
		}
		
		CommandType.Command_Types getType = CommandType.getType(keyFieldsList.get(0).getFields().split(" "));
		
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