package logic;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.FileStorage;
import utility.MessageList;

public class MenuTest {

	Menu controller;
	String expected;
	String fileName = "taskListTest.txt";
	String fileNameLastUnusedIndex = "lastUnusedIndexTest.txt";

	/* add sample tasks */
	String task1 = "Add submit report by Mon";
	String task2 = "Add attending meeting by Tue";
	String task3 = "Add prepare a proposal by 14-05-2015";

	/* add test */
	String addValidTask = "Add submit report by Fri";
	String addValidWeeklyTask = "Add submit report every Fri";
	String addValidFromTimeToTimeTask = "Add attend meeting from 11am to 12pm by Fri";
	String addInvalidTask = "Add submit report by when";

	/* delete test */
	String deleteValidTask = "Delete 1";
	String deleteInvalidTask = "Delete a";
	String deleteNoSuchTask = "Delete 100";

	/* update test */
	String updateValidTask = "update 1 taskdesc submit a report by mon";
	String updateValidWeeklyTask = "update 2 every monday";
	String updateValidFromTimeToTimeTask = "update 3 from 10am to 1pm";
	String updateInvalidNoSuchTask = "update 8 from 10am to 1pm";
	String updateInvalidTimeMismatchTask = "update 1 from 2pm to 1pm";
	String updateInvalidWeeklyTask = "update 1 every when";
	String updateInvalidDescTask = "update 1 submit";

	/* display test */
	String displayPendingValid = "display Pending";
	String displayScheduleValid = "display Schedule";
	String displayCompletedValid = "display Completed";
	String displayTodayValid = "display Today";
	String displayPendingInvalid = "display[ending";
	String displayScheduleInvalid = "display Schefule";
	String displayCompletedInvalid = "display xomplete";
	String displayTodayInvalid = "display T0day";

	/* search test */
	String searchTaskByID = "search 1 1";
	String searchTaskByDesc = "search 2 attending meeting";
	String searchTaskByDate = "search 3 14-05-2015";
	String searchTaskByIncorrectID = "search 2 14-05-2015";
	String searchTaskByIncorrectDate = "search 1 14-05-2015";
	String searchTaskByNegativeIndex = "search -2 meeting Professor";
	String searchTaskByNumberDesc = "search 2 !}{JHKDKJH1";
	String searchTaskByDateIncorrectFormat = "search 14 May 2015";
	String searchTaskByDatePastYear = "search 14 May 1992";

	/* sort test */
	String sortTaskInvalid = "s0rt";
	String sortDescValid = "Sort Description";
	String sortDeadLineValid = "Sort Deadline";
	String sortStartDateValid = "Sort StartDate";
	String sortCompletedValid = "Sort Completed";
	String sortPendingValid = "Sort Pending";
	String sortPendingInvalid = "Sort StartDate";
	
	/* undo test */
	String undoTaskValid = "undo";
	String undoTaskInvalid = "und0";
	
	/* redo test */
	
	/* Block test*/
	
	/*Unblock test*/
	

	@Before
	public void setUp() throws Exception {

		expected = new String();
		controller = Menu.getInstance();
		FileStorage.setFileNameForTasksList(fileName);
		FileStorage.setFileNameForLastUnusedIndex(fileNameLastUnusedIndex);
		controller.setUp();
	}

	@After
	public void tearDown() throws Exception {
		File textList = new File(fileName);
		textList.delete();

		textList = new File(fileNameLastUnusedIndex);
		textList.delete();
	}

