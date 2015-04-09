//@A0111935L
package unit_testing;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Task;
import data.TaskParserFromTextFile;


public class TaskParserFromTextFileTest {

	Task testTaskObj;
	@Before
	public void setUp(){
		testTaskObj = new Task();
	}

	@After
	public void tearDown(){
		testTaskObj = null;
	}

	@Test
	public void testgenerateTaskWithNull() {
		String taskStr = null;
		Task expected = null;
		assertTrue(expected == TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr));
	}
	
	@Test
	public void testgenerateInvalidWithOnlyTaskWithTaskId() {
		String taskStr = "Taskid=2";
		Task expected = null;
		assertTrue(expected == TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr));
	}
	
	@Test
	public void testgenerateTaskWithTaskIdAndDesc() {
		String taskStr = "Taskid=2|TASKDESC=dadd adad dada";
		testTaskObj = TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr);
		assertEquals("dadd adad dada", testTaskObj.getTaskDescription());
		assertEquals(2, testTaskObj.getTaskId());
	}
	
	@Test
	public void testgenerateTaskWithTaskIdAndDescStartDateTime() {
		String taskStr = "Taskid=2|TASKDESC=dadd adad dada|TASKSTARTDATETIME=2015-03-03T00:00:00.000+08:00";
		testTaskObj = TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr);
		assertEquals("dadd adad dada", testTaskObj.getTaskDescription());
		assertEquals("2015-03-03T00:00:00.000+08:00", testTaskObj.getTaskStartDateTime().toString());
	}

	@Test
	public void testgenerateTaskWithTaskIdAndDescStartDateTimeEndDateTime() {
		String taskStr = "Taskid=2|TASKDESC=dadd adad dada|TASKSTARTDATETIME=2015-03-03T00:00:00.000+08:00|TASKENDDATETIME=2015-04-03T00:00:00.000+08:00";
		testTaskObj = TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr);
		assertEquals("dadd adad dada", testTaskObj.getTaskDescription());
		assertEquals("2015-03-03T00:00:00.000+08:00", testTaskObj.getTaskStartDateTime().toString());
		assertEquals("2015-04-03T00:00:00.000+08:00", testTaskObj.getTaskEndDateTime().toString());
	}
	
	@Test
	public void testgenerateTaskInvalidTaskIdWithTaskDesc() {
		String taskStr = "Taskid=a|TASKDESC=please submit";
		Task expected = null;
		assertTrue(expected == TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr));
	}
	
	@Test
	public void testgenerateTaskInvalidTaskDesc() {
		String taskStr = "Taskid=2|TASKDESC=";
		Task expected = null;
		assertTrue(expected == TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr));
	}
	
	@Test
	public void testgenerateTaskInvalidIfNoTaskId() {
		String taskStr = "TASKDESC=please submit a report|TASKSTARTDATE=3-3-2015";
		Task expected = null;
		assertTrue(expected == TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr));
	}
	
	@Test
	public void testgenerateTaskIfTaskIdIsBehind() {
		String taskStr = "TASKDESC=please submit a report|TASKID=3";
		testTaskObj = TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr);
		assertEquals("please submit a report", testTaskObj.getTaskDescription());
		assertEquals(3, testTaskObj.getTaskId());
	}
	
	@Test
	public void testgenerateTaskIfDeadLineIsGiven() {
		String taskStr = "TASKDESC=please submit a report|TASKID=3|TASKENDDATETIME=2015-04-03T00:00:00.000+08:00|TASKDEADLINESET=true";
		testTaskObj = TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr);
		assertEquals("please submit a report", testTaskObj.getTaskDescription());
		assertEquals(3, testTaskObj.getTaskId());
		assertTrue(testTaskObj.getDeadLineStatus());
	}
	
	@Test
	public void testgenerateTaskIfDeadLineKeyIsInvalid() {
		String taskStr = "TASKDESC=please submit a report|TASKID=3|TASKENDDATETIME=2015-04-03T00:00:00.000+08:00|TASKDEAeINESET=true";
		Task expected = null;
		assertTrue(expected == TaskParserFromTextFile.generateStringFromTextFileToTask(taskStr));
		
	}
}
