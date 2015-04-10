//@A0111935L
package data;

/**
 * This class is to convert each task object into a format in string and will be
 * storing to the file which will be handled by the FileStorage class
 */
public class TaskParserWriteToTextFile {

	// task component separator char
	private static final String TASK_COMPONENT_SEPARATOR = "|";
	// task field and data separator char
	private static final String TASK_FIELD_DATA_SEPARATOR = "=";
	// task fields list taskid offset
	private static final int TASKID_OFFSET = 0;
	// task fields list taskdesc offset
	private static final int TASKDESC_OFFSET = 1;
	// task fields list taskstartdatetime offset
	private static final int TASKSTARTDATETIME_OFFSET = 2;
	// task fields list taskenddatetime offset
	private static final int TASKENDDATETIME_OFFSET = 3;
	// task fields list taskweeklyday offset
	private static final int TASKWEEKLYDAY_OFFSET = 4;
	// task fields list taskstatus offset
	private static final int TASKSTATUS_OFFSET = 5;
	// task fields list taskdeadlineset offset
	private static final int TASKDEADLINESET_OFFSET = 6;
	private static final int SUBSTRING_OFFSET = 1;

	/**
	 * concatTaskFieldToString method will concatenate the heading of each task
	 * field
	 * 
	 * @param oneTask
	 * @return a text line
	 */
	public static String concatTaskFieldToString(Task oneTask) {
		if (oneTask == null) {
			return null;
		}
		String textLine = new String();
		TaskFieldList.List_Task_Field[] taskFields = TaskFieldList.List_Task_Field
				.values();
		if (oneTask.getTaskId() > 0) {
			textLine += taskFields[TASKID_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += String.valueOf(oneTask.getTaskId());
			textLine += TASK_COMPONENT_SEPARATOR;
		}
		if (oneTask.getTaskDescription() != null
				&& !oneTask.getTaskDescription().isEmpty()) {
			textLine += taskFields[TASKDESC_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += oneTask.getTaskDescription();
			textLine += TASK_COMPONENT_SEPARATOR;
		}
		if (oneTask.getTaskStartDateTime() != null) {
			textLine += taskFields[TASKSTARTDATETIME_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += oneTask.getTaskStartDateTime().toString();
			textLine += TASK_COMPONENT_SEPARATOR;
		}
		if (oneTask.getTaskEndDateTime() != null) {
			textLine += taskFields[TASKENDDATETIME_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += oneTask.getTaskEndDateTime().toString();
			textLine += TASK_COMPONENT_SEPARATOR;
		}

		if (oneTask.getWeeklyDay() != null && !oneTask.getWeeklyDay().isEmpty()) {
			textLine += taskFields[TASKWEEKLYDAY_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += oneTask.getWeeklyDay();
			textLine += TASK_COMPONENT_SEPARATOR;
		}

		if (textLine.isEmpty()) {
			return null;
		}

		textLine += taskFields[TASKSTATUS_OFFSET].name();
		textLine += TASK_FIELD_DATA_SEPARATOR;
		textLine += oneTask.getTaskStatus();
		textLine += TASK_COMPONENT_SEPARATOR;

		textLine += taskFields[TASKDEADLINESET_OFFSET].name();
		textLine += TASK_FIELD_DATA_SEPARATOR;
		textLine += oneTask.getDeadLineStatus();
		textLine += TASK_COMPONENT_SEPARATOR;

		return textLine.substring(0, textLine.length() - SUBSTRING_OFFSET);
	}
}
