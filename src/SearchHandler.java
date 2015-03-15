import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

public class SearchHandler {

	public static String executeSearch(ArrayList<KeyParamPair> keyParamList,
			ArrayList<Task> listTask) {
		if (keyParamList == null || keyParamList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (listTask == null) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}

		if (keyParamList.size() != 1) {
			return MessageList.MESSAGE_INVAILD_SEARCH;
		}

		return searchTask(listTask, keyParamList.get(0).getParam());
	}

	private static String searchTask(ArrayList<Task> listTask,
			String searchCriteria) {

		String[] searchKeyField = searchCriteria.split(" ");

		if (searchKeyField.length < 2) {
			return MessageList.MESSAGE_INVAILD_SEARCH_CRITERIA;
		}

		KeywordType.List_Keywords getKey = KeywordType
				.getKeyword(searchKeyField[0]);

		switch (getKey) {
		case TASKID:
			return searchTaskID(searchKeyField, listTask);
			break;
		case TASKDESC:
			return searchTaskDesc(listTask, searchKeyField);
		case DEADLINE:
			return searchTaskDate(searchKeyField, listTask);
		default:
			return MessageList.MESSAGE_INVAILD_SEARCH;
		}
	}

	/**
	 * This is to check for a list of tasks with given deadLine
	 * 
	 * @param deadLine
	 * @param listTask
	 * @return
	 */
	private static String searchTaskDate(String[] deadLine,
			ArrayList<Task> listTask) {
		if (deadLine.length != 2) {
			return MessageList.MESSAGE_INVAILD_SEARCH_CRITERIA;
		}

		String searchDetails = "";

		for (int i = 0; i <= listTask.size(); i++) {
			DateTime endDate = DateParser.generateDate(deadLine[1]);

			if (listTask.get(i).getTaskEndDateTime().equals(endDate))
				searchDetails += listTask.get(i).toString();
		}
		if (!searchDetails.isEmpty()) {
			return searchDetails;
		} else {
			return MessageList.MESSAGE_NO_MATCH_FOUND;
		}
	}

	private static String searchTaskID(String[] index, ArrayList<Task> listTask) {
		if (index.length != 2)
			return "invaild argument for searching for searchID";

		if (!checkInteger(index[1])) {
			return "Please enter a integer";
		}

		for (int i = 0; i <= listTask.size(); i++) {
			if (listTask.get(i).getTaskId() == Integer.parseInt(index[1])) {
				return listTask.get(i).toString();
			}
		}
		return MessageList.MESSAGE_NO_MATCH_FOUND;
	}

	private static String searchTaskDesc(ArrayList<Task> listTask,
			String[] wordAbstracted) {

		String criteriaWord = "";
		for (int i = 1; i < wordAbstracted.length; i++) {
			criteriaWord += wordAbstracted[i] + " ";
		}
		criteriaWord = criteriaWord.trim();

		ArrayList<Task> tempList = new ArrayList<Task>();
		for (int i = 0; i <= listTask.size(); i++) {
			if (listTask.get(i).getTaskDescription().equals(criteriaWord)) {
				tempList.add(listTask.get(i));
			}
		}
		if (tempList.size() == 0) {
			return MessageList.MESSAGE_NO_MATCH_FOUND;
		}
		return displayTaskDetails(tempList);

	}

	private static String displayTaskDetails(ArrayList<Task> displayTasksList) {
		String taskDetails = "";
		for (int i = 0; i < displayTasksList.size(); i++) {
			taskDetails += displayTasksList.get(i).toString() + "\n";
		}
		return taskDetails;
	}

	private static boolean checkInteger(String text) {
		try {
			Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}
