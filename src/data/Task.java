//@author A0112978W
package data;

import parser.DateTimeParser;
import utility.KeywordType;

import org.joda.time.DateTime;

/**
 * This class will create the task object and provide the get and set methods.
 * It has toString() to display the details of the tasks.  
 */
public class Task implements Comparable<Task> {
	
	// Declaration of attributes
	private int taskId;
	private String taskDescription;
	private DateTime taskStartDateTime;
	private DateTime taskEndDateTime;
	private boolean taskStatus;
	private String weeklyDay;
	private String taskCompleted = "Completed";
	private String taskPending = "Pending";
	private boolean deadlineSet;

	// Overriding Constructor
	public Task(int taskId, String taskDescription, DateTime taskStartDateTime, DateTime taskEndDateTime, String weeklyDay) {
		super();
		this.taskId = taskId;
		this.taskDescription = taskDescription;
		this.taskStartDateTime = taskStartDateTime;
		this.taskEndDateTime = taskEndDateTime;
		this.weeklyDay = weeklyDay;
	}
	
	// Overriding Constructor
	public Task(int taskId, String taskDescription, DateTime taskStartDateTime, DateTime taskEndDateTime, boolean taskStatus, String weeklyDay, boolean deadlineSet) {
		super();
		this.taskId = taskId;
		this.taskDescription = taskDescription;
		this.taskStartDateTime = taskStartDateTime;
		this.taskEndDateTime = taskEndDateTime;
		this.taskStatus = taskStatus;
		this.weeklyDay = weeklyDay;
		this.deadlineSet = deadlineSet;
	}
	
	// Constructor
	public Task(){
		this.taskId = -1;
		this.taskDescription = "";
		this.taskStartDateTime = null;
		this.taskEndDateTime = null;
		this.taskStatus = false;
		this.weeklyDay = "";
		this.deadlineSet = false;
	}
	
	/**
	 * This method will get the Task ID
	 * 
	 * @return The Task ID.
	 */
	public int getTaskId() {
		return taskId;
	}
	
	/**
	 * This method sets a new Task ID
	 * 
	 * @param taskId
	 * 			This is the new Task ID.
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	/**
	 * This method will get the Task Description
	 * 
	 * @return The Task Description.
	 */
	public String getTaskDescription() {
		return taskDescription;
	}
	
	/**
	 * This method sets a new Task Description
	 * 
	 * @param taskDescription
	 * 			This is the new Task Description.
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	/**
	 * This method will get the Task Start Time
	 * 
	 * @return The Task Start Time.
	 */
	public DateTime getTaskStartDateTime() {
		return taskStartDateTime;
	}
	
	/**
	 * This method sets a new Task Start Time
	 * 
	 * @param taskStartDateTime
	 * 			This is the new Task Start Time.
	 */
	public void setTaskStartDateTime(DateTime taskStartDateTime) {
		this.taskStartDateTime = taskStartDateTime;
	}
	
	/**
	 * This method will get the Task End Date
	 * 
	 * @return The Task End Date.
	 */
	public DateTime getTaskEndDateTime() {
		return taskEndDateTime;
	}
	
	/**
	 * This method sets a new Task End Date
	 * 
	 * @param taskEndDateTime
	 * 			This is the new Task End Date.
	 */
	public void setTaskEndDateTime(DateTime taskEndDateTime) {
		this.taskEndDateTime = taskEndDateTime;
	}
	
	/**
	 * This method will get the Task Status
	 * 
	 * @return The Task Status.
	 */
	public boolean getTaskStatus() {
		return taskStatus;
	}
	
	/**
	 * This method sets a new Task Status
	 * 
	 * @param taskStatus
	 * 			This is the new Task Status.
	 */
	public void setTaskStatus(boolean taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	/**
	 * This method will get the Weekly Day
	 * 
	 * @return The Weekly Day.
	 */
	public String getWeeklyDay() {
		return weeklyDay;
	}
	
	/**
	 * This method sets a new Weekly Day
	 * 
	 * @param weeklyDay
	 * 			This is the new Weekly Day.
	 */
	public void setWeeklyDay(String weeklyDay) {
		this.weeklyDay = weeklyDay;
	}
	
	/**
	 * This method will get the Deadline Status
	 * 
	 * @return The Deadline Status.
	 */
	public boolean getDeadLineStatus() {
		return deadlineSet;
	}
	
	/**
	 * This method sets a new Deadline Status
	 * 
	 * @param deadlineSet
	 * 			This is the new Deadline Status.
	 */
	public void setDeadLineStatus(boolean deadlineSet) {
		this.deadlineSet = deadlineSet;
	}
	
	@Override
	/**
	 * This method will display the Task's information
	 */
	public String toString() {
		
		String fullDetails = "";
		
		if(taskId > 0) {
			fullDetails += "\nTask ID: " + taskId + "\n";
		}
		
		if(taskDescription != null && !taskDescription.isEmpty()) {
			fullDetails += "Description: " + taskDescription + "\n";
		}
		
		if(taskStartDateTime != null) {
			fullDetails += "Start Time: " + DateTimeParser.displayTime(taskStartDateTime) + "\n";
		}
		
		if(taskEndDateTime != null) {
			fullDetails += "End Time: " + DateTimeParser.displayTime(taskEndDateTime) + "\n";
			if(deadlineSet) {
				fullDetails += "Deadline: " + DateTimeParser.displayDate(taskEndDateTime) + "\n";
			} else {
				fullDetails += "Deadline: No specified date.\n";
			}
		}
		
		if(weeklyDay != null && !weeklyDay.isEmpty()) {
			fullDetails += "Every: " + weeklyDay + "\n";
		}
		
		if(taskStatus) {
			fullDetails += "Status: " + taskCompleted;
		} else {
			fullDetails += "Status: " + taskPending;
		}

		return fullDetails;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + taskId;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (taskId != other.taskId)
			return false;
		return true;
	}

	@Override
	public int compareTo(Task arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public KeywordType getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}