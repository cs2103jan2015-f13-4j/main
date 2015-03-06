import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddHandler {
	
	private static int taskId = 1;
	private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private static Date startDate;
	private static Date endDate; 
	
	public static String executeAdd(String fileName,String input, ArrayList<Task> listTask) {
		
		String inputTxt = removeFirstWord(input);
		try {
			startDate = dateFormat.parse("06-03-2015");
			endDate = dateFormat.parse("09-03-2015");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		listTask.add(new Task(taskId, inputTxt, startDate, endDate));
		
		return MessageList.MESSAGE_ADDED;
	}
	
	private static String removeFirstWord(String input)
	{
			return input.replace(getFirstWord(input), "").trim();
	}
	
	private static String getFirstWord(String input)
	{
		String commandTypeString = input.split("\\s+")[0];
		return commandTypeString;
	}

}
