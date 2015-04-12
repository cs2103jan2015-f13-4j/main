//@author A0112978W
package utility;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class will provide the logging function to log the events occurred.
 */
public class TaskLogging {
	public static Logger logger;
	public static java.util.logging.FileHandler fileHandler = null;
	
	public TaskLogging() {
	}
	
	/**
	 * This method will return the Logger instance
	 * 
	 * @return The Logger instance.
	 */
	public static Logger getInstance() {
		logger = Logger.getLogger("SMT");
		
		try {
			// Initialize the file handler
			fileHandler = new java.util.logging.FileHandler("tasklogging.log", true);
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Configurations
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.setLevel(Level.CONFIG);
		
		return logger;
	}
}
