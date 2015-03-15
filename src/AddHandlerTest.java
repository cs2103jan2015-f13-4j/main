import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddHandlerTest {
	
	String fileName = "testFile.txt";
	String lastUsedIndexFileName = "testLastUnusedIndex.txt";

	ArrayList<KeyFieldPair> keyFieldsTest;
	ArrayList<Task> taskList;

	@Before
	public void setUp() {
		keyFieldsTest = new ArrayList<KeyFieldPair>();
		taskList = new ArrayList<Task>();
		taskList.add(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		taskList.add(new Task(2, "Submit report to Ms Sarah",new DateTime(), new DateTime(), ""));
		taskList.add(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
		
		
		FileHandler.writeToFile(lastUsedIndexFileName, 4, new IndicatorMessagePair());
	}

	@After
	public void tearDown() {
		keyFieldsTest.clear();
		taskList.clear();
		File textList = new File(lastUsedIndexFileName);
		textList.delete();
	}

	@Test
	public void testAddWithDescRegular() {
		keyFieldsTest.add(new KeyFieldPair("add", "submit proposal"));
		keyFieldsTest.add(new KeyFieldPair("by", "03-03-2015"));
		String expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected, AddHandler.executeAdd(fileName, lastUsedIndexFileName, keyFieldsTest, taskList));
	}

	@Test 
	public void testAddWithDescWithoutDate()	
	{
		keyFieldsTest.add(new KeyFieldPair("add", "Submit Proposal"));
		keyFieldsTest.add(new KeyFieldPair("by",""));
		String expected = MessageList.MESSAGE_NO_DATE_GIVEN;
		assertEquals(expected, AddHandler.executeAdd(fileName, lastUsedIndexFileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testAddWithDescWithWrongDateFormat()
	{
		keyFieldsTest.add(new KeyFieldPair("add","Submit Report"));
		keyFieldsTest.add(new KeyFieldPair("by","03-March-2014"));
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, AddHandler.executeAdd(fileName, lastUsedIndexFileName, keyFieldsTest, taskList));
	}
	@Test
	public void testAddWithDescWithWrongDateFormatforMonth()
	{
		keyFieldsTest.add(new KeyFieldPair("add","Submit Report"));
		keyFieldsTest.add(new KeyFieldPair("by","03-2015-08"));
		String expected = String.format(MessageList.MESSAGE_INCORRECT_DATE_FORMAT);
		assertEquals(expected, AddHandler.executeAdd(fileName, lastUsedIndexFileName, keyFieldsTest, taskList));
	}
	
	@Test
	public void testInvalidDate()
	{
		keyFieldsTest.add(new KeyFieldPair("add", "Submit Assignment"));
		keyFieldsTest.add(new KeyFieldPair("by", "AA-12-2015"));
		String expected = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
		assertEquals(expected, AddHandler.executeAdd(fileName, lastUsedIndexFileName, keyFieldsTest, taskList));
	}
	
}
	

