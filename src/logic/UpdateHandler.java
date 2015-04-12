//@author A0111935L
package logic;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

import parser.DateTimeParser;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import utility.TaskLogging;
import data.Data;
import data.Task;

/**
 * This class updates the task with the specific task id
 */
public class UpdateHandler {

	private static Logger taskLogger = TaskLogging.getInstance();
	private static final int EXPECTED_UPDATE_KEY_VALUE_LENGTH = 1;
	private static final int KEYWORDS_MUST_NOT_APPEARED_COUNT = 1;
	private static final String EMPTY_STRING = "";
	private static final String WEEKLY_WORD = "Weekly";
	private static final String UPDATE_WORD = "Update";

	/**
	 * This method handle the update execution and update the contents to the
	 * ArrayList of tasks
	 * 
	 * @param keyPFieldsList
	 *            contains the list of keyword and the data it has
	 * @param smtData
	 *            contains the whole information including the task list
	 * @return message
	 */
	public static String executeUpdate(Map<String, String> keyFieldsList,
			Data smtData) {
		int minTaskListSize = 0;

		if (smtData == null) {
			assert false : "Data object is null";
		}

		if (keyFieldsList == null) {
			assert false : "Map object is null";
		}
		
		//for unit testing to throw in null object
		if (keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (!keyFieldsList.containsKey(CommandType.Command_Types.UPDATE.name())) {
			assert false : "Update should be present before coming to this method";
			return MessageList.MESSAGE_NO_UPDATE_COMMAND_FOUND;
		}

		if (smtData == null || smtData.getListTask().isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}

		if (keyFieldsList.get(CommandType.Command_Types.UPDATE.name()).split(
				" ").length > EXPECTED_UPDATE_KEY_VALUE_LENGTH) {
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
					UPDATE_WORD);
		}

		if (!isStringAnInteger(keyFieldsList
				.get(CommandType.Command_Types.UPDATE.name()))) {
			return MessageList.MESSAGE_INVALID_UPDATE_ID;
		}

		int index = searchTaskIndexStored(Integer.parseInt(keyFieldsList
				.get(CommandType.Command_Types.UPDATE.name())), smtData);

		if (index < minTaskListSize || index >= smtData.getSize()) {
			return MessageList.MESSAGE_NO_SUCH_TASK;
		}
		// remove the update key pair as it has the index extracted
		keyFieldsList.remove(CommandType.Command_Types.UPDATE.name());

		if(keyFieldsList.isEmpty()){
			return MessageList.MESSAGE_NO_UPDATE_FIELDS_FOUND;
		}
		
		return checkAndUpdateContents(keyFieldsList, index, smtData);
	}

	/**
	 * checkAndUpdateContents method will update the contents based on the key
	 * word
	 * 
	 * @param keyFieldsList
	 *            contains the list of keyword and the data it has
	 * @param index
	 *            indicate the location of the intended task to be updated
	 * @param smtData
	 *            contains the whole information including the task list
	 * @return message
	 */
	private static String checkAndUpdateContents(
			Map<String, String> keyFieldsList, int index, Data smtData) {
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		indicMsg.setTrue(true);

		Task awaitingTask = smtData.getATask(index);

		if (restrictOnlyUnqiueKeyWord(keyFieldsList) > KEYWORDS_MUST_NOT_APPEARED_COUNT) {
			return MessageList.MESSAGE_NO_WEEKLY_DEADLINE;
		}

		// first check if command contains from and to keywords and process them
		// first
		if (checkFromTimeToTimeBothExist(keyFieldsList)) {
			indicMsg = processBothTimes(keyFieldsList, awaitingTask);
		}

		if (indicMsg != null && !indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		return updateEachFieldsAndSave(keyFieldsList, index, smtData, indicMsg,
				awaitingTask);
	}

	/**
	 * updateEachFieldsAndSave method updates individual fields and update the
	 * awaiting task object to the Data object
	 * 
	 * @param keyFieldsList
	 *            contains the list of keyword and the data it has
	 * @param index
	 *            indicate the location of the intended task to be updated
	 * @param smtData
	 *            contains the whole information including the task list
	 * @param indicMsg
	 *            the indicator message pair
	 * @param awaitingTask
	 *            the temporary task which is updated task
	 * @return the return message
	 */
	private static String updateEachFieldsAndSave(
			Map<String, String> keyFieldsList, int index, Data smtData,
			IndicatorMessagePair indicMsg, Task awaitingTask) {
		KeywordType.List_Keywords getKey;
		for (String key : keyFieldsList.keySet()) {
			getKey = KeywordType.getKeyword(key);
			switch (getKey) {
			case BY:
			case ON:
				indicMsg = updateTaskByOrEndWhen(smtData, awaitingTask,
						keyFieldsList.get(key));
				break;
			case TASKDESC:
				indicMsg = updateTaskDesc(awaitingTask, keyFieldsList.get(key));
				break;
			case FROM:
				indicMsg = updateTaskFromOrToTimeWhen(awaitingTask,
						keyFieldsList.get(key), KeywordType.List_Keywords.FROM);
				break;
			case TO:
				indicMsg = updateTaskFromOrToTimeWhen(awaitingTask,
						keyFieldsList.get(key), KeywordType.List_Keywords.TO);
				break;
			case EVERY:
				indicMsg = updateRecurringWeek(awaitingTask,
						keyFieldsList.get(key));
				break;
			case COMPLETE:
				indicMsg = updateTaskStatus(awaitingTask,
						keyFieldsList.get(key), true);
				break;
			case INCOMPLETE:
				indicMsg = updateTaskStatus(awaitingTask,
						keyFieldsList.get(key), false);
				break;
			default:
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
						"Update");
			}

			if (!indicMsg.isTrue()) {
				return indicMsg.getMessage();
			}
		}

		// until here it means success
		smtData.updateTaskList(index, awaitingTask);

		indicMsg = smtData.writeTaskListToFile();

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		// save the current history
		CacheCommandsHandler.newHistory(smtData);

		// logging
		taskLogger.log(
				Level.INFO,
				String.format(MessageList.MESSAGE_UPDATE_SUCCESS,
						smtData.getATask(index)));

		return String.format(MessageList.MESSAGE_UPDATE_SUCCESS,
				smtData.getATask(index));
	}

