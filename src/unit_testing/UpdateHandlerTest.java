//@A0111935L
package unit_testing;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import logic.UpdateHandler;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.FileStorage;
import utility.MessageList;
import data.Data;
import data.Task;

public class UpdateHandlerTest {

	Map<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "taskListTest.txt";

	@Before
	public void setUp() {
		int year = 2015;
		int month = 9;
		int day = 3;
		int hour = 0;
		int min = 0;
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		keyFieldsTest = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), false, "", true));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), false, "", true));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(
				year, month, day, hour, min), new DateTime(year, month, day,
				hour + 23, min), false, "", true));
	}

	@After
	public void tearDown() {
		keyFieldsTest.clear();
		File textList = new File(fileName);
		textList.delete();

	}

	/* This is a boundary case for the ‘0’ partition */
	@Test
	public void testUpdateZeroTaskId() {
		keyFieldsTest.put("UPDATE", "0");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		String expected = MessageList.MESSAGE_NO_SUCH_TASK;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case test for invalid task id */
	@Test
	public void testUpdateInvalidTaskId() {
		keyFieldsTest.put("UPDATE", "abc");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		String expected = String.format(
				MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Update");
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/*This test case test for regular update task description and return success message*/
	@Test
	public void testUpdateWithDescRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		String expected = String.format(MessageList.MESSAGE_UPDATE_SUCCESS, "\nTask ID: 2\nDescription: Submit report to Ms Sarah and to IVLE\nStart Time: 12.00 AM\nEnd Time: 11.00 PM\nDeadline: 3 September, 2015 (Thu)\nStatus: Pending");
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/*This test case test for regular update task description */
	@Test
	public void testUpdateWithDescCheckRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		String expected = "Submit report to Ms Sarah and to IVLE";
		UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest);
		for (int i = 0; i < smtDataTest.getSize(); i++) {
			if (smtDataTest.getATask(i).getTaskId() == 2) {
				assertEquals(expected, smtDataTest.getATask(i)
						.getTaskDescription());
			}
		}
	}

	/*This test case test update task to weekly task*/
	@Test
	public void testUpdateWithWeeklyCheckRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("every", "friday");
		String expected = "friday";
		UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest);
		for (int i = 0; i < smtDataTest.getSize(); i++) {
			if (smtDataTest.getATask(i).getTaskId() == 2) {
				assertEquals(expected, smtDataTest.getATask(i).getWeeklyDay());
			}
		}
	}


	/*This test case test update with description and deadline regular*/
	@Test
	public void testUpdateWithDescAndByRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		keyFieldsTest.put("by", "03-03-2016");
		String expected = String.format(MessageList.MESSAGE_UPDATE_SUCCESS, "\nTask ID: 2\nDescription: Submit report to Ms Sarah and to IVLE\nStart Time: 12.00 AM\nEnd Time: 11.00 PM\nDeadline: 3 March, 2016 (Thu)\nStatus: Pending");
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/*This test case test update with regular deadline*/
	@Test
	public void testUpdateWithByRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("by", "03-03-2016");
		String expected = String.format(MessageList.MESSAGE_UPDATE_SUCCESS, "\nTask ID: 2\nDescription: Submit report to Ms Sarah\nStart Time: 12.00 AM\nEnd Time: 11.00 PM\nDeadline: 3 March, 2016 (Thu)\nStatus: Pending");
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/*This test case test update with start time regular*/
	@Test
	public void testUpdateWithStartTimeRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("from", "5pm");
		String expected = String.format(MessageList.MESSAGE_UPDATE_SUCCESS, "\nTask ID: 2\nDescription: Submit report to Ms Sarah\nStart Time: 5.00 PM\nEnd Time: 11.00 PM\nDeadline: 3 September, 2015 (Thu)\nStatus: Pending");
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/*This test case test update with end time regular*/
	@Test
	public void testUpdateWithEndTimeRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("to", "11pm");
		String expected = String.format(MessageList.MESSAGE_UPDATE_SUCCESS, "\nTask ID: 2\nDescription: Submit report to Ms Sarah\nStart Time: 12.00 AM\nEnd Time: 11.00 PM\nDeadline: 3 September, 2015 (Thu)\nStatus: Pending");
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/*This test case test start time end time regular*/
	@Test
	public void testUpdateWithStartTimeAndEndTimeRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("FROM", "5pm");
		keyFieldsTest.put("TO", "6pm");
		String expected = String.format(MessageList.MESSAGE_UPDATE_SUCCESS, "\nTask ID: 2\nDescription: Submit report to Ms Sarah\nStart Time: 5.00 PM\nEnd Time: 6.00 PM\nDeadline: 3 September, 2015 (Thu)\nStatus: Pending");
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error message if found null in hashmap */
	@Test
	public void testUpdateWithNullKeyFields() {
		String expected = MessageList.MESSAGE_NULL;
		assertEquals(expected, UpdateHandler.executeUpdate(null, smtDataTest));
	}

	/* This test case will give error message if found null for Data */
	@Test
	public void testUpdateWithNullTaskList() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah.");
		String expected = MessageList.MESSAGE_NO_TASK_IN_LIST;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, null));
	}

	/* This test case will give error message if description is empty */
	@Test
	public void testUpdateWithDescEmpty() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "");
		String expected = MessageList.MESSAGE_DESCRIPTION_EMPTY;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error is description is null */
	@Test
	public void testUpdateWithDescNull() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", null);
		String expected = MessageList.MESSAGE_DESCRIPTION_EMPTY;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if start time is null */
	@Test
	public void testUpdateWithStartTimeNull() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("from", null);
		String expected = MessageList.MESSAGE_NO_TIME_GIVEN;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if start time is empty */
	@Test
	public void testUpdateWithStartTimeEmpty() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("from", "");
		String expected = MessageList.MESSAGE_NO_TIME_GIVEN;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if wrong time format */
	@Test
	public void testUpdateWithStartTimeInvalid() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("from", "5ap");
		String expected = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if end time is null */
	@Test
	public void testUpdateWithEndTimeNull() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("to", null);
		String expected = MessageList.MESSAGE_NO_TIME_GIVEN;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if end time is empty */
	@Test
	public void testUpdateWithEndTimeEmpty() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("to", "");
		String expected = MessageList.MESSAGE_NO_TIME_GIVEN;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if wrong time format */
	@Test
	public void testUpdateWithEndTimeInvalid() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("to", "11pa");
		String expected = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if weekly is set to empty*/
	@Test
	public void testUpdateWithWeeklyEmpty() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("every", "");
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;

		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if weekly is set to null */
	@Test
	public void testUpdateWithWeeklyNull() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("every", null);
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;

		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case is test the boundary case for weekly day */
	@Test
	public void testUpdateWithWeeklyInvalid() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("every", "testing");
		String expected = String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT,
				"Weekly");

		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/*
	 * This is to check if the value in the hashmap is null, it will trigger an
	 * error message
	 */
	@Test
	public void testUpdateWithStatusNull() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("complete", null);
		String expected = MessageList.MESSAGE_UPDATE_STATUS_EXTRA_FIELD;

		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This test case will give error if there is some words beside incomplete */
	@Test
	public void testUpdateWithStatusInvalid() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("incomplete", "abc");
		String expected = String.format(
				MessageList.MESSAGE_UPDATE_STATUS_EXTRA_FIELD, "Weekly");

		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

	/* This is to check if the input can be converted into integer */
	@Test
	public void testInvalidConvertAnInteger() {
		keyFieldsTest.put("UPDATE", "ser@");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah.");
		String expected = String.format(
				MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Update");
		assertEquals(expected,
				UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

}
