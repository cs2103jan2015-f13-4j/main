import java.util.Date;


public class Task {
	
	private static int taskId;
	private static String taskDescription;
	private static Date taskStartDate;
	private static Date taskEndDate;

	public Task(int taskId, String taskDescription, Date taskStartDate, Date taskEndDate) {
		
		this.taskId = taskId;
		this.taskDescription = taskDescription;
		this.taskStartDate = taskStartDate;
		this.taskEndDate = taskEndDate;
	}
	
	public static int getTaskId() {
		return taskId;
	}
	
	public static String getTaskDescription() {
		return taskDescription;
	}
	
	public static Date getTaskStartDate() {
		return taskStartDate;
	}
	
	public static Date getTaskEndDate()	{
		return taskEndDate;
	}
	
	public static void setTaskId(int newTaskId)	{
		taskId = newTaskId;
	}
	
	public static void setTaskDescription(String newTaskDescription) {
		taskDescription = newTaskDescription;
	}
	
	public static void setTaskStartDate(Date newTaskStartDate) {
		taskStartDate = newTaskStartDate;
	}
	
	public static void setTaskEndDate(Date newTaskEndDate) {
		taskEndDate = newTaskEndDate;
	}
	
	public static void displayTask() {
		System.out.println("Task ID: "+taskId+" Task Description: "+taskDescription+" Task Start Date: "+taskStartDate+" Task End Date: "+taskEndDate);
	}
}