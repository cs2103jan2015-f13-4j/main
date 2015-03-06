import java.util.ArrayList;

public class DeleteHandler {
	
	public static String delete(String fileName, String[] text, ArrayList<Task> list) {

		int i;
		int index = Integer.parseInt(text[1]);
		Task removedText = list.get(index - 1);
		
		if (text.length != 2) {
			return "Arguments invalid";
		}

		if (!checkInteger(text[1])) {
			return "Invalid delete argument";
		}
		
		// will search the task id from the list and delete the task
		for(i = 0; i < list.size(); i++){
			if(list.get(i).getTaskId() == index){
				break;
			}
		}
		
		if(i >= 0)
		{
			list.remove(i);
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
