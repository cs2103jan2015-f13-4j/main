import java.util.ArrayList;

public class AddHandler {
	
	public static String executeAdd(String fileName,String[] input, ArrayList<Task> listTask) {
		String txtToAdd = "";

		for (int i = 1; i < input.length; i++) {
			txtToAdd += input[i];
			
			if ((i + 1) != input.length)
				txtToAdd += " ";
		}
		
		return MessageList.MESSAGE_ADDED;
	}

}
