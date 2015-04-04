package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;

import parser.DateTimeParser;
import utility.IndicatorMessagePair;
import utility.MessageList;
import data.Task;
import data.TaskParserFromTextFile;
import data.TaskParserWriteToTextFile;

public class FileStorage {

	// file extension length including a '.'
	private static final int FILE_EXTENSION_LENGTH = 4;
	private static final String FILE_EXTENSION = ".txt";

	
	private static String fileName = "defaultTaskList.txt";
	private static String lastUnUsedIndexFileName = "lastUnusedIndex.txt";
	private static String blockedDateFileName = "defaultBlockedDateList.txt";
	

	/**
	 * get the filename for task list
	 * @return
	 */
	public static String getFileNameForTasksList(){
		return fileName;
	}
	
	/**
	 * get the filename for last unused index
	 * @return
	 */
	public static String getFileNameForLastUnusedIndex(){
		return lastUnUsedIndexFileName;
	}
	
	/**
	 * get the filename for blocked date list
	 * @return
	 */
	public static String getFileNameForBlockedDatesList(){
		return blockedDateFileName;
	}
	
	/**
	 * setFileNameForTasksList method set the filename for task list
	 * @param receivedFileName
	 */
	public static void setFileNameForTasksList(String receivedFileName){
		fileName = receivedFileName;
	}
	
	/**
	 * setFileNameForLastUnusedIndex method set the filename for last unused index
	 * @param receivedFileName
	 */
	public static void setFileNameForLastUnusedIndex(String receivedFileName){
		lastUnUsedIndexFileName = receivedFileName;
	}

	/**
	 * setFileNameForBlockedDatesList method set the filename for blocked date list
	 * @param receivedFileName
	 */
	public static void setFileNameForBlockedDatesList(String receivedFileName){
		blockedDateFileName = receivedFileName;
	}
	

	/**
	 * checkAndLoadTaskFile method checks if file exists and load it to a array list
	 * @param msgPair the indicator of whether the files can be loaded
	 * @return the list of task
	 */
	public static ArrayList<Task> checkAndLoadTaskFile(IndicatorMessagePair msgPair) {

		// Call to check for file is empty
		exitIfUnspecificFileName(fileName, msgPair);
		if (!msgPair.isTrue()) {
			return null;
		}

		// Call to check for file format
		exitIfWrongFileFormat(fileName, msgPair);
		if (!msgPair.isTrue()) {
			return null;
		}

		File filepath = new File(fileName);
		ArrayList<Task> tasksList = new ArrayList<Task>();
		if (filepath.exists() && !filepath.isDirectory()) {
			tasksList =  loadTaskListToArrayList(msgPair);
		} else {
			try {
				filepath.createNewFile();
			} catch (IOException e) {
				setIndicatorMessagePair(msgPair, false, e.toString());
				return null;
			}
		}
		
		return tasksList;
	}
	
	
	/**
	 * checkAndLoadLastTaskIndexFile checks if file exists and return the last unused index
	 * @param msgPair the indicator of whether the files can be loaded
	 * @return last unused index
	 */
	public static int checkAndLoadLastTaskIndexFile(
			IndicatorMessagePair msgPair) {

		// Call to check for file is empty
		exitIfUnspecificFileName(lastUnUsedIndexFileName, msgPair);
		if (!msgPair.isTrue()) {
			return -1;
		}

		// Call to check for file format
		exitIfWrongFileFormat(lastUnUsedIndexFileName, msgPair);
		if (!msgPair.isTrue()) {
			return -1;
		}

		File filepath = new File(lastUnUsedIndexFileName);

		/*
		 * Check if the file exists, it will load the data into the arraylist
		 * else it will create a new file
		 */
		if (filepath.exists() && !filepath.isDirectory()) {
			return loadLastIndexUnused(msgPair);
		} else {
			try {
				filepath.createNewFile();
			} catch (IOException e) {
				setIndicatorMessagePair(msgPair, false, e.toString());
				return -1;
			}
		}
		setIndicatorMessagePair(msgPair, true, "");
		return 1;
	}


	/**
	 * loadTaskListToArrayList method will load the contents from the text file to ArrayList
	 * @param msgPair the indicator of whether the files can be loaded into array list
	 * @return a list of task
	 */
	private static ArrayList<Task> loadTaskListToArrayList(IndicatorMessagePair msgPair) {
		FileReader reader = null;
		BufferedReader bufferRead = null;
		try {
			reader = new FileReader(fileName);
			bufferRead = new BufferedReader(reader);
		} catch (FileNotFoundException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return null;
		}
		
		assert(bufferRead != null);
		
		return readTaskFromFile(msgPair, bufferRead);

	}

