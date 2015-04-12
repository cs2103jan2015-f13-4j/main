//@author A0112978W
package logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import parser.DateTimeParser;
import utility.IndicatorMessagePair;
import utility.KeywordType;
import utility.MessageList;
import utility.TaskLogging;
import data.Data;
import data.Task;

/**
 * This class will handle the display tasks based on the User's input.
 * It supports displaying tasks by specifying the criteria. The entire list is as shown below.
 * 
 * display all - Displaying all the tasks in the list.
 * display today - Displaying Today's tasks.
 * display tomorrow - Displaying Tomorrow's tasks.
 * display yesterday - Displaying Yesterday's tasks.
 * display thisweek - Displaying This Week's tasks. 
 * display nextweek - Displaying Next Week's tasks.
 * display lastweek - Displaying Last Week's tasks.
 * display monday - Displaying Monday's tasks.
 *        :                    :
 *        :                    :
 * display sunday - Displaying Sunday's tasks.
 * display completed - Displaying Completed tasks.
 * display pending - Displaying Pending tasks.
 */
public class DisplayHandler {

	// Get the TaskLogging object to log the events
	private static Logger taskLogger = TaskLogging.getInstance();
	
	
	public static String executeDisplay(Map<String, String> keyFieldsList, Data smtData) {
		
		checkForValidData(keyFieldsList, smtData);
		
		return displayContents(keyFieldsList, smtData);	
	}
	
	private static String displayContents(Map<String, String> keyFieldsList, Data smtData) {
		
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		ArrayList<Task> displayTasksList = new ArrayList<Task>();
		ArrayList<DateTime> displayDataList = new ArrayList<DateTime>();
		
		String firstKey = keyFieldsList.get(keyFieldsList.keySet().iterator().next()).toUpperCase();
		
			switch(firstKey) {
			case "ALL":
				indicMsg = displaySchedule(keyFieldsList, smtData, displayTasksList);
				break;
			case "COMPLETED":
			case "COMP":
				indicMsg = displayCompletedTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "PENDING":
			case "PEND":
				indicMsg = displayNotCompletedTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "TODAY":
			case "TDY":
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
			case "THISWEEK":
			case "THISWK":
				indicMsg = displayThisWeekTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "LASTWEEK":
			case "LASTWK":
				indicMsg = displayLastWeekTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "NEXTWEEK":
			case "NEXTWK":
				indicMsg = displayNextWeekTasks(keyFieldsList, smtData, displayTasksList);
				break;
			case "BLOCK":
				indicMsg = displayBlockTasks(keyFieldsList, smtData, displayDataList);
				return displayDataDetails(displayDataList);
			default:
				taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
			}
		
		if(!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		if(displayTasksList.isEmpty()) {
			taskLogger.log(Level.INFO, "Display command: " +MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST);
			return MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		}
		taskLogger.log(Level.INFO, "Executed Display " +firstKey);
		return displayTaskDetails(displayTasksList);
	}
	
	private static IndicatorMessagePair displaySchedule(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.ALL.name());
		
		for(int i = 0; i < smtData.getSize(); i++) {
			displayTasksList.add(smtData.getATask(i));		
		}
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayCompletedTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.COMPLETED.name());
		
