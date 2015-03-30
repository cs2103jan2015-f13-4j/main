package logic;

import java.util.HashMap;

import org.joda.time.DateTime;

import parser.DateParser;
import data.Data;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;

public class BlockDateHandler {

	public static IndicatorMessagePair blockDate(
			HashMap<String, String> keyFieldsList, String keyCommand,
			Data smtData) {
		return null;

	}

	private static String CheckIfCommandEqualBlockOrUnblock(
			HashMap<String, String> keyFieldsList, String keyCommand,
			Data smtData) {
		IndicatorMessagePair indicMsg;

		switch (keyCommand) {
		case CommandType.Command_Types.BLOCK.name():
			indicMsg = blockDate(keyFieldsList.get(keyCommand, smtData));
			break;

		case CommandType.Command_Types.UNBLOCK.name():
			indicMsg = unblockDate(keyFieldsList.get(keyCommand, smtData));
			break;

		default:
			return String
					.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Update");
		}

		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
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
						.name())) { // &&keyFieldList.containKey(KeywordType.List_Keywords.TO))){
			return blockRangeOfDates(keyFieldList, smtData);
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
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		DateTime endDate = DateParser.generateDate(toDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		return null;
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
			return unblockDate(
					keyFieldList.get(CommandType.Command_Types.BLOCK.name()),
					smtData);
		} else if (keyFieldList.size() == 3
				&& keyFieldList.containsKey(KeywordType.List_Keywords.FROM
						.name())) { // &&keyFieldList.containKey(KeywordType.List_Keywords.TO))){
			return unblockRangeOfDates(keyFieldList, smtData);
		} else {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_DATE_BLOCKED_UNBLOCKED_FAILED);
		}

	}

	private static IndicatorMessagePair unblockRangeOfDates(
			HashMap<String, String> keyFieldList, Data smtData) {

		DateTime startDate = DateParser.generateDate(fromDate);
		if (startDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}

		DateTime endDate = DateParser.generateDate(toDate);
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));

			return null;
		}

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

	private static boolean checkIfunblockDateExist(DateTime dateTimeReceived,
			Data smtData) {
		for (int i = 0; i < smtData.getBlockedDateTimeList().size(); i++) {
			if (smtData.getBlockedDateTimeList().get(i).toLocalDate()
					.equals(dateTimeReceived.toLocalDate())) {
				return true;
			}
		}
		return false;
	}

}
