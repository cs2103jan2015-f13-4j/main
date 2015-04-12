//@author A0112501E 
package logic;

import java.util.Map;

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

/**
 * This class add in the task and date also allow adding in the task, time and
 * date.
 * 
 */
public class AddHandler {
	private static final int RESTRICT_KEYWORD = 1;

	/**
	 * Declaration for Logger
	 */
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
	 * This method is to check if the add command input is valid and if the
	 * storage exist.
	 * 
	 * @param keyFieldsList
	 * @param smtData
	 * @return
	 */
	public static String executeAdd(Map<String, String> keyFieldsList,
			Data smtData) {

		if (keyFieldsList == null) {
			assert false : "The mapped object is null";
		}

		if (smtData == null) {
			assert false : "The data object is null";
		}

		if (keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		return addContents(keyFieldsList, smtData);
	}

	/**
	 * This method is to add in the content added by the user
	 * 
	 * @param keyFieldsList
	 * @param smtData
	 * @return
	 */
	private static String addContents(Map<String, String> keyFieldsList,
			Data smtData) {
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		KeywordType.List_Keywords getKey;
		Task newTask = new Task();

		int lastUnUsedIndex = loadLastUsedIndex(smtData);
		indicMsg = new IndicatorMessagePair();

		// This restrict number of keyword to 1 per task.
		if (restrictOnlyUnqiueKeyWord(keyFieldsList) > RESTRICT_KEYWORD) {
			return MessageList.MESSAGE_NO_WEEKLY_DEADLINE;
		}
		// Get new index for a new task added.
		newTask.setTaskId(lastUnUsedIndex);
		indicMsg = addTaskDesc(newTask, lastUnUsedIndex, keyFieldsList);

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		// first check if command contains from and to keywords and process them
		//
		if (checkFromTimeToTimeBothExist(keyFieldsList)) {
			indicMsg = processBothTimes(keyFieldsList, newTask);
		}

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		// remove the add key pair as it has already been saved
		keyFieldsList.remove(CommandType.Command_Types.ADD.name());

		for (String key : keyFieldsList.keySet()) {
			getKey = KeywordType.getKeyword(key);
			switch (getKey) {
			case BY:
			case ON: {
				indicMsg = addTaskByWhen(smtData, newTask, lastUnUsedIndex,
						keyFieldsList, getKey);
				break;
			}

			case EVERY: {
				indicMsg = addRecurringWeek(newTask, lastUnUsedIndex,
						keyFieldsList);
				break;

			}
			default: {
				return String
						.format(MessageList.MESSAGE_INVALID_COMMAND, "Add");
			}
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

		// To log add operation
		taskLogger.log(Level.INFO, "Task Added");
		CacheCommandsHandler.newHistory(smtData);
		return String.format(MessageList.MESSAGE_ADDED, newTask.toString());
	}

	/**
	 * This method is to generate the date base on the keyword To check if the
	 * date is included as well as to check if the date chosen to block is
	 * already occupied.
	 * 
	 * @param newTask
	 * @param index
	 * @param keyFieldsList
	 * @return
	 */
	private static IndicatorMessagePair addTaskByWhen(Data smtData,
			Task newTask, int index, Map<String, String> keyFieldsList,
			KeywordType.List_Keywords keyword) {

		String errorMessage = "";
		IndicatorMessagePair indicMsg = checkEmptyKeyFieldsList(keyFieldsList,
				keyword.name(), MessageList.MESSAGE_NO_DATE_GIVEN);
		if (!indicMsg.isTrue()) {
			return indicMsg;
		}
		// checkEmptyKeyFieldsList(keyFieldsList, keyword.name(),
		// MessageList.MESSAGE_NO_DATE_GIVEN);

		// generate End date depending on the keyword
		DateTime endDate = DateTimeParser.generateDate(keyFieldsList
				.get(keyword.name()));
		if (endDate == null) {
			errorMessage = DateTimeParser.getDateFormatError(keyFieldsList
					.get(keyword.name()));
			return new IndicatorMessagePair(false, errorMessage);
		}

		if (clashWithBlockDate(smtData, endDate)) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_CONFLICT_WITH_BLOCKED_DATE, "End"));
		}

		DateTime newStartDateTime = null;
		DateTime newEndDateTime = null;

		if (newTask.getTaskStartDateTime() != null) {
			newStartDateTime = new DateTime(endDate.getYear(),
					endDate.getMonthOfYear(), endDate.getDayOfMonth(), newTask
							.getTaskStartDateTime().getHourOfDay(), newTask
							.getTaskStartDateTime().getMinuteOfHour());
		}

		if (newTask.getTaskEndDateTime() != null) {
			newEndDateTime = new DateTime(endDate.getYear(),
					endDate.getMonthOfYear(), endDate.getDayOfMonth(), newTask
							.getTaskEndDateTime().getHourOfDay(), newTask
							.getTaskEndDateTime().getMinuteOfHour());
		}

		if (newEndDateTime == null) {
			newEndDateTime = endDate;
		}

		newTask.setTaskStartDateTime(newStartDateTime);
		newTask.setTaskEndDateTime(newEndDateTime);
		newTask.setDeadLineStatus(true);
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
			int index, Map<String, String> keyFieldsList) {
		DateTime weeklyDate = DateTimeParser.generateDate(keyFieldsList
				.get(KeywordType.List_Keywords.EVERY.name()));

		if (weeklyDate == null) {
			return new IndicatorMessagePair(false,
					String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT));
		}

