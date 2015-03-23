package logic;

import java.util.HashMap;

import data.Data;
import data.Task;

import org.joda.time.DateTime;

import java.util.logging.Level;
import java.util.logging.Logger;

import parser.DateParser;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import utility.TaskLogging;

public class AddHandler {

	private static Logger taskLogger = TaskLogging.getInstance();

	/**
	 * This method retrieve the last unused index and return that index back
	 * 
	 * @param smtData
	 * @return the last unused index
	 */
	private static int loadLastUsedIndex(Data smtData) {
		return smtData.getLastUnUsedIndex();
	}

	public static String executeAdd(HashMap<String, String> keyFieldsList,
			Data smtData) {
		if (keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (smtData == null) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}

		return addContents(keyFieldsList, smtData);

	}

	private static String addContents(HashMap<String, String> keyFieldsList,
			Data smtData) {
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		KeywordType.List_Keywords getKey;
		Task newTask = new Task();

		int lastUnUsedIndex = loadLastUsedIndex(smtData);
		indicMsg = new IndicatorMessagePair();

		newTask.setTaskId(lastUnUsedIndex);
		indicMsg = addTaskDesc(newTask, lastUnUsedIndex, keyFieldsList);// field
																		// is
																		// here
																		// so no
																		// need
																		// to do
																		// in
																		// switch
																		// case
		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		keyFieldsList.remove(CommandType.Command_Types.ADD.name());// remove the
																	// add key
																	// pair as
																	// it has
																	// save the
																	// task desc

		for (String key : keyFieldsList.keySet()) {
			getKey = KeywordType.getKeyword(key);
			switch (getKey) {
			case BY:
				indicMsg = addTaskByWhen(newTask, lastUnUsedIndex,
						keyFieldsList);
				break;
			// case EVERY:
			// indicMsg =
			default:
				return String
						.format(MessageList.MESSAGE_INVALID_COMMAND, "Add");
			}

			if (!indicMsg.isTrue()) {
				return indicMsg.getMessage();
			}
		}
		indicMsg = smtData.addATaskToList(newTask);
		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		lastUnUsedIndex++;

		smtData.setLastUnUsedIndex(lastUnUsedIndex);
		indicMsg = smtData.writeTaskListToFile();

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		indicMsg = smtData.writeLastUnUsedIndexToFile();
		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		taskLogger.log(Level.INFO, "Task Added");
		return MessageList.MESSAGE_ADDED;
	}

	private static IndicatorMessagePair addTaskByWhen(Task newTask, int index,
			HashMap<String, String> keyFieldsList) {
		if (!keyFieldsList.containsKey(KeywordType.List_Keywords.BY.name())) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		if (keyFieldsList.get(KeywordType.List_Keywords.BY.name()) == null
				|| keyFieldsList.get(KeywordType.List_Keywords.BY.name())
						.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateTime endDate = DateParser.generateDate(keyFieldsList
				.get(KeywordType.List_Keywords.BY.name()));
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}
		newTask.setTaskEndDateTime(endDate);
		return new IndicatorMessagePair(true, "");
	}

	private static IndicatorMessagePair addTaskDesc(Task newTask, int index,
			HashMap<String, String> keyFieldsList) {
		if (!keyFieldsList.containsKey(CommandType.Command_Types.ADD.name())) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		if (keyFieldsList.get(CommandType.Command_Types.ADD.name()) == null
				|| keyFieldsList.get(CommandType.Command_Types.ADD.name())
						.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		newTask.setTaskDescription(keyFieldsList
				.get(CommandType.Command_Types.ADD.name()));
		return new IndicatorMessagePair(true, MessageList.MESSAGE_NO_DATE_GIVEN);
	}

}
