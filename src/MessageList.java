
public class MessageList {

	public static final String MESSAGE_INVALID_ARGUMENT = "Invalid argument for %1$s command."; 
	public static final String MESSAGE_INVALID_CONVERSION_INTEGER = "Invalid Type to Integer for %1$s command";
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

	public static final String MESSAGE_INVALID_ARGUMENT = "Invalid argument for %1$s command."; 	
	public static final String MESSAGE_DELETE = "deleted from %s: \"%s\"";
	public static final String MESSAGE_NOFILEDELETED = "No file to be deleted";
	public static final String MESSAGE_ADDED = "Message Added.";
}
