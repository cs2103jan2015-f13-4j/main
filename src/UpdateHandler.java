import java.util.ArrayList;
import org.joda.time.DateTime;

public class UpdateHandler {
	
	/**
	 * This method handle the update execution and update the contents to the ArrayList of tasks
	 * @param keyPFieldsList contains the list of keyword and the data it has
	 * @param listTask contains the list of current tasks
	 * @return message
	 */
	public static String executeUpdate(String fileName, ArrayList<KeyFieldPair> keyFieldsList, ArrayList<Task> listTask){
		int firstKeyFieldsPair = 0, minTaskListSize = 0;
		
		if(keyFieldsList == null || keyFieldsList.isEmpty()){
			return MessageList.MESSAGE_NULL;
		}
		
		if(listTask == null || listTask.isEmpty()){
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		if(!isStringAnInteger(keyFieldsList.get(firstKeyFieldsPair).getFields())){
			return String.format(MessageList.MESSAGE_INVALID_CONVERSION_INTEGER, "Update");
		}
		
		int index = searchTaskIndexStored(Integer.parseInt(keyFieldsList.get(firstKeyFieldsPair).getFields()), listTask);
		
		if(index < minTaskListSize){
			return MessageList.MESSAGE_NO_SUCH_TASK;
		}
		
		return updateContents(fileName, keyFieldsList, index, listTask);
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
	 * @param keyFieldsList contains the list of keyword and the data it has
	 * @param index indicate the location of the intended task to be updated
	 * @param listTask contains the list of current tasks
	 * @return message
	 */
	private static String updateContents(String fileName, ArrayList<KeyFieldPair> keyFieldsList, int index, ArrayList<Task> listTask){
		IndicatorMessagePair indicMsg;
		KeywordType.List_Keywords getKey;
		for(int i = 1; i < keyFieldsList.size(); i++){
			getKey = KeywordType.getKeyword(keyFieldsList.get(i).getKeyword());
			switch(getKey){
			case BY:
				indicMsg = updateTaskByOrEndWhen(listTask, index, keyFieldsList.get(i));
				break;
			case TASKDESC:
				indicMsg = updateTaskDesc(listTask, index, keyFieldsList.get(i));
				break;
			case TASKSTART:
				indicMsg = updateTaskStartWhen(listTask, index, keyFieldsList.get(i));
				break;
			case TASKEND:
				indicMsg = updateTaskByOrEndWhen(listTask, index, keyFieldsList.get(i));
				break;
			case COMPLETED:
				indicMsg = updateTaskStatus(listTask, index, keyFieldsList.get(i), true);
				break;
			case PENDING:
				indicMsg = updateTaskStatus(listTask, index, keyFieldsList.get(i), false);
				break;
			default:
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Update");
			}
			
			if(!indicMsg.isTrue()){
				return indicMsg.getMessage();
			}
		}
		indicMsg = new IndicatorMessagePair();
		FileHandler.writeToFile(fileName, listTask, indicMsg);
		if(!indicMsg.isTrue()){
			return indicMsg.getMessage();
		}
		return MessageList.MESSAGE_UPDATE_SUCCESS;
	}
	
	/**
	 * This method will update the task end date and will determine whether it can be updated as well.
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskByOrEndWhen(ArrayList<Task> listTask, int index, KeyFieldPair keyFields){
		if(keyFields.getFields() == null || keyFields.getFields().isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateTime endDate = DateParser.generateDate(keyFields.getFields());
		if(endDate == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "End"));
		}
		listTask.get(index).setTaskEndDateTime(endDate);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method will update the task description and will determine whether it can be updated as well.
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @return true if success, false if the parameter and message
	 */
	private static IndicatorMessagePair updateTaskDesc(ArrayList<Task> listTask, int index, KeyFieldPair keyFields){
		if(keyFields.getFields() == null || keyFields.getFields().isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_DESCRIPTION_EMPTY);
		}
		listTask.get(index).setTaskDescription(keyFields.getFields());
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method will update the task start date and will determine whether it can be updated as well.
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskStartWhen(ArrayList<Task> listTask, int index, KeyFieldPair keyFields){
		if(keyFields.getFields() == null || keyFields.getFields().isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		
		DateTime startDate = DateParser.generateDate(keyFields.getFields());
		if(startDate == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start"));
		}
		
		listTask.get(index).setTaskStartDateTime(startDate);
		return new IndicatorMessagePair(true, "");
	}
	
	/**
	 * This method update the status of the task to completed or pending.
	 * @param listTask contains the list of current tasks
	 * @param index indicate the location of the intended task to be updated
	 * @param keyFields contains the keyword and the data it has
	 * @param status the task status in boolean
	 * @return true if success, false if there is an invalid conversion object and message
	 */
	private static IndicatorMessagePair updateTaskStatus(ArrayList<Task> listTask, int index, KeyFieldPair keyFields, boolean status){
		if(keyFields.getFields() == null || !keyFields.getFields().isEmpty()){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_UPDATE_STATUS_EXTRA_FIELD);
		}
		
		listTask.get(index).setTaskStatus(status);
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
