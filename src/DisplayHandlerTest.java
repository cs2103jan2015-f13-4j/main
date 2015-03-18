import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DisplayHandlerTest {

	ArrayList<KeyFieldPair> keyFieldsTest;
	ArrayList<Task> taskList;
	String fileName = "taskList.txt";
	
	LocalDate date = new LocalDate();
	DateTime end = DateParser.generateDate(date.toString());
	String today = DateParser.displayDateTime(end);
	
	@Before
	public void setUp() throws Exception {
		
		DateTime startDate = DateParser.generateDate("12/3/2015");
		DateTime endDate = DateParser.generateDate("25/1/2015");
		keyFieldsTest = new ArrayList<KeyFieldPair>();
		taskList = new ArrayList<Task>();
		
		taskList.add(new Task(1, "CE1", startDate, endDate, true, ""));
		taskList.add(new Task(2, "CE2", startDate, endDate, true, ""));
		taskList.add(new Task(3, "V5.0", startDate, new DateTime(), false, ""));
		taskList.add(new Task(4, "Proj Demo", startDate, endDate, false, ""));
		taskList.add(new Task(5, "Proj Video", startDate, endDate, false, ""));
	}

	@After
	public void tearDown() throws Exception {
		
		keyFieldsTest.clear();
	}
	
	@Test
	public void testDisplaySchedule() {
		
		keyFieldsTest.add(new KeyFieldPair("display", ""));
		keyFieldsTest.add(new KeyFieldPair("schedule", ""));
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\nStatus: Completed\n\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: "+ today +"\nStatus: Pending\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testDisplayTodayTasks() {
		
		keyFieldsTest.add(new KeyFieldPair("display", ""));
		keyFieldsTest.add(new KeyFieldPair("today", ""));
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: "+ today + "\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyFieldsTest, taskList));
	}

	@Test
	public void testDisplayCompletedTasks() {
		
		keyFieldsTest.add(new KeyFieldPair("display", ""));
		keyFieldsTest.add(new KeyFieldPair("todo", ""));
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\nStatus: Completed\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\nStatus: Completed\n";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testDisplayNotCompletedTasks() {
		
		keyFieldsTest.add(new KeyFieldPair("display", ""));
		keyFieldsTest.add(new KeyFieldPair("pending", ""));
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: "+ today + "\nStatus: Pending\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\nStatus: Pending\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\nStatus: Pending\n";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyFieldsTest, taskList));
	}
}
