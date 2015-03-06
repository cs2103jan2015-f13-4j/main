import java.util.ArrayList;
import java.util.Date;


public class UpdateHandler {

	//private static final int 
	
	public static String executeUpdate(String[] commandInput, ArrayList<Task> listTask){
		
		if(commandInput.length == 1){
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Delete");
		}
		
		if(!isStringAnInteger(commandInput[1])){
			//return String.for
		}
		
		//dummy
		return "";
	}
	
	/**
	 * This method check if the given string can be converted to integer
	 * 
	 * @param inputStr
	 *            the string to be converted
	 * @return true if can be converted, else false
	 */
	private static boolean isStringAnInteger(String inputStr) {
		try {
			Integer.parseInt(inputStr);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}
