import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FileHandler {

	//file extension length including a '.'
	private static final int FILE_EXTENSION_LENGTH = 4;
	
	/**
	 * Check if file exists and load it to a list
	 * 
	 * @param args
	 *            receive argument which will contains the filename
	 * @return the filename if the argument fulfill the condition
	 */
	public static void checkAndLoadTaskFile(String fileName, ArrayList<Task> taskList) {

		exitIfWrongFileFormat(fileName);

		File filepath = new File(fileName);


		if (filepath.exists() && !filepath.isDirectory()) {
			loadToArrayList(fileName, taskList);
		} else {
			try {
				filepath.createNewFile();
			} catch (IOException e) {
				MessageList.printErrorMessageAndExit(e.toString());
			}
		}
	}
	

	/**
	 * Check if file exists and return the index of last unused task list
	 * @param fileName receive argument which will contains the filename
	 * @return an integer which indicate the last unused task id
	 */
	public static int checkAndLoadLastTaskIndexFile(String fileName) {

		// Call to check for file format
		exitIfWrongFileFormat(fileName);

		File filepath = new File(fileName);

		/*
		 * Check if the file exists, it will load the data into the arraylist
		 * else it will create a new file
		 */
		if (filepath.exists() && !filepath.isDirectory()) {
			return loadLastIndexUnused(fileName);
		} else {
			try {
				filepath.createNewFile();
			} catch (IOException e) {
				MessageList.printErrorMessageAndExit(e.toString());
			}
		}
		return 1;
	}
	
	/**
	 * This method will load the contents from the text file to ArrayList
	 * 
	 * @param fileName
	 *            the filename which will be opened and load contents into
	 */
	private static void loadToArrayList(String fileName, ArrayList<Task> taskList) {
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader bufferRead = new BufferedReader(reader);
			String txtLine = "";
			try {
				while ((txtLine = bufferRead.readLine()) != null) {
					Task taskObj= convertToTask(txtLine);
					if(taskObj != null){
						taskList.add(convertToTask(txtLine));
					}
					
				}
			} catch (IOException e) {
				MessageList.printErrorMessageAndExit(e.toString());
			}

		} catch (FileNotFoundException e) {
			MessageList.printErrorMessageAndExit(e.toString());
		}
	}

	private static int loadLastIndexUnused(String fileName) {
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader bufferRead = new BufferedReader(reader);
			String txtLine = "";
			try {
				while ((txtLine = bufferRead.readLine()) != null) {
					if(isStringAnInteger(txtLine)){
						bufferRead.close();
						return Integer.parseInt(txtLine);
					}
				}
			} catch (IOException e) {
				MessageList.printErrorMessageAndExit(e.toString());
			}
		} catch (FileNotFoundException e) {
			MessageList.printErrorMessageAndExit(e.toString());
		}
		return 1;
	}
	
	private static Task convertToTask(String input){
		String[] taskComponent = input.split("|");
		
		if(taskComponent.length == 4){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date startDate = new Date();
			Date endDate = new Date();
			try {
				startDate = dateFormat.parse(taskComponent[2]);
				endDate = dateFormat.parse(taskComponent[3]);
			} catch (ParseException e) {
				MessageList.printErrorMessageAndExit(e.toString());
			}
			return new Task(Integer.parseInt(taskComponent[0]),taskComponent[1], startDate, endDate);
		}
		return null;
	}
	
	/**
	 * This method write the contents to the text file
	 * 
	 * @param fileName
	 *            an filename for saving
	 */
	public static void writeToFile(String fileName, ArrayList<Task> taskList) {
		// Add the string to the file
		try {
			FileWriter fw = new FileWriter(fileName);// setup a file writer
			fw.flush();
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < taskList.size(); i++) {
				bw.write(taskList.get(i).toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			MessageList.printErrorMessageAndExit(e.toString());
		}
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
	 * This method will check if the file is in correct format and exit if
	 * incorrect
	 * 
	 * @param fileName
	 *            the string to compare
	 */
	private static void exitIfWrongFileFormat(String fileName) {
		boolean isFileContainsADot = fileName.contains(".");
		int fileExtLength = fileName.length() - fileName.indexOf(".");
		String fileExt = fileName.substring(fileName.length() - FILE_EXTENSION_LENGTH,
				fileName.length());

		if (!(isFileContainsADot) || !(fileExtLength == FILE_EXTENSION_LENGTH)
				|| !(fileExt.equals(".txt"))) {
			MessageList.printErrorMessageAndExit("Wrong file format");
		}

	}
	
}
