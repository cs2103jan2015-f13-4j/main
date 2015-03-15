import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SortHandlerTest {

	ArrayList<KeyParamPair> keyParamTest;
	ArrayList<Task> taskList;
	String fileName = "taskList.txt";
	
	@Before
	public void setUp() throws Exception {
		
		DateTime startDate = DateParser.generateDate("12/3/2015");
		DateTime endDate = DateParser.generateDate("25/1/2015");
		keyParamTest = new ArrayList<KeyParamPair>();
		taskList = new ArrayList<Task>();
		
		taskList.add(new Task(1, "CE1", startDate, endDate, true, ""));
		taskList.add(new Task(2, "CE2", startDate, endDate, true, ""));
		taskList.add(new Task(3, "V5.0", startDate, startDate, false, ""));
		taskList.add(new Task(4, "Proj Demo", startDate, endDate, false, ""));
		taskList.add(new Task(5, "Proj Video", startDate, endDate, false, ""));
	}

	@After
	public void tearDown() throws Exception {
		keyParamTest.clear();
	}

	@Test
	public void testSortDescription() {
		
		keyParamTest.add(new KeyParamPair("sort", ""));
		keyParamTest.add(new KeyParamPair("description", ""));
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: 12 March, 2015\n";
		assertEquals(expected, SortHandler.executeSort(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testSortDeadline() {
		
		keyParamTest.add(new KeyParamPair("sort", ""));
		keyParamTest.add(new KeyParamPair("deadline", ""));
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: 12 March, 2015\n";
		assertEquals(expected, SortHandler.executeSort(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testSortStartDate() {
		
		keyParamTest.add(new KeyParamPair("sort", ""));
		keyParamTest.add(new KeyParamPair("startdate", ""));
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: 12 March, 2015\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n";
		assertEquals(expected, SortHandler.executeSort(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testSortCompleted() {
		
		keyParamTest.add(new KeyParamPair("sort", ""));
		keyParamTest.add(new KeyParamPair("completed", ""));
		
		String expected = "\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: 12 March, 2015\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n";
		assertEquals(expected, SortHandler.executeSort(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testSortPending() {
		
		keyParamTest.add(new KeyParamPair("sort", ""));
		keyParamTest.add(new KeyParamPair("pending", ""));
		
		String expected = "\nTask ID: 3\nDescription: V5.0\nStart from: 12 March, 2015\nDeadline: 12 March, 2015\n\nTask ID: 4\nDescription: Proj Demo\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 5\nDescription: Proj Video\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 1\nDescription: CE1\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n\nTask ID: 2\nDescription: CE2\nStart from: 12 March, 2015\nDeadline: 25 January, 2015\n";
		assertEquals(expected, SortHandler.executeSort(fileName, keyParamTest, taskList));
	}

}
