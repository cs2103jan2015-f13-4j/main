import java.util.ArrayList;

import org.joda.time.DateTime;

public class AddHandler {

	public static String executeAdd(String fileName, String lastUnUsedIndexFileName,
			ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask,
			Integer lastUnusedIndex) {
		if (keyParamList == null || keyParamList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (listTask == null) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}

		return addContents(fileName, lastUnUsedIndexFileName,  keyParamList, listTask, lastUnusedIndex);

	}

	private static String addContents(String fileName, String lastUnUsedIndexFileName, 
			ArrayList<KeyParamPair> keyParamList, ArrayList<Task> listTask,
			Integer lastUnusedIndex) {
		IndicatorMessagePair indicMsg;
		KeywordType.List_Keywords getKey;
		Task newTask = new Task();
		newTask.setTaskId(lastUnusedIndex);
		addTaskDesc(newTask, lastUnusedIndex, keyParamList.get(0));//field is here so no need to do in switch case
		
		for (int i = 1; i < keyParamList.size(); i++) {
			getKey = KeywordType.getKeyword(keyParamList.get(i).getKeyword());
			switch (getKey) {
			case BY:
				indicMsg = addTaskByWhen(newTask, lastUnusedIndex,
						keyParamList.get(i));
				break;
			default:
				return String.format(MessageList.MESSAGE_INVALID_COMMAND,"Add");
			}

			if (!indicMsg.isTrue()) {
				return indicMsg.getMessage();
			}
		}
		listTask.add(newTask);
		lastUnusedIndex++;
		indicMsg = new IndicatorMessagePair();
		FileHandler.writeToFile(fileName, listTask, indicMsg);
		
		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		indicMsg = new IndicatorMessagePair();
		FileHandler.writeToFile(fileName, lastUnusedIndex, indicMsg);
		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		return MessageList.MESSAGE_ADDED;
	}

	private static IndicatorMessagePair addTaskByWhen(Task newTask, int index,
			KeyParamPair keyParam) {
		if (keyParam.getParam() == null || keyParam.getParam().isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateTime endDate = DateParser.generateDate(keyParam.getParam());
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}
		newTask.setTaskEndDateTime(endDate);
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
