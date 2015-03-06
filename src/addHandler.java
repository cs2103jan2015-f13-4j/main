import java.util.ArrayList;

public class addHandler {

	public static String add(String[] input, ArrayList<String> listTask) {
		String txtToAdd = new String();

		for (int i = 1; i < input.length; i++) {
			txtToAdd += input[i];

			if ((i + 1) != input.length)
				txtToAdd += " ";
		}
		listTask.add(txtToAdd);
		//writeToFile(fileName);
		//return ("added to " + fileName + ": \"" + txtToAdd + "\"");
		return MessageList.MESSAGE_ADD;
	}
}
