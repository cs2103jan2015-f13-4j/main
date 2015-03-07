import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class InputStringParserTest {

	ArrayList<KeyParamPair> keyParamTest;
	
	@Before
	public void setUp(){
		keyParamTest = new ArrayList<KeyParamPair>();
	}

	@After
	public void tearDown(){
		keyParamTest.clear();
	}

	@Test
	public void testProcessStringAddRegular() {
		String command = "add Do homework by Friday";
		String expected = "ADD";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringDeleteRegular() {
		String command = "delete 23";
		String expected = "DELETE";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringDisplayRegular() {
		String command = "display 24";
		String expected = "DISPLAY";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringClearRegular() {
		String command = "clear";
		String expected = "CLEAR";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringExitRegular() {
		String command = "exit";
		String expected = "EXIT";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringSearchRegular() {
		String command = "search 25";
		String expected = "SEARCH";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringSortRegular() {
		String command = "sort";
		String expected = "SORT";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringInvalidCommandType() {
		String command = "try to process";
		String expected = "INVALID";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringEmptyCommandRegular() {
		String command = "";
		String expected = "INVALID";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}
	
	@Test
	public void testProcessStringNullCommandRegular() {
		String command = null;
		String expected = "INVALID";
		assertEquals(expected, InputStringParser.processString(command, keyParamTest).name());
	}

	@Test
	public void testProcessStringAddWithReturnKeyParamPairNumberRegular() {
		String command = "add Do homework by Friday";
		int expected = 2;
		InputStringParser.processString(command, keyParamTest);
		assertTrue(expected == keyParamTest.size());
	}
}
