package logic;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.FileStorage;
import utility.MessageList;
import data.Data;
import data.Task;


public class UpdateHandlerTest {

	HashMap<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "taskListTest.txt";
	@Before
	public void setUp() {
		smtDataTest = new Data();
		FileStorage.setFileNameForTasksList(fileName);
		keyFieldsTest = new HashMap<String, String>();
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
	}

	@After
	public void tearDown() {
		keyFieldsTest.clear();
		File textList = new File(fileName);
		textList.delete();
		
	}

	@Test
	public void testUpdateWithDescRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithDescCheckRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		String expected = "Submit report to Ms Sarah and to IVLE";
		UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest);
		for(int i = 0; i < smtDataTest.getSize(); i++){
			if(smtDataTest.getATask(i).getTaskId() == 2){
				assertEquals(expected, smtDataTest.getATask(i).getTaskDescription());
			}
		}
	}
	
	@Test
	public void testUpdateWithDescAndEndDateRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		keyFieldsTest.put("taskend", "03-03-2015");
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithDescAndByRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah and to IVLE");
		keyFieldsTest.put("by", "03-03-2015");
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithByRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("by", "03-03-2015");
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithStartDateRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskstart", "02-01-2015");
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithEndDateRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskend", "02-01-2015");
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithStartDateAndEndDateRegular() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskstart", "02-01-2015");
		keyFieldsTest.put("taskend", "03-01-2015");
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithNullKeyFields() {
		String expected = MessageList.MESSAGE_NULL;
		assertEquals(expected, UpdateHandler.executeUpdate(null, smtDataTest));
	}
	
	@Test
	public void testUpdateWithNullTaskList() {
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah.");
		String expected = MessageList.MESSAGE_NO_TASK_IN_LIST;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, null));
	}
	
	@Test
	public void testUpdateWithDescEmpty(){
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", "");
		String expected = MessageList.MESSAGE_DESCRIPTION_EMPTY;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithDescNull(){
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskdesc", null);
		String expected = MessageList.MESSAGE_DESCRIPTION_EMPTY;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithStartDateNull(){
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskstart", null);
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithStartDateEmpty(){
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskstart", "");
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithStartDateInvalid(){
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskstart", "aa-22-2015");
		String expected = String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start");
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithEndDateNull(){
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskend", null);
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithEndDateEmpty(){
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskend", "");
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	@Test
	public void testUpdateWithEndDateInvalid(){
		keyFieldsTest.put("UPDATE", "2");
		keyFieldsTest.put("taskend", "abc-22-2015");
		String expected = String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "End");
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}
	
	
	@Test
	public void testInvalidConvertAnInteger() {
		keyFieldsTest.put("UPDATE", "ser@");
		keyFieldsTest.put("taskdesc", "Submit report to Ms Sarah.");
		String expected = String.format(MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Update");
		assertEquals(expected, UpdateHandler.executeUpdate(keyFieldsTest, smtDataTest));
	}

}
