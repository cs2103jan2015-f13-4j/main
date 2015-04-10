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

	private int year = 2015;
	private int month = 9;
	private int day = 3;
	private int hour = 0;
	private int min = 0;
	
	/* This is a null object testing */
	@Test
	public void testTaskNull() {
		String expected = null;
		assertEquals(expected,
				TaskParserWriteToTextFile.concatTaskFieldToString(null));
	}
	
	/* This is a success testing */
	@Test
	public void testToStringRegular() {
		String expected = "TASKID=1|TASKDESC=Prepare a proposal|TASKSTARTDATETIME=2015-09-03T00:00:00.000+08:00|TASKENDDATETIME=2015-09-03T23:00:00.000+08:00|TASKSTATUS=false|TASKDEADLINESET=true";
		Task testTask = new Task(1, "Prepare a proposal",
				new DateTime(year, month, day, hour, min), new DateTime(year,
						month, day, hour + 23, min), false, "", true);
		assertEquals(expected,
				TaskParserWriteToTextFile.concatTaskFieldToString(testTask));
	}
	
	/* This is a success testing */
	@Test
	public void testToStringNoEndDateTimeRegular() {
		String expected = "TASKID=1|TASKDESC=Prepare a proposal|TASKSTARTDATETIME=2015-09-03T00:00:00.000+08:00|TASKSTATUS=false|TASKDEADLINESET=true";
		Task testTask = new Task(1, "Prepare a proposal",
				new DateTime(year, month, day, hour, min), null, false, "", true);
		assertEquals(expected,
				TaskParserWriteToTextFile.concatTaskFieldToString(testTask));
	}
	
	/* This is a success testing */
	@Test
	public void testToStringNoDatesRegular() {
		String expected = "TASKID=1|TASKDESC=Prepare a proposal|TASKSTATUS=false|TASKDEADLINESET=true";
		Task testTask = new Task(1, "Prepare a proposal",
				null, null, false, "", true);
		assertEquals(expected,
				TaskParserWriteToTextFile.concatTaskFieldToString(testTask));
	}

}
