
public class MessageList {

	public static final String MESSAGE_INVALID_ARGUMENT = "Invalid argument for %1$s command."; 
	public static final String MESSAGE_INVALID_CONVERSION_INTEGER = "Invalid Type to Integer for %1$s command";
	public static final String MESSAGE_NO_SUCH_TASK = "Task does not exist.";
	public static final String MESSAGE_NULL = "Command is empty. Please enter again";
	public static final String MESSAGE_NO_TASK_IN_LIST = "There is no task in the list.";
	public static final String MESSAGE_DESCRIPTION_EMPTY = "Cannot let task description be empty";
	public static final String MESSAGE_NO_DATE_GIVEN = "No date given.";
	public static final String MESSAGE_WRONG_DATE_FORMAT = "Wrong date format for %1$s date";
	
	public static final String MESSAGE_UPDATE_SUCCESS = "Update successful.";
	/**
	 * This method will print out the error message before exiting the system
	 * 
	 * @param message
	 *            the message from various methods
	 */
	public static void printErrorMessageAndExit(String message) {
		System.out.println(message);
		System.exit(0);
	}
	
	public static final String MESSAGE_DELETE = "deleted from %s: \"%s\"";
	public static final String MESSAGE_NOFILEDELETED = "No file to be deleted";
	public static final String MESSAGE_ADDED = "Message Added.";

}
