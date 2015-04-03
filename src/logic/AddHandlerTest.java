package logic;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

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

	HashMap<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "taskListTest.txt";
	String lastUnUsedFileName = "lastUnUsedIndexTest.txt";
	
	
	@Before
	public void setUp() {
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		FileStorage.setFileNameForLastUnusedIndex(lastUnUsedFileName);
		keyFieldsTest = new HashMap<String, String>();
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
		
		
		//FileStorage.writeToFile(4, new IndicatorMessagePair());
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
	 * This is to test the correct format of adding in a task
	 * The output of this test is: Task Added
	 */
	@Test
	public void testAddWithDescRegular() {
		keyFieldsTest.put("ADD", "submit proposal");
		keyFieldsTest.put("BY", "03-03-2016");
		String expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test the the adding of correct format but without date
	 * The output of this test is: No Date Given
	 */
	@Test 
	public void testAddWithDescWithoutDate()	
	{
		keyFieldsTest.put("ADD", "Submit Proposal");
		keyFieldsTest.put("BY","");
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Add");
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	/**
	 * This is to test the adding of incorrect date format
	 * The output of this test is: Date Format is Incorrect
	 */
	@Test
	public void testAddWithDescWithWrongDateFormat()
	{
		keyFieldsTest.put("ADD","Submit Report");
		keyFieldsTest.put("BY","03-March-2014");
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Add");
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	/**
	 * This is to test the adding of incorrect date format for the data and year position
	 * The output of this test is: Date Format is Incorrect
	 */
	@Test
	public void testAddWithDescWithWrongDateFormatforMonth()
	{
		keyFieldsTest.put("ADD","Submit Report");
		keyFieldsTest.put("BY","03-2015-08");
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Add");
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	/**
	 * This is to test the adding of incorrect date format for the day in alphabetical form
	 * The output of this test is: Date Format is Incorrect 
	 */
	@Test
	public void testInvalidDate()
	{
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("BY", "AA-12-2015");
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Add");
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	/**
	 * This is to test adding the task by using day instead of a specified date
	 * The output of this test is: Task added
	 */
	@Test
	public void testAddByDay()
	{
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("BY", "Friday");
		String expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	/**
	 * This is to test adding the task within a certain period of time
	 * The output of this test is: Task added
	 */
	@Test
	public void testAddByTime()
	{
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "10am");
		keyFieldsTest.put("TO", "12pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	/**
	 * This is to test adding the task within a certain period of time
	 * The output of this test is: Task added
	 */
	@Test
	public void testAddInvalidTime()
	{
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "6pm");
		keyFieldsTest.put("TO", "5pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = "Time Mismatch";
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	/**
	 * This is to test adding the task, with missing time
	 * The output of this test is: Time is not entered correctly
	 */
	@Test
	public void testAddTimeIsEmpty()
	{
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "");
		keyFieldsTest.put("TO", "");
		keyFieldsTest.put("BY", "Friday");
		String expected = "Time is not entered correctly";
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}

	/**
	 * This is to test adding the task, with missing AddTime for TO
	 * The output of this test is: Time is not entered correctly
	 */
	@Test
	public void testAddTimeWithMissingTO()
	{
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "3pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = MessageList.MESSAGE_INVALID_COMMAND;
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	/**
	 * This is to test adding the task, with missing AddTime for FROM
	 * The output of this test is: Time is not entered correctly
	 */
	@Test
	public void testAddTimeWithMissingFROM()
	{
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("TO", "3pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = MessageList.MESSAGE_INVALID_COMMAND;
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
	//This got problem (CHECK)
	/**
	 * This is to test adding the task, with the time in wrong time format, e.g 10pm to 10zm
	 * The output of this test is: Time is not entered correctly
	 */
	@Test
	public void testAddTimeFormat()
	{
		keyFieldsTest.put("ADD", "Submit Assignment");
		keyFieldsTest.put("FROM", "10zm");
		keyFieldsTest.put("TO", "11pm");
		keyFieldsTest.put("BY", "Friday");
		String expected = "Time Mismatch";
		assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
	}
	
		/**
		 * This is to test adding the task, with the time in 10 pm format, adding a space
		 * in between time and am or pm
		 * The output of this test is: Task added
		 */
		@Test
		public void testAddTimeInDifferentFormat()
		{
			keyFieldsTest.put("ADD", "Submit Assignment");
			keyFieldsTest.put("FROM", "10 pm");
			keyFieldsTest.put("TO", "11 pm");
			keyFieldsTest.put("BY", "Friday");
			String expected = MessageList.MESSAGE_ADDED;
			assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
		}
		
		/**
		 * This is to test adding the task, with the time in 10 pm format, adding a space
		 * in between time and am or pm
		 * The output of this test is: Task added
		 */
		@Test
		public void testAddEmptyDescription()
		{
			keyFieldsTest.put("ADD", "");
			keyFieldsTest.put("FROM", "10 pm");
			keyFieldsTest.put("TO", "11 pm");
			keyFieldsTest.put("BY", "Friday");
			String expected = "Invalid argument for Add command.";
			assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
		}
		
		
		//GOT PROBLEM (NEED TO CHECK)
		/**
		 * This is to test adding the task, with the time in 10 pm format, adding a space
		 * in between time and am or pm
		 * The output of this test is: Task added
		 */
		@Test
		public void testAddTimeInBetween()
		{
			keyFieldsTest.put("ADD", "");
			keyFieldsTest.put("FROM", "10:30 pm");
			keyFieldsTest.put("TO", "11 pm");
			keyFieldsTest.put("BY", "Friday");
			String expected = "Task added.";
			assertEquals(expected, AddHandler.executeAdd(keyFieldsTest, smtDataTest));
		}
	
	
}
	

