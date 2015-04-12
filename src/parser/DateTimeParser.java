//@author A0112978W
package parser;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import utility.MessageList;
import utility.TaskLogging;

public class DateTimeParser {
	
	private static Logger taskLogger = TaskLogging.getInstance();
	
	private static String[] dateFormatList = {"dd-MM-yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "yyyy/MM/dd", "d MMMM, yyyy"}; 
	private static String[] timeFormatList = {"ha", "h a", "h.ma", "h.m a"};
	private static String[] dayFormatList = {"today", "tdy", "tomorrow", "tmr", "monday", "mon", "tuesday", "tue", "wednesday", "wed", "thursday", "thu", "friday", "fri", "saturday", "sat", "sunday", "sun"};
	private static String standardDateFormat = "d MMMM, yyyy (E)";
	private static String standardTimeFormat = "h.mm a";
	
	private static DateTimeFormatter dtf = new DateTimeFormatter(null, null);
	private static DateTime convertedDate = new DateTime();
	private static DateTime convertedTime = new DateTime();
	
	public static DateTime generateDate(String dateValue) {
		
		convertedDate = new DateTime();
		String checkDateFormatStatus = "";
		
		checkDateFormatStatus = checkDateFormat(dateValue);
		
		if(checkDateFormatStatus.equals(MessageList.MESSAGE_INCORRECT_DATE_FORMAT) || 
				checkDateFormatStatus.equals(MessageList.MESSAGE_DATE_IS_BEFORE_TODAY)) {
			return null;
		}
		
		if(dateValue.matches("[a-zA-Z]+")) {
			convertedDate = generateDateBasedOnDay(dateValue);
		} else {
			convertedDate = convertDateTime(checkDateFormatStatus, dateValue);
		}
		return new DateTime(convertedDate.getYear(), convertedDate.getMonthOfYear(), convertedDate.getDayOfMonth(), 23, 59);
	}
	
	public static String checkDateFormat(String dateValue) {
		
		String dateFormat = "";
		DateTime today = new DateTime();
		
		for(int i = 0; i < dateFormatList.length; i++) {
			try	{
				convertedDate = convertDateTime(dateFormatList[i], dateValue);
					
				if(convertedDate.toLocalDate().compareTo(today.toLocalDate()) < 0) {
					dateFormat = MessageList.MESSAGE_DATE_IS_BEFORE_TODAY;
					taskLogger.log(Level.INFO, "DateTimeParser: " +MessageList.MESSAGE_DATE_IS_BEFORE_TODAY);
					break;
				} else if(convertedDate.toLocalDate().compareTo(today.toLocalDate()) >= 0) {
					dateFormat = dateFormatList[i];
					break;
				}
			} catch (IllegalArgumentException e) {
				//Invalid conversion, continue to loop until valid format found
				dateFormat = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
				taskLogger.log(Level.INFO, "DateTimeParser: " +MessageList.MESSAGE_INCORRECT_DATE_FORMAT);
				continue;
			}
		}
		
		for(int i = 0; i < dayFormatList.length; i++) {
			if(dateValue.equalsIgnoreCase(dayFormatList[i])) {
				dateFormat = dayFormatList[i];
				break;
			}
		}
		
		return dateFormat;
	}
	
	public static DateTime generateTime(String timeValue) {
		
		convertedTime = new DateTime();
		String checkTimeFormatStatus = "";
		checkTimeFormatStatus = checkTimeFormat(timeValue);
		
		if(checkTimeFormatStatus.equals(MessageList.MESSAGE_INCORRECT_TIME_FORMAT)) {
			return null;
		}
		return convertedTime = convertDateTime(checkTimeFormatStatus, timeValue);
	}
	
	public static String checkTimeFormat(String timeValue) {
		String timeFormat = "";
		
		for(int i = 0; i < timeFormatList.length; i++) {
			try	{	
					convertedTime = convertDateTime(timeFormatList[i], timeValue);
					timeFormat = timeFormatList[i];
					break;
			} catch (IllegalArgumentException e) {
				//Invalid conversion, continue to loop until valid format found
				timeFormat = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
				taskLogger.log(Level.INFO, "DateTimeParser: " +MessageList.MESSAGE_INCORRECT_TIME_FORMAT);
				continue;
			}
		}
		return timeFormat;
	}
	
	public static DateTime convertDateTime(String datetimeFormat, String datetimeValue) {
		DateTime convertedDatetime = new DateTime();
		
		dtf = DateTimeFormat.forPattern(datetimeFormat);
		convertedDatetime = dtf.parseDateTime(datetimeValue);
		
		return convertedDatetime;
	}

	public static String displayDate(DateTime receivedDateTime) {
		if(receivedDateTime == null){
			return "";
		}
		DateTimeFormatter dtfout = DateTimeFormat.forPattern(standardDateFormat);
		return dtfout.print(receivedDateTime);
	}
	
	public static String displayTime(DateTime receivedDateTime) {
		if(receivedDateTime == null){
			return "";
		}
		DateTimeFormatter dtfout = DateTimeFormat.forPattern(standardTimeFormat);
		return dtfout.print(receivedDateTime);
	}
	
	public static DateTime generateDateBasedOnDay(String dateValue) {
		
		switch (dateValue.toUpperCase()) {
			case "TODAY":
			case "TDY":
				return convertedDate;
			case "TOMORROW": 
			case "TMR":
				return convertedDate.plusDays(1);
			case "MONDAY":
			case "MON":
				return generateDateAfterDayComparison(DateTimeConstants.MONDAY);
			case "TUESDAY":
			case "TUE":
				return generateDateAfterDayComparison(DateTimeConstants.TUESDAY);
			case "WEDNESDAY":
			case "WED":
				return generateDateAfterDayComparison(DateTimeConstants.WEDNESDAY);
			case "THURSDAY":
			case "THU":
				return generateDateAfterDayComparison(DateTimeConstants.THURSDAY);
			case "FRIDAY":
			case "FRI":
				return generateDateAfterDayComparison(DateTimeConstants.FRIDAY);
			case "SATURDAY":
			case "SAT":
				return generateDateAfterDayComparison(DateTimeConstants.SATURDAY);
			case "SUNDAY":
			case "SUN":
				return generateDateAfterDayComparison(DateTimeConstants.SUNDAY);
			default:
				return null;
		}
	}
	
	public static DateTime generateDateAfterDayComparison(int dayToCompare) {
		
		int dayOfWeek = convertedDate.getDayOfWeek();
		int numOfDays = 7;
		int daysToAdd = 0;
		int incrementDay = -1;
		
		if(dayOfWeek == dayToCompare) {
			return convertedDate;
		}
		else if(dayOfWeek > dayToCompare) {
			
			for(int i = dayOfWeek; i <= numOfDays; i++) {
				incrementDay++;
				daysToAdd = dayToCompare + incrementDay;
			}
			return convertedDate.plusDays(daysToAdd);
		}
		else if(dayOfWeek < dayToCompare) {
			return convertedDate.plusDays(dayToCompare - dayOfWeek);
		}
		return null;
	}
	
	public static String getDateFormatError(String dateValue) {
		String errorMessage = "";
		return errorMessage = checkDateFormat(dateValue);
	}
}
