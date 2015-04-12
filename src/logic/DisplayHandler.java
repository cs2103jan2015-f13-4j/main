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
	
	private static final int NUM_OF_DAYS = 7;
	private static final int NUM_ITEMS_EXPECTED = 1;
	
	/**
	 * This method will execute the display function based on the display criteria from the User
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * 
	 * @return The task's information to be displayed to the User.
	 */
	public static String executeDisplay(Map<String, String> keyFieldsList, Data smtData) {
		
		// Data validation
		checkForValidData(keyFieldsList, smtData);
		
		return displayContents(keyFieldsList, smtData);	
	}
	
	/**
	 * This method will retrieve the task's information and display to the User
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * 
	 * @return The retrieved task's information.
	 */
	private static String displayContents(Map<String, String> keyFieldsList, Data smtData) {
		// Initialize IndicatorMessagePair to get result message
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		
		// ArrayList to store the retrieved Task and DateTime objects
		ArrayList<Task> displayTasksList = new ArrayList<Task>();
		ArrayList<DateTime> displayDataList = new ArrayList<DateTime>();
		
		// Determine the display criteria
		String firstKey = keyFieldsList.get(keyFieldsList.keySet().iterator().next()).toUpperCase();
		
			// switch case selection to determine which display function to execute
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
				// Log the default case
				taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
			}
		
		if(!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		if(displayTasksList.isEmpty()) {
			// Log when display list is empty
			taskLogger.log(Level.INFO, "Display command: " +MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST);
			return MessageList.MESSAGE_NO_TASK_IN_DISPLAY_LIST;
		}
		// Log when display function executed
		taskLogger.log(Level.INFO, "Executed Display " +firstKey);
		return displayTaskDetails(displayTasksList);
	}
	
	/**
	 * This method will display all the task's information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displaySchedule(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.ALL.name());
		
		for(int i = 0; i < smtData.getSize(); i++) {
			displayTasksList.add(smtData.getATask(i));		
		}
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display completed task's information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayCompletedTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.COMPLETED.name());
		
		// Retrieve the task that matches the status
		retrieveMatchedStatusTasks(smtData, displayTasksList, true);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display pending task's information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayNotCompletedTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.PENDING.name());
		
		// Retrieve the task that matches the status
		retrieveMatchedStatusTasks(smtData, displayTasksList, false);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display today's task information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayTodayTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.TODAY.name());
		
		DateTime endDate = generateTodayDate();
		
		// Retrieve the task that matches the date
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate());
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display tomorrow's task information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayTomorrowTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.TOMORROW.name());
	
		DateTime endDate = generateTodayDate();
		
		// Retrieve the task that matches the date
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate().plusDays(1));
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display yesterday's task information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayYesterdayTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.YESTERDAY.name());
	
		DateTime endDate = generateTodayDate();
		
		// Retrieve the task that matches the date
		retrieveMatchedDateTasks(smtData, displayTasksList, endDate.toLocalDate().minusDays(1));
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display the specific day task's information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * @param day
	 * 			Day of the Week
	 * @param dayValue
	 * 			Day Value of the Week
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displaySpecificDayTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList, String day, int dayValue) {

		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, day);
		
		// Retrieve the task that matches the date
		retrieveMatchedDateTasks(smtData, displayTasksList, DateTimeParser.generateDateAfterDayComparison(dayValue).toLocalDate());
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display this week's task information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayThisWeekTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// ArrayList to store the dates in this week
		ArrayList<DateTime> datesOfWeekList = new ArrayList<DateTime>();
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.THISWEEK.name());
		
		DateTime endDate = generateTodayDate();
		DateTime weekStart = endDate.dayOfWeek().withMinimumValue();
		// Add first day of the week
		datesOfWeekList.add(weekStart);
		
		// Generate a list of dates in this week
		generateListOfDates(weekStart, datesOfWeekList);
		
		// Retrieve the task that matches the list of dates
		retrieveMatchedListOfDatesTasks(smtData, displayTasksList, datesOfWeekList);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display last week's task information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayLastWeekTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// ArrayList to store the dates of last week
		ArrayList<DateTime> datesOfWeekList = new ArrayList<DateTime>();
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.LASTWEEK.name());
		
		DateTime endDate = generateTodayDate();
		DateTime weekStart = endDate.dayOfWeek().withMinimumValue();
		weekStart = weekStart.minusDays(1).dayOfWeek().withMinimumValue();
		// Add the first day of last week
		datesOfWeekList.add(weekStart);
		
		// Generate a list of dates of last week
		generateListOfDates(weekStart, datesOfWeekList);
		
		// Retrieve the task that matches the list of dates
		retrieveMatchedListOfDatesTasks(smtData, displayTasksList, datesOfWeekList);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display next week's task information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayNextWeekTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<Task> displayTasksList) {
		
		// ArrayList to store the dates of next week
		ArrayList<DateTime> datesOfWeekList = new ArrayList<DateTime>();
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.NEXTWEEK.name());
		
		DateTime endDate = generateTodayDate();
		DateTime weekStart = endDate.dayOfWeek().withMaximumValue();
		weekStart = weekStart.plusDays(1).dayOfWeek().withMinimumValue();
		// Add the first day of next week
		datesOfWeekList.add(weekStart);
		
		// Generate a list of dates of next week
		generateListOfDates(weekStart, datesOfWeekList);
		
		// Retrieve the task that matches the list of dates
		retrieveMatchedListOfDatesTasks(smtData, displayTasksList, datesOfWeekList);
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will display blocked task's information in the list
	 * 
	 * @param keyFieldsList
	 * 			This list contains the keywords and data.
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayDataList
	 * 			This list contains the retrieved data.
	 * 
	 * @return The IndicatorMessagePair result message.
	 */
	private static IndicatorMessagePair displayBlockTasks(Map<String, String> keyFieldsList, Data smtData, ArrayList<DateTime> displayDataList) {
		
		// Check for invalid argument
		checkInvalidArgument(keyFieldsList, KeywordType.List_SearchKeywords.BLOCK.name());
		
		Data newData = new Data();
		// Set the blocked dates retrieved
		newData.setBlockedDateTimeList(smtData.getBlockedDateTimeList());
		
		for(int i = 0; i < newData.getBlockedDateTimeList().size(); i++) {
			displayDataList.add(newData.getABlockedDateTime(i));		
		}
		
		return new IndicatorMessagePair(true, "Success");
	}
	
	/**
	 * This method will generate today's date
	 * 
	 * @return Today's date.
	 */
	private static DateTime generateTodayDate() {
		LocalDate date = new LocalDate();
		DateTime endDate = DateTimeParser.generateDate(date.toString());
		return endDate;
	}
	
	/**
	 * This method will generate a list of dates in that particular week
	 * 
	 * @param weekStart
	 * 			First day of the Week.
	 * @param datesOfWeekList
	 * 			This list contains the dates in that particular week.
	 * 
	 * @return The list of dates in that particular week.
	 */
	private static ArrayList<DateTime> generateListOfDates(DateTime weekStart, ArrayList<DateTime> datesOfWeekList) {
		
		for(int i = 1; i < NUM_OF_DAYS; i++) {
			weekStart = weekStart.plusDays(1);
			datesOfWeekList.add(weekStart);
		}
		return datesOfWeekList;
	}
	
	/**
	 * This method will retrieve the task that matches the status
	 * 
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * @param status
	 * 			Task status to match.
	 * 
	 * @return The list of tasks that matches the status.
	 */
	private static ArrayList<Task> retrieveMatchedStatusTasks(Data smtData, ArrayList<Task> displayTasksList, boolean status) {
		for(int i = 0; i < smtData.getSize(); i++) {
			if(smtData.getATask(i).getTaskStatus() == status) {
				displayTasksList.add(smtData.getATask(i));		
			}
		}
		return displayTasksList;
	}
	
	/**
	 * This method will retrieve the task that matches the date
	 * 
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * @param matchDate
	 * 			Date to match.
	 * 
	 * @return The list of tasks that matches the date.
	 */
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
	
	/**
	 * This method will retrieve the task that matches a list of dates
	 * 
	 * @param smtData
	 * 			This data contains the task's information.
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * @param datesOfWeekList
	 * 			This list contains the blocked dates.
	 * 
	 * @return The list of tasks that matches the blocked dates.
	 */
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
	
	/**
	 * This method will check for valid date
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
			taskLogger.log(Level.INFO, "Display command: " +MessageList.MESSAGE_NULL);
			return MessageList.MESSAGE_NULL;
		}
		
		if(smtData == null || smtData.getListTask().isEmpty()) {
			taskLogger.log(Level.INFO, "Display command: " +MessageList.MESSAGE_NO_TASK_IN_LIST);
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(keyFieldsList.size() != NUM_ITEMS_EXPECTED) {
			taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display");
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
			taskLogger.log(Level.INFO, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Display"));
		}
		return new IndicatorMessagePair(true, String.format(MessageList.MESSAGE_VALID_ARGUMENT, "Display"));
	}
	
	/**
	 * This method will display the task's information
	 * 
	 * @param displayTasksList
	 * 			This list contains the retrieved tasks to be displayed.
	 * 
	 * @return The task's information.
	 */
	private static String displayTaskDetails(ArrayList<Task> displayTasksList) {
		String taskDetails = "";
		
		// displayTasksList is sorted by deadline
		Collections.sort(displayTasksList, SortHandler.TaskDeadlineComparator);
		
		for (int i = 0; i < displayTasksList.size(); i++) {
			taskDetails += displayTasksList.get(i).toString() +"\n";
		}
		return taskDetails;
	}
	
	/**
	 * This method will display the blocked dates
	 * 
	 * @param displayDataList
	 * 			This list contains the retrieved data.
	 * 
	 * @return The blocked dates.
	 */
	private static String displayDataDetails(ArrayList<DateTime> displayDataList) {
		String dataDetails = "";
		
		// displayDataList is sorted by deadline
		Collections.sort(displayDataList, SortHandler.TaskDateTimeDeadlineComparator);
		
		for (int i = 0; i < displayDataList.size(); i++) {
			dataDetails += displayDataList.get(i).toLocalDate().toString() +"\n";
		}
		return dataDetails;
	}
}
