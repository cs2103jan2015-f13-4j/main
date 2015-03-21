import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

public class SearchHandler {
private static Logger taskLogger = TaskLogging.getInstance();
	/**
	 * 
	 * @param keyFieldsList
	 * @param listTask
	 * @return
	 */
	public static String executeSearch(ArrayList<KeyFieldPair> keyFieldsList,
			ArrayList<Task> listTask) {
		if (keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (listTask == null) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}

		if (keyFieldsList.size() != 2) {
			return MessageList.MESSAGE_INVAILD_SEARCH;
		}

		return searchTask(listTask, keyFieldsList.get(1));
	}

	/**
	 * This method is to search task, further breakup the search by different
	 * method
	 * 
	 * @param listTask
	 * @param searchCriteria
	 * @return
	 */
	private static String searchTask(ArrayList<Task> listTask,
			KeyFieldPair searchCriteria) {

		if (searchCriteria.getKeyword() == null
				|| searchCriteria.getFields() == null
				|| searchCriteria.getFields().isEmpty()
				|| searchCriteria.getKeyword().isEmpty()) {
			return MessageList.MESSAGE_INVAILD_SEARCH_CRITERIA;
		}

		KeywordType.List_Keywords getKey = KeywordType
				.getKeyword(searchCriteria.getKeyword());

		switch (getKey) {
		case TASKID:
			return searchTaskID(searchCriteria.getFields(), listTask);
		case TASKDESC:
			return searchTaskDesc(listTask, searchCriteria.getFields());
		case DEADLINE:
			return searchTaskDate(searchCriteria.getFields(), listTask);
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
	private static String searchTaskDate(String deadLine,
			ArrayList<Task> listTask) {
		if (deadLine == null || deadLine.isEmpty()) {
			return MessageList.MESSAGE_INVAILD_SEARCH_CRITERIA;
		}

		String searchDetails = "";

		for (int i = 0; i < listTask.size(); i++) {
			DateTime endDate = DateParser.generateDate(deadLine);

			if (listTask.get(i).getTaskEndDateTime().toLocalDate().equals(endDate.toLocalDate()))
				searchDetails += listTask.get(i).toString();
		}
		if (!searchDetails.isEmpty()) {
			taskLogger.log(Level.INFO, "Search By Task Date");
			return searchDetails;
		} else {
			return MessageList.MESSAGE_NO_MATCH_FOUND;
		}
	}

	/**
	 * Search Task by the task index
	 * 
	 * @param index
	 * @param listTask
	 * @return
	 */
	private static String searchTaskID(String index, ArrayList<Task> listTask) {

		if (!checkInteger(index)) {
			return "Please enter a integer";
		}

		for (int i = 0; i < listTask.size(); i++) {
			if (listTask.get(i).getTaskId() == Integer.parseInt(index)) {
				return listTask.get(i).toString();
			}
		}
		taskLogger.log(Level.INFO, "Search By Task ID");
		return MessageList.MESSAGE_NO_MATCH_FOUND;
	}

	/**
	 * Search task by the task description
	 * 
	 * @param listTask
	 * @param wordAbstracted
	 * @return
	 */
	private static String searchTaskDesc(ArrayList<Task> listTask,
			String wordAbstracted) {

		ArrayList<Task> tempList = new ArrayList<Task>();
		for (int i = 0; i < listTask.size(); i++) {
			if (listTask.get(i).getTaskDescription().contains(wordAbstracted)) {
				tempList.add(listTask.get(i));
			}
		}
		if (tempList.size() == 0) {
			return MessageList.MESSAGE_NO_MATCH_FOUND;
		}
		taskLogger.log(Level.INFO, "Search By Task Description");
		return displayTaskDetails(tempList);

	}

	/**
	 * returning converted arraylist in String
	 * 
	 * @param displayTasksList
	 * @return
	 */
	private static String displayTaskDetails(ArrayList<Task> displayTasksList) {
		String taskDetails = "";
		for (int i = 0; i < displayTasksList.size(); i++) {
			taskDetails += displayTasksList.get(i).toString() + "\n";
		}
		return taskDetails;
	}

	/**
	 * convert to integer
	 * 
	 * @param text
	 * @return
	 */
	private static boolean checkInteger(String text) {
		try {
			Integer.parseInt(text);
		} catch (NumberFormatException e) {
			assert false: e.toString();
		}

		return true;
	}
}
