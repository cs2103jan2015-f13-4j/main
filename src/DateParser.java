import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateParser {
	
	private static String[] dateFormatList = {"dd-MM-yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "yyyy/MM/dd", "d MMMM, yyyy"}; 
	private static String standardFormat = "d MMMM, yyyy";
	private static String timeFormat = "hh:mm a";
	
	public static DateTime generateDate(String dateValue) {
		boolean validFormat = false;
		DateTimeFormatter dtf = new DateTimeFormatter(null, null);
		DateTime convertedDateTime = null;
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
		
		if(!validFormat) {
			return null;
		}
		return convertedDateTime;
	}
	
	public static DateTime generateTime(String timeValue) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern(timeFormat);
		DateTime time = dtf.parseDateTime(timeValue);
		
		return time;
	}
	
	public static String displayDateTime(DateTime receivedDateTime){
		if(receivedDateTime == null){
			return "";
		}
		DateTimeFormatter dtfout = DateTimeFormat.forPattern(standardFormat);
		return dtfout.print(receivedDateTime);
	}
}
