import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UpdateHandlerTest {

	ArrayList<KeyParamPair> keyParamTest;
	ArrayList<Task> taskList;
	@Before
	public void setUp() {
		keyParamTest = new ArrayList<KeyParamPair>();
		taskList = new ArrayList<Task>();
		taskList.add(new Task(1, "Prepare a proposal", new Date(), new Date()));
		taskList.add(new Task(2, "Submit report to Ms Sarah", new Date(), new Date()));
		taskList.add(new Task(3, "Prepare OP1", new Date(), new Date()));
	}

	@After
	public void tearDown() {
		keyParamTest.clear();
	}

	@Test
	public void testUpdateWithDescRegular() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskdesc", "Submit report to Ms Sarah and to IVLE"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithDescCheckRegular() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskdesc", "Submit report to Ms Sarah and to IVLE"));
		String expected = "Submit report to Ms Sarah and to IVLE";
		UpdateHandler.executeUpdate(keyParamTest, taskList);
		for(int i = 0; i < taskList.size(); i++){
			if(taskList.get(i).getTaskId() == 2){
				assertEquals(expected, taskList.get(i).getTaskDescription());
			}
		}
	}
	
	@Test
	public void testUpdateWithDescAndEndDateRegular() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskdesc", "Submit report to Ms Sarah and to IVLE"));
		keyParamTest.add(new KeyParamPair("taskend", "03-03-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithDescAndByRegular() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskdesc", "Submit report to Ms Sarah and to IVLE"));
		keyParamTest.add(new KeyParamPair("by", "03-03-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithByRegular() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("by", "03-03-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateRegular() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskstart", "02-01-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithEndDateRegular() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskend", "02-01-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateAndEndDateRegular() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskstart", "02-01-2015"));
		keyParamTest.add(new KeyParamPair("taskend", "03-01-2015"));
		String expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithNullKeyParam() {
		String expected = MessageList.MESSAGE_NULL;
		assertEquals(expected, UpdateHandler.executeUpdate(null, taskList));
	}
	
	@Test
	public void testUpdateWithNullTaskList() {
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskdesc", "Submit report to Ms Sarah."));
		String expected = MessageList.MESSAGE_NO_TASK_IN_LIST;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, null));
	}
	
	@Test
	public void testUpdateWithDescEmpty(){
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskdesc", ""));
		String expected = MessageList.MESSAGE_DESCRIPTION_EMPTY;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithDescNull(){
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskdesc", null));
		String expected = MessageList.MESSAGE_DESCRIPTION_EMPTY;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateNull(){
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskstart", null));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateEmpty(){
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskstart", ""));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithStartDateInvalid(){
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskstart", "aa-22-2015"));
		String expected = String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start");
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithEndDateNull(){
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskend", null));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithEndDateEmpty(){
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskend", ""));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	@Test
	public void testUpdateWithEndDateInvalid(){
		keyParamTest.add(new KeyParamPair("update", "2"));
		keyParamTest.add(new KeyParamPair("taskend", "aa-22-2015"));
		String expected = String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "End");
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}
	
	
	@Test
	public void testInvalidConvertAnInteger() {
		keyParamTest.add(new KeyParamPair("update", "ser@"));
		keyParamTest.add(new KeyParamPair("taskdesc", "Submit report to Ms Sarah."));
		String expected = String.format(MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Update");
		assertEquals(expected, UpdateHandler.executeUpdate(keyParamTest, taskList));
	}

}
