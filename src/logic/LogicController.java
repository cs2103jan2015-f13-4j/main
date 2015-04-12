//@author A0112501E
package logic;

import java.util.Map;
import java.util.TreeMap;

import parser.CommandStringParser;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.MessageList;
import data.Data;

public class LogicController {
	private Data smtData;
	private static LogicController onlyMenu;

	private LogicController() {
		smtData = new Data();
	}

	public static LogicController getInstance() {
		if (onlyMenu == null) {
			onlyMenu = new LogicController();
		}
		return onlyMenu;
	}

	public String commandExecution(String input) {
		Map<String, String> keyFieldsList = new TreeMap<String, String>(
				String.CASE_INSENSITIVE_ORDER);
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
		case INVALID: {
			return MessageList.MESSAGE_INVALID;
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
		case UNDO: {
			return CacheCommandsHandler.executeUndo(smtData);
		}
		case REDO: {
			return CacheCommandsHandler.executeRedo(smtData);

		}
		case BLOCK: {
			return BlockDateHandler.executeBlockOrUnblock(keyFieldsList,
					cmd.name(), smtData);
		}
		case UNBLOCK: {
			return BlockDateHandler.executeBlockOrUnblock(keyFieldsList,
					cmd.name(), smtData);
		}
		case EXIT: {
			LockApp.unLockApp();
			System.exit(0);
			break;
		}
		default: {

			return MessageList.MESSAGE_LOGICCONTROLLER_COMMAND_UNRECOGNISED;
		}
		}

		return MessageList.MESSAGE_LOGICCONTROLLER_NO_COMMAND;
	}

	public IndicatorMessagePair setUp() {
		IndicatorMessagePair indicMsg = smtData.loadEveryThingFromFile();
		CacheCommandsHandler.newHistory(smtData);
		return indicMsg;
	}

	public String getHint(String userCmd) {
		return HintHandler.executeHint(userCmd);
	}
}
