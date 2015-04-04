package logic;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.MessageList;
import data.Data;
import data.Task;

public class SearchHandlerTest {
	HashMap<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "listTaskTest.txt";

	@Before
	public void setUp() {
		int year = 2015;
		int month = 4;
		int day = 3;
		int hour = 0;
		int min = 0;
		smtDataTest = new Data();
		keyFieldsTest = new HashMap<String, String>();
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
	public void tearDown() throws Exception {

		keyFieldsTest.clear();
	}

	/**
	 * This is to test the search task using the keyword taskDesc The output is:
	 * EE2024 report proposal
	 */
	@Test
	public void testSearchWithRegularTaskDesc() {
		keyFieldsTest.put("SEARCH", "2 EE2024 report proposal");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the search task using the keyword taskId The output is:
	 * The task you have key in first
	 */
	@Test
	public void testSearchWithRegularTaskId() {
		keyFieldsTest.put("SEARCH", "1");
		keyFieldsTest.put("1", "");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH_CRITERIA;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the search when there is no matching task The output is:
	 * No Match Found
	 */
	@Test
	public void testSearchWithNoMatch() {
		keyFieldsTest.put("SEARCH", "1 8");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the search by the deadline The output is: the task of the
	 * matching date
	 */
	@Test
	public void testSearchWithDeadLine() {
		keyFieldsTest.put("SEARCH", "3 03-04-2015");
		String expected = "\nTask ID: 1\nDescription: Prepare a proposal\nStart from: 3 April, 2015 (Fri)\nDeadline: 3 April, 2015 (Fri)\nStatus: Pending\n\nTask ID: 2\nDescription: Submit report to Ms Sarah\nStart from: 3 April, 2015 (Fri)\nDeadline: 3 April, 2015 (Fri)\nStatus: Pending\n\nTask ID: 3\nDescription: Prepare OP1\nStart from: 3 April, 2015 (Fri)\nDeadline: 3 April, 2015 (Fri)\nStatus: Pending\n";
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the search by deadline which do not match with any task
	 * user have in the list The output is: No match found
	 */
	@Test
	public void testSearchWithInvalidDeadLine() {
		keyFieldsTest.put("SEARCH", "3 14-03-2016");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test the search without any description added to the search
	 * task The output is: No Match Found
	 */
	@Test
	public void testSearchWithWrongFormatForTaskDesc() {
		keyFieldsTest.put("SEARCH", "2 14-03-2015");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to search if user did not key in any keyword and any input The
	 * output is: This is a invalid search
	 */
	@Test
	public void testSearchWithEmptyTask() {
		keyFieldsTest.put("SEARCH", "");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH_CRITERIA;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test if the search keyword key in by user is TaskId which do
	 * not match with the input search entered by user which is description The
	 * output is: Please enter a integer
	 */
	@Test
	public void testSearchWithIncorrectTaskIdCommand() {
		keyFieldsTest.put("SEACH", "1 Assignment1");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test if the search keyword key in by user is TaskDesc which do
	 * not match with the input search entered by user which is deadline The
	 * output is: Please enter a integer
	 */
	@Test
	public void testSearchWithIncorrectTaskDescCommand() {
		keyFieldsTest.put("SEACH", "2 1");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

	/**
	 * This is to test if the search keyword key in by user is Deadline which do
	 * not match with the input search entered by user which is taskId The
	 * output is: Please enter a integer
	 */
	@Test
	public void testSearchWithIncorrectDeadLineCommand() {
		keyFieldsTest.put("SEACH", "3, 1");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}

}
