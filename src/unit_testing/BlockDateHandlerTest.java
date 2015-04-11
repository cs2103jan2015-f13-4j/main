//@A0112501E
package unit_testing;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import logic.BlockDateHandler;
import logic.SearchHandler;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import utility.MessageList;
import data.Data;
import data.Task;

public class BlockDateHandlerTest {

	Map<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "listTaskTest.txt";

	@Before
	public void setUp() {
		smtDataTest = new Data();
		keyFieldsTest = new TreeMap<String, String>();
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal",
				new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah",
				new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(),
				new DateTime(), ""));
	}

	/**
	 * This is to test the block one date out of bounce(Eg. April do not have
	 * 31st) The output is: Date Format is incorrect
	 */
	@Test
	public void testBlockOneDateThatDoNotExist() {
		keyFieldsTest.put("BLOCK", "31-04-2015");
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
	}

	/**
	 * This is to test the unblock one date out of bounce(Eg. April do not have
	 * 31st) The output is: Date Format is incorrect
	 */
	@Test
	public void testUnblockOneDateThatDoNotExist() {
		keyFieldsTest.put("UNBLOCK", "31-04-2015");
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
	}

	/**
	 * This is to test the correct format of block one date The output is:
	 * "19-04-2015" Blocked Successful
	 */
	@Test
	public void testBlockOneDate() {
		keyFieldsTest.put("BLOCK", "19-04-2015");
		String expected = String.format(MessageList.MESSAGE_BLOCKED,
				"19-04-2015");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
	}

	/**
	 * This is to test the correct format of the unblock one date The output is:
	 * "19-04-2015"Unblocked Successful
	 */

	@Test
	public void testUnBlockOneDate() {
		keyFieldsTest.put("BLOCK", "19-04-2015");
		BlockDateHandler.executeBlockOrUnblock(keyFieldsTest, "BLOCK",
				smtDataTest);
		keyFieldsTest.clear();
		keyFieldsTest.put("UNBLOCK", "19-04-2015");
		String expected = String.format(MessageList.MESSAGE_UNBLOCKED,
				"19-04-2015");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the blocking of range of date The output is: "19-04-2015"
	 * to "25-04-2015" Unblocked Successful
	 */

	@Test
	public void testBlockRangeOfDate() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "19-04-2015");
		keyFieldsTest.put("TO", "25-04-2015");
		String expected = String.format(MessageList.MESSAGE_BLOCKED_RANGE,
				"19-04-2015", "25-04-2015");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This to to test the blocking of date from a month extend into another
	 * month The output is : "19-04-2015" to "01-05-2015" Blocked Successful
	 */

	@Test
	public void testBlockRangeOfDateExtendToAnotherMonth() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "19-04-2015");
		keyFieldsTest.put("TO", "01-05-2015");
		String expected = String.format(MessageList.MESSAGE_BLOCKED_RANGE,
				"19-04-2015", "01-05-2015");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This to to test the unblocking of date from a month extend into another
	 * month The output is : "19-04-2015" to "01-06-2015 "Unblocked Successful
	 */
	@Test
	public void testUnBlockRangeOfDateExtendToAnotherMonth() {
		keyFieldsTest.put("UNBLOCK", "");
		keyFieldsTest.put("FROM", "19-04-2015");
		keyFieldsTest.put("TO", "01-06-2015");
		String expected = String.format(MessageList.MESSAGE_UNBLOCKED_RANGE,
				"19-04-2015", "01-06-2015");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the unblocking of a range of date The output is :
	 * "19-04-2015" to "21-04-2015' Unblocked Successful
	 */
	@Test
	public void testUnblockRangeOfDate() {
		keyFieldsTest.put("UNBLOCK", "");
		keyFieldsTest.put("FROM", "19-04-2015");
		keyFieldsTest.put("TO", "21-04-2015");
		String expected = String.format(MessageList.MESSAGE_UNBLOCKED_RANGE,
				"19-04-2015", "21-04-2015");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect of block date format The output is: Date
	 * format is incorrect
	 */
	@Test
	public void testInvalidBlockDate() {
		keyFieldsTest.put("BLOCK", "100-04-2015");
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect of unblock date format The output is: Date
	 * format is incorrect
	 */
	@Test
	public void testInvalidUnBlockDate() {
		keyFieldsTest.put("UNBLOCK", "100-04-2015");
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the block date with no date enter The output is: No date
	 * given.
	 */
	@Test
	public void testEmptyBlockDate() {
		keyFieldsTest.put("BLOCK", "");
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the unblock date with no date enter The output is: No
	 * date given.
	 */
	@Test
	public void testEmptyUnBlockDate() {
		keyFieldsTest.put("UNBLOCK", "");
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect date enter for unblock The output is: Date
	 * format is incorrect
	 */
	@Test
	public void testIncorrectUnBlockDate() {
		keyFieldsTest.put("UNBLOCK", "@#$%^&*^%$#$%^&");
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect date enter for block The output is : Date
	 * format is incorrect
	 */
	@Test
	public void testIncorrectBlockDate() {
		keyFieldsTest.put("BLOCK", "!?/*");
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the wrong Date format for block The output is : Date
	 * format is incorrect
	 */
	@Test
	public void testWrongDateFormatForBlockDate() {
		keyFieldsTest.put("BLOCK", "14 June 2015");
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the wrong Date format for unblock The output is : Date
	 * format is incorrect
	 */
	@Test
	public void testWrongUnBlockDate() {
		keyFieldsTest.put("UNBLOCK", "14 July 2015");
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect keyword input for block date The output is
	 * : Invalid argument for Block/Unblock command.
	 */
	@Test
	public void testIncorrectBlockDateKeyword() {
		keyFieldsTest.put("VLOCK", "14-04-2015");
		String expected = MessageList.MESSAGE_BLOCK_INVALID_BLOCK_UNBLOCK_COMMAND;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "VLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect keyword input for unblock date The output
	 * is : Invalid argument for Block/Unblock command.
	 */
	@Test
	public void testWrongUnBlockDateKeyword() {
		keyFieldsTest.put("UNVLOCK", "14-07-2015");
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
				"Block/Unblock");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNVLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the Invalid start date for block range of date The output
	 * is : Wrong date format for Start date
	 */

	@Test
	public void testInvalidForStartDateForBlockRangeOfDate() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "100-04-2015");
		keyFieldsTest.put("TO", "20-04-2015");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_START;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the Invalid end date for block range of date The output
	 * is : Wrong date format for End date
	 */

	@Test
	public void testInvalidForEndDateForBlockRangeOfDate() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "25-04-2015");
		keyFieldsTest.put("TO", "200-04-2015");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_END;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
	}

	/**
	 * This is to test the Invalid start date for unblock range of date The
	 * output is : Wrong date format for Start date
	 */
	@Test
	public void testInvalidForStartDateForUnblockRangeOfDate() {
		keyFieldsTest.put("UNBLOCK", "");
		keyFieldsTest.put("FROM", "100-04-2015");
		keyFieldsTest.put("TO", "20-04-2015");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_START;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
	}

	/**
	 * This is to test the Invalid keyword for unblock range of date The output
	 * is :Invalid argument for Block/Unblock command.
	 */

	@Test
	public void testInvalidUnblockRangeOfDate() {
		keyFieldsTest.put("UNBLOCKKK", "");
		keyFieldsTest.put("FROM", "10-04-2015");
		keyFieldsTest.put("TO", "20-04-2015");
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
				"Block/Unblock");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCKKK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
	}

	/**
	 * This is to test the invalid keyword for block for range of date The
	 * output is : Invalid argument for Block/Unblock command.
	 */
	@Test
	public void testInvalidBlockRangeOfDate() {
		keyFieldsTest.put("BLOCCCKKK", "");
		keyFieldsTest.put("FROM", "10-04-2015");
		keyFieldsTest.put("TO", "20-04-2015");
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
				"Block/Unblock");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCCCKKK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the invalid keyword FROM for range of date The output is
	 * : Invalid argument for Block/Unblock command.
	 */

	@Test
	public void testWrongKeywordFrom() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROMM", "10-04-2015");
		keyFieldsTest.put("TO", "20-04-2015");
		String expected = String.format(
				MessageList.MESSAGE_BLOCK_INCORRECT_KEYWORD, "Block/Unblock");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the invalid keyword TO for range of date The output is :
	 * Invalid argument for Block/Unblock command.
	 */

	@Test
	public void testWrongKeywordTo() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "10-04-2015");
		keyFieldsTest.put("T0", "20-04-2015");
		String expected = String.format(
				MessageList.MESSAGE_BLOCK_INCORRECT_KEYWORD, "Block/Unblock");
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the startDate being empty for the block date range The
	 * output is : Wrong date format for Start date
	 */
	@Test
	public void testEmptyForBlockStartDate() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", " ");
		keyFieldsTest.put("TO", "20-04-2015");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_START;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the end Date being empty for the block date range The
	 * output is : Wrong date format for End date
	 */
	@Test
	public void testEmptyForRangeBlockEndDate() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "20-04-2015");
		keyFieldsTest.put("TO", " ");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_END;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the end Date being empty for the unblock date range The
	 * output is : Wrong date format for Start date
	 */
	@Test
	public void testEmptyForRangeUNnblockStartDate() {
		keyFieldsTest.put("UNBLOCK", "");
		keyFieldsTest.put("FROM", " ");
		keyFieldsTest.put("TO", "20-04-2015");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_START;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the unblock to date is empty The output is : Wrong date
	 * format for End date
	 */
	@Test
	public void testEmptyForRangeUnblockEndDate() {
		keyFieldsTest.put("UNBLOCK", "");
		keyFieldsTest.put("FROM", "20-04-2015");
		keyFieldsTest.put("TO", " ");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_END;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect block start date The output is : Wrong date
	 * format for Start date
	 */
	@Test
	public void testIncorrectInputForRangeBlockStartDate() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "------====");
		keyFieldsTest.put("TO", "20-04-2015");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_START;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect block end date The output is : Wrong date
	 * format for End date
	 */
	@Test
	public void testIncorrectInputForRangeBlockEndtDate() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "20-04-2015");
		keyFieldsTest.put("TO", "!@#$%^$$^*%^&$");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_END;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect unblock end date The output is : Wrong date
	 * format for End date
	 */
	@Test
	public void testIncorrectInputForRangeUnBlockEndDate() {
		keyFieldsTest.put("UNBLOCK", "");
		keyFieldsTest.put("FROM", "20-04-2015");
		keyFieldsTest.put("TO", "!@#$%^&@#$%^@#$%^");
		String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_END;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "UNBLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the incorrect unblock end date The output is : Wrong date
	 * format for End date
	 */
	@Test
	public void testBlockStartDateIsLaterThanEndDate() {
		keyFieldsTest.put("BLOCK", "");
		keyFieldsTest.put("FROM", "20-05-2015");
		keyFieldsTest.put("TO", "10-05-2015");
		String expected = MessageList.MESSAGE_BLOCK_INCORRECT_START_EARLIER_THAN_END;
		assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
				keyFieldsTest, "BLOCK", smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	// GOT PROBLEM
	/**
	 * This is to test the blocking the blocked date again
	 * */

	// @Test
	// public void testBlockingTheBlockedDate() {
	// keyFieldsTest.put("BLOCK", "");
	// keyFieldsTest.put("FROM", "20-06-2015");
	// keyFieldsTest.put("TO", "30-06-2015");
	// keyFieldsTest.put("BLOCK", "25-06-2015");
	// String expected = MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_END;
	// assertEquals(expected, BlockDateHandler.executeBlockOrUnblock(
	// keyFieldsTest, "BLOCK", smtDataTest));
	// SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	// }
}
