
/**
 * This class generate the list of task field that this system will have
 * @author Tian
 *
 */
public class TaskFieldList {

	public enum List_Task_Field{
		TASKID, TASKDESC, TASKSTARTDATETIME, TASKENDDATETIME, WEEKLYDAY, TASKSTATUS, INVALID
	}
	
	/**
	 * This method will convert a string to a particular Enum value
	 * @param taskFieldString
	 * @return List_Task_Field
	 */
	public static List_Task_Field getTaskField(String taskFieldString){
		if (taskFieldString == null) {
			return List_Task_Field.INVALID;
		}

		switch(taskFieldString.toUpperCase()){
		case "TASKID":
			return List_Task_Field.TASKID;
		case "TASKDESC":
			return List_Task_Field.TASKDESC;
		case "TASKSTARTDATETIME":
			return List_Task_Field.TASKSTARTDATETIME;
		case "TASKENDDATETIME":
			return List_Task_Field.TASKENDDATETIME;
		case "WEEKLYDAY":
			return List_Task_Field.WEEKLYDAY;
		case "TASKSTATUS":
			return List_Task_Field.TASKSTATUS;
		default:
			return List_Task_Field.INVALID;
		}
	}
}
