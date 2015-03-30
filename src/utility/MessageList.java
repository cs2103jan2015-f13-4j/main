package utility;
import javax.swing.JOptionPane;
public class MessageList {
	
	/**
	 * This message are for HelpCommandList class
	 */
	public static final String MESSAGE_HELP = "************************************************************************************\n\n		 	          HELP LIST\n\n1) Add <Desc>\n\n 2) Delete <Task#>\n\n 3)Update\n 3i) Update <Task #> TaskDesc <description>\n3ii) Update <Task #> By <date/day>\n 3iii) Update <Task #> Completed\n3iv) Update <Task #> Pending\n3v) Update<Task #> TaskEnd<date>\n3vi) Update<Task #>TaskStart<date>\n3vii) Update<Task#> Every <Friday>\n3viii) Update<Task #> By date or weekly day\n\n 4) Delete <#>\n\n 5) Display \n 5i) Display Today \n5ii) Display Pending\n5iii) Display Schedule\n5v) Display Completed\n\n 6) Search\n 6i) Search TaskDesc<Task #>\n6ii) Search TaskId<Task #>\n6iii) Search Deadline <Date>\n\n 7) Block or Unblock\n 7i) Block <date>\n 7ii) Unblock <date>\n 7iii) Block from <date> to <date>\n 7v) Unblock from<date> to <date>\n\n************************************************************************************";
	public static final String MESSAGE_ADD_HELP = "Add <Desc>";
	public static final String MESSAGE_DELETE_HELP = "Delete #";
	public static final String MESSAGE_UPDATE_HELP = "1. Update <Task #> TaskDesc <description>\n2. Update <Task #> By <date/day>\n3. Update <Task #> Completed\n4. Update <Task #> Pending\n5. Update<Task #> TaskEnd<date>\n6. Update<Task #>TaskStart<date>\n7. Update<Task#> Every <Friday>\n8. Update<Task #> By date or weekly day";
	public static final String MESSAGE_DISPLAY_HELP = "1. Display Today\n2.Display Pending\n3.Display Schedule\n4.Display Completed";
	public static final String MESSAGE_SEARCH_HELP = "1. Search TaskDesc<Task #>\n2. Search TaskId<Task #>\n3. Search Deadline <Date>";
	public static final String MESSAGE_SORT_HELP = "Sort";
	public static final String MESSAGE_EXIT_HELP = "Exit";
	public static final String MESSAGE_BLOCK_HELP = "Block <Task #> <Date>";
	public static final String MESSAGE_UNBLOCK_HELP = "Unblock <Date>";
	
	

	public static final String MESSAGE_FILENAME_INVALID_UNSPECIFIED = "Filename not specified.";
	public static final String MESSAGE_FILENAME_INVALID_FORMAT = "Filename is in wrong format.";
	public static final String MESSAGE_TEXTFILE_INFO_CORRUPTED = "Contents in the %1$s has been corrupted.";
	public static final String MESSAGE_ERROR_ON_WRITING_TO_FILE = "Error on saving to file.";
	public static final String MESSAGE_INVALID_STATUS = "Invalid status for a task.";
	
	public static final String MESSAGE_INVALID_ARGUMENT = "Invalid argument for %1$s command.";
	public static final String MESSAGE_VALID_ARGUMENT = "Valid argument for %1$s command.";
	public static final String MESSAGE_INVALID_CONVERSION_INTEGER = "Invalid Type to Integer for %1$s command";
	public static final String MESSAGE_NO_SUCH_TASK = "Task does not exist.";
	public static final String MESSAGE_NULL = "Command is empty. Please enter again";
	public static final String MESSAGE_NO_TASK_IN_LIST = "There is no task in the list.";
	public static final String MESSAGE_NO_TASK_IN_DISPLAY_LIST = "There is no task in the display list.";
	public static final String MESSAGE_LIST_IS_NOT_EMPTY = "The list is not empty.";
	public static final String MESSAGE_DESCRIPTION_EMPTY = "Cannot let task description be empty";
	public static final String MESSAGE_NO_DATE_GIVEN = "No date given.";
	public static final String MESSAGE_WRONG_DATE_FORMAT = "Wrong date format for %1$s date";
	public static final String MESSAGE_INVALID_COMMAND = "This command is invalid";
	public static final String MESSAGE_INCORRECT_DATE_FORMAT ="Date Format is incorrect";
	public static final String MESSAGE_INCORRECT_TIME_FORMAT ="Time Format is incorrect";
	public static final String MESSAGE_DATE_IS_BEFORE_TODAY = "Date provided is before Today's date.";
	
	/**
	 * These message are for update class
	 */
	public static final String MESSAGE_UPDATE_SUCCESS = "Update successful.";
	public static final String MESSAGE_UPDATE_STATUS_EXTRA_FIELD = "Please remove any information after the word complete/pending.";
	
	public static final String MESSAGE_DELETE_SUCCESS = "deleted from %s: \"%s\"";
	public static final String MESSAGE_NO_FILE_DELETED = "There is no file to be deleted";
	public static final String MESSAGE_INVALID_DELETE = "Invalid delete arguments";
	public static final String MESSAGE_ADDED = "Task Added.";
	public static final String MESSAGE_ERROR_CONVERT_TASKID = "Error in converting the taskid to integer";
	public static final String MESSAGE_EMPTY_WEEKLY_DAY = "Weekly day is empty";
	
	/**
	 * This message are for CacheCommandsHandler class
	 */
	public static final String MESSAGE_NO_PREVIOUS_COMMAND = "No previous command entered";
	public static final String MESSAGE_UNDO_SUCCESS = "Undo operation done";
	
	/**
	 * This message are for HintHandler class
	 */
	public static final String MESSAGE_HINT_INVALID = "Invalid command";
	
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
	

	public static final String MESSAGE_UNDO_HELP = "undo";
	public static final String MESSAGE_REDO_HELP = "Redo";
	public static final String MESSAGE_INVAILD = "Please enter a vaild command.";
	public static final String MESSAGE_INVAILD_FOR_SYMBOL = "Please enter a vaild command. Remove characters such as '=' and '|'";

	
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
	
	/**
	 * These message are for BlockDate class 
	 */
	public static final String MESSAGE_BLOCK_NO_SPECIFICATION = "Please Enter a Valid Date";
	public static final String MESSAGE_BLOCKED = "Blocked Successful";
	public static final String MESSAGE_UNBLOCKED = "Unblocked Successful";
	public static final String MESSAGE_BLOCK_SPECIFICATION = "Please Enter a Valid Start Date and End Date";
	public static final String MESSAGE_BLOCK_INCORRECT_KEYWORD = "Please Enter a Valid Command";
}

	
