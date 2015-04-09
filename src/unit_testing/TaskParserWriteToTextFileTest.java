//@A0111935L
package unit_testing;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Task;
import data.TaskParserWriteToTextFile;

public class TaskParserWriteToTextFileTest {

	/* This is a null object testing */
	@Test
	public void testTaskNull() {
		String expected = null;
		assertEquals(expected,
				TaskParserWriteToTextFile.concatTaskFieldToString(null));
	}

}
