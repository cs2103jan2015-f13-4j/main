package logic;
import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import parser.DateParser;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import data.Data;
import data.Task;


public class DisplayHandler {


	private static String displayTaskDetails(ArrayList<Task> displayTasksList) {
		String taskDetails = "";
		for (int i = 0; i < displayTasksList.size(); i++) {
			taskDetails += displayTasksList.get(i).toString() +"\n";
		}
		return taskDetails;
	}
	
	
	
	
	//===========================================================================================
	//===========================================================================================
	
	
	public static String executeDisplay(HashMap<String, String> keyFieldsList, Data smtData) {

		int numItemExpected = 2;
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyFieldsList.size() != numItemExpected) {
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
		}
		
		return displayContents(keyFieldsList, smtData);	
	}
	
	private static String displayContents(HashMap<String, String> keyFieldsList, Data smtData) {
		
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		KeywordType.List_Keywords getKey;
		ArrayList<Task> displayTasksList = new ArrayList<Task>();
	
		keyFieldsList.remove(CommandType.Command_Types.DISPLAY.name());
		for (String key : keyFieldsList.keySet()){
			getKey = KeywordType.getKeyword(key);
			
			switch(getKey) {
			case ALL:
				indicMsg = displaySchedule(keyFieldsList, smtData, displayTasksList);
				break;
			case TODAY:
				indicMsg = displayTodayTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case COMPLETED:
				indicMsg = displayCompletedTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case PENDING:
				indicMsg = displayNotCompletedTasks(keyFieldsList, smtData, displayTasksList);
				break;
			default:
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
			}
		}
		
		
		if(!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		if(displayTasksList.isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		}
		
		return displayTaskDetails(displayTasksList);
	}
	
	private static IndicatorMessagePair displaySchedule(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		if(!keyFieldsList.get(KeywordType.List_Keywords.ALL.name()).isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		for(int i = 0; i < smtData.getSize(); i++) {
			displayTasksList.add(smtData.getATask(i));		
		}
		return new IndicatorMessagePair(true, "Success");
	}
		
	private static IndicatorMessagePair displayTodayTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		if(!keyFieldsList.get(KeywordType.List_Keywords.TODAY.name()).isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
	
		LocalDate date = new LocalDate();
		DateTime endDate = DateParser.generateDate(date.toString(), "yy-MM-dd");
		
		for(int i = 0; i < smtData.getSize(); i++) {
			if(smtData.getATask(i).getTaskEndDateTime() != null) {
				if(smtData.getATask(i).getTaskEndDateTime().toLocalDate().equals(endDate.toLocalDate())) {
				displayTasksList.add(smtData.getATask(i));	
				}
			}
		}
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayCompletedTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		if(!keyFieldsList.get(KeywordType.List_Keywords.COMPLETED.name()).isEmpty()) {	
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		for(int i = 0; i < smtData.getSize(); i++) {
			if(smtData.getATask(i).getTaskStatus()) {
				displayTasksList.add(smtData.getATask(i));		
			}
		}
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayNotCompletedTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		if(!keyFieldsList.get(KeywordType.List_Keywords.PENDING.name()).isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_ARGUMENT);
		}
		
		for(int i = 0; i < smtData.getSize(); i++) {
			if(!smtData.getATask(i).getTaskStatus()) {
				displayTasksList.add(smtData.getATask(i));		
			}
		}
		return new IndicatorMessagePair(true, "Success");
	}
	
}
