import static org.junit.Assert.*;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UpdateHandlerTest {

	ArrayList<KeyFieldPair> keyFieldsTest;
	ArrayList<Task> taskList;
	String fileName = "taskList.txt";
	@Before
	public void setUp() {
		keyFieldsTest = new ArrayList<KeyFieldPair>();
		taskList = new ArrayList<Task>();
		taskList.add(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		taskList.add(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		taskList.add(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
	}

	@After
	public void tearDown() {
		keyFieldsTest.clear();
	}

	@Test
	public void testUpdateWithDescRegular() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskdesc", "Submit report to Ms Sarah and to IVLE"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithDescCheckRegular() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskdesc", "Submit report to Ms Sarah and to IVLE"));
		String expected = "Submit report to Ms Sarah and to IVLE";
		UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList);
		for(int i = 0; i < taskList.size(); i++){
			if(taskList.get(i).getTaskId() == 2){
				assertEquals(expected, taskList.get(i).getTaskDescription());
			}
		}
	}
	
	@Test
	public void testUpdateWithDescAndEndDateRegular() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskdesc", "Submit report to Ms Sarah and to IVLE"));
		keyFieldsTest.add(new KeyFieldPair("taskend", "03-03-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithDescAndByRegular() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskdesc", "Submit report to Ms Sarah and to IVLE"));
		keyFieldsTest.add(new KeyFieldPair("by", "03-03-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithByRegular() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("by", "03-03-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateRegular() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskstart", "02-01-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithEndDateRegular() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskend", "02-01-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateAndEndDateRegular() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskstart", "02-01-2015"));
		keyFieldsTest.add(new KeyFieldPair("taskend", "03-01-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithNullKeyFields() {
		String expected = MessageList.MESSAGE_NULL;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, null, taskList));
	}
	
	@Test
	public void testUpdateWithNullTaskList() {
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskdesc", "Submit report to Ms Sarah."));
		String expected = MessageList.MESSAGE_NO_TASK_IN_LIST;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, null));
	}
	
	@Test
	public void testUpdateWithDescEmpty(){
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskdesc", ""));
		String expected = MessageList.MESSAGE_DESCRIPTION_EMPTY;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithDescNull(){
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskdesc", null));
		String expected = MessageList.MESSAGE_DESCRIPTION_EMPTY;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateNull(){
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskstart", null));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateEmpty(){
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskstart", ""));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateInvalid(){
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskstart", "aa-22-2015"));
		String expected = String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start");
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithEndDateNull(){
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskend", null));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithEndDateEmpty(){
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskend", ""));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testUpdateWithEndDateInvalid(){
		keyFieldsTest.add(new KeyFieldPair("update", "2"));
		keyFieldsTest.add(new KeyFieldPair("taskend", "aa-22-2015"));
		String expected = String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "End");
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}
	
	
	@Test
	public void testInvalidConvertAnInteger() {
		keyFieldsTest.add(new KeyFieldPair("update", "ser@"));
		keyFieldsTest.add(new KeyFieldPair("taskdesc", "Submit report to Ms Sarah."));
		String expected = String.format(MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Update");
		assertEquals(expected, UpdateHandler.executeUpdate(fileName, keyFieldsTest, taskList));
	}

}
