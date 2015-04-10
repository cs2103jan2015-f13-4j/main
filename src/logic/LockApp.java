//@A0111935L
package logic;

import java.io.File;
import java.io.IOException;


import utility.MessageList;

/**
 * This class allows the application to be able to use a localhost port in order to prevent the same application to run twice
 */
public class LockApp {
	private static String lockFileName = "lockApp.txt";

	/**
	 * checkExistingApp method bind to a local adapter to prevent launching same application
	 */
	public static void checkExistingApp() {
		File filepath = new File(lockFileName);
		
		if (filepath.exists() && !filepath.isDirectory()) {
			MessageList.printErrorMessageAndExit("Smart Management Tool is already running.");
		} else {
				createLockFile(filepath, lockFileName);
		}
	}
	
	/**
	 * unLockApp method will remove the file
	 */
	public static void unLockApp(){
		File filepath = new File(lockFileName);
		if (filepath.exists() && !filepath.isDirectory()) {
			filepath.delete();
		}
	}
	
	/**
	 * createLockFile method will create a file
	 * @param filepath
	 */
	private static void createLockFile(File filepath, String lockFileName) {
		try {
			filepath.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
