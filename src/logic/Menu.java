package logic;

import java.util.HashMap;

import parser.CommandStringParser;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.MessageList;
//import commonUsage.KeyFieldPair;
import data.Data;

public class Menu {
	private Data smtData;
	private static Menu onlyMenu;

	private Menu() {
		smtData = new Data();
	}

	public static Menu getInstance() {
		if (onlyMenu == null) {
			onlyMenu = new Menu();
		}
		return onlyMenu;
	}

	public String commandExecution(String input) {
		HashMap<String, String> keyFieldsList = new HashMap<String, String>();
		CommandType.Command_Types cmd = CommandStringParser.processString(
				input, keyFieldsList);

		switch (cmd) {

		case ADD: {
			return AddHandler.executeAdd(keyFieldsList, smtData);
		}
		case DISPLAY: {
			return DisplayHandler.executeDisplay(keyFieldsList, smtData);
		}
		case DELETE: {
			return DeleteHandler.executeDelete(keyFieldsList, smtData);
		}
		case CLEAR: {
			// return ClearHandler.executeClear(fileName, input, listTask);
		}
		case INVALID: {
			return MessageList.MESSAGE_INVAILD;
		}
		case SEARCH: {
			return SearchHandler.executeSearch(keyFieldsList, smtData);
		}
		case SORT: {
			return SortHandler.executeSort(keyFieldsList, smtData);
		}
		case HELP: {
			return HelpCommandListHandler.getCommandHelp(keyFieldsList);
		}
		case UPDATE: {
			return UpdateHandler.executeUpdate(keyFieldsList, smtData);
		}
		// case UNDO: {
		// return CacheCommandsHandler.executeUndo(fileName, listTask);
		// }
		// case REDO:{
		// return CacheCommandsHandler.executeRedo(fileName, listTask);

		// }
		case EXIT: {
			System.exit(0);
			break;
		}
		default: {

			return MessageList.MESSAGE_MENU_COMMAND_UNRECOGNISED;
		}
		}

		return MessageList.MESSAGE_MENU_NO_COMMAND;
	}

	public IndicatorMessagePair setUp() {
		return smtData.loadEveryThingFromFile();
	}
}
