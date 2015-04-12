//@author A0112501E
package unit_testing;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import logic.AddHandler;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.FileStorage;
//import utility.IndicatorMessagePair;
import utility.MessageList;
import data.Data;
import data.Task;

public class AddHandlerTest {

	Map<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "taskListTest.txt";
	String lastUnUsedFileName = "lastUnUsedIndexTest.txt";

	@Before
	public void setup() {
		int year = 2015;
		int month = 10;
		int day = 5;
		int hour = 0;
		int min = 0;
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		FileStorage.setFileNameForLastUnusedIndex(lastUnUsedFileName);
		keyFieldsTest = new TreeMap<String, String>(
				String.CASE_INSENSITIVE_ORDER);
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(
				year, month, day, hour, min), new DateTime(year, month, day,
				hour + 23, min), ""));

	}

	@After
	public void tearDown() {
		smtDataTest = null;
		keyFieldsTest.clear();
		File textList = new File(fileName);
		textList.delete();
		textList = new File(lastUnUsedFileName);
		textList.delete();
	}

	/**
	 * This is to test the correct format of adding in a task The output is :
	 * Task ID: 4 Description: submit proposal End Time: 11.59 PM Deadline: 3
	 * August, 2015 (Mon) status: Pending
	 */
	@Test
	public void testAddWithDescRegular() {
		keyFieldsTest.put("ADD", "submit proposal");
		keyFieldsTest.put("BY", "03-08-2015");
		String expected = String
				.format(MessageList.MESSAGE_ADDED,
						"\nTask ID: 4\nDescription: submit proposal\nEnd Time: 11.59 PM\nDeadline: 3 August, 2015 (Mon)\nStatus: Pending");
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test the the adding of correct format but without date The
	 * output of this test is: No Date Given
	 */
	@Test
	public void testAddWithDescWithoutDate() {
		keyFieldsTest.put("ADD", "Submit Proposal");
		keyFieldsTest.put("BY", "");
		String expected = String
				.format(MessageList.MESSAGE_INCORRECT_DATE_FORMAT);
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test the adding of incorrect date format The output is : Date
	 * Format is Incorrect
	 */
	@Test
	public void testAddWithDescWithWrongDateFormat() {
		keyFieldsTest.put("ADD", "Submit Report");
		keyFieldsTest.put("BY", "03-March-2014");
		String expected = String
				.format(MessageList.MESSAGE_INCORRECT_DATE_FORMAT);
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test the adding of incorrect date format for month and year
	 * position The output is : Date format is Incorrect
	 */
	@Test
	public void testAddWithDescWithWrongDateFormatforMonth() {
		keyFieldsTest.put("ADD", "Submit Report");
		keyFieldsTest.put("BY", "03-2015-08");
		String expected = String
				.format(MessageList.MESSAGE_INCORRECT_DATE_FORMAT);
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test the adding of incorrect date format for the day in
	 * alphabetical form The output of this test is : Date format is Incorrect
	 */
	@Test
	public void testInvalidDate() {
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("BY", "AA-12-2015");
		String expected = String
				.format(MessageList.MESSAGE_INCORRECT_DATE_FORMAT);
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task by using day instead of a specified date
	 * The output is : Task ID: 4 Description: Submit Assignment End Time: 11.59
	 * PM Deadline: 17 April, 2015 (Fri) Status: Pending"
	 * 
	 */
	@Test
	public void testAddByDay() {
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("BY", "Friday");
		String expected = String
				.format(MessageList.MESSAGE_ADDED,
						"\nTask ID: 4\nDescription: Submit Assignment\nEnd Time: 11.59 PM\nDeadline: 17 April, 2015 (Fri)\nStatus: Pending");
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task within a certain period of time The
	 * output of is : Task ID: 4 Description: Submit Assignment Start Time:
	 * 10.00 AM End Time: 12.00 PM\nDeadline: 17 April, 2015 (Fri) Status:
	 * Pending
	 */
	@Test
	public void testAddByTime() {
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "10am");
		keyFieldsTest.put("TO", "12pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = String
				.format(MessageList.MESSAGE_ADDED,
						"\nTask ID: 4\nDescription: Submit Assignment\nStart Time: 10.00 AM\nEnd Time: 12.00 PM\nDeadline: 17 April, 2015 (Fri)\nStatus: Pending");
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task within a certain period of time The
	 * output is: End Time is supposed to be later than the Start Time
	 */
	@Test
	public void testAddInvalidTime() {
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "6pm");
		keyFieldsTest.put("TO", "5pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = String.format(MessageList.MESSAGE_TIME_MISMATCHED);
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task, with missing time The output is : Please
	 * specify the Start time and the End Time.
	 */
	@Test
	public void testAddTimeIsEmpty() {
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "");
		keyFieldsTest.put("TO", "");
		keyFieldsTest.put("BY", "Friday");
		String expected = String.format(MessageList.MESSAGE_TIME_SLOT_EMPTY);
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task, with missing AddTime for TO The output
	 * is: This command is invalid
	 */
	@Test
	public void testAddTimeWithMissingTOKeyword() {
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "3pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = MessageList.MESSAGE_INVALID_COMMAND;
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task, with missing AddTime for FROM The output
	 * is: This command is invalid
	 */
	@Test
	public void testAddTimeWithMissingFROM() {
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("TO", "3pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = MessageList.MESSAGE_INVALID_COMMAND;
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task, with the time in 10 pm format, adding a
	 * space in between time and am or pm The output is : Task ID: 4
	 * Description: Submit Assignment Start Time: 10.00 PM End Time: 11.00 PM
	 * Deadline: 17 April, 2015 (Fri) Status: Pending
	 */
	@Test
	public void testAddTimeInDifferentFormat() {
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "10 pm");
		keyFieldsTest.put("TO", "11 pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = String
				.format(MessageList.MESSAGE_ADDED,
						"\nTask ID: 4\nDescription: Submit Assignment\nStart Time: 10.00 PM\nEnd Time: 11.00 PM\nDeadline: 17 April, 2015 (Fri)\nStatus: Pending");
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task, with the time in 10 pm format, adding a
	 * space in between time and am or pm The output is : Invalid argument for
	 * add command.\nPlease specify what to add
	 */
	@Test
	public void testAddEmptyDescription() {
		keyFieldsTest.put("ADD", "");
		keyFieldsTest.put("FROM", "10 pm");
		keyFieldsTest.put("TO", "11 pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = String.format(MessageList.MESSAGE_ADD_NO_DESCRIPTION,
				"add");
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task, with the time in not whole number format
	 * eg. 10.30pm The output is : Task ID: 4 Description: submit Start Time:
	 * 10.30 PM End Time: 11.00 PM Deadline: 17 April, 2015 (Fri) Status: Pending");
	 * Task added
	 */
	@Test
	public void testAddTimeInBetween() {
		keyFieldsTest.put("ADD", "submit");
		keyFieldsTest.put("FROM", "10.30pm");
		keyFieldsTest.put("TO", "11pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = String
				.format(MessageList.MESSAGE_ADDED,
						"\nTask ID: 4\nDescription: submit\nStart Time: 10.30 PM\nEnd Time: 11.00 PM\nDeadline: 17 April, 2015 (Fri)\nStatus: Pending");
		assertEquals(expected,
				AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

}
