import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateHandler {
	
	/**
	 * This method handle the update execution and update the contents to the ArrayList of tasks
	 * @param keyParamList contains the list of keyword and the data it has
	 * @param listTask contains the list of current tasks
	 * @return message
	 */
	public static String executeUpdate(ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask){
		
		if(keyParamList == null || keyParamList.isEmpty()){
			return MessageList.MESSAGE_NULL;
		}
		
		if(listTask == null || listTask.isEmpty()){
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(!isStringAnInteger(keyParamList.get(0).getParam())){
			return String.format(MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Update");
		}
		
		int index = searchTaskIndexStored(Integer.parseInt(keyParamList.get(0).getParam()), listTask);
		
		if(index < 0){
			return MessageList.MESSAGE_NO_SUCH_TASK;
		}
		
		return updateContents(keyParamList, index, listTask);
	}
	
	/**
	 * This method search for a task in the ArrayList
	 * @param taskId the task id for searching
	 * @param listTask contains the list of current tasks
	 * @return message
	 */
	private static int searchTaskIndexStored(int taskId, ArrayList<Task> listTask){
		for(int i = 0; i < listTask.size(); i++){
			if(taskId == listTask.get(i).getTaskId()){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * This method will update the contents based on the key word
	 * @param keyParam contains the list of keyword and the data it has
	 * @param index indicate the location of the intended task to be updated
	 * @param listTask contains the list of current tasks
	 * @return message
	 */
	private static String updateContents(ArrayList<KeyParamPair> keyParamList, int index, ArrayList<Task> listTask){
		IndicatorMessagePair indicMsg;
		KeywordType.List_Keywords getKey;
		for(int i = 1; i < keyParamList.size(); i++){
			getKey = KeywordType.getKeyword(keyParamList.get(i).getKeyword());
			switch(getKey){
			case BY:
				indicMsg = updateTaskByOrEndWhen(listTask, index, keyParamList.get(i));
				break;
			case TASKDESC:
				indicMsg = updateTaskDesc(listTask, index, keyParamList.get(i));
				break;
			case TASKSTART:
				indicMsg = updateTaskStartWhen(listTask, index, keyParamList.get(i));
				break;
			case TASKEND:
				indicMsg = updateTaskByOrEndWhen(listTask, index, keyParamList.get(i));
				break;
			default:
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Update");
			}
			
			if(!indicMsg.isTrue()){
				return indicMsg.getMessage();
			}
		}
		return MessageList.MESSAGE_UPDATE_SUCCESS;
	}
	
	/**
	 * This method will update the task end date and will determine whether it can be updated as well.
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyParam contains the keyword and the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskByOrEndWhen(ArrayList<Task> listTask, int index, KeyParamPair keyParam){
		if(keyParam.getParam() == null || keyParam.getParam().isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date startDate = new Date();
		try {
			startDate = dateFormat.parse(keyParam.getParam());
		} catch (ParseException e) {
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "End"));
		}
		listTask.get(index).setTaskStartDate(startDate);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method will update the task description and will determine whether it can be updated as well.
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyParam contains the keyword and the data it has
	 * @return true if success, false if the parameter and message
	 */
	private static IndicatorMessagePair updateTaskDesc(ArrayList<Task> listTask, int index, KeyParamPair keyParam){
		if(keyParam.getParam() == null || keyParam.getParam().isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_DESCRIPTION_EMPTY);
		}
		listTask.get(index).setTaskDescription(keyParam.getParam());
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method will update the task start date and will determine whether it can be updated as well.
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyParam contains the keyword and the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskStartWhen(ArrayList<Task> listTask, int index, KeyParamPair keyParam){
		if(keyParam.getParam() == null || keyParam.getParam().isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date endDate = new Date();
		try {
			endDate = dateFormat.parse(keyParam.getParam());
		} catch (ParseException e) {
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start"));
		}
		listTask.get(index).setTaskEndDate(endDate);
		return new IndicatorMessagePair(true, "");
	}
	
	
	/**
	 * This method check if the given string can be converted to integer
	 * 
	 * @param inputStr
	 *            the string to be converted
	 * @return true if it is a integer, else false
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
