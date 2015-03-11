import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;


public class DisplayHandler {

	public static String executeDisplay(String fileName, ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask) {

		int numItemExpected = 2;
		
		if(keyParamList == null || keyParamList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}
		
		if(listTask == null || listTask.isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyParamList.size() != numItemExpected) {
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
		}
		
		return displayContents(fileName, keyParamList, listTask);	
	}
	
	private static String displayContents(String fileName, ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask) {
		
		IndicatorMessagePair indicMsg;
		KeywordType.List_Keywords getKey;
		ArrayList<Task> displayTasksList = new ArrayList<Task>();

		getKey = KeywordType.getKeyword(keyParamList.get(1).getKeyword());
		
		switch(getKey) {
		case TODAY:
			indicMsg = displayTodayTasks(keyParamList.get(1), listTask, displayTasksList);
			break;
		case TODO:
			indicMsg = displayCompletedTasks(keyParamList.get(1), listTask, displayTasksList);
			break;
		case PENDING:
			indicMsg = displayNotCompletedTasks(keyParamList.get(1), listTask, displayTasksList);
			break;
		default:
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
		}
		
		displayTaskDetails(displayTasksList);
		
		return indicMsg.getMessage();
	}
		
	private static IndicatorMessagePair displayTodayTasks(KeyParamPair keyParam, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		//if(keyParam.getParam() != null || !keyParam.getParam().isEmpty()) {
		if(!keyParam.getParam().isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
	
		LocalDate date = new LocalDate();
		DateTime endDate = DateParser.generateDate(date.toString());
		
		for(int i = 0; i < listTask.size(); i++) {
			if(listTask.get(i).getTaskEndDateTime().equals(endDate)) {
				displayTasksList.add(listTask.get(i));		
			}
		}
		System.out.println("TODAY SIZE: " +displayTasksList.size());
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayCompletedTasks(KeyParamPair keyParam, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyParam.getParam().isEmpty()) {	
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		for(int i = 0; i < listTask.size(); i++) {
			if(listTask.get(i).getTaskStatus()) {
				displayTasksList.add(listTask.get(i));		
			}
		}
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayNotCompletedTasks(KeyParamPair keyParam, ArrayList<Task> listTask, ArrayList<Task> displayTasksList) {
		
		if(!keyParam.getParam().isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		for(int i = 0; i < listTask.size(); i++) {
			if(!listTask.get(i).getTaskStatus()) {
				displayTasksList.add(listTask.get(i));		
			}
		}
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static void displayTaskDetails(ArrayList<Task> displayTasksList) {
		for (int i = 0; i < displayTasksList.size(); i++) {
			System.out.println(displayTasksList.get(i).toString());
		}
	}
}
