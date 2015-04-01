package logic;

import java.util.HashMap;

import data.Data;
import data.Task;

import org.joda.time.DateTime;

import java.util.logging.Level;
import java.util.logging.Logger;

import parser.DateTimeParser;
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
	
/**
 * This method is to check if the add command input is valid and if the storage exist.
 * @param keyFieldsList
 * @param smtData
 * @return
 */
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

	/**
	 * This method is to add in the content added by the user
	 * @param keyFieldsList
	 * @param smtData
	 * @return
	 */
	private static String addContents(HashMap<String, String> keyFieldsList,
			Data smtData) {
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		KeywordType.List_Keywords getKey;
		Task newTask = new Task();

		int lastUnUsedIndex = loadLastUsedIndex(smtData);
		indicMsg = new IndicatorMessagePair();

		newTask.setTaskId(lastUnUsedIndex);
		indicMsg = addTaskDesc(newTask, lastUnUsedIndex, keyFieldsList);
		// field is here so no need to do in switch case

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		keyFieldsList.remove(CommandType.Command_Types.ADD.name());
		// remove the add key pair as it has already been saved to the taskDesc

		for (String key : keyFieldsList.keySet()) {
			getKey = KeywordType.getKeyword(key);
			switch (getKey) {
			case BY:
				indicMsg = addTaskByWhen(newTask, lastUnUsedIndex,
						keyFieldsList);
				break;

			case EVERY:
				indicMsg = addRecurringWeek(newTask, lastUnUsedIndex,
						keyFieldsList);
				break;

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
		CacheCommandsHandler.newHistory(smtData);
		return MessageList.MESSAGE_ADDED;
	}

	/**
	 * This method is to check if the date is not included or the date format is
	 * incorrect
	 * 
	 * @param newTask
	 * @param index
	 * @param keyFieldsList
	 * @return
	 */
	private static IndicatorMessagePair addTaskByWhen(Task newTask, int index,
			HashMap<String, String> keyFieldsList) {
		
		checkEmptyKeyFieldsList(keyFieldsList, KeywordType.List_Keywords.BY.name(), MessageList.MESSAGE_NO_DATE_GIVEN);
		
		DateTime endDate = DateTimeParser.generateDate(keyFieldsList
				.get(KeywordType.List_Keywords.BY.name()));
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INVALID_ARGUMENT, "Add"));
		}
		newTask.setTaskEndDateTime(endDate);
		return new IndicatorMessagePair(true, "");
	}

	/**
	 * This method is for the use of adding recurring week feature
	 * 
	 * @param newTask
	 * @param index
	 * @param keyFieldsList
	 * @return
	 */
	private static IndicatorMessagePair addRecurringWeek(Task newTask,
			int index, HashMap<String, String> keyFieldsList) {
		DateTime weeklyDate = DateTimeParser.generateDate(keyFieldsList
				.get(KeywordType.List_Keywords.EVERY.name()));

		if (weeklyDate == null) {
			return new IndicatorMessagePair(false,
					String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT));
		}

		newTask.setTaskStartDateTime(null);
		newTask.setTaskEndDateTime(null);
		newTask.setWeeklyDay(KeywordType.List_Keywords.EVERY.name());
		return new IndicatorMessagePair(true, "");
	}
	/**
	 * This method is to check the add content....
	 * @param newTask
	 * @param index
	 * @param keyFieldsList
	 * @return
	 */
	private static IndicatorMessagePair addTaskDesc(Task newTask, int index,
			HashMap<String, String> keyFieldsList) {
		IndicatorMessagePair indicMsg = checkEmptyKeyFieldsList(keyFieldsList, CommandType.Command_Types.ADD.name(), String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Add"));
		
		if(!indicMsg.isTrue()){
			return indicMsg;
		}
	
		newTask.setTaskDescription(keyFieldsList
				.get(CommandType.Command_Types.ADD.name()));
		return new IndicatorMessagePair(true, MessageList.MESSAGE_NO_DATE_GIVEN);
	}
	
	private static IndicatorMessagePair checkEmptyKeyFieldsList(HashMap<String, String> keyFieldsList, String keyWord, String message) {
		if (!keyFieldsList.containsKey(keyWord)) {
			return new IndicatorMessagePair(false,
					message);
		}
		if (keyFieldsList.get(keyWord) == null
				|| keyFieldsList.get(keyWord)
						.isEmpty()) {
			return new IndicatorMessagePair(false,
					message);
		}
		return new IndicatorMessagePair(true, String.format("", "Add"));
	}

}
