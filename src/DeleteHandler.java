import java.util.ArrayList;

public class deleteHandler {
	
	public static String delete(String fileName, String[] text, ArrayList<String> list) {

		if (text.length != 2) {
			return "Arguments invalid";
		}

		if (!checkInteger(text[1])) {
			return "Invalid delete argument";
		}

		int index = Integer.parseInt(text[1]);

		if (index > 0 && index <= list.size()) {
			String removedText = list.get(index - 1);
			list.remove(index - 1);
			return String.format(MessageList.MESSAGE_DELETE, fileName, removedText);
		}

		return MessageList.MESSAGE_NOFILEDELETED;
	}
	
	private static boolean checkInteger(String text){
		try{
			Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
}
