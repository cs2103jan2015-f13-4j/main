//@author A0112502A
package unit_testing;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeMap;

import logic.DeleteHandler;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.DateTimeParser;
import storage.FileStorage;
import utility.MessageList;
import data.Data;
import data.Task;

/**
 * This class is the junit testing for the delete operation
 *
 */

public class DeleteHandlerTest {

	String fileName = "testfile.txt";
	Data smtDataTest;
	TreeMap<String, String> keyFieldsTest;
	ArrayList<Task> taskList;
	
	@Before
	public void setUp() {
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		DateTime startDate = DateTimeParser.generateDate("30/8/2015");
		DateTime endDate = DateTimeParser.generateDate("30/8/2015");
		keyFieldsTest = new TreeMap<String, String>();
		taskList = new ArrayList<Task>();
		
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal", startDate, endDate, ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah", null, null, ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", startDate, endDate, ""));
	}

	@After
	public void tearDown() {
		keyFieldsTest.clear();
		taskList.clear();
	}
	
	// This is to test normal deletion
	@Test
	public void testDeleteWithIDRegular() {
		keyFieldsTest.put("DELETE", "2");
		String expected = String.format("\nTask ID: 2\nDescription: Submit report to Ms Sarah\nStatus: Pending\nDeleted");
		assertEquals(expected, DeleteHandler.executeDelete(keyFieldsTest, smtDataTest));
	}
	
	// This is to test if user can delete with an empty input
	@Test
	public void testDeleteWithIDEmpty(){
		smtDataTest.clearTaskList();
		keyFieldsTest.put("DELETE", "");
		String expected = MessageList.MESSAGE_NO_FILE_DELETED;
		assertEquals(expected, DeleteHandler.executeDelete(keyFieldsTest, smtDataTest));
	}
	
	// This is to test if the ID that user has keyed in is invalid
	@Test
	public void testDeleteWithIDInvalid(){
		keyFieldsTest.put("DELETE", "4abc");
		String expected = MessageList.MESSAGE_INVALID_DELETE;
		assertEquals(expected, DeleteHandler.executeDelete(keyFieldsTest, smtDataTest));
	}
	
	//This is to test if user can delete with multiple ID
	@Test
	public void testDeleteWithMultipleID(){
		keyFieldsTest.put("DELETE", "1 2");
		String expected = MessageList.MESSAGE_INVALID_DELETE;
		assertEquals(expected, DeleteHandler.executeDelete(keyFieldsTest, smtDataTest));
	}
	
	// This is to test if the ID entered by user is out of bound
	@Test
	public void testDeleteOutOfBound() {
		keyFieldsTest.put("DELETE", "4");
		String expected = MessageList.MESSAGE_NO_FILE_DELETED;
		assertEquals(expected, DeleteHandler.executeDelete(keyFieldsTest, smtDataTest));
	}
}