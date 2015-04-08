//@A0111935L
package data;
import org.joda.time.DateTime;

import utility.IndicatorMessagePair;
import utility.MessageList;

/**
 * This class allow the string to be converted into Task object.
 * It is from the text file loaded into the system
 * @author Tian
 *
 */
public class TaskParserFromTextFile {

	//task component separator char
	private static final String TASK_COMPONENT_SEPARATOR = "|";
	//task component separator char
	private static final String TASK_COMPONENT_SEPARATOR_SPLIT = "\\|";
	//task field and data separator char
	private static final String TASK_FIELD_DATA_SEPARATOR = "=";
	//task field type at slot 0
	private static final int TASK_FIELD_SLOT = 0;
	//data in task field at slot 1
	private static final int TASK_FIELD_DATA_SLOT = 1;
	
	public static Task generateStringFromTextFileToTask(String eachTaskString){
		if(eachTaskString == null){
			return null;
		}
		
		if(!eachTaskString.contains(TASK_COMPONENT_SEPARATOR)){
			return null;
		}
		
		String[] taskComponent = eachTaskString.split(TASK_COMPONENT_SEPARATOR_SPLIT);
		Task newTask = new Task();
		
		for(int i = 0; i < taskComponent.length; i++){
			String[] separateFieldData = taskComponent[i].split(TASK_FIELD_DATA_SEPARATOR);
			if(separateFieldData.length != 2){
				return null;
			}
			IndicatorMessagePair indicMsg;
			TaskFieldList.List_Task_Field fieldType = TaskFieldList.getTaskField(separateFieldData[TASK_FIELD_SLOT]);
			switch(fieldType){
			case TASKID:
				indicMsg = setTaskId(newTask, separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKDESC:
				indicMsg = setTaskDesc(newTask, separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKSTARTDATETIME:
				indicMsg = setTaskStartDateTime(newTask, separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKENDDATETIME:
				indicMsg = setTaskEndDateTime(newTask, separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case WEEKLYDAY:
				indicMsg = setWeeklyDay(newTask, separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKSTATUS:
				indicMsg = setTaskStatus(newTask, separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKDEADLINESET:
				indicMsg = setTaskDeadLineSet(newTask, separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			default:
				return null;
			}
			
			if(!indicMsg.isTrue()){
				return null;
			}
		}
		if(newTask.getTaskId() < 0){
			return null;
		}
		return newTask;
	}
	
	private static IndicatorMessagePair setTaskId(Task newTask, String id){
		if(!isStringAnInteger(id) || id == null){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_ERROR_CONVERT_TASKID);
		}
		
		newTask.setTaskId(Integer.parseInt(id));
		
		return new IndicatorMessagePair(true, "");
	}
	
	private static IndicatorMessagePair setTaskDesc(Task newTask, String desc){
		if(desc == null){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_DESCRIPTION_EMPTY);
		}
		
		newTask.setTaskDescription(desc);
		
		return new IndicatorMessagePair(true, "");
	}
	
	private static IndicatorMessagePair setTaskStartDateTime(Task newTask, String startDateTime){
		if(startDateTime == null){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		
		DateTime convertedDateTime = DateTime.parse(startDateTime);
		
		if(convertedDateTime == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start"));
		}
		
		newTask.setTaskStartDateTime(convertedDateTime);
		
		return new IndicatorMessagePair(true, "");
	}
	
	private static IndicatorMessagePair setTaskEndDateTime(Task newTask, String endDateTime){
		if(endDateTime == null){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		
		DateTime convertedDateTime = DateTime.parse(endDateTime);
		
		if(convertedDateTime == null){
			return new IndicatorMessagePair(false, String.format(MessageList.MESSAGE_WRONG_DATE_FORMAT, "Start"));
		}

		newTask.setTaskEndDateTime(convertedDateTime);
		
		return new IndicatorMessagePair(true, "");
	}
	
	private static IndicatorMessagePair setWeeklyDay(Task newTask, String weeklyDay){
		if(weeklyDay == null){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_EMPTY_WEEKLY_DAY);
		}
		
		newTask.setWeeklyDay(weeklyDay);
		
		return new IndicatorMessagePair(true, "");
	}
	
	private static IndicatorMessagePair setTaskStatus(Task newTask, String taskStatus){
		if(taskStatus == null || !isStringAnBoolean(taskStatus)){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_STATUS);
		}
		
		newTask.setTaskStatus(Boolean.parseBoolean(taskStatus));
		
		return new IndicatorMessagePair(true, "");
	}
	
	
	private static IndicatorMessagePair setTaskDeadLineSet(Task newTask, String taskDeadLineSet){
		if(taskDeadLineSet == null || !isStringAnBoolean(taskDeadLineSet)){
			return new IndicatorMessagePair(false, MessageList.MESSAGE_INVALID_DEADLINESETSTATUS);
		}
		
		newTask.setDeadLineStatus(Boolean.parseBoolean(taskDeadLineSet));
		
		return new IndicatorMessagePair(true, "");
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
	
	private static boolean isStringAnBoolean(String inputStr) {
		try {
			Boolean.parseBoolean(inputStr);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}
