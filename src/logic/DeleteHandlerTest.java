import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DeleteHandlerTest {

	String fileName = "testfile.txt";
	ArrayList<KeyFieldPair> keyFieldsTest;
	ArrayList<Task> taskList;
	
	@Before
	public void setUp() {
		keyFieldsTest = new ArrayList<KeyFieldPair>();
		taskList = new ArrayList<Task>();
	}

	@After
	public void tearDown() {
		keyFieldsTest.clear();
		taskList.clear();
	}
	
	@Test
	public void testDeleteWithIDRegular() {
		taskList.add(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		taskList.add(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		taskList.add(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
		keyFieldsTest.add(new KeyFieldPair("delete", "2"));
		String expected = String.format(MessageList.MESSAGE_DELETE_SUCCESS, fileName, "Submit report to Ms Sarah");
		assertEquals(expected, DeleteHandler.executeDelete(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testDeleteWithIDEmpty(){
		keyFieldsTest.add(new KeyFieldPair("delete", ""));
		String expected = MessageList.MESSAGE_NO_FILE_DELETED;
		assertEquals(expected, DeleteHandler.executeDelete(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testDeleteWithIDInvalid(){
		taskList.add(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		taskList.add(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		taskList.add(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
		keyFieldsTest.add(new KeyFieldPair("delete", "1. Prepare a proposal"));
		String expected = MessageList.MESSAGE_INVALID_DELETE;
		assertEquals(expected, DeleteHandler.executeDelete(fileName, keyFieldsTest, taskList));
	}
}
