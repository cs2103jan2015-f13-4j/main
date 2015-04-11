//@A0112501E
package unit_testing;

import static org.junit.Assert.*;

import java.io.File;

import logic.Menu;

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
	String fileNameBlockedDateList = "blockedDateList.txt";

	/* add sample tasks */
	String task1 = "Add submit report by 18-06-2015";
	String task2 = "Add attending meeting by 20-05-2015";
	String task3 = "Add prepare a proposal by 14-05-2015";
	String task12 = "Add ";

	/* block sample tasks */
	String task4 = "Block 18-07-2015";
	String task5 = "Block from 14-05-2015 to 20-05-2015";
	String task6 = "Delete 1";
	String task7 = "redo";
	String task8 = "block 18-06-2015";
	String task10 = "Block ";

	/* unblock sample tasks */
	String task11 = "Unblock ";

	/* delete sample task */
	String task13 = "Delete ";

	/* update sample task */
	String task14 = "Update ";

	/* display sample task */
	String task15 = "Display ";

	/* search sample task */
	String task16 = "Search ";

	/* sort sample task */
	String task17 = "Sort ";

	/* redo sample task */
	String task18 = "add submit EE2024 assignment2 by 20-08-2015";
	String task19 = "add submit developer guide by 21-05-2015";
	String task20 = "undo";
	String task21 = "redo";

	/* add test */
	String addValidTask = "Add submit report by Fri";
	String addValidWeeklyTask = "Add submit report every Fri";
	String addValidFromTimeToTimeTask = "Add attend meeting from 11am to 12pm by Fri";
	String addInvalidTask = "Add submit report by when";
	String addByandOnAtSameTask = "Add submit assignment by monday and report on tuesday";
	String addEmpty = "";

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
	String searchTaskByIncorrectID = "search 1 14-05-2015";
	String searchTaskByIncorrectDate = "search 3 a-05-2015";
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
	String redoTaskValid = "redo";
	String redoTaskInvalid = "red0";
	String redoLastTask = "redo";

	/* Block Task */
	String BlockOneDateTaskValid = "Block 18-07-2015";
	String BlockRangeOfDateValid = "Block from 19-08-2015 to 30-08-2015";
	String BlockDateClashes = "Block 18-06-2015";
	String BlockDateOnlyRestrictedToAMonthValid = "Block from 10-06-2015  to 09-07-2015";
	String BlockDateOverOneMonthInvalid = "Block from 10-07-2015 to 12-09-2015";
	String BlockDateExceedTwoYears = "Block from 10-06-2018 to 19-06-2018";
	String BlockWrongDateFormatEnd = "Block from 20-07-2015 to 80 Auguest 2o15";
	String BlockWrongDateFormatStart = "Block from 1o-o7-2o15 to 12-07-2015";
	String BlockEndDateIsEarlierThanStart = "block from 10-06-2015 to 08-06-2015";
	String BlockEmpty = " ";

	/* Unblock test */
	String UnblockOneDateTaskValid = "Unblock 18-07-2015";
	String UnblockRangeOfDateValid = "Unblock from 19-07-2015 to 30-07-2015";
	String UnblockInvalidDate = "Unblock from 20-07-2015 to 40 August 2015";

	@Before
	public void setUp() throws Exception {

		expected = new String();
		controller = Menu.getInstance();
		FileStorage.setFileNameForTasksList(fileName);
		FileStorage.setFileNameForLastUnusedIndex(fileNameLastUnusedIndex);
		FileStorage.setFileNameForBlockedDatesList(fileNameBlockedDateList);
		controller.setUp();
	}

	@After
	public void tearDown() throws Exception {
		File textList = new File(fileName);
		textList.delete();

		textList = new File(fileNameLastUnusedIndex);
		textList.delete();

		textList = new File(fileNameBlockedDateList);
		textList.delete();

	}

	/**
	 * This is to test adding a empty content The output is : Invalid argument
	 * for add command. Please specify what to add";
	 */
	@Test
	public void testAddEmpty() {
		expected = String.format(MessageList.MESSAGE_ADD_NO_DESCRIPTION, "add");
		assertEquals(expected, controller.commandExecution(task12));
	}

	/**
	 * This is to test the adding new task The output is : Task ID: 1
	 * Description: submit report End Time: 11.59 PM Deadline: 17 April, 2015
	 * (Fri) Status: Pending
	 */
	@Test
	public void testAddNewTaskValid() {
		expected = String
				.format(MessageList.MESSAGE_ADDED,
						"\nTask ID: 1\nDescription: submit report\nEnd Time: 11.59 PM\nDeadline: 17 April, 2015 (Fri)\nStatus: Pending");
		assertEquals(expected, controller.commandExecution(addValidTask));
	}

	/**
	 * This is to test the adding recurring task The output is : New Task
	 * TaskID: 5 Description: submit report Every:fri Status:Pending Added
	 */
	@Test
	public void testAddNewTaskWeeklyValid() {
		expected = String
				.format(MessageList.MESSAGE_ADDED,
						"\nTask ID: 1\nDescription: submit report\nEvery: Fri\nStatus: Pending");
		assertEquals(expected, controller.commandExecution(addValidWeeklyTask));
	}

	/**
	 * This is to test the adding new task for a specific time The output is :
	 * New Task Task ID: 1 Description: attend meeting Start Time: 11.00AM End
	 * Time: 12.00 PM Deadline: 17 April, 2015 (Fri) Status: Pending Task ID:1
	 * Description: attend meeting Start Time: 11.00 AM
	 */
	@Test
	public void testAddNewTaskFromTimeToTimeValid() {
		expected = String
				.format(MessageList.MESSAGE_ADDED,
						"\nTask ID: 1\nDescription: attend meeting\nStart Time: 11.00 AM\nEnd Time: 12.00 PM\nDeadline: 17 April, 2015 (Fri)\nStatus: Pending");
		assertEquals(expected,
				controller.commandExecution(addValidFromTimeToTimeTask));
	}

	/**
	 * This is to test the adding of invalid task The output is : Date format is
	 * incorrect
	 */
	@Test
	public void testAddNewTaskInValid() {
		expected = String.format(MessageList.MESSAGE_INCORRECT_DATE_FORMAT,
				"Add");
		assertEquals(expected, controller.commandExecution(addInvalidTask));
	}

	/**
	 * This is to test the add by and on in a single task The output is : Only
	 * one date can be added for a single task.
	 */
	@Test
	public void addByandOnAtSameTask() {
		expected = String.format(MessageList.MESSAGE_NO_WEEKLY_DEADLINE);
		assertEquals(expected,
				controller.commandExecution(addByandOnAtSameTask));
	}

	/**
	 * This is to test when the delete is empty The output is There is no file
	 * to be deleted
	 */
	@Test
	public void testDeleteEmpty() {
		expected = String.format(MessageList.MESSAGE_NO_FILE_DELETED);
		assertEquals(expected, controller.commandExecution(task13));
	}

	/**
	 * This is to test delete task The output is : deleted from
	 * defaultTaskList.txt:"Add submit report by Mon".
	 */
	//@Test
	//public void testDeleteTaskValid() {
		//expected = String.format(MessageList.MESSAGE_DELETE_SUCCESS,
			//	FileStorage.getFileNameForTasksList(), "submit report");
		//controller.commandExecution(task1);
		//controller.commandExecution(task2);
		//controller.commandExecution(task3);
		//controller.commandExecution(task4);
		//assertEquals(expected, controller.commandExecution(deleteValidTask));
	//}

	/**
	 * This is to test invalid delete task The output is : Invalid delete
	 * arguments
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
	 * This is to test delete task that do not exist The output is : There is no
	 * file to be deleted
	 */
	@Test
	public void testDeleteTaskNotExist() {
		expected = MessageList.MESSAGE_NO_FILE_DELETED;
		assertEquals(expected, controller.commandExecution(deleteNoSuchTask));
	}

	/**
	 * This is to test the update with no input The output is : There is no task
	 * in the list.
	 */
	@Test
	public void testUpdateEmpty() {
		expected = String.format(MessageList.MESSAGE_NO_TASK_IN_LIST);
		assertEquals(expected, controller.commandExecution(task14));
	}

	/**
	 * This is to test update task The output is : Update successful.
	 */
	@Test
	public void testUpdateValid() {
		expected = String
				.format(MessageList.MESSAGE_UPDATE_SUCCESS,
						"\nTask ID: 1\nDescription: submit a report\nEnd Time: 11.59 PM\nDeadline: 13 April, 2015 (Mon)\nStatus: Pending");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(updateValidTask));
	}

	/**
	 * This is to test update task to recurring The output is : Update
	 * successful.
	 */
	@Test
	public void testUpdateWeeklyValid() {
		expected = String
				.format(MessageList.MESSAGE_UPDATE_SUCCESS,
						"\nTask ID: 2\nDescription: attending meeting\nEnd Time: 11.59 PM\nDeadline: No specified date.\nEvery: monday\nStatus: Pending");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateValidWeeklyTask));
	}

	/**
	 * This is to test update task for a specific time The output is : Update
	 * successful.
	 */
	@Test
	public void testUpdateFromTimeToTimeValid() {
		expected = String
				.format(MessageList.MESSAGE_UPDATE_SUCCESS,
						"\nTask ID: 3\nDescription: prepare a proposal\nStart Time: 10.00 AM\nEnd Time: 1.00 PM\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateValidFromTimeToTimeTask));
	}

	/**
	 * This is to test update task that do not exist The output is : Task does
	 * not exist.
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
	 * This is to test update Invalid time mismatch The output is : Start Time
	 * and End Time conflicts.
	 */

	@Test
	public void testUpdateInValidTimeMismatch() {
		expected = MessageList.MESSAGE_TIME_WRONG_FLOW;
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateInvalidTimeMismatchTask));
	}

	/**
	 * This is to test update Invalid recurring task The output is : Wrong date
	 * format for Weekly date
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
	 * This is to test update Invalid task by description The output is :
	 * Invalid argument for Update command.
	 */
	@Test
	public void testUpdateInValidDescTask() {
		expected = String
				.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Update");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(updateInvalidDescTask));
	}

	/**
	 * This is to test when the search is empty The output is : Invalid argument
	 * for search command Please specify what to search.
	 */

	@Test
	public void testSearchEmpty() {
		expected = String.format(MessageList.MESSAGE_INVALID_SEARCH_CRITERIA);
		assertEquals(expected, controller.commandExecution(task16));
	}

	/**
	 * This is to test search task by ID The output is : Task ID: 1 Description:
	 * submit report Deadline: 18 May, 2015 (Mon) Status: Pending";
	 */
	@Test
	public void testSearchTaskByIDValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nEnd Time: 11.59 PM\nDeadline: 18 June, 2015 (Thu)\nStatus: Pending";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(searchTaskByID));
	}

	/**
	 * This is to test search task by Date The output is : Task ID: 3
	 * Description: prepare a proposal Deadline: 14 May, 2015 (Thu) Status:
	 * Pending
	 */
	@Test
	public void testSearchTaskByDateValid() {
		expected = "\nTask ID: 3\nDescription: prepare a proposal\nEnd Time: 11.59 PM\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(searchTaskByDate));
	}

	/**
	 * This is to test search task by Description The output is : "Task ID: 2
	 * Description: attending meeting Deadline: 19 May, 2015 (Tue) Status:
	 * Pending
	 */
	@Test
	public void testSearchTaskDescValid() {
		expected = "\nTask ID: 2\nDescription: attending meeting\nEnd Time: 11.59 PM\nDeadline: 20 May, 2015 (Wed)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(searchTaskByDesc));
	}

	/**
	 * This is to test search task by invalid ID The output is : Invalid
	 * argument for search command Please specify what to search.
	 */
	@Test
	public void testSearchTaskByIDInvalid() {
		expected = String.format(MessageList.MESSAGE_INVALID_SEARCH_CRITERIA);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByIncorrectDate));
	}

	/**
	 * This is to test search task by invalid Date The output is : Search format
	 * is invalid, please look at our hint or enter help keyword for
	 * assistance";
	 */
	@Test
	public void testSearchTaskByDateInvalid() {
		expected = MessageList.MESSAGE_INVALID_SEARCH;
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByIncorrectID));
	}

	/**
	 * This is to test search task by invalid Description The output is : Search
	 * format is invalid, please look at our hint or enter help keyword for
	 * assistance";
	 */
	@Test
	public void testSearchTaskDescInvalid() {
		expected = MessageList.MESSAGE_INVALID_SEARCH;
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByIncorrectID));
	}

	/**
	 * This is to test search task with negative index The output is : Search
	 * format is invalid, please look at our hint or enter help keyword for
	 * assistance";
	 */
	@Test
	public void testSearchTaskIDWithNegativeIndex() {
		expected = String.format(MessageList.MESSAGE_INVALID_SEARCH);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByNegativeIndex));
	}

	/**
	 * This is to test search task by description that do not exist The output
	 * is : Description !}{JHKDKJH1 not found.
	 */
	@Test
	public void testSearchTaskByDescNonExist() {
		expected = String.format(MessageList.MESSAGE_NO_MATCH_FOUND_BY_DESC,
				"!}{JHKDKJH1");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByNumberDesc));
	}

	/**
	 * This is to test search task by date with incorrect format The output is :
	 * Search format is invalid, please look at our hint or enter help keyword
	 * for assistance";
	 */
	@Test
	public void testSearchTaskByDatetIncorrectFormat() {
		expected = String.format(MessageList.MESSAGE_INVALID_SEARCH);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByDateIncorrectFormat));
	}

	/**
	 * This is to test search task with past year The output is : Search format
	 * is invalid, please look at our hint or enter help keyword for
	 * assistance";
	 */
	@Test
	public void testSearchTaskByDatePastYear() {
		expected = String.format(MessageList.MESSAGE_INVALID_SEARCH);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(searchTaskByDatePastYear));
	}

	/**
	 * This is to test when the display is empty The output is : Invalid
	 * argument for update command.
	 */

	@Test
	public void testDisplayEmpty() {
		expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
				"Display");
		assertEquals(expected, controller.commandExecution(task15));
	}

	/**
	 * This is to test display pending of task The output is : Task ID:1
	 * Description: submit report Deadline: 18
	 */
	@Test
	public void testDisplayPendingValid() {
		expected = "\nTask ID: 3\nDescription: prepare a proposal\nEnd Time: 11.59 PM\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending"
				+ "\n\nTask ID: 2\nDescription: attending meeting\nEnd Time: 11.59 PM\nDeadline: 20 May, 2015 (Wed)\nStatus: Pending"
				+ "\n\nTask ID: 1\nDescription: submit report\nEnd Time: 11.59 PM\nDeadline: 18 June, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayPendingValid));
	}

	/**
	 * This is to test display of schedule The output is : Task ID: 1
	 * Description: submit report Deadline: 6 April, 2015 (Mon) Status: Pending
	 * 
	 * Task ID: 2 Description: attending meeting Deadline: 7 April, 2015 (Tue)
	 * Status: Pending
	 * 
	 * Task ID: 3 Description: prepare a proposal Deadline: 14 May, 2015 (Thu)
	 * Status: Pending";
	 */
	@Test
	public void testDisplayScheduleValid() {
		expected = "\nTask ID: 3\nDescription: prepare a proposal\n"
				+ "End Time: 11.59 PM\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending"
				+ "\n\nTask ID: 2\nDescription: attending meeting\nEnd Time: 11.59 PM\nDeadline: 20 May, 2015 (Wed)\nStatus: Pending"
				+ "\n\nTask ID: 1\nDescription: submit report\nEnd Time: 11.59 PM\nDeadline: 18 June, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayPendingValid));
	}

	/**
	 * This is to test display Completed task The output is : There is no task
	 * in the display list.
	 */
	@Test
	public void testDisplayCompletedValid() {
		expected = "There is no task in the display list.";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(displayCompletedValid));

	}

	/**
	 * This is to test display Today task The output is : There is no task in
	 * the display list.
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
	 * This is to test display Invalid pending task The output is : Please enter
	 * a valid command.
	 */
	@Test
	public void testDisplayPendingInvalid() {
		expected = String.format(MessageList.MESSAGE_INVALID);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(displayPendingInvalid));
	}

	/**
	 * This is to test display Invalid Schedule task The output is : Please
	 * enter a valid command.
	 */
	@Test
	public void testDisplayScheduleInvalid() {
		expected = String.format(MessageList.MESSAGE_INVALID);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(displayPendingInvalid));
	}

	/**
	 * This is to test display Invalid Completed task The output is : Invalid
	 * argument for Display command
	 */
	@Test
	public void testDisplayCompletedInvalid() {
		expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
				"Display");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected,
				controller.commandExecution(displayCompletedInvalid));

	}

	/**
	 * This is to test display Invalid Today task The output is : Invalid
	 * argument for Display command.
	 */
	@Test
	public void testDisplayTodayInvalid() {
		expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT,
				"Display");
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(displayTodayInvalid));
	}

	/**
	 * This is to test sort with no input The output is : Invalid argument for
	 * Sort command.
	 * 
	 */

	@Test
	public void testSortEmpty() {
		expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		assertEquals(expected, controller.commandExecution(task17));
	}

	/**
	 * This is to test sort task by description The output is : Task ID: 2
	 * Description: attending meeting Deadline: 19 May, 2015 (Tue) Status:
	 * Pending
	 * 
	 * Task ID: 3 Description: prepare a proposal Deadline: 14 May,2015 (Thu)
	 * Status: Pending
	 * 
	 * Task ID: 1Description: submit report Deadline: 18 May, 2015 (Mon) Status:
	 * Pending
	 */
	@Test
	public void testSortDescValid() {
		expected = "\nTask ID: 2\nDescription: attending meeting\nEnd Time: 11.59 PM\nDeadline: 20 May, 2015 (Wed)\nStatus: Pending"
				+ "\n\nTask ID: 3\nDescription: prepare a proposal\nEnd Time: 11.59 PM\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending"
				+ "\n\nTask ID: 1\nDescription: submit report\nEnd Time: 11.59 PM\nDeadline: 18 June, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(sortDescValid));
	}

	/**
	 * This is to test sort completed task The output is : Task ID: 1
	 * Description: submit report Deadline: 18 May, 2015 (Mon) Status: Pending
	 * 
	 * Task ID: 2 Description: attending meeting Deadline: 19 May,2015 (Tue)
	 * Status: Pending
	 * 
	 * Task ID: 3 Description: prepare a proposal Deadline: 14 May, 2015 (Thu)
	 * Status: Pending
	 */
	@Test
	public void testSortCompletedValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nEnd Time: 11.59 PM\nDeadline: 18 June, 2015 (Thu)"
				+ "\nStatus: Pending\n\nTask ID: 2\nDescription: attending meeting\nEnd Time: 11.59 PM\nDeadline: 20 May, 2015 (Wed)\nStatus: Pending"
				+ "\n\nTask ID: 3\nDescription: prepare a proposal\nEnd Time: 11.59 PM\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(sortCompletedValid));
	}

	/**
	 * This is to test sort pending task The output is : Task ID: 1 Description:
	 * submit report Deadline: 18 May, 2015 (Mon) Status: Pending Task ID: 2
	 * Description: attending meeting Deadline: 19 May,2015 (Tue) Status:
	 * Pending
	 * 
	 * Task ID: 3 Description: prepare a proposal Deadline: 14 May, 2015 (Thu)
	 * Status: Pending
	 */
	@Test
	public void testSortPendingValid() {
		expected = "\nTask ID: 1\nDescription: submit report\nEnd Time: 11.59 PM\nDeadline: 18 June, 2015 (Thu)\nStatus: Pending"
				+ "\n\nTask ID: 2\nDescription: attending meeting\nEnd Time: 11.59 PM\nDeadline: 20 May, 2015 (Wed)\nStatus: Pending"
				+ "\n\nTask ID: 3\nDescription: prepare a proposal\nEnd Time: 11.59 PM\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(sortPendingValid));
	}

	/**
	 * This is to test the sorting by deadline The output is Task ID: 3
	 * Description: prepare a proposal Deadline: 14 May, 2015 (Thu) Status:
	 * Pending
	 * 
	 * Task ID: 2 Description: attending meeting Deadline: 20 May, 2015 (Wed)
	 * Status: Pending
	 * 
	 * Task ID: 1 Description: submit report Deadline: 18 June, 2015 (Thu)
	 * Status: Pending";
	 */

	@Test
	public void testSortDeadlineValid() {
		expected = "\nTask ID: 3\nDescription: prepare a proposal\nEnd Time: 11.59 PM\nDeadline: 14 May, 2015 (Thu)\nStatus: Pending"
				+ "\n\nTask ID: 2\nDescription: attending meeting\nEnd Time: 11.59 PM\nDeadline: 20 May, 2015 (Wed)\nStatus: Pending"
				+ "\n\nTask ID: 1\nDescription: submit report\nEnd Time: 11.59 PM\nDeadline: 18 June, 2015 (Thu)\nStatus: Pending\n";
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(sortDeadLineValid));
	}

	@Test
	public void testUndoEmpty() {
		expected = String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		assertEquals(expected, controller.commandExecution(task17));
	}

	/**
	 * This is to test the valid undo The output is : Undo operation done
	 */

	@Test
	public void testUndoValid() {
		expected = String.format(MessageList.MESSAGE_UNDO_SUCCESS);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(undoTaskValid));
	}

	/**
	 * This is to test the invalid undo The out put is : Please enter a valid
	 * command.
	 */

	@Test
	public void testUndoInvalid() {
		expected = String.format(MessageList.MESSAGE_INVALID);
		controller.commandExecution(task1);
		controller.commandExecution(task2);
		controller.commandExecution(task3);
		assertEquals(expected, controller.commandExecution(undoTaskInvalid));
	}
	/**
	 * This is to test the redo when the list is empty
	 * The output is : Last command in the list. Could not perform redo
	 */
	@Test
	public void testRedoEmpty() {
		expected = String.format(MessageList.MESSAGE_LAST_COMMAND);
		controller.commandExecution(task21);
		assertEquals(expected, controller.commandExecution(redoTaskValid));
	}
	
	/**
	 * This is to test wrong spelling of the command redo to red0
	 * The output is : Please enter a valid command.
	 */
	@Test
	public void testRedoIncorrectCommand() {
		expected = String.format(MessageList.MESSAGE_INVALID);
		controller.commandExecution(task21);
		assertEquals(expected, controller.commandExecution(redoTaskInvalid));
	}
	
	/**
	 * This is to test the redo successfully
	 * The output is : Redo operation done.
	 */
	@Test
	public void testRedoValid() {
		expected = String.format(MessageList.MESSAGE_REDO_SUCCESS);
		controller.commandExecution(task18);
		controller.commandExecution(task19);
		controller.commandExecution(task20);
		assertEquals(expected, controller.commandExecution(redoTaskValid));
	}
	
	/**
	 * This is to test the block with no date input
	 * The output is No date given.
	 */

	@Test
	public void testBlockEmpty() {
		expected = String.format(MessageList.MESSAGE_NO_DATE_GIVEN,
				"No date given");
		assertEquals(expected, controller.commandExecution(task10));
	}
	
	/**
	 * This is to test the blocking successfully of a day
	 * The output is : "18-07-2015" Blocked Successfully
	 */
	@Test
	public void testBlockOndDateValid() {
		expected = String.format(MessageList.MESSAGE_BLOCKED, "18-07-2015");
		assertEquals(expected,
				controller.commandExecution(BlockOneDateTaskValid));
	}

	/**
	 * This is to test the range of valid blocked date The output is :
	 * "19-08-2015" to "30-08-2015" Blocked Successfully.
	 */
	@Test
	public void testBlockRangeOfDateValid() {
		expected = String.format(MessageList.MESSAGE_BLOCKED_RANGE,
				"19-08-2015", "30-08-2015");
		assertEquals(expected,
				controller.commandExecution(BlockRangeOfDateValid));
	}

	/**
	 * This is to test the date to block already have task on that date. The
	 * output is : "18-06-2015" already exist.
	 */
	@Test
	public void testblockDateThatHaveClashes() {
		expected = String.format(MessageList.MESSAGE_BLOCK_DATE_ALREADY_EXIST,
				"18-06-2015");
		controller.commandExecution(task1);
		controller.commandExecution(task8);
		assertEquals(expected, controller.commandExecution(BlockDateClashes));

	}

	/**
	 * This is to test the block date whether it is within 31 days period The
	 * output is : "10-06-2015" to "09-07-2015" Blocked Successfully.
	 */
	@Test
	public void testblockDateWithinAMonthValid() {
		expected = String.format(MessageList.MESSAGE_BLOCKED,
				"10-06-2015\" to \"09-07-2015");
		assertEquals(expected,
				controller
						.commandExecution(BlockDateOnlyRestrictedToAMonthValid));
	}

	/**
	 * This is to test block date exceeded 31 days The output is : "10-07-2015"
	 * to "12-09-2015" has exceeded 31 days.
	 */
	@Test
	public void testBlockDateWithinAMonthInvalid() {
		expected = String.format(
				MessageList.MESSAGE_BLOCK_RANGE_EXCEED_A_MONTH, "10-07-2015",
				"12-09-2015");
		assertEquals(expected,
				controller.commandExecution(BlockDateOverOneMonthInvalid));
	}

	/**
	 * This is to test the block date has exceeded two years The output is :
	 * Blocking of dates only allow up to 2 years starting from "10-06-2015"
	 */

	@Test
	public void testBlockDateExceedTwoYears() {
		expected = String.format(MessageList.MESSAGE_BLOCK_DATE_OVER_TWO_YEARS,
				"10-06-2018");
		assertEquals(expected,
				controller.commandExecution(BlockDateExceedTwoYears));
	}

	/**
	 * This is to test the end block date invalid The output is : Wrong date
	 * format for End date
	 */
	@Test
	public void testBlockDateInvalid() {
		expected = String.format(
				MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_END,
				"Wrong date format for End date");
		assertEquals(expected,
				controller.commandExecution(BlockWrongDateFormatEnd));
	}

	/**
	 * This is to test the start block date invalid The output is : Wrong date
	 * format for Start date
	 */
	@Test
	public void testStartBlockDateInvalid() {
		expected = String.format(
				MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_START,
				"Wrong date format for Start date");
		assertEquals(expected,
				controller.commandExecution(BlockWrongDateFormatStart));
	}
	
	/**
	 * This is to test the block end date is earlier than the start date
	 * The output is : Start Date should not be later than End Date
	 */
	@Test
	public void testBlockEndDateIsEarlierThanStart() {
		expected = String
				.format(MessageList.MESSAGE_BLOCK_INCORRECT_START_EARLIER_THAN_END);
		assertEquals(expected,
				controller.commandExecution(BlockEndDateIsEarlierThanStart));
	}
	
	/**
	 * This is to test no date input for unblock
	 * The output is : No date given.
	 */
	@Test
	public void testUnblockEmpty() {
		expected = String.format(MessageList.MESSAGE_NO_DATE_GIVEN,
				"No date given");
		assertEquals(expected, controller.commandExecution(task11));
	}

	/**
	 * This is to test the unblock range of date which is valid The output is :
	 * "19-07-2015" to "30-07-2015" Unblocked Successfully.
	 * 
	 */
	@Test
	public void testUnblockRangeOfDateValid() {
		expected = String.format(MessageList.MESSAGE_UNBLOCKED_RANGE,
				"19-07-2015", "30-07-2015");
		assertEquals(expected,
				controller.commandExecution(UnblockRangeOfDateValid));
	}

	/**
	 * This is to test unblocking of a single date The output is : "18-07-2015"
	 * Unblocked Successfully
	 */
	@Test
	public void testUnblockOneDateValid() {
		expected = String.format(MessageList.MESSAGE_UNBLOCKED, "18-07-2015");
		controller.commandExecution(task4);
		assertEquals(expected,
				controller.commandExecution(UnblockOneDateTaskValid));
	}

	/**
	 * This is to test invalid unblocking date format The output is : Wrong date
	 * format for Start date
	 */
	@Test
	public void testUnblockRangeOfDateInvalid() {
		expected = String
				.format(MessageList.MESSAGE_BLOCK_WRONG_DATE_FORMAT_END);
		assertEquals(expected, controller.commandExecution(UnblockInvalidDate));
	}

}
