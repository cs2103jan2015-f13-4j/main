import java.util.ArrayList;

public class DeleteHandler {
	
	public static String executeDelete(String fileName, ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask) {

		int i;
		int index;
		Task removedText;
		
		if(keyParamList == null || keyParamList.isEmpty()){
			return MessageList.MESSAGE_INVALID_DELETE;
		}
		
		if(listTask == null || listTask.isEmpty()){
			return MessageList.MESSAGE_NO_FILE_DELETED;
		}
		
		if(!(keyParamList.size() == 1) || !(checkInteger(keyParamList.get(0).getParam()))){
			return MessageList.MESSAGE_INVALID_DELETE;
		}
		
		index = Integer.parseInt(keyParamList.get(0).getParam());
		
		// will search the task id from the list and delete the task
		for(i = 0; i < listTask.size(); i++){
			if(listTask.get(i).getTaskId() == index){
				break;
			}
		}
		
		if(i >= 0)
		{
			removedText = listTask.remove(i);
			return String.format(MessageList.MESSAGE_DELETE_SUCCESS, fileName, removedText.getTaskDescription());
		}
			
		return MessageList.MESSAGE_NO_FILE_DELETED;
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
