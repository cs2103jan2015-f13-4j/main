//@A0112978W
package logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.joda.time.DateTime;

import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import data.Data;
import data.Task;


public class SortHandler {
	
	public static String executeSort(Map<String, String> keyFieldsList, Data smtData) {
		
		checkForValidData(keyFieldsList, smtData);
		
		return sortContents(keyFieldsList, smtData);	
	}
	
	private static String sortContents(Map<String, String> keyFieldsList, Data smtData) {
		
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		ArrayList<Task> displayTasksList = new ArrayList<Task>();
		
		String firstKey = keyFieldsList.get(keyFieldsList.keySet().iterator().next()).toUpperCase();
		
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
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		}
		
		if(!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		if(displayTasksList.isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		}
		
		return sortTaskDetails(displayTasksList);
	}
	
	private static IndicatorMessagePair sortDescription(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.DESCRIPTION.name());
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskDescriptionComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortDeadline(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.DEADLINE.name());
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskDeadlineComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortStartTime(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.STARTTIME.name());
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskDeadlineComparator);
		Collections.sort(displayTasksList, SortHandler.TaskStartTimeComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortCompleted(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.COMPLETED.name());
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskCompletedComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortPending(Map<String, String> keyFieldsList, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.PENDING.name());
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskPendingComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static String sortTaskDetails(ArrayList<Task> displayTasksList) {
		String taskDetails = "";
		for (int i = 0; i < displayTasksList.size(); i++) {
			taskDetails += displayTasksList.get(i).toString() +"\n";
		}
		return taskDetails;
	}
	
	private static void cloneTaskList(ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		for(int i = 0; i < listTask.size(); i++) {
			displayTasksList.add(listTask.get(i));		
		}
	}
	
	public static Comparator<Task> TaskDescriptionComparator 
								= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {
		
		String taskDescription1 = task1.getTaskDescription().toUpperCase();
		String taskDescription2 = task2.getTaskDescription().toUpperCase();
		
		return taskDescription1.compareTo(taskDescription2);
		}
	};
	
	public static Comparator<Task> TaskDeadlineComparator 
								= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {
		
		if(task1.getTaskEndDateTime() == null || task2.getTaskEndDateTime() == null) {
			return 0;
		}
		
		return task1.getTaskEndDateTime().compareTo(task2.getTaskEndDateTime());
		}
	};
	
	public static Comparator<DateTime> TaskDateTimeDeadlineComparator 
								= new Comparator<DateTime>() {
		
		public int compare(DateTime date1, DateTime date2) {
		
		if(date1.toLocalDate() == null || date2.toLocalDate() == null) {
			return 0;
		}
		
		return date1.toLocalDate().compareTo(date2.toLocalDate());
		}
	};
	
	public static Comparator<Task> TaskStartTimeComparator 
								= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {

		if(task1.getTaskStartDateTime() == null || task2.getTaskStartDateTime() == null) {
			return 0;
		}

		return task1.getTaskStartDateTime().compareTo(task2.getTaskStartDateTime());
		}
	};
	
	public static Comparator<Task> TaskCompletedComparator 
									= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {

			String taskDescription1 = ""+ task1.getTaskStatus();
			String taskDescription2 = ""+ task2.getTaskStatus();
			
			return taskDescription2.compareTo(taskDescription1);
		}
	};
	
	public static Comparator<Task> TaskPendingComparator 
									= new Comparator<Task>() {

		public int compare(Task task1, Task task2) {
		
		String taskDescription1 = ""+ task1.getTaskStatus();
		String taskDescription2 = ""+ task2.getTaskStatus();
		
		return taskDescription1.compareTo(taskDescription2);
		}
	};
	
	private static String checkForValidData(Map<String, String> keyFieldsList, Data smtData) {
		int numItemExpected = 1;
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyFieldsList.size() != numItemExpected) {
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		}
		
		return MessageList.MESSAGE_LIST_IS_NOT_EMPTY;
	}
	
	private static IndicatorMessagePair checkInvalidArgument(Map<String, String> keyFieldsList, String keyWord) {
		if(keyFieldsList.get(keyWord) != null) {
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort"));
		}
		return new IndicatorMessagePair(true, String.format(MessageList.MESSAGE_VALID_ARGUMENT, "Sort"));
	}
}
