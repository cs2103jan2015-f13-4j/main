import java.io.BufferedReader;
import java.io.BufferedWriter;
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

	/**
	 * This method will load the contents from the text file to ArrayList
	 * 
	 * @param fileName
	 *            the filename which will be opened and load contents into
	 */
	public static void loadToArrayList(String fileName, ArrayList<Task> taskList) {
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader bufferRead = new BufferedReader(reader);
			String txtLine = "";
			try {
				while ((txtLine = bufferRead.readLine()) != null) {
					taskList.add(txtLine);
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	private static Task convertToTask(String input){
		String[] taskComponent = input.split("|");
		
		if(taskComponent.length == 4){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date startDate;
			try {
				startDate = dateFormat.parse(taskComponent[2]);
				Date endDate = dateFormat.parse(taskComponent[3]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return new Task(Integer.parseInt(taskComponent[0]),taskComponent[1], startDate, endDate);
		}
		return new Task(1,"", new Date(), new Date());
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
			System.out.println(e.toString());
		}
	}
	
	
	
}
