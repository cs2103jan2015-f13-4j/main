package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Data;
import data.Task;
import utility.IndicatorMessagePair;
import utility.MessageList;


public class FileStorageTest {
	IndicatorMessagePair msgPair;
	private String testFileName = "taskListTest.txt";
	private String testLastUnusedIndex = "lastUnUsedIndexFileNameTest.txt";
	private String testBlockedDateTimeFileName = "blockedDateTimeFileNameTest.txt";
	
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
	
	/*This test case give error if filename is invalid*/
	@Test
	public void testLoadToArrayListInvalidFileName() {
		FileStorage.setFileNameForTasksList("taskListstxt");
		FileStorage.checkAndLoadTaskFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_FORMAT;
		assertEquals(expected, msgPair.getMessage());
	}
	
	/*This test case give error if empty filename*/
	@Test
	public void testLoadToArrayListEmptyFileName() {
		FileStorage.setFileNameForTasksList("");
		FileStorage.checkAndLoadTaskFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	/*This test case give error if null filename*/
	@Test
	public void testLoadToArrayListNullFileName() {
		FileStorage.setFileNameForTasksList(null);
		FileStorage.checkAndLoadTaskFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testTaskListWriteToFile() {
		Data smtDataTest = new Data();
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal",
				new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah",
				new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(),
				new DateTime(), ""));
		FileStorage.setFileNameForBlockedDatesList(testFileName);
		msgPair = FileStorage.writeToFile(smtDataTest.getListTask());
		assertTrue(msgPair.isTrue());
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
		msgPair = FileStorage.writeToFile(lastUnusedIndex);
		assertTrue(msgPair.isTrue());
	}

	@Test
	public void testBlockedDateTimeValidFileName() {
		FileStorage.setFileNameForBlockedDatesList(testBlockedDateTimeFileName);
		FileStorage.checkAndLoadBlockedDateFile(msgPair);
		assertTrue(msgPair.isTrue());
	}
	
	@Test
	public void testBlockedDateTimeInvalidFileName() {
		FileStorage.setFileNameForBlockedDatesList("taskListtxt");
		FileStorage.checkAndLoadBlockedDateFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_FORMAT;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testBlockedDateTimeEmptyFileName() {
		FileStorage.setFileNameForBlockedDatesList("");
		FileStorage.checkAndLoadBlockedDateFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testBlockedDateTimeNullFileName() {
		FileStorage.setFileNameForBlockedDatesList(null);
		FileStorage.checkAndLoadBlockedDateFile(msgPair);
		String expected = MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED;
		assertEquals(expected, msgPair.getMessage());
	}
	
	@Test
	public void testBlockedDateTimeWriteToFile() {
		FileStorage.setFileNameForBlockedDatesList(testBlockedDateTimeFileName);
		ArrayList<DateTime> listBlockedDateTime = new ArrayList<DateTime>();
		listBlockedDateTime.add(new DateTime());
		msgPair = FileStorage.writeBlockedDateTimeToFile(listBlockedDateTime);
		assertTrue(msgPair.isTrue());
	}
}
