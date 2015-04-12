//@author A0112501E
package logic;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import parser.DateTimeParser;
import data.Data;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;

/**
 * This class is for blocking, unblocking of dates
 * 
 */
public class BlockDateHandler {

	/* Global variable */
	private static final int ONE_KEYWORD = 1;
	private static final int THREE_KEYWORDS = 3;
	private static final int ONE_MONTH = 31;
	private static final int TWO_YEARS_LIMIT = 2;
	private static final int ADD_ONE_DAY = 1;
	private static final int ONE_DAY = 1;

	/**
	 * This method is used to check whether is it block or unblock
	 * 
	 * @param keyFieldsList
	 * @param keyCommand
	 * @param smtData
	 * @return
	 */
	public static String executeBlockOrUnblock(
			Map<String, String> keyFieldsList, String keyCommand, Data smtData) {
		IndicatorMessagePair indicMsg = checkIfCommandKeyExist(keyFieldsList,
				keyCommand);

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		CommandType.Command_Types cmd = CommandType.getType(keyCommand
				.split(" "));
		switch (cmd) {
		case BLOCK:
			indicMsg = checkBlockDate(keyFieldsList, smtData);
			break;
		case UNBLOCK:
			indicMsg = checkUnblockDate(keyFieldsList, smtData);
			break;

		default:
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
					"Block/Unblock");
		}

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}

		// writing the block date into the storage
		IndicatorMessagePair indicMsg_File = smtData
				.writeBlockedDateTimeListToFile();

		if (!indicMsg_File.isTrue()) {
			return indicMsg.getMessage();
		}
		CacheCommandsHandler.newHistory(smtData);

		return indicMsg.getMessage();
	}

	// CHECK IF COMMENTS CORRECT
	/**
	 * This method is to check if the time is specified
	 * 
	 * @param keyFieldsList
	 * @param keyCommand
	 * @return
	 */
	private static IndicatorMessagePair checkIfCommandKeyExist(
			Map<String, String> keyFieldsList, String keyCommand) {

		if (!keyFieldsList.containsKey(keyCommand)) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_BLOCK_SPECIFICATION);
		}
		if (keyFieldsList.get(keyCommand) == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		return new IndicatorMessagePair(true, "");
	}

	/**
	 * This method is to check if the block is a date or a range of dates
	 * 
	 * @param keyFieldList
	 * @param smtData
	 * @return
	 */
	private static IndicatorMessagePair checkBlockDate(
			Map<String, String> keyFieldList, Data smtData) {

		if (keyFieldList == null) {
			assert false : "The mapped object is null";
		}

		if (smtData == null) {
			assert false : "The data object is null";
		}

		if (keyFieldList.isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NULL);
		}

		if (keyFieldList.size() == ONE_KEYWORD) {
			return blockOneDate(
					keyFieldList.get(CommandType.Command_Types.BLOCK.name()),
					smtData);
		} else if (keyFieldList.size() == THREE_KEYWORDS
				&& keyFieldList.containsKey(KeywordType.List_Keywords.FROM
						.name())
				&& keyFieldList
						.containsKey(KeywordType.List_Keywords.TO.name())) {
			return blockRangeOfDates(
					keyFieldList.get(KeywordType.List_Keywords.FROM.name()),
					keyFieldList.get(KeywordType.List_Keywords.TO.name()),
					smtData);
		} else {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_BLOCK_INCORRECT_KEYWORD);
		}
	}

	/**
	 * This method is to check if the range of block start and end time correct
	 * 
	 */
	private static IndicatorMessagePair blockRangeOfDates(String fromDate,
			String toDate, Data smtData) {
		if (fromDate.isEmpty() || toDate.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_BLOCK_SPECIFICATION);
		}

		DateTime startDate = DateTimeParser.generateDate(fromDate);
		if (startDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start"));
		}

		DateTime endDate = DateTimeParser.generateDate(toDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, "End"));
		}

		if (!checkFromTimeToTimeBothValid(startDate, endDate)) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_BLOCK_INCORRECT_START_EARLIER_THAN_END,
					fromDate, toDate));
		}

		// change the message
		DateTime twoYearsLater = DateTime.now().plusYears(TWO_YEARS_LIMIT);
		if (!checkFromTimeToTimeBothValid(startDate, twoYearsLater)) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_BLOCK_DATE_OVER_TWO_YEARS, fromDate));
		}
		if (!checkFromTimeToTimeBothValid(endDate, twoYearsLater)) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_BLOCK_DATE_OVER_TWO_YEARS, fromDate));
		}

		int numberOfDatesFromThisRange = Days.daysBetween(
				startDate.toLocalDate(), endDate.toLocalDate()).getDays()
				+ ADD_ONE_DAY;
		if (numberOfDatesFromThisRange > ONE_MONTH) {
			// change the message that the range has exceed 31 days
			return new IndicatorMessagePair(true, String.format(
					MessageList.MESSAGE_BLOCK_RANGE_EXCEED_A_MONTH, fromDate,
					toDate));
		}

		// will execute checkBlockedFailed to check for blocked range
		return checkBlockedFailed(fromDate, toDate, smtData, startDate, endDate);
	}

	/**
	 * This is to check how many dates that want to block are already occupied.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param smtData
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private static IndicatorMessagePair checkBlockedFailed(String fromDate,
			String toDate, Data smtData, DateTime startDate, DateTime endDate) {
		int countBlockedFailed = 0;
		int totalBlockedDatesPending = 0;
		for (LocalDate date = startDate.toLocalDate(); date.isBefore((endDate
				.plusDays(ONE_DAY)).toLocalDate()); date = date
				.plusDays(ONE_DAY)) {
			if (!blockOneDate(date.toString(), smtData).isTrue()) {
				countBlockedFailed++;
			}
			totalBlockedDatesPending++;

		}
		// total number of block dates is not fully occupied, will count number
		// of occupied dates
		if (countBlockedFailed > totalBlockedDatesPending
				&& (countBlockedFailed < totalBlockedDatesPending)) {
			return new IndicatorMessagePair(true, String.format(
					MessageList.MESSAGE_BLOCKED_CLASHED_WITH_ADD_DATE,
					fromDate, toDate));
		}
		// All block dates are already occupied
		if (countBlockedFailed > totalBlockedDatesPending
				&& (countBlockedFailed == totalBlockedDatesPending)) {
			return new IndicatorMessagePair(true, String.format(
					MessageList.MESSAGE_BLOCKED_DATE_NOT_AVAILABLE, fromDate,
					toDate, countBlockedFailed));
		}
		// No dates are occupied. blocked successfully.
		return new IndicatorMessagePair(true, String.format(
				MessageList.MESSAGE_BLOCKED_RANGE, fromDate, toDate));
	}

	/**
	 * This method is to check the date is input, or if the date format is
	 * incorrect also to limit the length of blocked dates to 2 years.
	 * 
	 * @param receivedDate
	 * @param smtData
	 * @return
	 */
	private static IndicatorMessagePair blockOneDate(String receivedDate,
			Data smtData) {
		if (receivedDate.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}

		DateTime endDate = DateTimeParser.generateDate(receivedDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		// change the message
		DateTime twoYearsLater = DateTime.now().plusYears(TWO_YEARS_LIMIT);
		if (!checkFromTimeToTimeBothValid(endDate, twoYearsLater)) {
			return new IndicatorMessagePair(false,
					String.format(
							MessageList.MESSAGE_BLOCK_DATE_OVER_TWO_YEARS,
							receivedDate));
		}

		if (checkIfBlockDateExist(endDate, smtData)) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_BLOCK_DATE_ALREADY_EXIST, receivedDate));
		}

		if (checkIfBlockDateClashedWithTask(endDate, smtData)) {
			// return blocked date clashed with a task
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_BLOCK_DATE_ALREADY_EXIST, receivedDate));
		}

		smtData.addBlockedDateTime(endDate);
		return new IndicatorMessagePair(true, String.format(
				MessageList.MESSAGE_BLOCKED, receivedDate));
	}

	/**
	 * This is to check if block date is already occupied
	 * 
	 * @param dateTimeReceived
	 * @param smtData
	 * @return
	 */
	private static boolean checkIfBlockDateClashedWithTask(
			DateTime dateTimeReceived, Data smtData) {

		for (int i = 0; i < smtData.getSize(); i++) {
			if (smtData.getATask(i).getTaskEndDateTime() != null
					&& smtData.getATask(i).getDeadLineStatus()
					&& smtData.getATask(i).getTaskEndDateTime().toLocalDate()
							.equals(dateTimeReceived.toLocalDate())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This is to check if the block date has already been blocked.
	 * 
	 * @param dateTimeReceived
	 * @param smtData
	 * @return
	 */
	private static boolean checkIfBlockDateExist(DateTime dateTimeReceived,
			Data smtData) {
		for (int i = 0; i < smtData.getBlockedDateTimeList().size(); i++) {
			if (smtData.getBlockedDateTimeList().get(i).toLocalDate()
					.equals(dateTimeReceived.toLocalDate())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This is to check the unblock date, is unblock for a single date or a
	 * range of dates.
	 * 
	 * @param keyFieldList
	 * @param smtData
	 * @return
	 */
	private static IndicatorMessagePair checkUnblockDate(
			Map<String, String> keyFieldList, Data smtData) {

		if (keyFieldList == null || keyFieldList.isEmpty())
			if (keyFieldList == null || keyFieldList.isEmpty()) {
				return new IndicatorMessagePair(false, MessageList.MESSAGE_NULL);
			}

		if (smtData == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_TASK_IN_LIST);
		}

		if (keyFieldList.size() == ONE_KEYWORD) {
			return unblockOneDate(
					keyFieldList.get(CommandType.Command_Types.UNBLOCK.name()),
					smtData);
		} else if (keyFieldList.size() == THREE_KEYWORDS
				&& keyFieldList.containsKey(KeywordType.List_Keywords.FROM
						.name())
				&& keyFieldList
						.containsKey(KeywordType.List_Keywords.TO.name())) {
			return unblockRangeOfDates(
					keyFieldList.get(KeywordType.List_Keywords.FROM.name()),
					keyFieldList.get(KeywordType.List_Keywords.TO.name()),
					smtData);
		} else {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_BLOCK_INCORRECT_KEYWORD);
		}

	}

	/**
	 * This method is to check if the start date and end date are in correct
	 * format if the start date and end date is correct,unblock successfully.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param smtData
	 * @return
	 */
	private static IndicatorMessagePair unblockRangeOfDates(String fromDate,
			String toDate, Data smtData) {

		DateTime startDate = DateTimeParser.generateDate(fromDate);
		if (startDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start"));
		}

		DateTime endDate = DateTimeParser.generateDate(toDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, "End"));
		}

		for (LocalDate date = startDate.toLocalDate(); date.isBefore((endDate
				.plusDays(1)).toLocalDate()); date = date.plusDays(1)) {
			unblockOneDate(date.toString(), smtData);
		}
		return new IndicatorMessagePair(true, String.format(
				MessageList.MESSAGE_UNBLOCKED_RANGE, fromDate, toDate));

	}

	/**
	 * This method is to check if no date received, the date is never block
	 * before, or the unblock date do not exit
	 * 
	 * @param receivedDate
	 * @param smtData
	 * @return
	 */
	private static IndicatorMessagePair unblockOneDate(String receivedDate,
			Data smtData) {
		if (receivedDate.isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateTime endDate = DateTimeParser.generateDate(receivedDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		if (!checkIfBlockDateExist(endDate, smtData)) {
			// return this date is not inside the block list
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_BLOCK_DATE_DO_NOT_EXIST, receivedDate));
		}
		int i;

		for (i = 0; i < smtData.getBlockedDateTimeList().size(); i++) {
			if (smtData.getBlockedDateTimeList().get(i).toLocalDate()
					.equals(endDate.toLocalDate())) {
				break;
			}
		}

		IndicatorMessagePair msgPair = new IndicatorMessagePair();
		smtData.removeBlockedDateTime(i, msgPair);
		if (!msgPair.isTrue()) {
			return msgPair;
		}

		return new IndicatorMessagePair(true, String.format(
				MessageList.MESSAGE_UNBLOCKED, receivedDate));
	}

	/**
	 * This is to check the end time later than start time
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
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
}
