import java.util.ArrayList;

import org.joda.time.DateTime;

public class AddHandler {
	
	public static String executeAdd(String fileName, String lastUnUsedIndexFileName,
			ArrayList<KeyFieldPair> keyFieldsList, ArrayList<Task> listTask) {
		if (keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (listTask == null) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}

		return addContents(fileName, lastUnUsedIndexFileName,  keyFieldsList, listTask);

	}

	private static String addContents(String fileName, String lastUnUsedIndexFileName, 
			ArrayList<KeyFieldPair> keyFieldsList, ArrayList<Task> listTask) {
		IndicatorMessagePair indicMsg = new IndicatorMessagePair();
		KeywordType.List_Keywords getKey;
		Task newTask = new Task();
		
		int lastUnUsedIndex = loadLastUsedIndex(lastUnUsedIndexFileName, indicMsg);
		indicMsg = new IndicatorMessagePair();
		
		newTask.setTaskId(lastUnUsedIndex);
		addTaskDesc(newTask, lastUnUsedIndex, keyFieldsList.get(0));//field is here so no need to do in switch case
		
		for (int i = 1; i < keyFieldsList.size(); i++) {
			getKey = KeywordType.getKeyword(keyFieldsList.get(i).getKeyword());
			switch (getKey) {
			case BY:
				indicMsg = addTaskByWhen(newTask, lastUnUsedIndex,
						keyFieldsList.get(i));
				break;
			//case EVERY:
				//indicMsg = 
			default:
				return String.format(MessageList.MESSAGE_INVALID_COMMAND,"Add");
			}

			if (!indicMsg.isTrue()) {
				return indicMsg.getMessage();
			}
		}
		
		listTask.add(newTask);
		lastUnUsedIndex++;
		indicMsg = new IndicatorMessagePair();
		FileHandler.writeToFile(fileName, listTask, indicMsg);
		
		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		indicMsg = new IndicatorMessagePair();
		FileHandler.writeToFile(lastUnUsedIndexFileName, lastUnUsedIndex, indicMsg);
		
		if (!indicMsg.isTrue()) {
			return indicMsg.getMessage();
		}
		
		return MessageList.MESSAGE_ADDED;
	}

	private static IndicatorMessagePair addTaskByWhen(Task newTask, int index,
			KeyFieldPair keyFields) {
		if (keyFields.getFields() == null || keyFields.getFields().isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		DateTime endDate = DateParser.generateDate(keyFields.getFields());
		if (endDate == null) {
			return new IndicatorMessagePair(false, String.format(
					MessageList.MESSAGE_INCORRECT_DATE_FORMAT, "End"));
		}
		newTask.setTaskEndDateTime(endDate);
		return new IndicatorMessagePair(true, "");
	}

	private static IndicatorMessagePair addTaskDesc(Task newTask, int index,
			KeyFieldPair keyFields) {
		if (keyFields.getFields() == null || keyFields.getFields().isEmpty()) {
			return new IndicatorMessagePair(false,
					MessageList.MESSAGE_NO_DATE_GIVEN);
		}
		newTask.setTaskDescription(keyFields.getFields());
		return null;
	}
	
	/**
	 * This method call the file handler to retrieve the last unused index and return that index back
	 * @param lastUsedIndexFileName
	 * @param msgPair
	 * @return either -1 or the last unused index
	 */
	private static int loadLastUsedIndex(String lastUsedIndexFileName, IndicatorMessagePair msgPair){
		int lastUnusedIndex = FileHandler.checkAndLoadLastTaskIndexFile(lastUsedIndexFileName, msgPair);
		if(!msgPair.isTrue){
			return -1;
		}
		return lastUnusedIndex;
	}

}
