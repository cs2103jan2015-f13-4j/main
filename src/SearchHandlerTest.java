import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SearchHandlerTest {
	ArrayList<KeyFieldPair> keyFieldsTest;
	ArrayList<Task> listTask;
	String fileName = "listTask.txt";
	

	@Before
	public void setUp() {
		keyFieldsTest = new ArrayList<KeyFieldPair>();
		listTask = new ArrayList<Task>();
		listTask.add(new Task(1, "Prepare a proposal", new DateTime(), new DateTime(), ""));
		listTask.add(new Task(2, "Submit report to Ms Sarah", new DateTime(), new DateTime(), ""));
		listTask.add(new Task(3, "Prepare OP1", new DateTime(), new DateTime(), ""));
		}
	

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
