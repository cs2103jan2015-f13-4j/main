//@A0112978W
package unit_testing;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import logic.DisplayHandler;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.DateTimeParser;
import storage.FileStorage;
import utility.MessageList;
import data.Data;
import data.Task;


public class DisplayHandlerTest {

	Map<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "taskList.txt";
	
	String today = DateTimeParser.displayDate(new DateTime());
	
	@Before
	public void setUp() throws Exception {
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		DateTime endDate = DateTimeParser.generateDate("30/8/2015");
		keyFieldsTest = new TreeMap<String, String>();

		smtDataTest.addATaskToList(new Task(1, "CE1", null, endDate, true, "", true));
		smtDataTest.addATaskToList(new Task(2, "CE2", null, endDate, true, "", true));
		smtDataTest.addATaskToList(new Task(3, "V5.0", null, DateTimeParser.generateDate("today"), false, "", true));
		smtDataTest.addATaskToList(new Task(4, "Proj Demo", null, endDate, false, "", true));
		smtDataTest.addATaskToList(new Task(5, "Proj Video", null, endDate, false, "", true));
	}

	@After
	public void tearDown() throws Exception {
		
		keyFieldsTest.clear();
		smtDataTest.clearTaskList();
	}
	
	// To test displaying empty task list
	@Test
	public void testDisplayEmpty() {
		
		smtDataTest.clearTaskList();
		keyFieldsTest.put("DISPLAY", "ALL");
		
		String expected = MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}
	
	// To test displaying all tasks
	@Test
	public void testDisplaySchedule() {
		
		keyFieldsTest.put("DISPLAY", "ALL");
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nEnd Time: 11.59 PM\nDeadline: "+ today +"\nStatus: Pending\n\nTask ID: 1\nDescription: CE1\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 4\nDescription: Proj Demo\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}
	
	// To test displaying today's tasks
	@Test
	public void testDisplayTodayTasks() {
		
		keyFieldsTest.put("DISPLAY", "TODAY");
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nEnd Time: 11.59 PM\nDeadline: "+ today + "\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}

	// To test displaying completed tasks
	@Test
	public void testDisplayCompletedTasks() {
		
		keyFieldsTest.put("DISPLAY", "COMPLETED");
		
		String expected = "\nTask ID: 1\nDescription: CE1\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n";
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}
	
	// To test displaying pending tasks
	@Test
	public void testDisplayNotCompletedTasks() {
		
		keyFieldsTest.put("DISPLAY", "PENDING");
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nEnd Time: 11.59 PM\nDeadline: "+ today + "\nStatus: Pending\n\nTask ID: 4\nDescription: Proj Demo\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nEnd Time: 11.59 PM\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}
	
	// To test invalid display argument
	@Test
	public void testDisplayInvalid() {
		
		keyFieldsTest.put("DISPLAY", "EVERYTHING");
		
		String expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}
}
