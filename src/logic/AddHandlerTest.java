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
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
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
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
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
		String expected = String.format(MessageList.MESSAGE_INCORRECT_DATE_FORMAT);
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
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
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
	
}
	

