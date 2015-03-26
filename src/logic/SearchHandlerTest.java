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

	@Test
	public void testSearchWithRegularTaskDesc() {
		keyFieldsTest.put("SEARCH", "TaskDesc EE2024 report proposal");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}
	
	@Test
	public void testSearchWithRegularTaskId() {
		keyFieldsTest.put("SEARCH", "TaskId 1");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}
	
	@Test
	public void testSearchWithNoMatch() {
		keyFieldsTest.put("SEARCH","");
		keyFieldsTest.put("TaskId", "1");
		String expected = "\nTask ID: 1\nDescription: Prepare a proposal\nStart from: 27 March, 2015\nDeadline: 27 March, 2015\nStatus: Pending";
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}
	@Test
	public void testSearchWithDeadLine() {
		keyFieldsTest.put("SEARCH","");
		keyFieldsTest.put("deadline", "27-03-2015");
		String expected = "\nTask ID: 1\nDescription: Prepare a proposal\nStart from: 27 March, 2015\nDeadline: 27 March, 2015\nStatus: Pending\nTask ID: 2\nDescription: Submit report to Ms Sarah\nStart from: 27 March, 2015\nDeadline: 27 March, 2015\nStatus: Pending\nTask ID: 3\nDescription: Prepare OP1\nStart from: 27 March, 2015\nDeadline: 27 March, 2015\nStatus: Pending";
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest);

	}
	
	@Test
	public void testSearchWithInvalidDeadLine(){
		keyFieldsTest.put("SEARCH","");
		keyFieldsTest.put("deadline", "14-03-2015");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	@Test
	public void testSearchWithWrongFormatForTaskDesc(){
		keyFieldsTest.put("SEARCH","");
		keyFieldsTest.put("TaskDesc", "14-03-2015");
		String expected = MessageList.MESSAGE_NO_MATCH_FOUND;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	
	@Test
	public void testSearchWithEmptyTask(){
		keyFieldsTest.put("SEARCH","");
	    keyFieldsTest.put(" ", " ");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	
	@Test
	public void testSearchWithIncorrectCommand(){
		keyFieldsTest.put("SEACH","");
	    keyFieldsTest.put("TaskDesc", "Assignment1");
		String expected = MessageList.MESSAGE_INVAILD_SEARCH;
		assertEquals(expected, SearchHandler.executeSearch(keyFieldsTest, smtDataTest));
		SearchHandler.executeSearch(keyFieldsTest, smtDataTest);
		
	}
	


}
