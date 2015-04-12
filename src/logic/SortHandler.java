//@author A0112978W
package logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import utility.TaskLogging;
import data.Data;
import data.Task;

/**
 * This class will handle the sort tasks based on the User's input.
 * It supports sorting tasks by specifying the criteria. The entire list is as shown below.
 * 
 * sort description - Sorting the task description by alphabetical order.
 * sort deadline - Sorting the task deadline by earliest date.
 * sort starttime - Sorting the task start time by earliest time.
 * sort completed - Sorting the tasks by completed status.
 * sort pending - Sorting the tasks by pending status.
 */
public class SortHandler {
	
	// Get the TaskLogging object to log the events
	private static Logger taskLogger = TaskLogging.getInstance();
	
	private static final int NUM_ITEMS_EXPECTED = 1;
	
	/**
	 * This method will execute the sort function based on the sort criteria from the User
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * 
	 * @return The sorted task's information to be displayed to the User.
	 */
	public static String executeSort(Map<String, String> keyFieldsList, Data smtData) {
		
		// Data validation
		checkForValidData(keyFieldsList, smtData);
		
		return sortContents(keyFieldsList, smtData);	
	}
	
	/**
	 * This method will sort the task's information and display to the User
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * 
	 * @return The sorted task's information.
	 */
	private static String sortContents(Map<String, String> keyFieldsList, Data smtData) {
		
		// Initialize IndicatorMessagePair to get result message
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		// ArrayList to store the retrieved Task object
		ArrayList<Task> displayTasksList = new ArrayList<Task>();
		
		// Determine the sort criteria
		String firstKey = keyFieldsList.get(keyFieldsList.keySet().iterator().next()).toUpperCase();
		
		// switch case selection to determine which sort function to execute
		switch(firstKey) {
		case "DESCRIPTION":
		case "DESC":
			indicMsg = sortDescription(keyFieldsList, smtData.getListTask(), displayTasksList);
			break;
		case "DEADLINE":
			indicMsg = sortDeadline(keyFieldsList, smtData.getListTask(), displayTasksList);
			break;
		case "STARTTIME":
			indicMsg = sortStartTime(keyFieldsList, smtData.getListTask(), displayTasksList);
			break;
		case "COMPLETED":
		case "COMP":
			indicMsg = sortCompleted(keyFieldsList, smtData.getListTask(), displayTasksList);
			break;
		case "PENDING":
		case "PEND":
			indicMsg = sortPending(keyFieldsList, smtData.getListTask(), displayTasksList);
			break;
		default:
			// Log the default case
			taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort"));
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		}
		
		if(!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		if(displayTasksList.isEmpty()) {
			// Log when display list is empty
			taskLogger.log(Level.INFO, "Sort command: " +MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST);
			return MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		}
		// Log when sort function executed
		taskLogger.log(Level.INFO, "Executed Sort " +firstKey);
		return sortTaskDetails(displayTasksList);
	}
	
	/**
	 * This method will sort the task description by alphabetical order
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param listTask
	 * 			This list contains a list of tasks.
	 * @param displayTasksList
	 * 			This list is cloned from listTask.
	 * 			
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair sortDescription(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.DESCRIPTION.name());
		
		// Clone the task list to displayTasksList
		cloneTaskList(listTask, displayTasksList);
		
		// displayTasksList is sorted by task description
		Collections.sort(displayTasksList, SortHandler.TaskDescriptionComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will sort the task deadline by earliest date
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param listTask
	 * 			This list contains a list of tasks.
	 * @param displayTasksList
	 * 			This list is cloned from listTask.
	 * 			
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair sortDeadline(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.DEADLINE.name());
		
		// Clone the task list to displayTasksList
		cloneTaskList(listTask, displayTasksList);
		
		// displayTasksList is sorted by deadline
		Collections.sort(displayTasksList, SortHandler.TaskDeadlineComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will sort the task start time by earliest time
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param listTask
	 * 			This list contains a list of tasks.
	 * @param displayTasksList
	 * 			This list is cloned from listTask.
	 * 			
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair sortStartTime(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.STARTTIME.name());
		
		// Clone the task list to displayTasksList
		cloneTaskList(listTask, displayTasksList);
		
		// displayTasksList is sorted by deadline
		Collections.sort(displayTasksList, SortHandler.TaskDeadlineComparator);
		// displayTasksList is sorted by start time
		Collections.sort(displayTasksList, SortHandler.TaskStartTimeComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will sort the task by completed status
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param listTask
	 * 			This list contains a list of tasks.
	 * @param displayTasksList
	 * 			This list is cloned from listTask.
	 * 			
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair sortCompleted(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.COMPLETED.name());
		
		// Clone the task list to displayTasksList
		cloneTaskList(listTask, displayTasksList);
		
		// displayTasksList is sorted by completed status
		Collections.sort(displayTasksList, SortHandler.TaskCompletedComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will sort the task by pending status
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param listTask
	 * 			This list contains a list of tasks.
	 * @param displayTasksList
	 * 			This list is cloned from listTask.
	 * 			
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair sortPending(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.PENDING.name());
		
		// Clone the task list to displayTasksList
		cloneTaskList(listTask, displayTasksList);
		
		// displayTasksList is sorted by pending status
		Collections.sort(displayTasksList, SortHandler.TaskPendingComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display the sorted task's information
	 * 
	 * @param displayTasksList
	 * 			This list contains the sorted tasks to be displayed.
	 * 
	 * @return The sorted task's information.
	 */
	private static String sortTaskDetails(ArrayList<Task> displayTasksList) {
		String taskDetails = "";
		for (int i = 0; i < displayTasksList.size(); i++) {
			taskDetails += displayTasksList.get(i).toString() +"\n";
		}
		return taskDetails;
	}
	
	/**
	 * This method will clone the listTask to displayTasksList
	 * 
	 * @param listTask
	 * 			This list contains a list of tasks.
	 * @param displayTasksList
	 * 			This list is cloned from listTask.
	 */
	private static void cloneTaskList(ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		for(int i = 0; i < listTask.size(); i++) {
			displayTasksList.add(listTask.get(i));		
		}
	}
	
	/**
	 * Comparator<Task> to compare task description between the tasks
	 */
	public static Comparator<Task> TaskDescriptionComparator 
								= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {
		
		String taskDescription1 = task1.getTaskDescription().toUpperCase();
		String taskDescription2 = task2.getTaskDescription().toUpperCase();
		
		return taskDescription1.compareTo(taskDescription2);
		}
	};
	
