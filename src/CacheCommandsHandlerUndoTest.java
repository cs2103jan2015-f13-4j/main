import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CacheCommandsHandlerUndoTest {
	
	String fileName = "testfile.txt";
	ArrayList<Task> taskList;

	@Before
	public void setUp() {
		taskList = new ArrayList<Task>();
	}

	@After
	public void tearDown() {
		taskList.clear();
	}
	
	@Test
	public void testUndoEmpty(){
		String expected = MessageList.MESSAGE_NO_PREVIOUS_COMMAND;
		assertEquals(expected, CacheCommandsHandler.undo(fileName, taskList, new IndicatorMessagePair()));
	}
	
	@Test
	public void testUndoRegular() {
		taskList.add(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		taskList.add(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		String expected = MessageList.MESSAGE_UNDO_SUCCESS;
		assertEquals(expected, CacheCommandsHandler.undo(fileName, taskList, new IndicatorMessagePair()));
	}
}