		DateTime newStartTime = null;
		DateTime newEndTime = null;

		if (newTask.getTaskStartDateTime() != null) {
			newStartTime = new DateTime(weeklyDate.getYear(),
					weeklyDate.getMonthOfYear(), weeklyDate.getDayOfMonth(),
					newTask.getTaskStartDateTime().getHourOfDay(), newTask
							.getTaskStartDateTime().getMinuteOfHour());
		}

		if (newTask.getTaskEndDateTime() != null) {
			newEndTime = new DateTime(weeklyDate.getYear(),
					weeklyDate.getMonthOfYear(), weeklyDate.getDayOfMonth(),
					newTask.getTaskEndDateTime().getHourOfDay(), newTask
							.getTaskEndDateTime().getMinuteOfHour());
		}

		newTask.setTaskStartDateTime(newStartTime);
		newTask.setTaskEndDateTime(newEndTime);
		newTask.setWeeklyDay(keyFieldsList.get(KeywordType.List_Keywords.EVERY
				.name()));
		newTask.setDeadLineStatus(false);
		return new IndicatorMessagePair(true, "");
	}

	/**
	 * This method is to check the the add content
	 * 
	 * @param newTask
	 * @param index
	 * @param keyFieldsList
	 * @return
	 */
	private static IndicatorMessagePair addTaskDesc(Task newTask, int index,
			Map<String, String> keyFieldsList) {
		IndicatorMessagePair indicMsg = checkEmptyKeyFieldsList(keyFieldsList,
				CommandType.Command_Types.ADD.name(),
				String.format(MessageList.MESSAGE_ADD_NO_DESCRIPTION, "add"));

		if (!indicMsg.isTrue()) {
			return indicMsg;
		}

		newTask.setTaskDescription(keyFieldsList
				.get(CommandType.Command_Types.ADD.name()));
		return new IndicatorMessagePair(true, MessageList.MESSAGE_NO_DATE_GIVEN);
	}

	// This is to check if the keyword is empty or null
	private static IndicatorMessagePair checkEmptyKeyFieldsList(
			Map<String, String> keyFieldsList, String keyWord, String message) {
		if (!keyFieldsList.containsKey(keyWord)) {
			return new IndicatorMessagePair(false, message);
		}
		if (keyFieldsList.get(keyWord) == null
				|| keyFieldsList.get(keyWord).isEmpty()) {
			return new IndicatorMessagePair(false, message);
		}
		return new IndicatorMessagePair(true, String.format("", "Add"));
	}

	/**
	 * determineBothTimes method checks if both times are valid and proceed to
	 * update if possible
	 * 
	 * @param keyFieldsList
	 *            the input command
	 * @param index
	 *            the current task location
	 * @param smtData
	 *            the data object which contains the whole data
	 * @return IndicatorMessagePair which states whether the times can be update
	 */
	private static IndicatorMessagePair processBothTimes(
			Map<String, String> keyFieldsList, Task newTask) {

		if (!checkFromTimeToTimeBothField(keyFieldsList)) {
			return new IndicatorMessagePair(false,
					String.format(MessageList.MESSAGE_TIME_SLOT_EMPTY));
		}

		DateTime startTime = DateTimeParser.generateTime(keyFieldsList
				.get(KeywordType.List_Keywords.FROM.name()));
		DateTime endTime = DateTimeParser.generateTime(keyFieldsList
				.get(KeywordType.List_Keywords.TO.name()));
		if (!checkFromTimeToTimeBothValid(startTime, endTime)) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_TIME_MISMATCHED, startTime, endTime));
		}

		DateTime newDateStartTime = new DateTime(DateTime.now().getYear(),
				DateTime.now().getMonthOfYear(),
				DateTime.now().getDayOfMonth(), startTime.getHourOfDay(),
				startTime.getMinuteOfHour());
		DateTime newDateEndTime = new DateTime(DateTime.now().getYear(),
				DateTime.now().getMonthOfYear(),
				DateTime.now().getDayOfMonth(), endTime.getHourOfDay(),
				endTime.getMinuteOfHour());

		updateBothTimes(newTask, newDateStartTime, newDateEndTime);
		keyFieldsList.remove(KeywordType.List_Keywords.FROM.name());
		keyFieldsList.remove(KeywordType.List_Keywords.TO.name());
		return new IndicatorMessagePair(true, "");

	}

	/**
	 * updateBothTimes method will update both times to a task
	 * 
	 * @param index
	 *            the index where the existing task is
	 * @param smtData
	 *            the data object which contains all the data
	 * @param startTime
	 *            the start time to update
	 * @param endTime
	 *            the end time to update
	 */
	private static void updateBothTimes(Task newTask, DateTime startTime,
			DateTime endTime) {
		newTask.setTaskStartDateTime(startTime);
		newTask.setTaskEndDateTime(endTime);
	}

	/**
	 * checkFromTimeToTimeBothExist method checks if both from and to keyword
	 * exists
	 * 
	 * @param keyFieldsList
	 *            the command input
	 * @return true if it contains, else false
	 */
	private static boolean checkFromTimeToTimeBothExist(
			Map<String, String> keyFieldsList) {
		if (keyFieldsList.containsKey(KeywordType.List_Keywords.FROM.name())
				&& keyFieldsList.containsKey(KeywordType.List_Keywords.TO
						.name())) {
			return true;
		}
		return false;
	}

	/**
	 * checkFromTimeToTimeBothField method checks if it receives from and to
	 * time
	 * 
	 * @param keyFieldsList
	 *            the command input
	 * @return true if both fields have some info, else false
	 */
	private static boolean checkFromTimeToTimeBothField(
			Map<String, String> keyFieldsList) {
		if (keyFieldsList.get(KeywordType.List_Keywords.FROM.name()) != null
				&& keyFieldsList.get(KeywordType.List_Keywords.FROM.name())
						.isEmpty()
				&& keyFieldsList.get(KeywordType.List_Keywords.TO.name()) != null
				&& keyFieldsList.get(KeywordType.List_Keywords.TO.name())
						.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * This method is to count the number of keywords appeared in a single task
	 * 
	 * @param keyFieldsList
	 * @return
	 */

	private static int restrictOnlyUnqiueKeyWord(
			Map<String, String> keyFieldsList) {
		int count = 0;
		if (keyFieldsList.containsKey(KeywordType.List_Keywords.ON.name())) {
			count++;
		}
		if (keyFieldsList.containsKey(KeywordType.List_Keywords.BY.name())) {
			count++;
		}
		if (keyFieldsList.containsKey(KeywordType.List_Keywords.EVERY.name())) {
			count++;
		}
		return count;
	}

	/**
	 * checkFromTimeToTimeBothValid method checks if the time from both sides
	 * are valid to proceed
	 * 
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @return true if possible, else false
	 */
	private static boolean checkFromTimeToTimeBothValid(DateTime startTime,
			DateTime endTime) {
		if (startTime == null) {
			return false;
		}

		if (endTime == null) {
			return false;
		}

		if (startTime.isAfter(endTime)) {
			return false;
		}

		return true;
	}

	/**
	 * This method is to check the block date input with the date blocked in the
	 * storage to check if it clash
	 * 
	 * @param smtData
	 * @param endDate
	 * @return
	 */
	private static boolean clashWithBlockDate(Data smtData, DateTime endDate) {
		for (int i = 0; i < smtData.getBlockedDateTimeList().size(); i++) {
			if (smtData.getBlockedDateTimeList().get(i).toLocalDate()
					.equals(endDate.toLocalDate())) {
				return true;
			}
		}
		return false;
	}
}
