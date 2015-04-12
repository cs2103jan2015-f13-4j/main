//@author A0111935L
package unit_testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.IndicatorMessagePair;
import utility.MessageList;
import data.Data;
import data.Task;

public class DataTest {

	Data smtDataTest;
	String fileName = "taskListTest.txt";
	int year;
	int month;
	int day;
	int hour;
	int min;
	
	@Before
	public void setUp() throws Exception {
		year = 2016;
		month = 10;
		day = 4;
		hour = 0;
		min = 0;
		smtDataTest = new Data();
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), false, "", true));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), false, "", true));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(
				year, month, day, hour, min), new DateTime(year, month, day,
				hour + 23, min), false, "", true));
		
		smtDataTest.addBlockedDateTime(DateTime.now());
	}

	@After
	public void tearDown() throws Exception {
	}

	/*boundary case where Task ID is not before 1*/
	@Test
	public void testGetATaskInvalid() {
		String expected = null;
		assertEquals(expected, smtDataTest.getATask(-1));
	}
	
	/*test get a valid task*/
	@Test
	public void testGetATaskValid() {
		String expected = smtDataTest.getATask(1).toString();
		assertEquals(expected, smtDataTest.getATask(1).toString());
	}
	
	/*boundary case where Task ID is out of the list*/
	@Test
	public void testGetATaskInvalidTwo() {
		String expected = null;
		assertEquals(expected, smtDataTest.getATask(3));
	}

	/*test remove a task from the list*/
	@Test
	public void testRemoveATaskFromListValid() {
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		String expected = smtDataTest.getATask(2).toString();
		assertEquals(expected, smtDataTest.removeATaskFromList(2, indicMsg).toString());
	}
	
	/*boundary case where Task ID is out of the list (e.g 3) for remove*/
	@Test
	public void testRemoveATaskFromListInValid() {
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		String expected = null;
		assertEquals(expected, smtDataTest.removeATaskFromList(3, indicMsg));
	}
	
	/*test set a task*/
	@Test
	public void testSetATaskValid() {
		ArrayList<Task> updatedTaskList = new ArrayList<Task>();
		Task updatedTask = (new Task(1, "Prepare a proposal",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), false, "", true));
		updatedTaskList.add(updatedTask);
		String expected = updatedTask.toString();
		smtDataTest.setListTask(updatedTaskList);
		assertEquals(expected, smtDataTest.getATask(0).toString());
	}
	
	
	/*update a task*/
	@Test
	public void testUpdateATaskValid() {
		Task updatedTask = (new Task(1, "Prepare two proposal",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), false, "", true));
		String expected = updatedTask.toString();
		smtDataTest.updateTaskList(0, updatedTask);
		assertEquals(expected, smtDataTest.getATask(0).toString());
	}
	
	/*boundary case where the task id is not valid for update*/
	@Test
	public void testUpdateATaskInValid() {
		Task updatedTask = (new Task(1, "Prepare two proposal",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), false, "", true));
		String expected = MessageList.MESSAGE_INDEX_OUT_OF_RANGE;
		
		assertEquals(expected, smtDataTest.updateTaskList(-1, updatedTask).getMessage());
	}
	
	/*set blocked date list */
	@Test
	public void testSetBlockedDateListValid() {
		ArrayList<DateTime> blockedDateList = new ArrayList<DateTime>();
		blockedDateList.add(DateTime.now());
		smtDataTest.setBlockedDateTimeList(blockedDateList);
		assertEquals(blockedDateList.size(), smtDataTest.getBlockedDateTimeList().size());
	}
	
}
