package utility;

import javax.swing.JOptionPane;

public class MessageList {

	// @author A0112978W
	/**
	 * These message are the Commonly Used Message
	 */
	public static final String MESSAGE_NO_SUCH_TASK = "Task does not exist.";
	public static final String MESSAGE_NULL = "Command is empty. Please enter again";
	public static final String MESSAGE_ERROR_CONVERT_TASKID = "Error in converting the taskid to integer";
	public static final String MESSAGE_LIST_IS_NOT_EMPTY = "The list is not empty.";

	/**
	 * These message are for valid Message
	 */
	public static final String MESSAGE_INVALID = "Please enter a valid command.";
	public static final String MESSAGE_VALID_ARGUMENT = "Valid argument for %1$s command.";

	/**
	 * These message are for Invalid Message
	 */
	public static final String MESSAGE_INVALID_ARGUMENT = "Invalid argument for %1$s command.";
	public static final String MESSAGE_INVALID_CONVERSION_INTEGER = "Invalid type to integer for %1$s command";
	public static final String MESSAGE_INVALID_COMMAND = "This command is invalid";
	public static final String MESSAGE_INVALID_FOR_SYMBOL = "Please enter a valid command. Remove characters such as '=' and '|'";

	/**
	 * These message are for DateTime
	 */
	public static final String MESSAGE_NO_WEEKLY_DEADLINE = "Only one date can be added for a single task.";
	public static final String MESSAGE_TIME_WRONG_FLOW = "Start Time and End Time conflicts.";
	public static final String MESSAGE_INCORRECT_DATE_FORMAT = "Date format is incorrect";
	public static final String MESSAGE_INCORRECT_TIME_FORMAT = "Time format is incorrect";
	public static final String MESSAGE_DATE_IS_BEFORE_TODAY = "Date provided is before Today's date.";
	public static final String MESSAGE_NO_DATE_GIVEN = "No date given.";
	public static final String MESSAGE_NO_TIME_GIVEN = "No time given.";
	public static final String MESSAGE_WRONG_DATE_FORMAT = "Wrong date format for %1$s date";

	/**
	 * These message are for Display
	 * 
	 */
	public static final String MESSAGE_NO_TASK_IN_LIST = "There is no task in the list.";
	public static final String MESSAGE_NO_TASK_IN_DISPLAY_LIST = "There is no task in the display list.";

	// @author A0112502A
	/**
	 * These message are for DeleteHandler Class
	 */
	public static final String MESSAGE_NO_FILE_DELETED = "Task not found";
	public static final String MESSAGE_INVALID_DELETE = "Invalid ID entered, please try again";
	public static final String MESSAGE_EMPTY_WEEKLY_DAY = "Weekly day is empty";

	/**
	 * This message are for CacheCommandsHandler class
	 */
	public static final String MESSAGE_NO_PREVIOUS_COMMAND = "No previous command entered";
	public static final String MESSAGE_LAST_COMMAND = "Last command in the list. Could not perform redo";
	public static final String MESSAGE_ERROR = "Unable to proceed";
	public static final String MESSAGE_UNDO_SUCCESS = "Undo operation done";
	public static final String MESSAGE_REDO_SUCCESS = "Redo operation done";

	/**
	 * This message are for HintHandler class
	 */
	public static final String MESSAGE_HINT_INVALID = "Please key in a command";

	/**
	 * These message are for the LogicController
	 */
	public static final String MESSAGE_LOGICCONTROLLER_NO_COMMAND = "There is no command.";
	public static final String MESSAGE_LOGICCONTROLLER_COMMAND_UNRECOGNISED = "Command unrecognised, prevent using \"=\",\"|\"";

	// @author A0112501E
	/**
	 * These message are for HelpCommandList class
	 */
	public static final String MESSAGE_HELP = "************************************************************************************"

			// Add
			+ "\n\n1) Add\n\n"
			+ "- Add <Description>\n"
			+ "- Add <Description> from <Date> to <Date>\n"
			+ "- Add <Description> every <Date/Day>\n"
			+ "- Add <Description> from <Time> to <time>\n\n"

			// Delete
			+ "2) Delete\n\n- Delete <Task #>\n\n"

