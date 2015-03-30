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
		smtDataTest = new Data();
		keyFieldsTest = new HashMap<String, String>();
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal",
				new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah",
				new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(),
				new DateTime(), ""));
	}

	@After
	public void tearDown() throws Exception {

		keyFieldsTest.clear();
	}
	
	/**
	 * This is to test the search task using the keyword taskDesc
	 * The output is: EE2024 report proposal
	 */
	@Test
	public void testSearchWithRegularTaskDesc() {
		keyFieldsTest.put("SEARCH", "TaskDesc EE2024 report proposal");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}
	
	/**
	 * This is to test the search task using the keyword taskId
	 * The output is: The task you have key in first
	 */
	@Test
	public void testSearchWithRegularTaskId() {
		keyFieldsTest.put("SEARCH", "TaskId 1");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}
	
	/**
	 * This is to test the search when there is no matching task
	 * The output is: No Match Found
	 */
	@Test
	public void testSearchWithNoMatch() {
		keyFieldsTest.put("SEARCH","");
		keyFieldsTest.put("TaskId", "8");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}
	
	/**
	 * This is to test the search by the deadline
	 * The output is: the task of the matching date 
	 */
	@Test
	public void testSearchWithDeadLine() {
		keyFieldsTest.put("SEARCH","");
		keyFieldsTest.put("deadline", "29-03-2015");
		String expected = "\nTask ID: 1\nDescription: Prepare a proposal\nStart from: 29 March, 2015\nDeadline: 29 March, 2015\nStatus: Pending\nTask ID: 2\nDescription: Submit report to Ms Sarah\nStart from: 29 March, 2015\nDeadline: 29 March, 2015\nStatus: Pending\nTask ID: 3\nDescription: Prepare OP1\nStart from: 29 March, 2015\nDeadline: 29 March, 2015\nStatus: Pending";
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}
	
	/**
	 * This is to test the search by deadline which do not match with any task user have in the list
	 * The output is: No match found
	 */
	@Test
	public void testSearchWithInvalidDeadLine(){
		keyFieldsTest.put("SEARCH","");
		keyFieldsTest.put("deadline", "14-03-2015");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	/**
	 * This is to test the search without any description added to the search task
	 * The output is: No Match Found
	 */
	@Test
	public void testSearchWithWrongFormatForTaskDesc(){
		keyFieldsTest.put("SEARCH","");
		keyFieldsTest.put("TaskDesc", "14-03-2015");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	
	/**
	 * This is to search if user did not key in any keyword and any input
	 * The output is: This is a invalid search
	 */
	@Test
	public void testSearchWithEmptyTask(){
		keyFieldsTest.put("SEARCH","");
	    keyFieldsTest.put(" ", " ");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	
	/**
	 * This is to test if the search keyword key in by user is TaskId
	 * which do not match with the input search entered by user which is description
	 * The output is: Please enter a integer
	 */
	@Test
	public void testSearchWithIncorrectTaskIdCommand(){
		keyFieldsTest.put("SEACH","");
	    keyFieldsTest.put("TaskId", "Assignment1");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	
	/**
	 * This is to test if the search keyword key in by user is TaskDesc
	 * which do not match with the input search entered by user which is deadline
	 * The output is: Please enter a integer
	 */
	@Test
	public void testSearchWithIncorrectTaskDescCommand(){
		keyFieldsTest.put("SEACH","");
	    keyFieldsTest.put("TaskDesc", "11-04-2015");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	
	/**
	 * This is to test if the search keyword key in by user is Deadline
	 * which do not match with the input search entered by user which is taskId
	 * The output is: Please enter a integer
	 */
	@Test
	public void testSearchWithIncorrectDeadLineCommand(){
		keyFieldsTest.put("SEACH","");
	    keyFieldsTest.put("Deadline", "1");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	


}
