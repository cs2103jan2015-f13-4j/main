package logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.joda.time.DateTime;

import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import data.Data;
import data.Task;


public class SortHandler {

	
	
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
	
	public static Comparator<Task> TaskStartDateComparator 
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
	
	
	
	
	//================================================================================
	//================================================================================
	
	
	
	
	public static String executeSort(HashMap<String, String> keyFieldsList, Data smtData) {

		int numItemExpected = 2;
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyFieldsList.size() != numItemExpected) {
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		}
		
		return sortContents(keyFieldsList, smtData);	
	}
	
	private static String sortContents(HashMap<String, String> keyFieldsList, Data smtData) {
		
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		KeywordType.List_Keywords getKey;
		ArrayList<Task> displayTasksList = new ArrayList<Task>();

		keyFieldsList.remove(CommandType.Command_Types.SORT.name());//remove the sort key pair
		if (keyFieldsList.isEmpty() || keyFieldsList.size() > 1) {
			return "Invalid sort";
		}
		
		for (String key : keyFieldsList.keySet()) {
			 getKey = KeywordType.getKeyword(key);
		
		switch(getKey) {
		case DESCRIPTION:
			indicMsg = sortDescription(keyFieldsList.get(key), smtData.getListTask(), displayTasksList);
			break;
		case DEADLINE:
			indicMsg = sortDeadline(keyFieldsList.get(key), smtData.getListTask(), displayTasksList);
			break;
		case STARTDATE:
			indicMsg = sortStartDate(keyFieldsList.get(key), smtData.getListTask(), displayTasksList);
			break;
		case COMPLETED:
			indicMsg = sortCompleted(keyFieldsList.get(key), smtData.getListTask(), displayTasksList);
			break;
		case PENDING:
			indicMsg = sortPending(keyFieldsList.get(key), smtData.getListTask(), displayTasksList);
			break;
		default:
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		}
		}
		
		if(!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		if(displayTasksList.isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		}
		
		return sortTaskDetails(displayTasksList);
	}
	
	private static IndicatorMessagePair sortDescription(String keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskDescriptionComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortDeadline(String keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskDeadlineComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortStartDate(String keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskStartDateComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortCompleted(String keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskCompletedComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortPending(String keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskPendingComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
}