			// Update
			+ "3) Update\n\n- Update <Task #> TaskDesc <Description>"
			+ "\n- Update <Task #> by <Date/Day>"
			+ "\n- Update <Task #> complete"
			+ "\n- Update <Task #> incomplete"
			+ "\n- Update <Task #> from <Time>"
			+ "\n- Update <Task #> to <Time>"
			+ "\n- Update <Task #> on <Date/Day>"
			+ "\n- Update <Task #> from <Time> to <Time>"
			+ "\n- Update <Task #> every <Day>"
			+ "\n- Update <Task #> by Date/Day\n\n"

			// Display
			+ "4) Display \n\n- Display All"
			+ "\n- Display Today [tdy] "
			+ "\n- Display Tomorrow [tmr]"
			+ "\n- Display Yesterday [ytd]"
			+ "\n- Display Thisweek [thiswk]"
			+ "\n- Display Lastweek [lastwk]"
			+ "\n- Display Nextweek [nextwk]"
			+ "\n- Display Pending [pend]"
			+ "\n- Display Completed [comp]"
			+ "\n- Display Monday [mon]"
			+ "\n- Display Tuesday [tue]"
			+ "\n- Display Wednesday [wed]"
			+ "\n- Display Thursday [thu]"
			+ "\n- Display Friday [fri]"
			+ "\n- Display Saturday [sat]"
			+ "\n- Display Sunday [sun]"

			// Search
			+ "\n\n5) Search\n\n"
			+ "- Search by Task ID\n Search 1 <Task #> "
			+ "\n- Search by Task Description\n Search 2 <Task Description>"
			+ "\n- Search by Date\n Search 3 <Date>\n\n "

			// Block
			+ "6) Block\n\n- Block <Date>\n-Block from <Date> to <Date>\n\n "

			// Unblock
			+ "7) Unblock\n\n- Unblock <Date>\n- Unblock from <Date> to <Date>\n\n "

			// Sort
			+ "8) Sort\n\n -Sort Description [desc]\n -Sort Deadline\n -Sort StartTime\n -Sort Completed [comp]\n -Sort Pending [pend]"

			// Undo
			+ "\n\n9) Undo\n\n -Undo\n\n"

			// Redo
			+ "10) Redo\n\n -Redo\n\n************************************************************************************";

	// Add Help
	public static final String MESSAGE_ADD_HELP = "Date Formats [ 25-12-2015   |   25/12/2015   |   2015-12-25   |   2015/12/25 ]\n"
			+ "\nTime Formats [ 6pm   |   6 pm   |   6.30pm   |   6.30 pm ]"
			+ "\n\n- Add <Description>\n\n- Add <Description> by <Date>"
			+ "\n\n- Add <Description> on <Date>\n\n- Add <Description> from <Time> to <Time> by <Date>"
			+ "\n\n- Add <Description> every <Date>";

	// Delete Help
	public static final String MESSAGE_DELETE_HELP = "- Delete #\n\n";

	// Update Help
	public static final String MESSAGE_UPDATE_HELP = "Date Formats [ 25-12-2015   |   25/12/2015   |   2015-12-25   |   2015/12/25 ]\n"
			+ "\nTime Formats [ 6pm   |   6 pm   |   6.30pm   |   6.30 pm ]"
			+ "\n\n- Update <Task #> TaskDesc <Description>\n\n- Update <Task #> By <Date/Day>"
			+ "\n\n- Update <Task #> Complete\n\n- Update <Task #> Incomplete\n\n- Update<Task #> From <Time>\n\n- Update<Task #> To <Time>"
			+ "\n\n- Update<Task#> Every <Day>\n\n- Update<Task #> By date or weekly day\n\n";

	// Display Help
	public static final String MESSAGE_DISPLAY_HELP = "- Display All\n\n- Display Today [tdy]\n\n- Display Tomorrow [tmr]\n\n"
			+ "- Display Yesterday [ytd]\n\n- Display Thisweek [thiswk]\n\n"
			+ "- Display Lastweek [lastwk]\n\n- Display Nextweek [nextweek]\n\n"
			+ "- Display Pending [pend]\n\n- Display Completed [comp]\n\n"
			+ "- Display Monday [mon]\n\n- Display Tuesday [tue]\n\n- Display Wednesday [wed]\n\n"
			+ "- Display Thursday [thu]\n\n- Display Friday [fri]\n\n- Display Saturday [sat]\n\n"
			+ "- Display Sunday [sun]";

