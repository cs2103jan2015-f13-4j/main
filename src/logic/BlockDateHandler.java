package logic;

import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import parser.DateParser;
import data.Data;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;

public class BlockDateHandler {

	public static String CheckIfCommandEqualBlockOrUnblock(
			HashMap<String, String> keyFieldsList, String keyCommand,
			Data smtData) {
		IndicatorMessagePair indicMsg = checkIfCommandKeyExist(keyFieldsList, keyCommand);
		
		if(!indicMsg.isTrue()){
			return indicMsg.getMessage();
		}
		
		CommandType.Command_Types cmd = CommandType.getType(keyCommand
				.split(" "));
		switch (cmd) {
		case BLOCK:
			indicMsg = blockDate(keyFieldsList, smtData);
			break;
		case UNBLOCK:
			indicMsg = unblockDate(keyFieldsList, smtData);
			break;

		default:
			return String
					.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Update");
		}

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		return indicMsg.getMessage();
	}

	private static IndicatorMessagePair checkIfCommandKeyExist(
			HashMap<String, String> keyFieldsList, String keyCommand) {

		if (!keyFieldsList.containsKey(keyCommand)) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_SPECIFICATION);
		}
		if (keyFieldsList.get(keyCommand) == null
				|| keyFieldsList.get(keyCommand).isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		return new IndicatorMessagePair(true, "");
	}

	private static IndicatorMessagePair blockDate(
			HashMap<String, String> keyFieldList, Data smtData) {

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
					MessageList.MESSAGE_DATE_BLOCKED_UNBLOCKED_FAILED);
		}

	}

	private static IndicatorMessagePair blockRangeOfDates(String fromDate,
			String toDate, Data smtData) {
		DateTime startDate = DateParser.generateDate(fromDate);
		if (startDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "Start"));
		}

		DateTime endDate = DateParser.generateDate(toDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		for (LocalDate date = startDate.toLocalDate(); date.isBefore(endDate
				.toLocalDate()); date = date.plusDays(1)) {
			indicMsg = blockOneDate(date.toString(), smtData);
			if (!indicMsg.isTrue()) {
				return indicMsg;
			}
		}
		return new IndicatorMessagePair(true, "Blocked Successful");
	}

	private static IndicatorMessagePair blockOneDate(String receivedDate,
			Data smtData) {
		DateTime endDate = DateParser.generateDate(receivedDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		if (!checkIfBlockDateExist(endDate, smtData)) {
			smtData.addBlockedDateTime(endDate);
		}
		return new IndicatorMessagePair(true,
				String.format(MessageList.MESSAGE_BLOCK_HELP)); // change msg
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

	private static IndicatorMessagePair unblockDate(
			HashMap<String, String> keyFieldList, Data smtData) {

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
			return unblockRangeOfDates(keyFieldList.get(KeywordType.List_Keywords.FROM.name()),
					keyFieldList.get(KeywordType.List_Keywords.TO.name()), smtData);
		} else {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_DATE_BLOCKED_UNBLOCKED_FAILED);
		}

	}

	private static IndicatorMessagePair unblockRangeOfDates(String fromDate,
			String toDate, Data smtData) {

		DateTime startDate = DateParser.generateDate(fromDate);
		if (startDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		DateTime endDate = DateParser.generateDate(toDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}
		
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		for (LocalDate date = startDate.toLocalDate(); date.isBefore(endDate
				.toLocalDate()); date = date.plusDays(1)) {
			indicMsg = blockOneDate(date.toString(), smtData);
			if (!indicMsg.isTrue()) {
				return indicMsg;
			}
		}
		return new IndicatorMessagePair(true, "Unblocked Successful");

	}

	private static IndicatorMessagePair unblockOneDate(String receivedDate,
			Data smtData) {
		DateTime endDate = DateParser.generateDate(receivedDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		if (!checkIfBlockDateExist(endDate, smtData)) {
			smtData.addBlockedDateTime(endDate);
		}
		return new IndicatorMessagePair(true,
				String.format(MessageList.MESSAGE_BLOCK_HELP)); // change msg
	}

}
