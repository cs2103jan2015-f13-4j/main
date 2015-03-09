import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class FileHandlerTest {
	private static ArrayList<Task> taskListTest;
	IndicatorMessagePair msgPair;
	
	@Before
	public void setUp(){
		taskListTest = new ArrayList<Task>();
		msgPair = new IndicatorMessagePair();
	}

	@After
	public void tearDown(){
		taskListTest = null;
		msgPair = null;
	}

	@Test
	public void testLoadToArrayListValidFileName() {
		String fileName = "taskList.txt";
		FileHandler.checkAndLoadTaskFile(fileName, taskListTest, msgPair);
		assertTrue(msgPair.isTrue());
	}
	
	@Test
	public void testLoadToArrayListInvalidFileName() {
		String fileName = "taskListtxt";
		FileHandler.checkAndLoadTaskFile(fileName, taskListTest, msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_FORMAT;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLoadToArrayListEmptyFileName() {
		String fileName = "";
		FileHandler.checkAndLoadTaskFile(fileName, taskListTest, msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLoadToArrayListNullFileName() {
		String fileName = null;
		FileHandler.checkAndLoadTaskFile(fileName, taskListTest, msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLastUnusedIndexValidFileName() {
		String fileName = "lastUnused.txt";
		FileHandler.checkAndLoadLastTaskIndexFile(fileName, msgPair);
		assertTrue(msgPair.isTrue());
	}
	
	@Test
	public void testLastUnusedIndexInvalidFileName() {
		String fileName = "taskListtxt";
		FileHandler.checkAndLoadLastTaskIndexFile(fileName, msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_FORMAT;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLastUnusedIndexEmptyFileName() {
		String fileName = "";
		FileHandler.checkAndLoadLastTaskIndexFile(fileName, msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLastUnusedIndexNullFileName() {
		String fileName = null;
		FileHandler.checkAndLoadLastTaskIndexFile(fileName, msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}

}