	/**
	 * updateTaskByOrEndWhen method will update the task end date and will
	 * determine whether it can be updated as well.
	 * 
	 * @param smtData
	 *            contains the whole information including the task list
	 * @param index
	 *            indicate the location of the intended task to be updated
	 * @param keyFields
	 *            contains the data it has
	 * @return true if success, false if there is an invalid conversion object
	 *         and message
	 */
	private static IndicatorMessagePair updateTaskByOrEndWhen(Data smtData,
			Task currentTask, String keyFields) {
		if (currentTask == null) {
			assert false : "Task object is null";
		}

		DateTime newStartDateTime = null;
		DateTime newEndDateTime = null;

		if (keyFields.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}

		DateTime endDate = DateTimeParser.generateDate(keyFields);
		String errorMessage = "";
		if (endDate == null) {
			errorMessage = DateTimeParser.getDateFormatError(keyFields);
			return new IndicatorMessagePair(false, errorMessage);
		}

		if (clashWithBlockDate(smtData, endDate)) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_CONFLICT_WITH_BLOCKED_DATE);
		}

		if (currentTask.getTaskStartDateTime() != null) {
			newStartDateTime = new DateTime(endDate.getYear(),
					endDate.getMonthOfYear(), endDate.getDayOfMonth(),
					currentTask.getTaskStartDateTime().getHourOfDay(),
					currentTask.getTaskStartDateTime().getMinuteOfHour());
		}

		if (currentTask.getTaskEndDateTime() != null) {
			newEndDateTime = new DateTime(endDate.getYear(),
					endDate.getMonthOfYear(), endDate.getDayOfMonth(),
					currentTask.getTaskEndDateTime().getHourOfDay(),
					currentTask.getTaskEndDateTime().getMinuteOfHour());
		}

		if (newEndDateTime == null) {
			newEndDateTime = endDate;
		}

		currentTask.setTaskStartDateTime(newStartDateTime);
		currentTask.setTaskEndDateTime(newEndDateTime);
		currentTask.setWeeklyDay(EMPTY_STRING);
		currentTask.setDeadLineStatus(true);
		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * updateRecurringWeek method will update the task to be happening in every
	 * week
	 * 
	 * @param smtData
	 *            contains the whole information including the task list
	 * @param index
	 *            indicate the location of the intended task to be updated
	 * @param keyFields
	 *            contains the data it has
	 * @return true if success, false if there is an invalid conversion object
	 *         and message
	 */
	private static IndicatorMessagePair updateRecurringWeek(Task currentTask,
			String keyFields) {
		if (currentTask == null) {
			assert false : "Task object is null";
		}

		if (keyFields == null || keyFields.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}

		DateTime weeklyDate = DateTimeParser.generateDate(keyFields);

		if (weeklyDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, WEEKLY_WORD));
		}
		DateTime newStartTime = null;
		DateTime newEndTime = null;

		if (currentTask.getTaskStartDateTime() != null) {
			newStartTime = new DateTime(weeklyDate.getYear(),
					weeklyDate.getMonthOfYear(), weeklyDate.getDayOfMonth(),
					currentTask.getTaskStartDateTime().getHourOfDay(),
					currentTask.getTaskStartDateTime().getMinuteOfHour());
		}

		if (currentTask.getTaskEndDateTime() != null) {
			newEndTime = new DateTime(weeklyDate.getYear(),
					weeklyDate.getMonthOfYear(), weeklyDate.getDayOfMonth(),
					currentTask.getTaskEndDateTime().getHourOfDay(),
					currentTask.getTaskEndDateTime().getMinuteOfHour());
		}

		currentTask.setTaskStartDateTime(newStartTime);
		currentTask.setTaskEndDateTime(newEndTime);
		currentTask.setWeeklyDay(keyFields);
		currentTask.setDeadLineStatus(false);
		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * This method will update the task description and will determine whether
	 * it can be updated as well.
	 * 
	 * @param smtData
	 *            contains the whole information including the task list
	 * @param index
	 *            indicate the location of the intended task to be updated
	 * @param keyFields
	 *            contains the keyword and the data it has
	 * @return true if success, false if the parameter and message
	 */
	private static IndicatorMessagePair updateTaskDesc(Task currentTask,
			String keyFields) {
		if (currentTask == null) {
			assert false : "Task object is null";
		}

		if (keyFields == null || keyFields.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_DESCRIPTION_EMPTY);
		}

		currentTask.setTaskDescription(keyFields);
		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * This method will update the task start date and will determine whether it
	 * can be updated as well.
	 * 
	 * @param smtData
	 *            contains the whole information including the task list
	 * @param index
	 *            indicate the location of the intended task to be updated
	 * @param keyFields
	 *            contains the keyword and the data it has
	 * @return true if success, false if there is an invalid conversion object
	 *         and message
	 */
	private static IndicatorMessagePair updateTaskFromOrToTimeWhen(
			Task currentTask, String keyFields,
			KeywordType.List_Keywords direction) {
		if (currentTask == null) {
			assert false : "Task object is null";
		}

		if (keyFields == null || keyFields.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_TIME_GIVEN);
		}

		DateTime startOrEndTime = DateTimeParser.generateTime(keyFields);
		if (startOrEndTime == null) {
			return new IndicatorMessagePair(false,
					String.format(MessageList.MESSAGE_INCORRECT_TIME_FORMAT));
		}

		if (direction == KeywordType.List_Keywords.FROM) {
			DateTime newStartTime = updateTimeToExistingDateTime(currentTask,
					startOrEndTime);
			if (currentTask.getTaskEndDateTime() != null
					&& !checkFromTimeToTimeBothValid(newStartTime,
							currentTask.getTaskEndDateTime())) {
				return new IndicatorMessagePair(false,
						MessageList.MESSAGE_TIME_WRONG_FLOW);
			}
			currentTask.setTaskStartDateTime(newStartTime);
		} else if (direction == KeywordType.List_Keywords.TO) {
			DateTime newEndTime = updateTimeToExistingDateTime(currentTask,
					startOrEndTime);
			if (currentTask.getTaskStartDateTime() != null
					&& !checkFromTimeToTimeBothValid(
							currentTask.getTaskStartDateTime(), newEndTime)) {
				return new IndicatorMessagePair(false,
						MessageList.MESSAGE_TIME_WRONG_FLOW);
			}
			currentTask.setTaskEndDateTime(newEndTime);
		}

		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * This method update the status of the task to completed or pending.
	 * 
	 * @param smtData
	 *            contains the whole information including the task list
	 * @param index
	 *            indicate the location of the intended task to be updated
	 * @param keyFields
	 *            contains the keyword and the data it has
	 * @param status
	 *            the task status in boolean
	 * @return true if success, false if there is an invalid conversion object
	 *         and message
	 */
	private static IndicatorMessagePair updateTaskStatus(Task currentTask,
			String keyFields, boolean status) {
		if (currentTask == null) {
			assert false : "Task object is null";
		}

		if (keyFields == null || !keyFields.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_UPDATE_STATUS_EXTRA_FIELD);
		}

		currentTask.setTaskStatus(status);
		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * searchTaskIndexStored method search for a task in the ArrayList
	 * 
	 * @param taskId
	 *            taskId the task id for searching
	 * @param smtData
	 *            the data contains all the information such as task list
	 * @return
	 */
	private static int searchTaskIndexStored(int taskId, Data smtData) {
		for (int i = 0; i < smtData.getSize(); i++) {
			if (taskId == smtData.getATask(i).getTaskId()) {
				return i;
			}
		}
		return -1;
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
			Map<String, String> keyFieldsList, Task currentTask) {

		if (!checkFromTimeToTimeBothField(keyFieldsList)) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_INCORRECT_TIME_FORMAT);
		}

		DateTime startTime = DateTimeParser.generateTime(keyFieldsList
				.get(KeywordType.List_Keywords.FROM.name()));
		DateTime endTime = DateTimeParser.generateTime(keyFieldsList
				.get(KeywordType.List_Keywords.TO.name()));

		if (!checkFromTimeToTimeBothValid(startTime, endTime)) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_TIME_WRONG_FLOW);
		}

		if (currentTask.getTaskStartDateTime() == null
				&& currentTask.getTaskEndDateTime() == null) {
			startTime = new DateTime(DateTime.now().getYear(), DateTime.now()
					.getMonthOfYear(), DateTime.now().getDayOfMonth(),
					startTime.getHourOfDay(), startTime.getMinuteOfHour());
			endTime = new DateTime(DateTime.now().getYear(), DateTime.now()
					.getMonthOfYear(), DateTime.now().getDayOfMonth(),
					endTime.getHourOfDay(), endTime.getMinuteOfHour());
		}

		updateBothTimes(currentTask, startTime, endTime);

		keyFieldsList.remove(KeywordType.List_Keywords.FROM.name());
		keyFieldsList.remove(KeywordType.List_Keywords.TO.name());
		return new IndicatorMessagePair(true, EMPTY_STRING);
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
	private static void updateBothTimes(Task currentTask, DateTime startTime,
			DateTime endTime) {
		DateTime newStartTime = updateTimeToExistingDateTime(currentTask,
				startTime);
		DateTime newEndTime = updateTimeToExistingDateTime(currentTask, endTime);

		currentTask.setTaskStartDateTime(updateTimeToExistingDateTime(
				currentTask, newStartTime));
		currentTask.setTaskEndDateTime(updateTimeToExistingDateTime(
				currentTask, newEndTime));
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
	 * updateTimeToExistingDateTime method updates the current task start or end
	 * time with the existing time
	 * 
	 * @param currentTask
	 *            the current task
	 * @param existingDateTime
	 *            the existing time to update
	 * @return
	 */
	private static DateTime updateTimeToExistingDateTime(Task currentTask,
			DateTime existingDateTime) {
		if (currentTask.getTaskStartDateTime() == null
				&& currentTask.getTaskEndDateTime() == null) {
			// do nothing
		} else if (currentTask.getTaskStartDateTime() == null) {
			existingDateTime = new DateTime(currentTask.getTaskEndDateTime()
					.getYear(), currentTask.getTaskEndDateTime()
					.getMonthOfYear(), currentTask.getTaskEndDateTime()
					.getDayOfMonth(), existingDateTime.getHourOfDay(),
					existingDateTime.getMinuteOfHour());
		} else {
			existingDateTime = new DateTime(currentTask.getTaskStartDateTime()
					.getYear(), currentTask.getTaskStartDateTime()
					.getMonthOfYear(), currentTask.getTaskStartDateTime()
					.getDayOfMonth(), existingDateTime.getHourOfDay(),
					existingDateTime.getMinuteOfHour());
		}

		return existingDateTime;
	}

	/**
	 * restrictOnlyUnqiueKeyWord method checks if the 'ON', 'BY', 'EVERY' exist
	 * and count them
	 * 
	 * @param keyFieldsList
	 *            the keyfieldList object
	 * @return count
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
	 * clashWithBlockDate checks if the list of blocked date clashes with the
	 * date currently want to update
	 * 
	 * @param smtData
	 *            Data object which contains the data of the overall
	 * @param endDate
	 *            the received date
	 * @return true if clashed, else false
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

	/**
	 * This method check if the given string can be converted to integer
	 * 
	 * @param inputStr
	 *            the string to be converted
	 * @return true if it is a integer, else false
	 */
	private static boolean isStringAnInteger(String inputStr) {
		try {
			Integer.parseInt(inputStr);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
