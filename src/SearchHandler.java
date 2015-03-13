import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class SearchHandler {
	private static String executeSearch(ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask){
		if (keyParamList == null || keyParamList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (listTask == null) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}
		
		searchTask(keyParamList,listTask);
		return "";
	}
	
	private static void searchTask(ArrayList<KeyParamPair> keyParamList,
			ArrayList<Task> listTask) {
		
	}
	
	private static void searchTaskByDate(ArrayList<KeyParamPair> keyParamList,
			ArrayList<Task> listTask){
		
	}
	

	private static ArrayList<Task> searchTask(ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask, IndicatorMessagePair indicMsg){
		KeywordType.List_Keywords getKey;
		indicMsg = new IndicatorMessagePair();
		for (int i = 1; i < keyParamList.size(); i++) {
			getKey = KeywordType.getKeyword(keyParamList.get(i).getKeyword());
			switch (getKey) {
			case BY:
				indicMsg = addTaskByWhen(newTask, lastUnUsedIndex,
						keyParamList.get(i));
				break;
			default:
				return new ArrayList<Task>();
			}

			if (!indicMsg.isTrue()) {
				return new ArrayList<Task>();
			}
		}
	}
	


}
