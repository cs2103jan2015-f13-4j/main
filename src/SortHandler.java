import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.joda.time.DateTime;


public class SortHandler {

	public static String executeSort(String fileName, ArrayList<KeyFieldPair> keyParamList, ArrayList<Task> listTask) {

		int numItemExpected = 2;
		
		if(keyParamList == null || keyParamList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}
		
		if(listTask == null || listTask.isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyParamList.size() != numItemExpected) {
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		}
		
		return sortContents(fileName, keyParamList, listTask);	
	}
	
	private static String sortContents(String fileName, ArrayList<KeyFieldPair> keyParamList, ArrayList<Task> listTask) {
		
		IndicatorMessagePair indicMsg;
		KeywordType.List_Keywords getKey;
		ArrayList<Task> displayTasksList = new ArrayList<Task>();

		getKey = KeywordType.getKeyword(keyParamList.get(1).getKeyword());
		
		switch(getKey) {
		case DESCRIPTION:
			indicMsg = sortDescription(keyParamList.get(1), listTask, displayTasksList);
			break;
		case DEADLINE:
			indicMsg = sortDeadline(keyParamList.get(1), listTask, displayTasksList);
			break;
		case STARTDATE:
			indicMsg = sortStartDate(keyParamList.get(1), listTask, displayTasksList);
			break;
		case COMPLETED:
			indicMsg = sortCompleted(keyParamList.get(1), listTask, displayTasksList);
			break;
		case PENDING:
			indicMsg = sortPending(keyParamList.get(1), listTask, displayTasksList);
			break;
		default:
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Sort");
		}
		
		if(!indicMsg.isTrue) {
			return indicMsg.getMessage();
		}
		
		if(displayTasksList.isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		}
		
		return sortTaskDetails(displayTasksList);
	}
	
	private static IndicatorMessagePair sortDescription(KeyFieldPair keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.getFields().isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskDescriptionComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortDeadline(KeyFieldPair keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.getFields().isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskDeadlineComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortStartDate(KeyFieldPair keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.getFields().isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskStartDateComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortCompleted(KeyFieldPair keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.getFields().isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		cloneTaskList(listTask, displayTasksList);
		
		Collections.sort(displayTasksList, SortHandler.TaskCompletedComparator);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair sortPending(KeyFieldPair keyFields, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyFields.getFields().isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
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
}
