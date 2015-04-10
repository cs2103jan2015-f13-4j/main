//@A0111935L
package data;

import org.joda.time.DateTime;

import parser.DateTimeParser;
import utility.IndicatorMessagePair;
import utility.MessageList;

/**
 * This class allow the string to be converted into Task object. It is from the
 * text file loaded into the system
 * 
 */
public class TaskParserFromTextFile {

	// task component separator char
	private static final String TASK_COMPONENT_SEPARATOR = "|";
	// task component separator char
	private static final String TASK_COMPONENT_SEPARATOR_SPLIT = "\\|";
	// task field and data separator char
	private static final String TASK_FIELD_DATA_SEPARATOR = "=";
	// task field type at slot 0
	private static final int TASK_FIELD_SLOT = 0;
	// data in task field at slot 1
	private static final int TASK_FIELD_DATA_SLOT = 1;
	private static final String EMPTY_STRING = "";
	private static final String END_WORD = "End";
	private static final String START_WORD = "Start";
	private static final String WEEKLY_WORD = "Weekly";
	private static final int TASK_ID_BELOW = 0;

	/**
	 * generateStringFromTextFileToTask method generates a task object from a
	 * given string format stored in the textfile
	 * 
	 * @param eachTaskString
	 * @return
	 */
	public static Task generateStringFromTextFileToTask(String eachTaskString) {
		if (eachTaskString == null) {
			return null;
		}

		if (!eachTaskString.contains(TASK_COMPONENT_SEPARATOR)) {
			return null;
		}

		return processTaskString(eachTaskString);
	}

