import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddHandlerTest {
	
	String fileName = "testFile.txt";

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
		taskList.clear();
	}

	@Test
	public void testAddWithDescRegular() {
		keyParamTest.add(new KeyParamPair("add", "submit proposal"));
		keyParamTest.add(new KeyParamPair("by", "03-03-2015"));
		String expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected, AddHandler.executeAdd(fileName, keyParamTest, taskList, 4));
	}

	@Test 
	public void testAddWithDescAndDate()	
	{
		keyParamTest.add(new KeyParamPair("add", "1"));
		keyParamTest.add(new KeyParamPair("by",))
	}
}
