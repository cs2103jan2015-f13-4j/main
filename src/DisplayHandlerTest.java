import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DisplayHandlerTest {

	ArrayList<KeyParamPair> keyParamTest;
	ArrayList<Task> taskList;
	String fileName = "taskList.txt";
	
	@Before
	public void setUp() throws Exception {
		
		DateTime testDate = DateParser.generateDate("25/1/2015");
		keyParamTest = new ArrayList<KeyParamPair>();
		taskList = new ArrayList<Task>();
		
		taskList.add(new Task(1, "CE1", new DateTime(), testDate, true, ""));
		taskList.add(new Task(2, "CE2", new DateTime(), testDate, true, ""));
		taskList.add(new Task(3, "V5.0", new DateTime(), new DateTime(), false, ""));
		taskList.add(new Task(4, "Proj Demo", new DateTime(), testDate, false, ""));
		taskList.add(new Task(5, "Proj Video", new DateTime(), testDate, false, ""));
	}

	@After
	public void tearDown() throws Exception {
		
		keyParamTest.clear();
	}
	
	@Test
	public void testDisplaySchedule() {
		
		keyParamTest.add(new KeyParamPair("display", ""));
		keyParamTest.add(new KeyParamPair("schedule", ""));
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: 12 March, 2015\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testDisplayTodayTasks() {
		
		keyParamTest.add(new KeyParamPair("display", ""));
		keyParamTest.add(new KeyParamPair("today", ""));
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: 12 March, 2015\n";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyParamTest, taskList));
	}

	@Test
	public void testDisplayCompletedTasks() {
		
		keyParamTest.add(new KeyParamPair("display", ""));
		keyParamTest.add(new KeyParamPair("todo", ""));
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testDisplayNotCompletedTasks() {
		
		keyParamTest.add(new KeyParamPair("display", ""));
		keyParamTest.add(new KeyParamPair("pending", ""));
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: 12 March, 2015\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyParamTest, taskList));
	}
}