	/**
	 * Comparator<Task> to compare task deadline between the tasks
	 */
	public static Comparator<Task> TaskDeadlineComparator 
								= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {
		
		if(task1.getTaskEndDateTime() == null || task2.getTaskEndDateTime() == null) {
			return 0;
		}
		
		return task1.getTaskEndDateTime().compareTo(task2.getTaskEndDateTime());
		}
	};
	
	/**
	 * Comparator<DateTime> to compare date between the blocked dates
	 */
	public static Comparator<DateTime> TaskDateTimeDeadlineComparator 
								= new Comparator<DateTime>() {
		
		public int compare(DateTime date1, DateTime date2) {
		
		if(date1.toLocalDate() == null || date2.toLocalDate() == null) {
			return 0;
		}
		
		return date1.toLocalDate().compareTo(date2.toLocalDate());
		}
	};
	
	/**
	 * Comparator<Task> to compare task start time between the tasks
	 */
	public static Comparator<Task> TaskStartTimeComparator 
								= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {

		if(task1.getTaskStartDateTime() == null || task2.getTaskStartDateTime() == null) {
			return 0;
		}

		return task1.getTaskStartDateTime().compareTo(task2.getTaskStartDateTime());
		}
	};
	
	/**
	 * Comparator<Task> to compare task completed status between the tasks
	 */
	public static Comparator<Task> TaskCompletedComparator 
									= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {

			String taskDescription1 = ""+ task1.getTaskStatus();
			String taskDescription2 = ""+ task2.getTaskStatus();
			
			return taskDescription2.compareTo(taskDescription1);
		}
	};
	
	/**
	 * Comparator<Task> to compare task pending status between the tasks
	 */
	public static Comparator<Task> TaskPendingComparator 
									= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {
		
		String taskDescription1 = ""+ task1.getTaskStatus();
		String taskDescription2 = ""+ task2.getTaskStatus();
		
		return taskDescription1.compareTo(taskDescription2);
		}
	};
	
	/**
	 * This method will check for valid data
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * 
	 * @return The error message.
	 */
	private static String checkForValidData(Map<String, String> keyFieldsList, Data smtData) {
		
		if (keyFieldsList == null) {
			assert false : "Map object is null.";
		}
		
		if (smtData == null) {
			assert false : "Data object is null.";
		}
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()) {
			taskLogger.log(Level.INFO, "Sort command: " +MessageList.MESSAGE_NULL);
			return MessageList.MESSAGE_NULL;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()) {
			taskLogger.log(Level.INFO, "Sort command: " +MessageList.MESSAGE_NO_TASK_IN_LIST);
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyFieldsList.size() != NUM_ITEMS_EXPECTED) {
			taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort"));
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		}
		
		return MessageList.MESSAGE_LIST_IS_NOT_EMPTY;
	}
	
	/**
	 * This method will check for invalid argument
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param keyWord
	 * 			This is the keyword.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair checkInvalidArgument(Map<String, String> keyFieldsList, String keyWord) {
		if(keyFieldsList.get(keyWord) != null) {
			taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort"));
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort"));
		}
		return new IndicatorMessagePair(true, String.format(MessageList.MESSAGE_VALID_ARGUMENT, "Sort"));
	}
}
