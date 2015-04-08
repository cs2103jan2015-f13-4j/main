package utility;

import javax.swing.JOptionPane;

public class MessageList {

	/**
	 * These message are for HelpCommandList class
	 */
	public static final String MESSAGE_HELP = "************************************************************************************\n\n1) Add\n\n- Add <Desc>\n- Add <Description> from <Date> to <Date>"
			+ "\n-Add<Description> every <Date>\n\n 2) Delete\n\n- Delete<Task#>\n\n "
			+ "3) Update\n\n- Update <Task #> TaskDesc <description>\n- Update <Task #> By <date/day>\n- Update <Task #> Complete\n- Update <Task #> Incomplete\n- Update<Task #> TaskEnd<date>\n- Update<Task #>TaskStart<date>\n- Update<Task#> Every <Friday>\n- Update<Task #> By date or weekly day\n\n"
			+ "4) Delete\n\n- Delete <#>\n\n"
			+ "5) Display \n\n- Display Today [tdy] \n- Display Tomorrow [tmr]\n- Display monday [mon]\n- Display tuesday [tue]\n- Display wednesday [wed]\n- Display thursday [thu]\n- Display friday [fri]\n- Display saturday [sat]\n- Display sunday [sun]\n- Display Pending[pend]\n- Display Schedule\n- Display Complete[comp]\n\n "
			+ "6) Search\n\n- Search by Task ID \n Search 1 <Task #> \n Search by Task Description\n- Search 2<Task Description>\n- Search 3 <Date>\n\n "
			+ "7) Block\n\n- Block <date>\n-Block from <Date> to <Date>\n\n "
			+ "8) Unblock\n\n- Unblock <date>\n- Unblock from <date> to <Date>\n\n "
			+ "9) Sort\n\n -Sort Description\n\n Sort Deadline\n\n Sort Completed\n\n Sort StartDate\n\n Sort Pending "
			+ "10) Undo\n\n -Undo\n\n "
			+ "11) Redo\n\n************************************************************************************";
	
	public static final String MESSAGE_ADD_HELP = "- Add <Description>\n\n- Add <Description> by <Date>\n\n- Add <Description> on <Date>\n\n- Add <Description> from <Time> to <Time> by <Date>\n\n- Add <Description> every <Date>";
	public static final String MESSAGE_DELETE_HELP = "- Delete #\n\n";
	public static final String MESSAGE_UNDO_HELP = "- Undo\n\n";
	public static final String MESSAGE_UNBLOCK_HELP = "- Unblock <Date>\n\n- Unblock from <Date> to <Date>\n\n";
	public static final String MESSAGE_UPDATE_HELP = "- Update <Task #> TaskDesc <description>\n\n- Update <Task #> By <date/day>\n\n- Update <Task #> Completed\n\n- Update <Task #> Pending\n\n- Update<Task #> From <Time>\n\n- Update<Task #> To <Time>\n\n- Update<Task#> Every <Friday>\n\n- Update<Task #> By date or weekly day\n\n";
	public static final String MESSAGE_DISPLAY_HELP = "- Display All\n\n- Display Today [tdy]\n\n- Display Tomorrow [tmr]\n\n"
			+ "- Display Yesterday [ytd]\n\n- Display Thisweek [thiswk]\n\n"
			+ "- Display Lastweek [lastwk]\n\n- Display Nextweek [nextweek]\n\n"
			+ "- Display Monday [mon]\n\n- Display Tuesday [tue]\n\n- Display Wednesday [wed]\n\n"
			+ "- Display Thursday [thu]\n\n- Display Friday [fri]\n\n- Display Saturday [sat]\n\n"
			+ "- Display Sunday [sun]\n\n- Display Pending [pend]\n\n- Display Completed [comp]";
	
	public static final String MESSAGE_SORT_HELP = "- Sort Description [desc]\n\n- Sort Deadline\n\n- Sort StartDate\n\n- Sort Completed [comp]\n\n- Sort Pending [pend]";
	public static final String MESSAGE_SEARCH_HELP = "- Search 1 <Task #>\n  Search by Task ID\n\n- Search 2 <Task Description>\n  Search by Description\n\n- Search 3 <Date>\n  Search by Date\n";
	public static final String MESSAGE_EXIT_HELP = "- Exit\n\n";
	public static final String MESSAGE_BLOCK_HELP = "- Block <Date>\n\n- Block from <Date> to <Date>\n\n";
	public static final String MESSAGE_REDO_HELP = "- Redo\n\n";

	/**
	 * These message are for File Storage
	 */
	public static final String MESSAGE_FILENAME_INVALID_UNSPECIFIED = "Filename not specified.";
	public static final String MESSAGE_FILENAME_INVALID_FORMAT = "Filename is in wrong format.";
	public static final String MESSAGE_TEXTFILE_INFO_CORRUPTED = "Contents in the %1$s has been corrupted.";
	public static final String MESSAGE_ERROR_ON_WRITING_TO_FILE = "Error on saving to file.";
	public static final String MESSAGE_INVALID_STATUS = "Invalid status for a task.";

