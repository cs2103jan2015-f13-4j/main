import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {

	// file extension length including a '.'
	private static final int FILE_EXTENSION_LENGTH = 4;
	// task component separator char
	private static final String TASK_COMPONENT_SEPARATOR = "|";
	// task field and data separator char
	private static final String TASK_FIELD_DATA_SEPARATOR = "=";
	// task fields list taskid offset
	private static final int TASKID_OFFSET = 0;
	// task fields list taskdesc offset
	private static final int TASKDESC_OFFSET = 1;
	// task fields list taskstartdatetime offset
	private static final int TASKSTARTDATETIME_OFFSET = 2;
	// task fields list taskenddatetime offset
	private static final int TASKENDDATETIME_OFFSET = 3;
	// task fields list taskweeklyday offset
	private static final int TASKWEEKLYDAY_OFFSET = 4;
	// task fields list taskstatus offset
	private static final int TASKSTATUS_OFFSET = 5;
	
	/**
	 * Check if file exists and load it to a list
	 * 
	 * @param args
	 *            receive argument which will contains the filename
	 * @return the filename if the argument fulfill the condition
	 */
	public static void checkAndLoadTaskFile(String fileName,
			ArrayList<Task> tasksList, IndicatorMessagePair msgPair) {

		// Call to check for file is empty
		exitIfUnspecificFileName(fileName, msgPair);
		if (!msgPair.isTrue()) {
			return;
		}

		// Call to check for file format
		exitIfWrongFileFormat(fileName, msgPair);
		if (!msgPair.isTrue()) {
			return;
		}

		File filepath = new File(fileName);

		if (filepath.exists() && !filepath.isDirectory()) {
			loadToArrayList(fileName, tasksList, msgPair);
		} else {
			try {
				filepath.createNewFile();
			} catch (IOException e) {
				setIndicatorMessagePair(msgPair, false, e.toString());
				return;
			}
		}
	}

	/**
	 * Check if file exists and return the index of last unused task list
	 * 
	 * @param fileName
	 *            receive argument which will contains the filename
	 * @return an integer which indicate the last unused task id
	 */
	public static int checkAndLoadLastTaskIndexFile(String fileName,
			IndicatorMessagePair msgPair) {

		// Call to check for file is empty
		exitIfUnspecificFileName(fileName, msgPair);
		if (!msgPair.isTrue()) {
			return -1;
		}

		// Call to check for file format
		exitIfWrongFileFormat(fileName, msgPair);
		if (!msgPair.isTrue()) {
			return -1;
		}

		File filepath = new File(fileName);

		/*
		 * Check if the file exists, it will load the data into the arraylist
		 * else it will create a new file
		 */
		if (filepath.exists() && !filepath.isDirectory()) {
			return loadLastIndexUnused(fileName, msgPair);
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
	 * This method will load the contents from the text file to ArrayList
	 * 
	 * @param fileName
	 *            the filename which will be opened and load contents into
	 */
	private static void loadToArrayList(String fileName,
			ArrayList<Task> tasksList, IndicatorMessagePair msgPair) {
		FileReader reader = null;
		BufferedReader bufferRead = null;
		try {
			reader = new FileReader(fileName);
			bufferRead = new BufferedReader(reader);
			
		} catch (FileNotFoundException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return;
		}
		
		assert(bufferRead != null);
		
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
					return;
				}
				tasksList.add(taskObj);
			}
			bufferRead.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return;
		}
		
		setIndicatorMessagePair(msgPair, true, "");

	}

	/**
	 * This method will load the last unused index number from the file
	 * 
	 * @param fileName
	 * @return last unused index number
	 */
	private static Integer loadLastIndexUnused(String fileName,
			IndicatorMessagePair msgPair) {
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader bufferRead = new BufferedReader(reader);
			String txtLine = "";
			try {
				while ((txtLine = bufferRead.readLine()) != null) {
					if (!isStringAnInteger(txtLine)) {
						setIndicatorMessagePair(msgPair, false,
								MessageList.MESSAGE_TEXTFILE_INFO_CORRUPTED);
						return -1;
					}
					bufferRead.close();
					return Integer.parseInt(txtLine);
				}
				bufferRead.close();
			} catch (IOException e) {
				setIndicatorMessagePair(msgPair, false, e.toString());
				return -1;
			}
		} catch (FileNotFoundException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return -1;
		}
		setIndicatorMessagePair(msgPair, true, "");
		return 1;
	}

	/**
	 * This method write the task list to the text file
	 * 
	 * @param fileName
	 *            an filename for saving
	 */
	public static void writeToFile(String fileName, ArrayList<Task> tasksList,
			IndicatorMessagePair msgPair) {
		// Add the string to the file
		try {
			FileWriter fw = new FileWriter(fileName);// setup a file writer
			fw.flush();
			BufferedWriter bw = new BufferedWriter(fw);
			String formattedString = new String();
			for (int i = 0; i < tasksList.size(); i++) {
				formattedString = concatTaskFieldToString(tasksList.get(i));
				if (formattedString == null) {
					setIndicatorMessagePair(msgPair, false,
							MessageList.MESSAGE_ERROR_ON_WRITING_TO_FILE);
					bw.close();
					return;
				}
				bw.write(formattedString);
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return;
		}
		setIndicatorMessagePair(msgPair, true, "");
	}

	/**
	 * This method write the last unused index to the text file
	 * 
	 * @param fileName
	 *            an filename for saving
	 */
	public static void writeToFile(String fileName, Integer lastUnUsedIndex,
			IndicatorMessagePair msgPair) {
		// Add the string to the file
		try {
			FileWriter fw = new FileWriter(fileName);// setup a file writer
			fw.flush();
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(lastUnUsedIndex.toString());
			bw.close();
			fw.close();
		} catch (IOException e) {
			setIndicatorMessagePair(msgPair, false, e.toString());
			return;
		}
		setIndicatorMessagePair(msgPair, true, "");
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
	 * This method will concatenate the heading of each task field
	 * 
	 * @param oneTask
	 * @return a text line
	 */
	private static String concatTaskFieldToString(Task oneTask) {
		if (oneTask == null) {
			return null;
		}
		String textLine = new String();
		TaskFieldList.List_Task_Field[] taskFields = TaskFieldList.List_Task_Field
				.values();
		if (oneTask.getTaskId() > 0) {
			textLine += taskFields[TASKID_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += String.valueOf(oneTask.getTaskId());
			textLine += TASK_COMPONENT_SEPARATOR;
		}
		if (oneTask.getTaskDescription() != null
				&& !oneTask.getTaskDescription().isEmpty()) {
			textLine += taskFields[TASKDESC_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += oneTask.getTaskDescription();
			textLine += TASK_COMPONENT_SEPARATOR;
		}
		if (oneTask.getTaskStartDateTime() != null) {
			textLine += taskFields[TASKSTARTDATETIME_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += oneTask.getTaskStartDateTime().toString();
			textLine += TASK_COMPONENT_SEPARATOR;
		}
		if (oneTask.getTaskEndDateTime() != null) {
			textLine += taskFields[TASKENDDATETIME_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += oneTask.getTaskEndDateTime().toString();
			textLine += TASK_COMPONENT_SEPARATOR;
		}

		if (oneTask.getWeeklyDay() != null && !oneTask.getWeeklyDay().isEmpty()) {
			textLine += taskFields[TASKWEEKLYDAY_OFFSET].name();
			textLine += TASK_FIELD_DATA_SEPARATOR;
			textLine += oneTask.getWeeklyDay();
			textLine += TASK_COMPONENT_SEPARATOR;
		}
		
		if (textLine.isEmpty()) {
			return null;
		}
		
		textLine += taskFields[TASKSTATUS_OFFSET].name();
		textLine += TASK_FIELD_DATA_SEPARATOR;
		textLine += oneTask.getTaskStatus();
		textLine += TASK_COMPONENT_SEPARATOR;
		
		return textLine.substring(0, textLine.length() - 1);
	}

	/**
	 * This method will check if the filename is specified and exit if there is
	 * no filename given
	 * 
	 * @param fileName
	 */
	private static void exitIfUnspecificFileName(String fileName,
			IndicatorMessagePair msgPair) {
		if (fileName == null || fileName.trim().isEmpty()) {
			setIndicatorMessagePair(msgPair, false,
					MessageList.MESSAGE_FILENAME_INVALID_UNSPECIFIED);
			return;
		}
		setIndicatorMessagePair(msgPair, true, "");
	}

	/**
	 * This method will check if the file is in correct format and exit if
	 * incorrect
	 * 
	 * @param fileName
	 *            the string to compare
	 */
	private static void exitIfWrongFileFormat(String fileName,
			IndicatorMessagePair msgPair) {
		boolean isFileContainsADot = fileName.contains(".");
		int fileExtLength = fileName.length() - fileName.indexOf(".");
		String fileExt = fileName.substring(fileName.length()
				- FILE_EXTENSION_LENGTH, fileName.length());

		if (!(isFileContainsADot) || !(fileExtLength == FILE_EXTENSION_LENGTH)
				|| !(fileExt.equals(".txt"))) {
			setIndicatorMessagePair(msgPair, false,
					MessageList.MESSAGE_FILENAME_INVALID_FORMAT);
			return;
		}
		setIndicatorMessagePair(msgPair, true, "");
	}

	private static void setIndicatorMessagePair(IndicatorMessagePair msgPair,
			boolean isTrue, String msg) {
		msgPair.setTrue(isTrue);
		msgPair.setMessage(msg);
	}

	
	

}
