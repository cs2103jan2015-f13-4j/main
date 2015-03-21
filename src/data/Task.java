package data;

import parser.DateParser;

import org.joda.time.DateTime;

public class Task implements Comparable<Task> {
	
	private int taskId;
	private String taskDescription;
	private DateTime taskStartDateTime;
	private DateTime taskEndDateTime;
	private boolean taskStatus;
	private String weeklyDay;
	private String taskCompleted = "Completed";
	private String taskPending = "Pending";

	
	public Task(int taskId, String taskDescription, DateTime taskStartDateTime, DateTime taskEndDateTime, String weeklyDay) {
		super();
		this.taskId = taskId;
		this.taskDescription = taskDescription;
		this.taskStartDateTime = taskStartDateTime;
		this.taskEndDateTime = taskEndDateTime;
		this.weeklyDay = weeklyDay;
	}
	
	public Task(int taskId, String taskDescription, DateTime taskStartDateTime, DateTime taskEndDateTime, boolean taskStatus, String weeklyDay) {
		super();
		this.taskId = taskId;
		this.taskDescription = taskDescription;
		this.taskStartDateTime = taskStartDateTime;
		this.taskEndDateTime = taskEndDateTime;
		this.taskStatus = taskStatus;
		this.weeklyDay = weeklyDay;
	}
	
	public Task(){
		this.taskId = -1;
		this.taskDescription = "";
		this.taskStartDateTime = null;
		this.taskEndDateTime = null;
		this.taskStatus = false;
		this.weeklyDay = "";
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public DateTime getTaskStartDateTime() {
		return taskStartDateTime;
	}
	public void setTaskStartDateTime(DateTime taskStartDateTime) {
		this.taskStartDateTime = taskStartDateTime;
	}
	public DateTime getTaskEndDateTime() {
		return taskEndDateTime;
	}
	public void setTaskEndDateTime(DateTime taskEndDateTime) {
		this.taskEndDateTime = taskEndDateTime;
	}
	public boolean getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(boolean taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getWeeklyDay() {
		return weeklyDay;
	}
	public void setWeeklyDay(String weeklyDay) {
		this.weeklyDay = weeklyDay;
	}
	
	@Override
	public String toString() {
		
		String fullDetails = "";
		
		if(taskId > 0) {
			fullDetails += "\nTask ID: " + taskId + "\n";
		}
		
		if(taskDescription != null && !taskDescription.isEmpty()) {
			fullDetails += "Description: " + taskDescription + "\n";
		}
		
		if(taskStartDateTime != null) {
			fullDetails += "Start from: " + DateParser.displayDateTime(taskStartDateTime) + "\n";
		}
		
		if(taskEndDateTime != null) {
			fullDetails += "Deadline: " + DateParser.displayDateTime(taskEndDateTime) + "\n";
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
}