	/**
	 * These message are the Commonly Used Message
	 */

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
	public static final String MESSAGE_NO_TIME_GIVEN = "No time given.";
	public static final String MESSAGE_WRONG_DATE_FORMAT = "Wrong date format for %1$s date";
	public static final String MESSAGE_INVALID_COMMAND = "This command is invalid";
	public static final String MESSAGE_INCORRECT_DATE_FORMAT = "Date Format is incorrect";
	public static final String MESSAGE_INCORRECT_TIME_FORMAT = "Time Format is incorrect";
	public static final String MESSAGE_DATE_IS_BEFORE_TODAY = "Date provided is before Today's date.";
	public static final String MESSAGE_ERROR_CONVERT_TASKID = "Error in converting the taskid to integer";
	public static final String MESSAGE_EMPTY_WEEKLY_DAY = "Weekly day is empty";
	public static final String MESSAGE_INVAILD = "Please enter a vaild command.";
	public static final String MESSAGE_INVAILD_FOR_SYMBOL = "Please enter a vaild command. Remove characters such as '=' and '|'";
	public static final String MESSAGE_NO_WEEKLY_DEADLINE = "No deadline and weekly task should be happening at the same time.";
	public static final String MESSAGE_TIME_WRONG_FLOW = "Start Time and End Time conflicts.";
	public static final String MESSAGE_CONFLICT_WITH_BLOCKED_DATE = "This date conflicted in your blocked out date list";
	
	/**
	 * These message are for UpdateHandler class
	 */
	public static final String MESSAGE_UPDATE_SUCCESS = "%1$s\nUpdated";
	public static final String MESSAGE_UPDATE_STATUS_EXTRA_FIELD = "Please remove any information after the word complete/pending.";

	/**
	 * These message are for the AddHandler Class
	 */
	public static final String MESSAGE_ADDED = "New Task %1$s\nAdded";

	/**
	 * These message are for DeleteHandler Class
	 */
	public static final String MESSAGE_DELETE_SUCCESS = "deleted from %s: \"%s\"";
	public static final String MESSAGE_NO_FILE_DELETED = "There is no file to be deleted";
	public static final String MESSAGE_INVALID_DELETE = "Invalid delete arguments";

	/**
	 * This message are for HintHandler class
	 */
	public static final String MESSAGE_HINT_INVALID = "Please key in a command";

	/**
	 * These message are for the Menu
	 */
	public static final String MESSAGE_MENU_NO_COMMAND = "There is no command.";
	public static final String MESSAGE_MENU_COMMAND_UNRECOGNISED = "Command Unrecognised!";

	/**
	 * These message are for SearchHandler class
	 */
	public static final String MESSAGE_NO_MATCH_FOUND = "Task %1$s\nnot found.";
	public static final String MESSAGE_INVAILD_SEARCH = "This is a invaild search.";
	public static final String MESSAGE_INVAILD_SEARCH_CRITERIA = "This is a invaild search criteria.";

	/**
	 * These message are for BlockDate class
	 */
	public static final String MESSAGE_BLOCK_NO_SPECIFICATION = "Please Enter a Valid Date";
	public static final String MESSAGE_BLOCKED = "\"%1$s\"\nBlocked Successfully";
	public static final String MESSAGE_UNBLOCKED = "\"%1$s\"\nUnblocked Successfully";
	public static final String MESSAGE_BLOCKED_RANGE = "Date from \"%1$s\" to \"%2$s\"\nBlocked Successfully";
	public static final String MESSAGE_UNBLOCKED_RANGE = "Date from \"%1$s\" to \"%2$s\"\nBlocked Successfully";
	public static final String MESSAGE_BLOCK_SPECIFICATION = "Please Enter a Valid Start Date and End Date";
	public static final String MESSAGE_BLOCK_INCORRECT_KEYWORD = "Please Enter a Valid Command";
	public static final String MESSAGE_BLOCK_INCORRECT_START_EARLIER_THAN_END = "Start Date should not be later than End Date";
	public static final String MESSAGE_BLOCK_DATE_DO_NOT_EXIST = "\"%1$s\" is not inside the block list";
	public static final String MESSAGE_BLOCK_DATE_ALREADY_EXIST = "\"%1$s\" already exist";
	public static final String MESSAGE_BLOCK_WRONG_DATE_FORMAT_END ="Wrong date format for End date";
	public static final String MESSAGE_BLOCK_WRONG_DATE_FORMAT_START ="Wrong date format for Start date";
	public static final String MESSAGE_BLOCK_INVALID_BLOCK_UNBLOCK_COMMAND = "Invalid argument for Block/Unblock command.";
	

	/**
	 * This message are for CacheCommandsHandler class
	 */
	public static final String MESSAGE_NO_PREVIOUS_COMMAND = "No previous command entered";
	public static final String MESSAGE_ONLY_ONE_ITEM = "Last command in the list. Could not perform redo";
	public static final String MESSAGE_ERROR = "Unable to proceed";
	public static final String MESSAGE_UNDO_SUCCESS = "Undo operation done";
	public static final String MESSAGE_REDO_SUCCESS = "Redo operation done";
	

	/**
	 * This method will print out the error message before exiting the system
	 * 
	 * @param message
	 *            the message from various methods
	 */
	public static void printErrorMessageAndExit(String message) {
		JOptionPane.showMessageDialog(null, message, "Error",
				JOptionPane.ERROR_MESSAGE);
		;
		System.exit(0);
	}
}
