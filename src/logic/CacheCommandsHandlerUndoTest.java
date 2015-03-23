package logic;

import static org.junit.Assert.*;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utility.MessageList;
import data.Data;
import data.Task;


public class CacheCommandsHandlerUndoTest {
	
	String fileName = "testfile.txt";
	Data smtDataTest;

	@Before
	public void setUp() {
		smtDataTest = new Data();
	}

	@After
	public void tearDown() {
		smtDataTest = null;
	}
	
	@Test
	public void testUndoEmpty(){
		String expected = MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
		assertEquals(expected, CacheCommandsHandler.executeUndo(smtDataTest));
	}
	
	@Test
	public void testUndoRegular() {
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		CacheCommandsHandler.newHistory(smtDataTest);
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		CacheCommandsHandler.newHistory(smtDataTest);
		String expected = MessageList.MESSAGE_UNDO_SUCCESS;
		assertEquals(expected, CacheCommandsHandler.executeUndo(smtDataTest));
	}
}
