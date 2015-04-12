//@author A0112978W
package utility;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TaskLogging {
	public static Logger logger;
	public static java.util.logging.FileHandler fileHandler = null;
	
	public TaskLogging() {

	}
	
	public static Logger getInstance() {
		logger = Logger.getLogger("SMT");
		
		try {
			fileHandler = new java.util.logging.FileHandler("tasklogging.log", true);
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.setLevel(Level.CONFIG);
		
		return logger;
	}
}
