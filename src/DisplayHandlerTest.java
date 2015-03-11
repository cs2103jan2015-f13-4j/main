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
		
		LocalDate date = new LocalDate();
		DateTime endDate = DateParser.generateDate(date.toString());
		keyParamTest = new ArrayList<KeyParamPair>();
		taskList = new ArrayList<Task>();
		taskList.add(new Task(1, "CE1", new DateTime(), new DateTime(), true, ""));
		taskList.add(new Task(2, "CE2", new DateTime(), new DateTime(), true, ""));
		taskList.add(new Task(3, "V5.0", new DateTime(), endDate, false, ""));
		taskList.add(new Task(4, "Proj Demo", new DateTime(), new DateTime(), false, ""));
		taskList.add(new Task(5, "Proj Video", new DateTime(), new DateTime(), false, ""));
	}

	@After
	public void tearDown() throws Exception {
		
		keyParamTest.clear();
	}
	
	@Test
	public void testDisplayTodayTasks() {
		
		keyParamTest.add(new KeyParamPair("display", ""));
		keyParamTest.add(new KeyParamPair("today", ""));
		
		String expected = "Success";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyParamTest, taskList));
	}

	@Test
	public void testDisplayCompletedTasks() {
		
		keyParamTest.add(new KeyParamPair("display", ""));
		keyParamTest.add(new KeyParamPair("todo", ""));
		
		String expected = "Success";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testDisplayNotCompletedTasks() {
		
		keyParamTest.add(new KeyParamPair("display", ""));
		keyParamTest.add(new KeyParamPair("pending", ""));
		
		String expected = "Success";
		assertEquals(expected, DisplayHandler.executeDisplay(fileName, keyParamTest, taskList));
	}
}