	/**
	 * This is to test the adding new task
	 * The output is : Task Added.
	 */
	@Test
	public void testAddNewTaskValid() {
		expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected, controller.commandExecution(addValidTask));
	}
	
	/**
	 * This is to test the adding recurring task
	 * The output is : Task Added.
	 */
	@Test
	public void testAddNewTaskWeeklyValid() {
		expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected, controller.commandExecution(addValidWeeklyTask));
	}
	
	/**
	 * This is to test the adding new task for a specific time
	 * The output is : Task Added.
	 */
	@Test
	public void testAddNewTaskFromTimeToTimeValid() {
		expected = MessageList.MESSAGE_ADDED;
		assertEquals(expected,
				controller.commandExecution(addValidFromTimeToTimeTask));
	}
	
	/**
	 * This is to test the adding of invalid task
	 * The output is : Invalid argument for add command.
	 */
	@Test
	public void testAddNewTaskInValid() {
		expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Add");
		assertEquals(expected, controller.commandExecution(addInvalidTask));
	}
	
	/**
	 * This is to test delete task
	 * The output is : deleted from defaultTaskList.txt:"Add submit report by Mon".
	 */
	@Test
	public void testDeleteTaskValid() {
		expected = String.format(MessageList.MESSAGE_DELETE_SUCCESS,
				FileStorage.getFileNameForTasksList(), "submit report");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(deleteValidTask));
	}

	/**
	 * This is to test invalid delete task
	 * The output is : Invalid delete arguments
	 */
	@Test
	public void testDeleteInValid() {
		expected = MessageList.MESSAGE_INVALID_DELETE;
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(deleteInvalidTask));
	}

	/**
	 * This is to test delete task that do not exist
	 * The output is : There is no file to be deleted
	 */
	@Test
	public void testDeleteTaskNotExist() {
		expected = MessageList.MESSAGE_NO_FILE_DELETED;
		assertEquals(expected, controller.commandExecution(deleteNoSuchTask));
	}

	/**
	 * This is to test update task
	 * The output is : Update successful.
	 */
	@Test
	public void testUpdateValid() {
		expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(updateValidTask));
	}

	/**
	 * This is to test update task to recurring
	 * The output is : Update successful.
	 */
	@Test
	public void testUpdateWeeklyValid() {
		expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateValidWeeklyTask));
	}
	
	/**
	 * This is to test update task for a specific time
	 * The output is : Update successful.
	 */
	@Test
	public void testUpdateFromTimeToTimeValid() {
		expected = MessageList.MESSAGE_UPDATE_SUCCESS;
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateValidFromTimeToTimeTask));
	}
	
	/**
	 * This is to test update task that do not exist
	 * The output is : MESSAGE_NO_SUCH_TASK
	 */

	@Test
	public void testUpdateInValidNoSuch() {
		expected = MessageList.MESSAGE_NO_SUCH_TASK;
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateInvalidNoSuchTask));
	}
	
	/**
	 * This is to test update Invalid time mismatch
	 * The output is : Time mismatch.
	 */

	@Test
	public void testUpdateInValidTimeMismatch() {
		expected = "Time mismatch.";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateInvalidTimeMismatchTask));
	}
	
	/**
	 * This is to test update Invalid recurring task
	 * The output is : Wrong date format for Weekly date
	 */
	@Test
	public void testUpdateInValidWeeklyTask() {
		expected = String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT,
				"Weekly");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateInvalidWeeklyTask));
	}
	
	/**
	 * This is to test update Invalid task by description
	 * The output is : Invalid argument for Update command.
	 */
	@Test
	public void testUpdateInValidDescTask() {
		expected = "Invalid argument for Update command.";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateInvalidDescTask));
	}
	
	/**
	 * This is to test search task by ID
	 * The output is : Task ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending";
	 */
	@Test
	public void testSearchTaskByIDValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(searchTaskByID));
	}

	/**
	 * This is to test search task by Date
	 * The output is : "\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
	 */
	@Test
	public void testSearchTaskByDateValid() {
		expected = "\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(searchTaskByDate));
	}
	
	/**
	 * This is to test search task by Description
	 * The output is : "\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n";
	 */
	@Test
	public void testSearchTaskDescValid() {
		expected = "\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(searchTaskByDesc));
	}
	
	/**
	 * This is to test search task by invalid ID
	 * The output is : This is a invalid search.
	 */
	@Test
	public void testSearchTaskByIDInvalid() {
		expected = String.format(MessageList.MESSAGE_INVAILD_SEARCH);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByIncorrectDate));
	}
	
	/**
	 * This is to test search task by invalid Date
	 * The output is : No Match Found
	 */
	@Test
	public void testSearchTaskByDateInvalid() {
		expected = String.format(MessageList.MESSAGE_NO_MATCH_FOUND);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByIncorrectID));
	}
	
	/**
	 * This is to test search task by invalid Description
	 * The output is : No Match Found
	 */
	@Test
	public void testSearchTaskDescInvalid() {
		expected = String.format(MessageList.MESSAGE_NO_MATCH_FOUND);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByIncorrectID));
	}
	
	/**
	 * This is to test search task with negative index
	 * The output is : This is a invalid search.
	 */
	@Test
	public void testSearchTaskIDWithNegativeIndex() {
		expected = String.format(MessageList.MESSAGE_INVAILD_SEARCH);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByNegativeIndex));
	}
	
	/**
	 * This is to test search task by description that do not exist
	 * The output is : No Match Found
	 */
	@Test
	public void testSearchTaskByDescNonExist() {
		expected = String.format(MessageList.MESSAGE_NO_MATCH_FOUND);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByNumberDesc));
	}
	
	/**
	 * This is to test search task by date with incorrect format
	 * The output is : This is a invalid search.
	 */
	@Test
	public void testSearchTaskByDatetIncorrectFormat() {
		expected = String.format(MessageList.MESSAGE_INVAILD_SEARCH);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByDateIncorrectFormat));
	}
	/**
	 * This is to test search task with past year
	 * The output is : This is a invalid search.
	 */
	@Test
	public void testSearchTaskByDatePastYear() {
		expected = String.format(MessageList.MESSAGE_INVAILD_SEARCH);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByDatePastYear));
	}
	
	/**
	 * This is to test display pending of task
	 * The output is : \nTask ID: 1\nDescription: submit report\nDeadline: 18 
	 */
	@Test
	public void testDisplayPendingValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayPendingValid));
	}
	
	/**
	 * This is to test display of schedule
	 * The output is : \nTask ID: 1\nDescription: submit report\nDeadline: 6 April, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 7 April, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
	 */
	@Test
	public void testDisplayScheduleValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nDeadline: 6 April, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 7 April, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayPendingValid));
	}
	
	/**
	 * This is to test display Completed task
	 * The output is : There is no task in the display list.
	 */
	@Test
	public void testDisplayCompletedValid() {
		expected = "There is no task in the display list.";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayCompletedValid));
		
	}
	/**
	 * This is to test display Today task
	 * The output is : There is no task in the display list.
	 */
	@Test
	public void testDisplayTodayValid() {
		expected = "There is no task in the display list.";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayTodayValid));
	}
	
	/**
	 * This is to test display Invalid pending task
	 * The output is : This is a invalid search.
	 */
	@Test
	public void testDisplayPendingInvalid() {
		expected = String.format(MessageList.MESSAGE_INVAILD);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayPendingInvalid));
	}
	
	/**
	 * This is to test display Invalid Schedule task
	 * The output is : This is a invalid search.
	 */
	@Test
	public void testDisplayScheduleInvalid() {
		expected = String.format(MessageList.MESSAGE_INVAILD);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayPendingInvalid));
	}
	
	/**
	 * This is to test display Invalid Completed task
	 * The output is : Invalid argument for Display command.
	 */
	@Test
	public void testDisplayCompletedInvalid() {
		expected = "Invalid argument for Display command.";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayCompletedInvalid));
		
	}
	
	/**
	 * This is to test display Invalid Today task
	 * The output is : Invalid argument for Display command.
	 */
	@Test
	public void testDisplayTodayInvalid() {
		expected = "Invalid argument for Display command.";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayTodayInvalid));
	}
	/**
	 * This is to test sort task by description 
	 * The output is : \nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n";
	 */
	
	@Test
	public void testSortDescValid() {
		expected = "\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(sortDescValid));
	}
	
	/**
	 * This is to test sort start date
	 * \nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
	 */
	
	@Test
	public void testSortStartDateValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(sortStartDateValid));
	}
	
	/**
	 * This is to test sort completed task
	 * The output is :"\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
	 */
	@Test
	public void testSortCompletedValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(sortCompletedValid));
	}
	
	/**
	 * This is to test sort pending task
	 * The output is : "\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
	 */
	@Test
	public void testSortPendingValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nDeadline: 18 May, 2015 (Mon)\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nDeadline: 19 May, 2015 (Tue)\nStatus: Pending\n\nTask ID: 3\nDescription: prepare a proposal\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(sortPendingValid));
	}
	//@Test
	//public void testSortDeadlineValid() {
		//expected = String.format(MessageList.MESSAGE_INVAILD);
		//controller.commandExecution(task1);
		//controller.commandExecution(task2);
		//controller.commandExecution(task3);
		//assertEquals(expected, controller.commandExecution(sortDeadLineValid));
	//}
	
	/*
	 * This is to test the valid undo
	 * The output is :Undo operation done
	 */
	@Test
	public void testUndoValid() {
		expected = String.format(MessageList.MESSAGE_UNDO_SUCCESS);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(undoTaskValid));
	}
	/*
	 * This is to test the invalid undo
	 * The output is : Please enter a vaild command.
	 */
	@Test
	public void testUndoInvalid() {
		expected = String.format(MessageList.MESSAGE_INVAILD);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(undoTaskInvalid));
	}
	
}