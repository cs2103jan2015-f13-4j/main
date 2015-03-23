package parser;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateParser {
	
	private static String[] dateFormatList = {"dd-MM-yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "yyyy/MM/dd", "d MMMM, yyyy"}; 
	private static String standardDateFormat = "d MMMM, yyyy";
	private static String timeFormat = "hh:mm a";
	private static DateTime convertedDateTime = new DateTime();
	
	public static DateTime generateDate(String dateValue) {
		boolean validFormat = false;
		DateTimeFormatter dtf = new DateTimeFormatter(null, null);
		
		for(int i = 0; i < dateFormatList.length; i++) {
			
			try	{
					dtf = DateTimeFormat.forPattern(dateFormatList[i]);
					convertedDateTime = dtf.parseDateTime(dateValue); 
					validFormat = true;
					break;
			} catch (IllegalArgumentException e) {
				//Invalid conversion, continue to loop until valid format found
				continue;
			}
		}
		
		switch (dateValue.toLowerCase()) {
			case "today": {
				return convertedDateTime;
			} 
			case "tomorrow": {
				return convertedDateTime.plusDays(1);
			} 
			case "monday":
			case "mon": {
				return dayComparison(DateTimeConstants.MONDAY);
			}
			case "tuesday":
			case "tue": {
				return dayComparison(DateTimeConstants.TUESDAY);
			}
			case "wednesday":
			case "wed": {
				return dayComparison(DateTimeConstants.WEDNESDAY);
			} 
			case "thursday":
			case "thur": {
				return dayComparison(DateTimeConstants.THURSDAY);
			} 
			case "friday":
			case "fri": {
				return dayComparison(DateTimeConstants.FRIDAY);
			} 
			case "saturday":
			case "sat": {
				return dayComparison(DateTimeConstants.SATURDAY);
			} 
			case "sunday":
			case "sun": {
				return dayComparison(DateTimeConstants.SUNDAY);
			} 
			default: {
				//default condition
			}
		}
		
		if(!validFormat) {
			return null;
		}
		return convertedDateTime;
	}
	
	public static DateTime generateTime(String timeValue) {
		boolean validFormat = false;
		DateTimeFormatter dtf = new DateTimeFormatter(null, null);
		DateTime time = new DateTime();
		
		try {
				dtf = DateTimeFormat.forPattern(timeFormat);
				time = dtf.parseDateTime(timeValue);
				validFormat = true;
		} catch (IllegalArgumentException e) {
			//Invalid conversion, continue to loop until valid format found
		}
		
		if(!validFormat) {
			return null;
		}
		
		return time;
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
		DateTimeFormatter dtfout = DateTimeFormat.forPattern(timeFormat);
		return dtfout.print(receivedDateTime);
	}
	
	public static DateTime dayComparison(int dayToCompare) {
		int dayOfWeek = convertedDateTime.getDayOfWeek();
		int numOfDays = 7;
		
		if(dayOfWeek == dayToCompare) {
			return convertedDateTime;
		}
		else if(dayOfWeek > dayToCompare) {
			return convertedDateTime.plusDays(numOfDays - dayToCompare);
		}
		else if(dayOfWeek < dayToCompare) {
			return convertedDateTime.plusDays(dayToCompare - dayOfWeek);
		}
		return null;
	}
}