	/**
	 * readTaskFromFile method read a list of task from the file
	 * @param msgPair to indicate success or fail
	 * @param bufferRead the buffer reader to read the contents in the file
	 * @return a list of task list
	 */
	private static ArrayList<Task> readTaskFromFile(IndicatorMessagePair msgPair, BufferedReader bufferRead){
		ArrayList<Task> tasksList = new ArrayList<Task>();
		String txtLine = "";
		try {
			while ((txtLine = bufferRead.readLine()) != null) {
				Task taskObj = TaskParserFromTextFile
						.generateStringFromTextFileToTask(txtLine);
				if (taskObj == null) {
					setIndicatorMessagePair(msgPair, false, String.format(
							MessageList.MESSAGE_TEXTFILE_INFO_CORRUPTED,
							fileName));
					bufferRead.close();
					return null;
				}
				tasksList.add(taskObj);
			}
			bufferRead.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return null;
		}
		
		setIndicatorMessagePair(msgPair, true, "");
		return tasksList;
	}

	/**
	 * loadLastIndexUnused method will load the last unused index number from the file
	 * @param msgPair to indicate whether it reads success or fail
	 * @return the last unused index
	 */
	private static Integer loadLastIndexUnused(
			IndicatorMessagePair msgPair) {
		FileReader reader;
		BufferedReader bufferRead;
		try {
			reader = new FileReader(lastUnUsedIndexFileName);
			bufferRead = new BufferedReader(reader);
			
		} catch (FileNotFoundException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return -1;
		}
		
		assert(bufferRead != null);
		
		return readLastUnUsedIndexFromFile(msgPair, bufferRead);
	}
	
	/**
	 * readLastUnUsedIndexFromFile method read a line from text file
	 * @param msgPair to indicate whether it reads success or fail
	 * @param bufferRead the buffer reader to read the contents in the file
	 * @return the last unused index
	 */
	private static Integer readLastUnUsedIndexFromFile(IndicatorMessagePair msgPair, BufferedReader bufferRead){
		String txtLine = "";
		try {
			while ((txtLine = bufferRead.readLine()) != null) {
				if (!isStringAnInteger(txtLine)) {
					setIndicatorMessagePair(msgPair, false,
							MessageList.MESSAGE_TEXTFILE_INFO_CORRUPTED);
					return -1;
				}
				setIndicatorMessagePair(msgPair, true, "");
				return Integer.parseInt(txtLine);
			}
			bufferRead.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return -1;
		}
		
		return 1;
	}


