import java.util.Date;


public class Task {
	
	private int taskId;
	private String taskDescription;
	private Date taskStartDate;
	private Date taskEndDate;

	
	public Task(int taskId, String taskDescription, Date taskStartDate, Date taskEndDate) {
		super();
		this.taskId = taskId;
		this.taskDescription = taskDescription;
		this.taskStartDate = taskStartDate;
		this.taskEndDate = taskEndDate;
	}
	
	public Task(){
		this.taskId = -1;
		this.taskDescription = "";
		this.taskStartDate = null;
		this.taskEndDate = null;
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
	public Date getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	public Date getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	@Override
	public String toString() {
		return taskId + "|"
				+ taskDescription + "|" + taskStartDate
				+ "|" + taskEndDate;
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
}