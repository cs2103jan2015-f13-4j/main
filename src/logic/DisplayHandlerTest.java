package logic;
import static org.junit.Assert.*;


import java.util.Map;
import java.util.TreeMap;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.DateTimeParser;
import storage.FileStorage;
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
		DateTime startDate = DateTimeParser.generateDate("27/8/2015");
		DateTime endDate = DateTimeParser.generateDate("30/8/2015");
		keyFieldsTest = new TreeMap<String, String>();

		smtDataTest.addATaskToList(new Task(1, "CE1", startDate, endDate, true, ""));
		smtDataTest.addATaskToList(new Task(2, "CE2", startDate, endDate, true, ""));
		smtDataTest.addATaskToList(new Task(3, "V5.0", startDate, new DateTime(), false, ""));
		smtDataTest.addATaskToList(new Task(4, "Proj Demo", startDate, endDate, false, ""));
		smtDataTest.addATaskToList(new Task(5, "Proj Video", startDate, endDate, false, ""));
	}

	@After
	public void tearDown() throws Exception {
		
		keyFieldsTest.clear();
	}
	
	@Test
	public void testDisplaySchedule() {
		
		keyFieldsTest.put("DISPLAY", "ALL");
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 27 August, 2015 (Thu)\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nStart from: 27 August, 2015 (Thu)\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 3\nDescription: V5.0\nStart from: 27 August, 2015 (Thu)\nDeadline: "+ today +"\nStatus: Pending\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 27 August, 2015 (Thu)\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nStart from: 27 August, 2015 (Thu)\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testDisplayTodayTasks() {
		
		keyFieldsTest.put("DISPLAY", "TODAY");
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nStart from: 27 August, 2015 (Thu)\nDeadline: "+ today + "\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}

	@Test
	public void testDisplayCompletedTasks() {
		
		keyFieldsTest.put("DISPLAY", "COMPLETED");
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 27 August, 2015 (Thu)\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nStart from: 27 August, 2015 (Thu)\nDeadline: 30 August, 2015 (Sun)\nStatus: Completed\n";
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testDisplayNotCompletedTasks() {
		
		keyFieldsTest.put("DISPLAY", "PENDING");
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nStart from: 27 August, 2015 (Thu)\nDeadline: "+ today + "\nStatus: Pending\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 27 August, 2015 (Thu)\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nStart from: 27 August, 2015 (Thu)\nDeadline: 30 August, 2015 (Sun)\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(keyFieldsTest, smtDataTest));
	}
}
