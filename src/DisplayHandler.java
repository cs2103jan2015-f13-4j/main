import java.util.ArrayList;


public class DisplayHandler {

		public static String executeDisplay(String[] commandInput, ArrayList<Task> listTask) {

			String message = "";
			int dataCounter = 1;
			String taskDetails = "";

			if(commandInput.length == 1) {
				message = "Invalid Arguement";
			}
			
			for (Task taskData : listTask) {
				taskDetails = taskData.toString();
				message = "\n" + (dataCounter++) + ". " + taskDetails;
			}
			
			return message;
		}
}
