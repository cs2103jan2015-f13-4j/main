package logic;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import parser.DateTimeParser;
import data.Data;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;

public class BlockDateHandler {

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

		IndicatorMessagePair indicMsg_File = smtData
				.writeBlockedDateTimeListToFile();

		if (!indicMsg_File.isTrue()) {
			return indicMsg.getMessage();
		}
		CacheCommandsHandler.newHistory(smtData);

		return indicMsg.getMessage();
	}

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

	private static IndicatorMessagePair checkBlockDate(
			Map<String, String> keyFieldList, Data smtData) {

		if (keyFieldList == null || keyFieldList.isEmpty())
			if (keyFieldList == null || keyFieldList.isEmpty()) {
				return new IndicatorMessagePair(false, MessageList.MESSAGE_NULL);
			}

		if (smtData == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_TASK_IN_LIST);
		}

		if (keyFieldList.size() == 1) {
			return blockOneDate(
					keyFieldList.get(CommandType.Command_Types.BLOCK.name()),
					smtData);
		} else if (keyFieldList.size() == 3
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

		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		for (LocalDate date = startDate.toLocalDate(); date.isBefore((endDate.plusDays(1))
				.toLocalDate()); date = date.plusDays(1)) {
			blockOneDate(date.toString(), smtData);

		}
		return new IndicatorMessagePair(true, MessageList.MESSAGE_BLOCKED);
	}

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

		if (checkIfBlockDateExist(endDate, smtData)) {
			// change msgto date exist inside blocklist
			return new IndicatorMessagePair(false, "This date already exist");
		}
		smtData.addBlockedDateTime(endDate);
		return new IndicatorMessagePair(true,
				String.format(MessageList.MESSAGE_BLOCKED));
	}

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

		if (keyFieldList.size() == 1) {
			return unblockOneDate(
					keyFieldList.get(CommandType.Command_Types.UNBLOCK.name()),
					smtData);
		} else if (keyFieldList.size() == 3
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

		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		for (LocalDate date = startDate.toLocalDate(); date.isBefore((endDate.plusDays(1))
				.toLocalDate()); date = date.plusDays(1)) {
			unblockOneDate(date.toString(), smtData);
		}
		return new IndicatorMessagePair(true, MessageList.MESSAGE_UNBLOCKED);

	}

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
			return new IndicatorMessagePair(false,
					"This date is not inside the block list");
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

		return new IndicatorMessagePair(true,
				String.format(MessageList.MESSAGE_UNBLOCKED));
	}

}
