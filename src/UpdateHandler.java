import java.util.ArrayList;
import java.util.Date;


public class UpdateHandler {

	public static String executeUpdate(String[] commandInput, ArrayList<Task> listTask){
		
		if(commandInput.length == 1){
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Delete");
		}
		if(!isStringAnInteger(commandInput[1])){
			return String.format(MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Delete");
		}
		
		if(searchTaskIndexStored(Integer.parseInt(commandInput[1]), listTask) >=0){
			
		}
		
		//dummy
		return "";
	}
	
	private static int searchTaskIndexStored(int taskId, ArrayList<Task> listTask){
		for(int i = 0; i < listTask.size(); i++){
			if(taskId == listTask.get(i).getTaskId()){
				return i;
			}
		}
		return -1;
	}
	
	private static String updateContents(String[] commandInput, int index, ArrayList<Task> listTask){
		if(commandInput.length < 3)
		{
			return "There is nothing to update";
		}
		
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