	// Search Help
	public static final String MESSAGE_SEARCH_HELP = "Date Formats [ 25-12-2015   |   25/12/2015   |   2015-12-25   |   2015/12/25 ]"
			+ "\n\n- Search 1 <Task #>\n  Search by Task ID\n\n- Search 2 <Task Description>"
			+ "\n  Search by Description\n\n- Search 3 <Date>\n  Search by Date\n";

	// Sort Help
	public static final String MESSAGE_SORT_HELP = "- Sort Description [desc]\n\n- Sort Deadline\n\n- Sort StartTime\n\n- Sort Completed [comp]"
			+ "\n\n- Sort Pending [pend]";

	// Block Help
	public static final String MESSAGE_BLOCK_HELP = "Date Formats [ 25-12-2015   |   25/12/2015   |   2015-12-25   |   2015/12/25 ]\n\n"
			+ "- Block <Date>\n\n- Block from <Date> to <Date>\n\n";

	// Unblock Help
	public static final String MESSAGE_UNBLOCK_HELP = "Date Formats [ 25-12-2015   |   25/12/2015   |   2015-12-25   |   2015/12/25 ]\n\n"
			+ "- Unblock <Date>\n\n- Unblock from <Date> to <Date>\n\n";

	// Undo Help
	public static final String MESSAGE_UNDO_HELP = "- Undo\n\n";

	// Redo Help
	public static final String MESSAGE_REDO_HELP = "- Redo\n\n";

	// Exit Help
	public static final String MESSAGE_EXIT_HELP = "- Exit\n\n";

	/**
	 * These message are for the AddHandler Class
	 */
	public static final String MESSAGE_ADDED = "New Task %1$s\nAdded";
	public static final String MESSAGE_ADD_NO_DESCRIPTION = "Invalid argument for %1$s command.\nPlease specify what to add";
	public static final String MESSAGE_TIME_MISMATCHED = "End Time is supposed to be later than the Start Time";
	public static final String MESSAGE_TIME_SLOT_EMPTY = "Please specify the Start time and the End Time.";

	/**
	 * These message are for SearchHandler class
	 */
	public static final String MESSAGE_NO_MATCH_FOUND_BY_DESC = "Description %1$s\nnot found.";
	public static final String MESSAGE_NO_MATCH_FOUND_BY_DATE = "Date %1$s\nnot found.";
	public static final String MESSAGE_NO_MATCH_FOUND_BY_ID = "ID %s\nnot found.";
	public static final String MESSAGE_INVALID_SEARCH = "Search format is invalid, please look at our hint or"
			+ "\nenter help keyword for assistance";
	public static final String MESSAGE_INVALID_SEARCH_CRITERIA = "Invalid argument for search command\nPlease specify what to search.";

