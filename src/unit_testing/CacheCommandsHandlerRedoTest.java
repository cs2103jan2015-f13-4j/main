//@A0112978W
package unit_testing;

import static org.junit.Assert.*;
import logic.CacheCommandsHandler;
import logic.DisplayHandler;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.DateTimeParser;
import storage.FileStorage;
import utility.MessageList;
import data.Data;
import data.Task;

public class CacheCommandsHandlerRedoTest {
	
	Data smtDataTest;
	String fileName = "taskList.txt";

	@Before
	public void setUp() throws Exception {
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		
		DateTime endDate = DateTimeParser.generateDate("30/8/2015");
		
		smtDataTest.addATaskToList(new Task(1, "CE1", null, endDate, true, "", true));
		CacheCommandsHandler.newHistory(smtDataTest);
		smtDataTest.addATaskToList(new Task(2, "CE2", null, endDate, true, "", true));
		CacheCommandsHandler.newHistory(smtDataTest);
	}
	
	@After
	public void tearDown() throws Exception {
		smtDataTest.clearTaskList();
	}
	
	// To test no command entered
	@Test
	public void testRedoEmpty() {
		smtDataTest.clearTaskList();
		
		String expected = MessageList.MESSAGE_LAST_COMMAND;
		assertEquals(expected, CacheCommandsHandler.executeRedo(smtDataTest));
	}

	// To test a valid redo (2 commands entered, then perform undo follow by redo)
	@Test
	public void testRedoValid() {
		
		CacheCommandsHandler.executeUndo(smtDataTest);
		
		String expected = MessageList.MESSAGE_REDO_SUCCESS;
		assertEquals(expected, CacheCommandsHandler.executeRedo(smtDataTest));
	}
	
	// To test an invalid redo (2 commands entered, then perform redo)
	@Test
	public void testRedoInvalid() {
		
		String expected = MessageList.MESSAGE_LAST_COMMAND;
		assertEquals(expected, CacheCommandsHandler.executeRedo(smtDataTest));
	}
}
