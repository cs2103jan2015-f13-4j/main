import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddHandler {

	public static String executeAdd(String fileName, ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask, Integer lastUnusedIndex) {
		if (keyParamList == null || keyParamList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (listTask == null) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}

		return addContents(keyParamList, listTask, lastUnusedIndex);

	}

	private static String addContents(ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask, Integer lastUnusedIndex) {
		IndicatorMessagePair indicMsg;
		KeywordType.List_Keywords getKey;
		Task newTask = new Task();
		newTask.setTaskId(lastUnusedIndex);
		
		for (int i = 1; i < keyParamList.size(); i++) {
			getKey = KeywordType.getKeyword(keyParamList.get(i).getKeyword());
			switch (getKey) {
			
			case BY:
			{
				indicMsg = addTaskByWhen(newTask, lastUnusedIndex, keyParamList.get(i));
				break;
			}
			case FIELD:
			{
				indicMsg = addTaskDesc(newTask, lastUnusedIndex, keyParamList.get(i));
				break;
			}
			
			default:
				return String.format(MessageList.MESSAGE_INVALID_ARGUMENT,"Add");
			}
			

			if (!indicMsg.isTrue()) {
				return indicMsg.getMessage();
			}
		}
		listTask.add(newTask);
		lastUnusedIndex++;
		return MessageList.MESSAGE_ADDED;
	}

	private static IndicatorMessagePair addTaskByWhen(Task newTask, int index,
			KeyParamPair keyParam) {
		if (keyParam.getParam() == null || keyParam.getParam().isEmpty()) {
			return new IndicatorMessagePair(false, MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date endDate = new Date();
		
		try {
			endDate = dateFormat.parse(keyParam.getParam());
		} catch (ParseException e) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_WRONG_DATE_FORMAT, "End"));
		}
		newTask.setTaskEndDate(endDate);
		return new IndicatorMessagePair(true, "");
	}

	private static IndicatorMessagePair addTaskDesc(Task newTask, int index,
			KeyParamPair keyParam) {
		if (keyParam.getParam() == null || keyParam.getParam().isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		newTask.setTaskDescription(keyParam.getParam());
		return null;
	}

}
