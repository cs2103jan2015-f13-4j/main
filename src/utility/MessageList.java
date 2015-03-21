import javax.swing.JOptionPane;
public class MessageList {

	public static final String MESSAGE_FILENAME_INVALID_UNSPECIFIED = "Filename not specified.";
	public static final String MESSAGE_FILENAME_INVALID_FORMAT = "Filename is in wrong format.";
	public static final String MESSAGE_TEXTFILE_INFO_CORRUPTED = "Contents in the %1$s has been corrupted.";
	public static final String MESSAGE_ERROR_ON_WRITING_TO_FILE = "Error on saving to file.";
	public static final String MESSAGE_INVALID_STATUS = "Invalid status for a task.";
	
	public static final String MESSAGE_INVALID_ARGUMENT = "Invalid argument for %1$s command."; 
	public static final String MESSAGE_INVALID_CONVERSION_INTEGER = "Invalid Type to Integer for %1$s command";
	public static final String MESSAGE_NO_SUCH_TASK = "Task does not exist.";
	public static final String MESSAGE_NULL = "Command is empty. Please enter again";
	public static final String MESSAGE_NO_TASK_IN_LIST = "There is no task in the list.";
	public static final String MESSAGE_NO_TASK_IN_DISPLAY_LIST = "There is no task in the display list.";
	public static final String MESSAGE_DESCRIPTION_EMPTY = "Cannot let task description be empty";
	public static final String MESSAGE_NO_DATE_GIVEN = "No date given.";
	public static final String MESSAGE_WRONG_DATE_FORMAT = "Wrong date format for %1$s date";
	public static final String MESSAGE_INVALID_COMMAND = "This command is invalid";
	public static final String MESSAGE_INCORRECT_DATE_FORMAT ="Date Format is incorrect";
	
	/**
	 * These message are for update class
	 */
	public static final String MESSAGE_UPDATE_SUCCESS = "Update successful.";
	public static final String MESSAGE_UPDATE_STATUS_EXTRA_FIELD = "Please remove any information after the word complete/pending.";
	
	public static final String MESSAGE_DELETE_SUCCESS = "deleted from %s: \"%s\"";
	public static final String MESSAGE_NO_FILE_DELETED = "There is no file to be deleted";
	public static final String MESSAGE_ADDED = "Task Added.";
	public static final String MESSAGE_INVALID_DELETE = "Invalid delete arguments";

	public static final String MESSAGE_ERROR_CONVERT_TASKID = "Error in converting the taskid to integer";
	public static final String MESSAGE_EMPTY_WEEKLY_DAY = "Weekly day is empty";
	
	/**
	 * This message are for CacheCommandsHandler class
	 */
	public static final String MESSAGE_NO_PREVIOUS_COMMAND = "No previous command entered";
	public static final String MESSAGE_UNDO_SUCCESS = "Undo operation done";
	
	/**
	 * This method will print out the error message before exiting the system
	 * 
	 * @param message
	 *            the message from various methods
	 */
	public static void printErrorMessageAndExit(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);;
		System.exit(0);
	}
	/**
	 * This message are for HelpCommandList class
	 */
	public static final String MESSAGE_HELP = "**************************************************\n1) Add <Desc>\n 2) Remove <Task #>\n 3) 1. Update <Task #> TaskDes <description> 2.Update <Task #> By <date/day>\n 4) Display <Task #>\n 5) Search <Task #>\n 6)Switchview <tabname>\n 7) Block <Task #><period>\n 8) Unblock <Task #><Period>\n**************************************************";
	public static final String MESSAGE_ADD_HELP = "Add <Desc>";
	public static final String MESSAGE_DELETE_HELP = "Delete <Task #>";
	public static final String MESSAGE_UPDATE_HELP = "1. Update <Task #> TaskDesc <description>\n2. Update <Task #> By <date/day>";
	public static final String MESSAGE_DISPLAY_HELP = "Display";
	public static final String MESSAGE_SEARCH_HELP = "Search <Task #>";
	public static final String MESSAGE_SORT_HELP = "Sort";
	public static final String MESSAGE_CLEAR_HELP = "Clear";
	public static final String MESSAGE_EXIT_HELP = "Exit";
	public static final String MESSAGE_SWITCHVIEW_HELP = "Switchview <tabname> <Desc>";
	public static final String MESSAGE_BLOCK_HELP = "Block <Task #> <Date>";
	public static final String MESSAGE_UNBLOCK_HELP = "Unblock <Date>";
	public static final String MESSAGE_INVAILD = "Please enter a vaild command.";
	
	/**
	 * These message are for the Menu
	 */
	public static final String MESSAGE_MENU_NO_COMMAND ="There is no command.";
	public static final String MESSAGE_MENU_COMMAND_UNRECOGNISED = "Command Unrecognised!";

	/**
	 * These message are for search class
	 */
	public static final String MESSAGE_NO_MATCH_FOUND = "No Match Found";
	public static final String MESSAGE_INVAILD_SEARCH = "This is a invaild search.";
	public static final String MESSAGE_INVAILD_SEARCH_CRITERIA = "This is a invaild search criteria.";
}

