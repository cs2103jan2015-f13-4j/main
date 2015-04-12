//@author A0112978W
package unit_testing;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import logic.DisplayHandler;
import logic.SortHandler;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.DateTimeParser;
import storage.FileStorage;
import utility.MessageList;
import data.Data;
import data.Task;


public class SortHandlerTest {

	Map<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "taskListTest.txt";
	
	@Before
	public void setUp() throws Exception {
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		DateTime startDate = DateTimeParser.generateDate("27/8/2015");
		DateTime endDate = DateTimeParser.generateDate("30/8/2015");
		keyFieldsTest = new TreeMap<String, String>();
		
		smtDataTest.addATaskToList(new Task(1, "CE1", null, endDate, true, "", true));
		smtDataTest.addATaskToList(new Task(2, "CE2", null, endDate, true, "", true));
		smtDataTest.addATaskToList(new Task(3, "V5.0", null, startDate, false, "", true));
		smtDataTest.addATaskToList(new Task(4, "Proj Demo", null, endDate, false, "", true));
		smtDataTest.addATaskToList(new Task(5, "Proj Video", null, endDate, false, "", true));
	}

	@After
	public void tearDown() throws Exception {
		keyFieldsTest.clear();
	}

	// To test sorting empty task list
	@Test
	public void testSortEmpty() {
		
		smtDataTest.clearTaskList();
		keyFieldsTest.put("SORT", "DESCRIPTION");
		
		String expected = MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		assertEquals(expected, SortHandler.executeSort(keyFieldsTest, smtDataTest));
	}
	
	// To test sorting tasks by description
	@Test
	public void testSortDescription() {
		
		keyFieldsTest.put("SORT", "DESCRIPTION");
		
		String expected = "\nTask ID: 1\nDescription: CE1\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 4\nDescription: Proj Demo\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 3\nDescription: V5.0\nEnd Time: 11.59 PM\nDeadline: 27 August, 2015 (Thu)\nStatus: Pending\n";
		assertEquals(expected, SortHandler.executeSort(keyFieldsTest, smtDataTest));
	}
	
	// To test sorting tasks by deadline
	@Test
	public void testSortDeadline() {
		
		keyFieldsTest.put("SORT", "DEADLINE");
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nEnd Time: 11.59 PM\nDeadline: 27 August, 2015 (Thu)\nStatus: Pending\n\nTask ID: 1\nDescription: CE1\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 4\nDescription: Proj Demo\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n";
		assertEquals(expected, SortHandler.executeSort(keyFieldsTest, smtDataTest));
	}
	
	// To test sorting tasks by start time
	@Test
	public void testSortStartTime() {
		
		keyFieldsTest.put("SORT", "STARTTIME");
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nEnd Time: 11.59 PM\nDeadline: 27 August, 2015 (Thu)\nStatus: Pending\n\nTask ID: 1\nDescription: CE1\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 4\nDescription: Proj Demo\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n";
		assertEquals(expected, SortHandler.executeSort(keyFieldsTest, smtDataTest));
	}
	
	// To test sorting tasks by completed status
	@Test
	public void testSortCompleted() {
		
		keyFieldsTest.put("SORT", "COMPLETED");
		
		String expected = "\nTask ID: 1\nDescription: CE1\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 3\nDescription: V5.0\nEnd Time: 11.59 PM\nDeadline: 27 August, 2015 (Thu)\nStatus: Pending\n\nTask ID: 4\nDescription: Proj Demo\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n";
		assertEquals(expected, SortHandler.executeSort(keyFieldsTest, smtDataTest));
	}
	
	// To test sorting tasks by pending status
	@Test
	public void testSortPending() {
		
		keyFieldsTest.put("SORT", "PENDING");
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nEnd Time: 11.59 PM\nDeadline: 27 August, 2015 (Thu)\nStatus: Pending\n\nTask ID: 4\nDescription: Proj Demo\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 1\nDescription: CE1\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n";
		assertEquals(expected, SortHandler.executeSort(keyFieldsTest, smtDataTest));
	}
	
	// To test invalid sort argument
	@Test
	public void testSortInvalid() {
		
		keyFieldsTest.put("SORT", "EVERYTHING");
		
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		assertEquals(expected, SortHandler.executeSort(keyFieldsTest, smtDataTest));
	}
}
