import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DeleteHandlerTest {

	String fileName = "testfile.txt";
	ArrayList<KeyParamPair> keyParamTest;
	ArrayList<Task> taskList;
	
	@Before
	public void setUp() {
		keyParamTest = new ArrayList<KeyParamPair>();
		taskList = new ArrayList<Task>();
	}

	@After
	public void tearDown() {
		keyParamTest.clear();
		taskList.clear();
	}
	
	@Test
	public void testDeleteWithIDRegular() {
		taskList.add(new Task(1, "Prepare a proposal", new Date(), new Date()));
		taskList.add(new Task(2, "Submit report to Ms Sarah", new Date(), new Date()));
		taskList.add(new Task(3, "Prepare OP1", new Date(), new Date()));
		keyParamTest.add(new KeyParamPair("delete", "2"));
		String expected = String.format(MessageList.MESSAGE_DELETE_SUCCESS, fileName, "Submit report to Ms Sarah");
		assertEquals(expected, DeleteHandler.executeDelete(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testDeleteWithIDEmpty(){
		keyParamTest.add(new KeyParamPair("delete", ""));
		String expected = MessageList.MESSAGE_NO_FILE_DELETED;
		assertEquals(expected, DeleteHandler.executeDelete(fileName, keyParamTest, taskList));
	}
	
	@Test
	public void testDeleteWithIDInvalid(){
		taskList.add(new Task(1, "Prepare a proposal", new Date(), new Date()));
		taskList.add(new Task(2, "Submit report to Ms Sarah", new Date(), new Date()));
		taskList.add(new Task(3, "Prepare OP1", new Date(), new Date()));
		keyParamTest.add(new KeyParamPair("delete", "1. Prepare a proposal"));
		String expected = MessageList.MESSAGE_INVALID_DELETE;
		assertEquals(expected, DeleteHandler.executeDelete(fileName, keyParamTest, taskList));
	}
}