	/**
	 * writeToFile method for this case is write the list of task to file
	 * @param tasksList the list of tasks
	 * @param msgPair to indicate whether it writes success or fail
	 */
	public static IndicatorMessagePair writeToFile(ArrayList<Task> tasksList) {
		IndicatorMessagePair msgPair = new IndicatorMessagePair();
		// Add the string to the file
		try {
			FileWriter fw = new FileWriter(fileName);// setup a file writer
			fw.flush();
			BufferedWriter bw = new BufferedWriter(fw);
			String formattedString = new String();
			for (int i = 0; i < tasksList.size(); i++) {
				formattedString = TaskParserWriteToTextFile.concatTaskFieldToString(tasksList.get(i));
				if (formattedString == null) {
					setIndicatorMessagePair(msgPair, false,
							MessageList.MESSAGE_ERROR_ON_WRITING_TO_FILE);
					bw.close();
					return msgPair;
				}
				bw.write(formattedString);
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return msgPair;
		}
		setIndicatorMessagePair(msgPair, true, "");
		return msgPair;
	}


	/**
	 * writeToFile method for last unused index
	 * @param lastUnUsedIndex the last unused index to be saved
	 * @param msgPair to indicate whether it writes success or fail
	 */
	public static IndicatorMessagePair writeToFile(Integer lastUnUsedIndex) {
		IndicatorMessagePair msgPair = new IndicatorMessagePair();
		// Add the string to the file
		try {
			FileWriter fw = new FileWriter(lastUnUsedIndexFileName);// setup a file writer
			fw.flush();
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(lastUnUsedIndex.toString());
			bw.close();
			fw.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return msgPair;
		}
		setIndicatorMessagePair(msgPair, true, "");
		return msgPair;
	}

	
	/**
	 * checkAndLoadBlockedDateFile method checks and load the list of blocked date time to array list
	 * @param msgPair to indicate whether it loads success or fail
	 * @return a list of blocked date time
	 */
	public static ArrayList<DateTime> checkAndLoadBlockedDateFile(IndicatorMessagePair msgPair) {

		// Call to check for file is empty
		exitIfUnspecificFileName(blockedDateFileName, msgPair);
		if (!msgPair.isTrue()) {
			return null;
		}

		// Call to check for file format
		exitIfWrongFileFormat(blockedDateFileName, msgPair);
		if (!msgPair.isTrue()) {
			return null;
		}

		File filepath = new File(blockedDateFileName);
		ArrayList<DateTime> blockedDatesList = new ArrayList<DateTime>();
		if (filepath.exists() && !filepath.isDirectory()) {
			blockedDatesList =  loadBlockedDatesToArrayList(msgPair);
		} else {
			try {
				filepath.createNewFile();
			} catch (IOException e) {
				setIndicatorMessagePair(msgPair, false, e.toString());
				return null;
			}
		}
		
		return blockedDatesList;
	}
	
	
	
	/**
	 * loadBlockedDatesToArrayList method load the list of blocked date time to arraylist
	 * @param msgPair to indicate whether it loads success or fail
	 * @return a list of blocked date time
	 */
	private static ArrayList<DateTime> loadBlockedDatesToArrayList(IndicatorMessagePair msgPair) {
		FileReader reader = null;
		BufferedReader bufferRead = null;
		try {
			reader = new FileReader(blockedDateFileName);
			bufferRead = new BufferedReader(reader);
		} catch (FileNotFoundException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return null;
		}
		
		assert(bufferRead != null);
		
		return readBlockedDateListFromFile(msgPair, bufferRead);

	}
	
	/**
	 * readBlockedDateListFromFile method read the list of blocked dates line by line from text file
	 * @param msgPair to indicate whether it loads success or fail
	 * @param bufferRead the buffer reader to read the contents in the file
	 * @return a list of blocked dates
	 */
	private static ArrayList<DateTime> readBlockedDateListFromFile(IndicatorMessagePair msgPair, BufferedReader bufferRead){
		ArrayList<DateTime> blockedDatesList = new ArrayList<DateTime>();
		String txtLine = "";
		try {
			while ((txtLine = bufferRead.readLine()) != null) {
				DateTime dateTimeObj = DateTimeParser.generateDate(txtLine);
				if (dateTimeObj == null) {
					setIndicatorMessagePair(msgPair, false, String.format(
							MessageList.MESSAGE_TEXTFILE_INFO_CORRUPTED,
							blockedDateFileName));
					bufferRead.close();
					return null;
				}
				blockedDatesList.add(dateTimeObj);
			}
			bufferRead.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return null;
		}
		
		setIndicatorMessagePair(msgPair, true, "");
		return blockedDatesList;
	}
	
	/**
	 * writeBlockedDateTimeToFile method write the list of blocked date time to file
	 * @param blockedDatesList a list of blocked date time
	 * @param msgPair indicator that whether it saved successfully
	 */
	public static IndicatorMessagePair writeBlockedDateTimeToFile(ArrayList<DateTime> blockedDatesList) {
		// Add the string to the file
		IndicatorMessagePair msgPair = new IndicatorMessagePair();
		try {
			FileWriter fw = new FileWriter(blockedDateFileName);// setup a file writer
			fw.flush();
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < blockedDatesList.size(); i++) {
				bw.write(blockedDatesList.get(i).toLocalDate().toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return msgPair;
		}
		setIndicatorMessagePair(msgPair, true, "");
		return msgPair;
	}
	
	
	
	
	/**
	 * This method check if the given string can be converted to integer
	 * 
	 * @param inputStr
	 *            the string to be converted
	 * @return true if can be converted, else false
	 */
	private static boolean isStringAnInteger(String inputStr) {
		try {
			Integer.parseInt(inputStr);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}


	/**
	 * This method will check if the filename is specified and exit if there is
	 * no filename given
	 * 
	 * @param fileName
	 */
	private static void exitIfUnspecificFileName(String receivedFileName,
			IndicatorMessagePair msgPair) {
		if (receivedFileName == null || receivedFileName.trim().isEmpty()) {
			setIndicatorMessagePair(msgPair, false,
					MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED);
			return;
		}
		setIndicatorMessagePair(msgPair, true, "");
	}


	/**
	 * exitIfWrongFileFormat method will check if the file is in correct format and exit if
	 * incorrect
	 * @param receivedFileName the filename to be checked
	 * @param msgPair to indicate success or fail
	 */
	private static void exitIfWrongFileFormat(String receivedFileName,
			IndicatorMessagePair msgPair) {
		boolean isFileContainsADot = receivedFileName.contains(".");
		int fileExtLength = receivedFileName.length() - receivedFileName.indexOf(".");
		String fileExt = receivedFileName.substring(receivedFileName.length()
				- FILE_EXTENSION_LENGTH, receivedFileName.length());

		if (!(isFileContainsADot) || !(fileExtLength == FILE_EXTENSION_LENGTH)
				|| !(fileExt.equals(FILE_EXTENSION))) {
			setIndicatorMessagePair(msgPair, false,
					MessageList.MESSAGE_FILENAME_INVALID_FORMAT);
			return;
		}
		setIndicatorMessagePair(msgPair, true, "");
	}

	/**
	 * setIndicatorMessagePair method sets the indication of the respective operations
	 * @param msgPair to indicate whether success or fail
	 * @param isTrue to set true or false
	 * @param msg the message to be written to
	 */
	private static void setIndicatorMessagePair(IndicatorMessagePair msgPair,
			boolean isTrue, String msg) {
		msgPair.setTrue(isTrue);
		msgPair.setMessage(msg);
	}

}