	/**
	 * These message are for BlockDate class
	 */
	public static final String MESSAGE_BLOCK_NO_SPECIFICATION = "Please Enter a Valid Date";
	public static final String MESSAGE_BLOCKED = "\"%1$s\"\nBlocked Successfully";
	public static final String MESSAGE_UNBLOCKED = "\"%1$s\"\nUnblocked Successfully";
	public static final String MESSAGE_BLOCKED_RANGE = "\"%1$s\" to \"%2$s\"\nBlocked Successfully";
	public static final String MESSAGE_UNBLOCKED_RANGE = "\"%1$s\" to \"%2$s\"\nUnblocked Successfully";
	public static final String MESSAGE_BLOCK_SPECIFICATION = "Please Enter a Valid Start Date and End Date";
	public static final String MESSAGE_BLOCK_INCORRECT_KEYWORD = "Please Enter a Valid Command";
	public static final String MESSAGE_BLOCK_INCORRECT_START_EARLIER_THAN_END = "Start Date should not be later than End Date";
	public static final String MESSAGE_BLOCK_DATE_DO_NOT_EXIST = "\"%1$s\" is not inside the block list";
	public static final String MESSAGE_BLOCK_DATE_ALREADY_EXIST = "\"%1$s\" already exist";
	public static final String MESSAGE_BLOCK_WRONG_DATE_FORMAT_END = "Wrong date format for End date";
	public static final String MESSAGE_BLOCK_WRONG_DATE_FORMAT_START = "Wrong date format for Start date";
	public static final String MESSAGE_BLOCK_INVALID_BLOCK_UNBLOCK_COMMAND = "Invalid argument for Block/Unblock command.";
	public static final String MESSAGE_CONFLICT_WITH_BLOCKED_DATE = "This date conflicted in your blocked out date list, "
			+ "\nthe date you are trying to block is already occupied";
	public static final String MESSAGE_BLOCKED_CLASHED_WITH_ADD_DATE = "%1$s is ocuppied\n";
	public static final String MESSAGE_UNBLOCKED_DATE_NOT_EXIST = "%1$s is not inside the block list";
	public static final String MESSAGE_BLOCKED_DATE_NOT_AVAILABLE = "\"%s\", \"%s\""
			+ "\n%s days are occupied, please select other dates.";
	public static final String MESSAGE_UNBLOCKED_DATE_NOT_BLOCKED = "\"%s\", \"%s\""
			+ "\n%s dates has never been block.";
	public static final String MESSAGE_BLOCK_DATE_OVER_TWO_YEARS = "Blocking of dates only allow up to 2 years\nstarting from \"%1$s\"";
	public static final String MESSAGE_BLOCK_RANGE_EXCEED_A_MONTH = "\"%1$s\" to \"%2$s\" has exceeded 31 days.";
	public static final String MESSAGE_PARTIAL_BLOCK = "Some of the date/dates are not block";

	// @author A0111935L
	/**
	 * These message are for UpdateHandler class
	 */
	public static final String MESSAGE_NO_UPDATE_COMMAND_FOUND = "No update command found.";
	public static final String MESSAGE_NO_UPDATE_FIELDS_FOUND = "Please update at least a particular detail.";
	public static final String MESSAGE_UPDATE_SUCCESS = "%1$s\nUpdated";
	public static final String MESSAGE_UPDATE_STATUS_EXTRA_FIELD = "Please remove any information after the word complete/pending.";
	public static final String MESSAGE_INVALID_UPDATE_ID = "Invalid Task ID for the update to proceed";
	public static final String MESSAGE_DESCRIPTION_EMPTY = "Cannot let task description be empty";

	/**
	 * These message are for File Storage
	 */
	public static final String MESSAGE_FILENAME_INVALID_UNSPECIFIED = "Filename not specified.";
	public static final String MESSAGE_FILENAME_INVALID_FORMAT = "Filename is in wrong format.";
	public static final String MESSAGE_TEXTFILE_INFO_CORRUPTED = "Contents in the %1$s has been corrupted.";
	public static final String MESSAGE_ERROR_ON_WRITING_TO_FILE = "Error on saving to file.";
	public static final String MESSAGE_INVALID_STATUS = "Invalid status for a task.";
	public static final String MESSAGE_INVALID_DEADLINESETSTATUS = "Invalid deadline set status for a task";

	/**
	 * These message are for the Data class
	 */
	public static final String MESSAGE_TASKID_ERROR = "The Last Unused Index has been modified illegally.";
	public static final String MESSAGE_NO_TASK_ADDED_TO_DATA = "No Task added";
	public static final String MESSAGE_INVALID_TASK_TO_REMOVE = "Invalid task";
	public static final String MESSAGE_SUCCESS_UPDATE_TASK_TO_ARRAYLIST = "Update Success";
	public static final String MESSAGE_NO_BLOCK_DATE_ADDED_TO_ARRAYLIST = "No blocked date added";
	public static final String MESSAGE_INVALID_BLOCKED_DATE_TO_REMOVE = "Invalid blocked date";
	public static final String MESSAGE_INDEX_OUT_OF_RANGE = "Index Out of Range.";

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
