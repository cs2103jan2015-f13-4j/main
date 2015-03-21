package parser;
import static org.junit.Assert.*;



import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CommandStringParserTest {

	HashMap<String, String> keyFieldsTest;
	
	@Before
	public void setUp(){
		keyFieldsTest = new HashMap<String, String>();
	}

	@After
	public void tearDown(){
		keyFieldsTest.clear();
	}

	@Test
	public void testProcessStringAddRegular() {
		String command = "add Do homework by Friday";
		String expected = "ADD";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringDeleteRegular() {
		String command = "delete 23";
		String expected = "DELETE";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringDisplayRegular() {
		String command = "display 24";
		String expected = "DISPLAY";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringClearRegular() {
		String command = "clear";
		String expected = "CLEAR";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringExitRegular() {
		String command = "exit";
		String expected = "EXIT";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringSearchRegular() {
		String command = "search 25";
		String expected = "SEARCH";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringSortRegular() {
		String command = "sort";
		String expected = "SORT";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringInvalidCommandType() {
		String command = "try to process";
		String expected = "INVALID";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringEmptyCommandRegular() {
		String command = "";
		String expected = "INVALID";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}
	
	@Test
	public void testProcessStringNullCommandRegular() {
		String command = null;
		String expected = "INVALID";
		assertEquals(expected, CommandStringParser.processString(command, keyFieldsTest).name());
	}

	@Test
	public void testProcessStringAddWithReturnKeyFieldsTestNumberRegular() {
		String command = "add Do homework by Friday";
		int expected = 2;
		CommandStringParser.processString(command, keyFieldsTest);
		assertTrue(expected == keyFieldsTest.size());
	}
}
