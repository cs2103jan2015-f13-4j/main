package logic;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.FileStorage;
import utility.MessageList;
import data.Data;
import data.Task;


public class DeleteHandlerTest {

	String fileName = "testfile.txt";
	Data smtDataTest;
	HashMap<String, String> keyFieldsTest;
	ArrayList<Task> taskList;
	
	@Before
	public void setUp() {
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		keyFieldsTest = new HashMap<String, String>();
		taskList = new ArrayList<Task>();
	}

	@After
	public void tearDown() {
		keyFieldsTest.clear();
		taskList.clear();
	}
	
	@Test
	public void testDeleteWithIDRegular() {
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
		keyFieldsTest.put("DELETE", "2");
		String expected = String.format(MessageList.MESSAGE_DELETE_SUCCESS, fileName, "Submit report to Ms Sarah");
		assertEquals(expected, DeleteHandler.executeDelete(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testDeleteWithIDEmpty(){
		keyFieldsTest.put("DELETE", "");
		String expected = MessageList.MESSAGE_NO_FILE_DELETED;
		assertEquals(expected, DeleteHandler.executeDelete(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testDeleteWithIDInvalid(){
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
		keyFieldsTest.put("DELETE", "1. Prepare a proposal");
		String expected = MessageList.MESSAGE_INVALID_DELETE;
		assertEquals(expected, DeleteHandler.executeDelete(keyFieldsTest, smtDataTest));
	}
}