	/**
	 * processTaskString method process the Task format in text file to Task
	 * object
	 * 
	 * @param eachTaskString
	 *            the Task format in string
	 * @return Task object or null
	 */
	private static Task processTaskString(String eachTaskString) {
		String[] taskComponent = eachTaskString
				.split(TASK_COMPONENT_SEPARATOR_SPLIT);
		Task newTask = new Task();

		for (int i = 0; i < taskComponent.length; i++) {
			String[] separateFieldData = taskComponent[i]
					.split(TASK_FIELD_DATA_SEPARATOR);
			if (separateFieldData.length != 2) {
				return null;
			}
			IndicatorMessagePair indicMsg;
			TaskFieldList.List_Task_Field fieldType = TaskFieldList
					.getTaskField(separateFieldData[TASK_FIELD_SLOT]);
			switch (fieldType) {
			case TASKID:
				indicMsg = setTaskId(newTask,
						separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKDESC:
				indicMsg = setTaskDesc(newTask,
						separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKSTARTDATETIME:
				indicMsg = setTaskStartDateTime(newTask,
						separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKENDDATETIME:
				indicMsg = setTaskEndDateTime(newTask,
						separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case WEEKLYDAY:
				indicMsg = setWeeklyDay(newTask,
						separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKSTATUS:
				indicMsg = setTaskStatus(newTask,
						separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			case TASKDEADLINESET:
				indicMsg = setTaskDeadLineSet(newTask,
						separateFieldData[TASK_FIELD_DATA_SLOT]);
				break;
			default:
				return null;
			}

			if (!indicMsg.isTrue()) {
				return null;
			}
		}
		if (newTask.getTaskId() < TASK_ID_BELOW) {
			return null;
		}

		if (newTask.getDeadLineStatus() && !newTask.getWeeklyDay().isEmpty()) {
			return null;
		}

		return newTask;
	}

	/**
	 * setTaskId method sets the task id from the text file to the task id from
	 * the task object
	 * 
	 * @param newTask
	 *            the new task which will be stored into
	 * @param id
	 *            the task id to store
	 * @return the indicator message which true if success, else false with the
	 *         message
	 */
	private static IndicatorMessagePair setTaskId(Task newTask, String id) {
		if (!isStringAnInteger(id) || id == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_ERROR_CONVERT_TASKID);
		}

		newTask.setTaskId(Integer.parseInt(id));

		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * setTaskDesc method sets the task description
	 * 
	 * @param newTask
	 *            to set the description into this task object
	 * @param desc
	 *            the description to be set
	 * @return the indicator message which true if success, else false with the
	 *         message
	 */
	private static IndicatorMessagePair setTaskDesc(Task newTask, String desc) {
		if (desc == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_DESCRIPTION_EMPTY);
		}

		newTask.setTaskDescription(desc);

		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * setTaskStartDateTime method will set the task start date time into the
	 * task object
	 * 
	 * @param newTask
	 *            to set the start date time into this task object
	 * @param startDateTime
	 *            the string to be set into
	 * @return the indicator message which true if success, else false with the
	 *         message
	 */
	private static IndicatorMessagePair setTaskStartDateTime(Task newTask,
			String startDateTime) {
		if (startDateTime == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}

		DateTime convertedDateTime = DateTime.parse(startDateTime);

		if (convertedDateTime == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, START_WORD));
		}

		newTask.setTaskStartDateTime(convertedDateTime);

		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * setTaskEndDateTime method will set the task end date time into the task
	 * object
	 * 
	 * @param newTask
	 *            to set the end date time into this task object
	 * @param endDateTime
	 *            the string to be set into
	 * @return the indicator message which true if success, else false with the
	 *         message
	 */
	private static IndicatorMessagePair setTaskEndDateTime(Task newTask,
			String endDateTime) {
		if (endDateTime == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}

		DateTime convertedDateTime = DateTime.parse(endDateTime);

		if (convertedDateTime == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, END_WORD));
		}

		newTask.setTaskEndDateTime(convertedDateTime);

		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * setWeeklyDay method sets the day of the weekly day into the task object
	 * 
	 * @param newTask
	 *            to set the weekly day into this task object
	 * @param weeklyDay
	 *            the string to be stored into
	 * @return the indicator message which true if success, else false with the
	 *         message
	 */
	private static IndicatorMessagePair setWeeklyDay(Task newTask,
			String weeklyDay) {
		if (weeklyDay == null) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_EMPTY_WEEKLY_DAY);
		}

		DateTime weeklyDate = DateTimeParser.generateDate(weeklyDay);

		if (weeklyDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, WEEKLY_WORD));
		}

		newTask.setWeeklyDay(weeklyDay);

		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * setTaskStatus method set the status of the task to the task object
	 * 
	 * @param newTask
	 *            to set the status into this task object
	 * @param taskStatus
	 *            the string to be stored into
	 * @return the indicator message which true if success, else false with the
	 *         message
	 */
	private static IndicatorMessagePair setTaskStatus(Task newTask,
			String taskStatus) {
		if (taskStatus == null || !isStringAnBoolean(taskStatus)) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_INVALID_STATUS);
		}

		newTask.setTaskStatus(Boolean.parseBoolean(taskStatus));

		return new IndicatorMessagePair(true, EMPTY_STRING);
	}

	/**
	 * setTaskDeadLineSet method set the deadlineset status of the task to the
	 * task object
	 * 
	 * @param newTask
	 *            to set the deadlineset status into this task object
	 * @param taskDeadLineSet
	 *            the string to be stored into
	 * @return the indicator message which true if success, else false with the
	 *         message
	 */
	private static IndicatorMessagePair setTaskDeadLineSet(Task newTask,
			String taskDeadLineSet) {
		if (taskDeadLineSet == null || !isStringAnBoolean(taskDeadLineSet)) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_INVALID_DEADLINESETSTATUS);
		}

		newTask.setDeadLineStatus(Boolean.parseBoolean(taskDeadLineSet));

		return new IndicatorMessagePair(true, EMPTY_STRING);
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

	/**
	 * isStringAnBoolean method will checks if the string can be converted into
	 * a boolean
	 * 
	 * @param inputStr
	 *            the input string
	 * @return true if it is a boolean, else false
	 */
	private static boolean isStringAnBoolean(String inputStr) {
		try {
			Boolean.parseBoolean(inputStr);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}