		retrieveMatchedStatusTasks(smtData, displayTasksList, true);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayNotCompletedTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.PENDING.name());
		
		retrieveMatchedStatusTasks(smtData, displayTasksList, false);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayTodayTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.TODAY.name());
		
		DateTime endDate = generateTodayDate();
		
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate());
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayTomorrowTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.TOMORROW.name());
	
		DateTime endDate = generateTodayDate();
		
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate().plusDays(1));
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayYesterdayTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.YESTERDAY.name());
	
		DateTime endDate = generateTodayDate();
		
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate().minusDays(1));
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displaySpecificDayTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList, String day, int dayValue) {

		checkInvalidArgument(keyFieldsList, day);
		
		retrieveMatchedDateTasks(smtData, displayTasksList, DateTimeParser.generateDateAfterDayComparison(dayValue).toLocalDate());
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayThisWeekTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		ArrayList<DateTime> datesOfWeekList = new ArrayList<DateTime>();
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.THISWEEK.name());
		
		DateTime endDate = generateTodayDate();
		DateTime weekStart = endDate.dayOfWeek().withMinimumValue();
		datesOfWeekList.add(weekStart);
		
		generateListOfDates(weekStart, datesOfWeekList);
		
		retrieveMatchedListOfDatesTasks(smtData, displayTasksList, datesOfWeekList);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayLastWeekTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		ArrayList<DateTime> datesOfWeekList = new ArrayList<DateTime>();
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.LASTWEEK.name());
		
		DateTime endDate = generateTodayDate();
		DateTime weekStart = endDate.dayOfWeek().withMinimumValue();
		weekStart = weekStart.minusDays(1).dayOfWeek().withMinimumValue();
		datesOfWeekList.add(weekStart);
		
		generateListOfDates(weekStart, datesOfWeekList);
		
		retrieveMatchedListOfDatesTasks(smtData, displayTasksList, datesOfWeekList);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayNextWeekTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		ArrayList<DateTime> datesOfWeekList = new ArrayList<DateTime>();
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.NEXTWEEK.name());
		
		DateTime endDate = generateTodayDate();
		DateTime weekStart = endDate.dayOfWeek().withMaximumValue();
		weekStart = weekStart.plusDays(1).dayOfWeek().withMinimumValue();
		datesOfWeekList.add(weekStart);
		
		generateListOfDates(weekStart, datesOfWeekList);
		
		retrieveMatchedListOfDatesTasks(smtData, displayTasksList, datesOfWeekList);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static IndicatorMessagePair displayBlockTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<DateTime> displayDataList) {
		
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.BLOCK.name());
		
		Data newData = new Data();
		newData.setBlockedDateTimeList(smtData.getBlockedDateTimeList());
		
		for(int i = 0; i < newData.getBlockedDateTimeList().size(); i++) {
			displayDataList.add(newData.getABlockedDateTime(i));		
		}
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	private static DateTime generateTodayDate() {
		LocalDate date = new LocalDate();
		DateTime endDate = DateTimeParser.generateDate(date.toString());
		return endDate;
	}
	
	private static ArrayList<DateTime> generateListOfDates(DateTime weekStart, ArrayList<DateTime> datesOfWeekList) {
		int numOfDays = 7;
		
		for(int i = 1; i < numOfDays; i++) {
			weekStart = weekStart.plusDays(1);
			datesOfWeekList.add(weekStart);
		}
		return datesOfWeekList;
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
	
	private static ArrayList<Task> retrieveMatchedListOfDatesTasks(Data smtData, ArrayList<Task> displayTasksList, ArrayList<DateTime> datesOfWeekList) {
		for(int i = 0; i < smtData.getSize(); i++) {
			if(smtData.getATask(i).getTaskEndDateTime() != null) {
				for(int j = 0; j < datesOfWeekList.size(); j++) {
					if(smtData.getATask(i).getTaskEndDateTime().toLocalDate().equals(datesOfWeekList.get(j).toLocalDate())) {
						displayTasksList.add(smtData.getATask(i));	
					}
				}
			}
		}
		return displayTasksList;
	}

	private static String checkForValidData(Map<String, String> keyFieldsList, Data smtData) {
		int numItemExpected = 1;
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()) {
			taskLogger.log(Level.INFO, "Display command: " +MessageList.MESSAGE_NULL);
			return MessageList.MESSAGE_NULL;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()) {
			taskLogger.log(Level.INFO, "Display command: " +MessageList.MESSAGE_NO_TASK_IN_LIST);
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyFieldsList.size() != numItemExpected) {
			taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
		}
		
		return MessageList.MESSAGE_LIST_IS_NOT_EMPTY;
	}
	
	private static IndicatorMessagePair checkInvalidArgument(Map<String, String> keyFieldsList, String keyWord) {
		if(keyFieldsList.get(keyWord) != null) {
			taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
		}
		return new IndicatorMessagePair(true, String.format(MessageList.MESSAGE_VALID_ARGUMENT, "Display"));
	}
	
	private static String displayTaskDetails(ArrayList<Task> displayTasksList) {
		String taskDetails = "";
		
		Collections.sort(displayTasksList, SortHandler.TaskDeadlineComparator);
		
		for (int i = 0; i < displayTasksList.size(); i++) {
			taskDetails += displayTasksList.get(i).toString() +"\n";
		}
		return taskDetails;
	}
	
	private static String displayDataDetails(ArrayList<DateTime> displayDataList) {
		String dataDetails = "";
		
		Collections.sort(displayDataList, SortHandler.TaskDateTimeDeadlineComparator);
		
		for (int i = 0; i < displayDataList.size(); i++) {
			dataDetails += displayDataList.get(i).toLocalDate().toString() +"\n";
		}
		return dataDetails;
	}
}
