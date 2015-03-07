
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
	
	public static final String MESSAGE_DELETE = "deleted from %s: \"%s\"";
	public static final String MESSAGE_NOFILEDELETED = "No file to be deleted";
	public static final String MESSAGE_ADDED = "Message Added.";

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

	//message for HelpCommandList class
	public static final String MESSAGE_HELP = "1) Add<Task Name> <Desc>\n 2) Remove<Task #>\n 3) Update<Task #><Desc>\n 4) Display<Task #>\n 5) Search <Task #>\n 6)Switchview <tabname>\n 7) Block <Task #><period>\n 8) Unblock <Task #><Period>";
	public static final String MESSAGE_ADD_HELP = "Add<Task Name> <Desc>";
	public static final String MESSAGE_DELETE_HELP = "Remove<Task #>";
	public static final String MESSAGE_UPDATE_HELP = "Update<Task #> <Desc>";
	public static final String MESSAGE_DISPLAY_HELP = "Display<Task #>";
	public static final String MESSAGE_SEARCH_HELP = "Search<Task #>";
	public static final String MESSAGE_SORT_HELP = "Sort";
	public static final String MESSAGE_CLEAR_HELP = "Clear";
	public static final String MESSAGE_EXIT_HELP = "Exit";
	public static final String MESSAGE_SWITCHVIEW_HELP = "Switchview<tabname> <Desc>";
	public static final String MESSAGE_BLOCK_HELP = "Block <Task #> <Desc>";
	public static final String MESSAGE_UNBLOCK_HELP = "Unblock<Task Name> <Desc>";
	public static final String MESSAGE_INVAILD = "Please enter a vaild command.";

}

