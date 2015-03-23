package logic;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.MessageList;
import data.Data;
import data.Task;

public class SearchHandlerTest {
	HashMap<String, String> keyFieldsTest;
	Data smtDataTest;
	String fileName = "listTaskTest.txt";

	@Before
	public void setUp() {
		smtDataTest = new Data();
		keyFieldsTest = new HashMap<String, String>();
		smtDataTest.addATaskToList(new Task(1, "Prepare a proposal",
				new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(2, "Submit report to Ms Sarah",
				new DateTime(), new DateTime(), ""));
		smtDataTest.addATaskToList(new Task(3, "Prepare OP1", new DateTime(),
				new DateTime(), ""));
	}

	@After
	public void tearDown() throws Exception {

		keyFieldsTest.clear();
	}

	@Test
	public void testSearchWithRegularDesc() {
		keyFieldsTest.put("SEARCH", "EE2024 report proposal");
		keyFieldsTest.put("BY", "03-03-2015");
		// keyFieldsTest.search();
		// keyFieldsTest.search();
		String expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected,
				SearchHandler.executeSearch(keyFieldsTest, smtDataTest));

	}

}
