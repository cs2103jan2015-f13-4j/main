import java.util.Date;


public class Task {
	
	private int taskId;
	private String taskDescription;
	private Date taskStartDate;
	private Date taskEndDate;

	public Task(int taskId, String taskDescription, Date taskStartDate, Date taskEndDate) {
		
			taskId = taskId;
			taskDescription = taskDescription;
			taskStartDate = taskStartDate;
			taskEndDate = taskEndDate;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public String getTaskDescription() {
		return taskDescription;
	}
	
	public Date getTaskStartDate() {
		return taskStartDate;
	}
	
	public Date getTaskEndDate()	{
		return taskEndDate;
	}
	
	public void setTaskId(int newTaskId)	{
		taskId = newTaskId;
	}
	
	public void setTaskDescription(String newTaskDescription) {
		taskDescription = newTaskDescription;
	}
	
	public void setTaskStartDate(Date newTaskStartDate) {
		taskStartDate = newTaskStartDate;
	}
	
	public void setTaskEndDate(Date newTaskEndDate) {
		taskEndDate = newTaskEndDate;
	}
	
	public void displayTask() {
		System.out.println("Task ID: "+taskId+" Task Description: "+taskDescription+" Task Start Date: "+taskStartDate+" Task End Date: "+taskEndDate);
	}
}