//@author A0112502A
package unit_testing;

import static org.junit.Assert.*;

import java.io.File;

import logic.CacheCommandsHandler;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.FileStorage;
import utility.MessageList;
import data.Data;
import data.Task;


public class CacheCommandsHandlerUndoTest {
	
	String fileName = "testfile.txt";
	String fileNameLastUnusedIndex = "testfileLastUnusedIndex.txt";
	String fileNameBlockedDateList = "testfileBlockedDates.txt";
	Data smtDataTest;

	@Before
	public void setUp() {
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		FileStorage.setFileNameForLastUnusedIndex(fileNameLastUnusedIndex);
		FileStorage.setFileNameForBlockedDatesList(fileNameBlockedDateList);
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		CacheCommandsHandler.newHistory(smtDataTest);
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
	}

	@After
	public void tearDown() {
		smtDataTest = null;
		File textList = new File(fileName);
		textList.delete();
		textList = new File(fileNameLastUnusedIndex);
		textList.delete();
		textList = new File(fileNameBlockedDateList);
		textList.delete();
	}
	
	// This is to check if the stack is empty, if it is empty, undo operation cannot be done
	@Test
	public void testUndoEmpty(){
		smtDataTest.clearTaskList();
		String expected = MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
		assertEquals(expected, CacheCommandsHandler.executeUndo(smtDataTest));
	}
	
	// This is to test the valid undo operation
	@Test
	public void testUndoValid() {
		CacheCommandsHandler.newHistory(smtDataTest);
		String expected = MessageList.MESSAGE_UNDO_SUCCESS;
		assertEquals(expected, CacheCommandsHandler.executeUndo(smtDataTest));
	}
}
