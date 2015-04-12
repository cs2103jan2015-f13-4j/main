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

/**
 * This class will check for valid date and time format before generating the date and time objects.
 */
public class DateTimeParser {
	
	// Get the TaskLogging object to log the events
	private static Logger taskLogger = TaskLogging.getInstance();
	
	private static final int NUM_OF_DAYS = 7;
	
	// Valid date and time formats
	private static String[] dateFormatList = {"dd-MM-yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "yyyy/MM/dd", "d MMMM, yyyy"}; 
	private static String[] timeFormatList = {"ha", "h a", "h.ma", "h.m a"};
	private static String[] dayFormatList = {"today", "tdy", "tomorrow", "tmr", "monday", "mon", "tuesday", "tue", "wednesday", "wed", "thursday", "thu", "friday", "fri", "saturday", "sat", "sunday", "sun"};
	private static String standardDateFormat = "d MMMM, yyyy (E)";
	private static String standardTimeFormat = "h.mm a";
	
	// Initialize the date and time objects
	private static DateTimeFormatter dtf = new DateTimeFormatter(null, null);
	private static DateTime convertedDate = new DateTime();
	private static DateTime convertedTime = new DateTime();
	
	/**
	 * This method will generate a date object from a dateValue string.
	 * 
	 * @param dateValue
	 * 			This is the value to be converted to a date object.
	 * 
	 * @return The date object.
	 */
	public static DateTime generateDate(String dateValue) {
		
		convertedDate = new DateTime();
		String checkDateFormatStatus = "";
		
		// Check for valid date format
		checkDateFormatStatus = checkDateFormat(dateValue);
		
		// Date format invalid, return null
		if(checkDateFormatStatus.equals(MessageList.MESSAGE_INCORRECT_DATE_FORMAT) || 
				checkDateFormatStatus.equals(MessageList.MESSAGE_DATE_IS_BEFORE_TODAY)) {
			return null;
		}
		
		if(dateValue.matches("[a-zA-Z]+")) {
			// generate date based on alphabetic format
			convertedDate = generateDateBasedOnDay(dateValue);
		} else { 
			// generate date based on numeric format
			convertedDate = convertDateTime(checkDateFormatStatus, dateValue);
		}
		return new DateTime(convertedDate.getYear(), convertedDate.getMonthOfYear(), convertedDate.getDayOfMonth(), 23, 59);
	}
	
	/**
	 * This method will check for valid date format
	 * 
	 * @param dateValue
	 * 			This is the value to be converted to a date object.
	 * 			
	 * @return The valid date format or error message.
	 */
	public static String checkDateFormat(String dateValue) {
		
		String dateFormat = "";
		DateTime today = new DateTime();
		
		for(int i = 0; i < dateFormatList.length; i++) {
			try	{
				// Try to see whether the dateValue matches the list of valid date formats
				convertedDate = convertDateTime(dateFormatList[i], dateValue);
				
				// Check if the date is before today's date
				if(convertedDate.toLocalDate().compareTo(today.toLocalDate()) < 0) {
					dateFormat = MessageList.MESSAGE_DATE_IS_BEFORE_TODAY;
					taskLogger.log(Level.INFO, "DateTimeParser: " +MessageList.MESSAGE_DATE_IS_BEFORE_TODAY);
					break;
				} // Check if the date falls on today or future date
				  else if(convertedDate.toLocalDate().compareTo(today.toLocalDate()) >= 0) {
					dateFormat = dateFormatList[i];
					break;
				}
			} catch (IllegalArgumentException e) {
				dateFormat = MessageList.MESSAGE_INCORRECT_DATE_FORMAT;
				taskLogger.log(Level.INFO, "DateTimeParser: " +MessageList.MESSAGE_INCORRECT_DATE_FORMAT);
				// Invalid conversion, continue to loop until valid date format found
				continue;
			}
		}
		
		// Check whether the dateValue is a valid alphabetic date format
		for(int i = 0; i < dayFormatList.length; i++) {
			if(dateValue.equalsIgnoreCase(dayFormatList[i])) {
				dateFormat = dayFormatList[i];
				break;
			}
		}
		return dateFormat;
	}
	
	/**
	 * This method will generate a time object from a dateValue string.
	 * 
	 * @param dateValue
	 * 			This is the value to be converted to a time object.
	 * 
	 * @return The time object.
	 */
	public static DateTime generateTime(String timeValue) {
		
		convertedTime = new DateTime();
		String checkTimeFormatStatus = "";
		
		// Check for valid time format
		checkTimeFormatStatus = checkTimeFormat(timeValue);
		
		// Time format invalid, return null
		if(checkTimeFormatStatus.equals(MessageList.MESSAGE_INCORRECT_TIME_FORMAT)) {
			return null;
		}
		// generate time with valid time format
		return convertedTime = convertDateTime(checkTimeFormatStatus, timeValue);
	}
	
	/**
	 * This method will check for valid time format
	 * 
	 * @param timeValue
	 * 			This is the value to be converted to a time object.
	 * 			
	 * @return The valid date format or error message.
	 */
	public static String checkTimeFormat(String timeValue) {
		String timeFormat = "";
		
		for(int i = 0; i < timeFormatList.length; i++) {
			try	{	
				// Try to see whether the timeValue matches the list of valid time formats
				convertedTime = convertDateTime(timeFormatList[i], timeValue);
				timeFormat = timeFormatList[i];
				break;
			} catch (IllegalArgumentException e) {
				timeFormat = MessageList.MESSAGE_INCORRECT_TIME_FORMAT;
				taskLogger.log(Level.INFO, "DateTimeParser: " +MessageList.MESSAGE_INCORRECT_TIME_FORMAT);
				// Invalid conversion, continue to loop until valid time format found
				continue;
			}
		}
		return timeFormat;
	}
	
	/**
	 * This method will convert the datetimeValue to a DateTime object with valid date or time formats
	 * 
	 * @param datetimeFormat
	 * 			This is the valid date or time format.
	 * @param datetimeValue
	 * 			This is the datetimeValue to be converted to DateTime object.
	 * 
	 * @return The converted DateTime object.
	 */
	public static DateTime convertDateTime(String datetimeFormat, String datetimeValue) {
		DateTime convertedDatetime = new DateTime();
		
		// Make use of DateTimeFormatter to define the valid date or time format
		dtf = DateTimeFormat.forPattern(datetimeFormat);
		// Make use of DateTimeFormatter to parse the datetimeValue
		convertedDatetime = dtf.parseDateTime(datetimeValue);
		
		return convertedDatetime;
	}

	/**
	 * This method will display the date in a standard date format
	 * 
	 * @param receivedDateTime
	 * 			This is the DateTime object to be displayed.
	 * 
	 * @return The String representation of the DateTime object in a standard date format.
	 */
	public static String displayDate(DateTime receivedDateTime) {
		if(receivedDateTime == null){
			return "";
		}
		// Make use of DateTimeFormatter to define the standard date format
		DateTimeFormatter dtfout = DateTimeFormat.forPattern(standardDateFormat);
		return dtfout.print(receivedDateTime);
	}
	
	/**
	 * This method will display the time in a standard time format
	 * 
	 * @param receivedDateTime
	 * 			This is the DateTime object to be displayed.
	 * 
	 * @return The String representation of the DateTime object in a standard time format.
	 */
	public static String displayTime(DateTime receivedDateTime) {
		if(receivedDateTime == null){
			return "";
		}
		// Make use of DateTimeFormatter to define the standard time format
		DateTimeFormatter dtfout = DateTimeFormat.forPattern(standardTimeFormat);
		return dtfout.print(receivedDateTime);
	}
	
	/**
	 * This method will generate a date object based on the alphabetic date format
	 * 
	 * @param dateValue
	 * 			This is the value to be converted to a date object.
	 * 			
	 * @return The date object.
	 */
	public static DateTime generateDateBasedOnDay(String dateValue) {
		
		// switch case selection to determine which day to execute
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
	
	/**
	 * This method will generate a date object based on the day value
	 * 
	 * @param dayToCompare
	 * 			This is the day value.
	 * 
	 * @return The date object.
	 */
	public static DateTime generateDateAfterDayComparison(int dayToCompare) {
		
		int dayOfWeek = convertedDate.getDayOfWeek();
		int daysToAdd = 0;
		int incrementDay = -1;
		
		if(dayOfWeek == dayToCompare) {
			return convertedDate;
		}
		else if(dayOfWeek > dayToCompare) {
			
			for(int i = dayOfWeek; i <= NUM_OF_DAYS; i++) {
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
	
	/**
	 * This method will get the date format error
	 * 
	 * @param dateValue
	 * 			This is the value to be converted to a date object.
	 * 
	 * @return The date format error.
	 */
	public static String getDateFormatError(String dateValue) {
		String errorMessage = "";
		return errorMessage = checkDateFormat(dateValue);
	}
}
