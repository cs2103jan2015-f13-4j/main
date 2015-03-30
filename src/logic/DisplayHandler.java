package logic;
import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import parser.DateTimeParser;
import utility.CommandType;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import data.Data;
import data.Task;


public class DisplayHandler {
	
	public static String executeDisplay(HashMap<String, String> keyFieldsList, Data smtData) {
		
		checkForValidData(keyFieldsList, smtData);
		
		return displayContents(keyFieldsList, smtData);	
	}
	
	private static String displayContents(HashMap<String, String> keyFieldsList, Data smtData) {
		
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		ArrayList<Task> displayTasksList = new ArrayList<Task>();
		
		String firstKey = keyFieldsList.get(keyFieldsList.keySet().iterator().next()).toUpperCase();
		
			switch(firstKey) {
			case "ALL":
				indicMsg = displaySchedule(keyFieldsList, smtData, displayTasksList);
				break;
			case "COMPLETED":
				indicMsg = displayCompletedTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "PENDING":
				indicMsg = displayNotCompletedTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "TODAY":
				indicMsg = displayTodayTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "TOMORROW":
			case "TMR":
				indicMsg = displayTomorrowTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "YESTERDAY":
			case "YTD":
				indicMsg = displayYesterdayTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "MONDAY":
			case "MON":
				indicMsg = displaySpecificDayTasks(keyFieldsList, smtData, displayTasksList, KeywordType.List_SearchKeywords.MONDAY.name(), DateTimeConstants.MONDAY);
				break;
			case "TUESDAY":
			case "TUE":
				indicMsg = displaySpecificDayTasks(keyFieldsList, smtData, displayTasksList, KeywordType.List_SearchKeywords.TUESDAY.name(), DateTimeConstants.TUESDAY);
				break;
			case "WEDNESDAY":
			case "WED":
				indicMsg = displaySpecificDayTasks(keyFieldsList, smtData, displayTasksList, KeywordType.List_SearchKeywords.WEDNESDAY.name(), DateTimeConstants.WEDNESDAY);
				break;
			case "THURSDAY":
			case "THU":
				indicMsg = displaySpecificDayTasks(keyFieldsList, smtData, displayTasksList, KeywordType.List_SearchKeywords.THURSDAY.name(), DateTimeConstants.THURSDAY);
				break;
			case "FRIDAY":
			case "FRI":
				indicMsg = displaySpecificDayTasks(keyFieldsList, smtData, displayTasksList, KeywordType.List_SearchKeywords.FRIDAY.name(), DateTimeConstants.FRIDAY);
				break;
			case "SATURDAY":
			case "SAT":
				indicMsg = displaySpecificDayTasks(keyFieldsList, smtData, displayTasksList, KeywordType.List_SearchKeywords.SATURDAY.name(), DateTimeConstants.SATURDAY);
				break;
			case "SUNDAY":
			case "SUN":
				indicMsg = displaySpecificDayTasks(keyFieldsList, smtData, displayTasksList, KeywordType.List_SearchKeywords.SUNDAY.name(), DateTimeConstants.SUNDAY);
				break;
			default:
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
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
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.ALL.name());
		
		for(int i = 0; i < smtData.getSize(); i++) {
			displayTasksList.add(smtData.getATask(i));		
		}
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayCompletedTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.COMPLETED.name());
		
		retrieveMatchedStatusTasks(smtData, displayTasksList, true);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayNotCompletedTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.PENDING.name());
		
		retrieveMatchedStatusTasks(smtData, displayTasksList, false);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayTodayTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.TODAY.name());
		
		DateTime endDate = generateTodayDate();
		
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate());
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayTomorrowTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.TOMORROW.name());
	
		DateTime endDate = generateTodayDate();
		
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate().plusDays(1));
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayYesterdayTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.YESTERDAY.name());
	
		DateTime endDate = generateTodayDate();
		
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate().minusDays(1));
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displaySpecificDayTasks(HashMap<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList, String day, int dayValue) {

		checkInvalidArgument(keyFieldsList, day);
		
		retrieveMatchedDateTasks(smtData, displayTasksList, DateTimeParser.generateDateAfterDayComparison(dayValue).toLocalDate());
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static DateTime generateTodayDate() {
		LocalDate date = new LocalDate();
		DateTime endDate = DateTimeParser.generateDate(date.toString(), "yy-MM-dd");
		return endDate;
	}
	
	private static ArrayList<Task> retrieveMatchedStatusTasks(Data smtData, ArrayList<Task> displayTasksList, boolean status) {
		for(int i = 0; i < smtData.getSize(); i++) {
			if(smtData.getATask(i).getTaskStatus() == status) {
				displayTasksList.add(smtData.getATask(i));		
			}
		}
		return displayTasksList;
	}
	
	private static ArrayList<Task> retrieveMatchedDateTasks(Data smtData, ArrayList<Task> displayTasksList, LocalDate matchDate) {
		for(int i = 0; i < smtData.getSize(); i++) {
			if(smtData.getATask(i).getTaskEndDateTime() != null) {
				if(smtData.getATask(i).getTaskEndDateTime().toLocalDate().equals(matchDate)) {
				displayTasksList.add(smtData.getATask(i));	
				}
			}
		}
		return displayTasksList;
	}

	private static String checkForValidData(HashMap<String, String> keyFieldsList, Data smtData) {
		int numItemExpected = 1;
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyFieldsList.size() != numItemExpected) {
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
		}
		
		return MessageList.MESSAGE_LIST_IS_NOT_EMPTY;
	}
	
	private static IndicatorMessagePair checkInvalidArgument(HashMap<String, String> keyFieldsList, String keyWord) {
		if(keyFieldsList.get(keyWord) != null) {
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
		}
		return new IndicatorMessagePair(true, String.format(MessageList.MESSAGE_VALID_ARGUMENT, "Display"));
	}
	
	private static String displayTaskDetails(ArrayList<Task> displayTasksList) {
		String taskDetails = "";
		for (int i = 0; i < displayTasksList.size(); i++) {
			taskDetails += displayTasksList.get(i).toString() +"\n";
		}
		return taskDetails;
	}
}
