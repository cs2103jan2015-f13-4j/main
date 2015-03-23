package storage;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.IndicatorMessagePair;
import utility.MessageList;


public class FileStorageTest {
	IndicatorMessagePair msgPair;
	private String testFileName = "taskListTest.txt";
	private String testLastUnusedIndex = "lastUnUsedIndexFileNameTest.txt";
	
	@Before
	public void setUp(){
		msgPair = new IndicatorMessagePair();
	}

	@After
	public void tearDown(){
		msgPair = null;
	}

	@Test
	public void testLoadToArrayListValidFileName() {
		FileStorage.setFileNameForTasksList(testFileName);
		FileStorage.checkAndLoadTaskFile(msgPair);
		assertTrue(msgPair.isTrue());
	}
	
	@Test
	public void testLoadToArrayListInvalidFileName() {
		FileStorage.setFileNameForTasksList("taskListstxt");
		FileStorage.checkAndLoadTaskFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_FORMAT;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLoadToArrayListEmptyFileName() {
		FileStorage.setFileNameForTasksList("");
		FileStorage.checkAndLoadTaskFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLoadToArrayListNullFileName() {
		FileStorage.setFileNameForTasksList(null);
		FileStorage.checkAndLoadTaskFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLastUnusedIndexValidFileName() {
		FileStorage.setFileNameForLastUnusedIndex(testLastUnusedIndex);
		FileStorage.checkAndLoadLastTaskIndexFile(msgPair);
		assertTrue(msgPair.isTrue());
	}
	
	@Test
	public void testLastUnusedIndexInvalidFileName() {
		FileStorage.setFileNameForLastUnusedIndex("taskListtxt");
		FileStorage.checkAndLoadLastTaskIndexFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_FORMAT;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLastUnusedIndexEmptyFileName() {
		FileStorage.setFileNameForLastUnusedIndex("");
		FileStorage.checkAndLoadLastTaskIndexFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLastUnusedIndexNullFileName() {
		FileStorage.setFileNameForLastUnusedIndex(null);
		FileStorage.checkAndLoadLastTaskIndexFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testLastUnusedIndexWriteToFile() {
		FileStorage.setFileNameForLastUnusedIndex(testLastUnusedIndex);
		Integer lastUnusedIndex = 1;
		FileStorage.writeToFile(lastUnusedIndex,  msgPair);
		assertTrue(msgPair.isTrue());
	}

}
