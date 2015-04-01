package logic;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import parser.DateTimeParser;
import utility.CommandType;
import utility.KeywordType;
import utility.MessageList;
import utility.TaskLogging;
import data.Data;
import data.Task;

import java.util.logging.Level;
import java.util.logging.Logger;


public class SearchHandler {

	private static Logger taskLogger = TaskLogging.getInstance();
	
	/**
	 * 
	 * @param keyFieldsList
	 * @param listTask
	 * @return
	 */
	public static String executeSearch(HashMap<String, String> keyFieldsList,
			Data smtData) {
		
		checkForValidData(keyFieldsList, smtData);
		
		return searchTask(smtData, keyFieldsList);
	}

	/**
	 * This method is to search task, further breakup the search by different
	 * method
	 * 
	 * @param listTask
	 * @param searchCriteria
	 * @return
	 */
	private static String searchTask(Data smtData,
			HashMap<String, String> searchCriteria) {
		searchCriteria.remove(CommandType.Command_Types.SEARCH.name());//remove the search key pair
		if (searchCriteria.isEmpty()) {
			return MessageList.MESSAGE_INVAILD_SEARCH_CRITERIA;
		}

		for (String key : searchCriteria.keySet()) {
			KeywordType.List_Keywords getKey = KeywordType
					.getKeyword(key);

			switch (getKey) {
			case TASKID:
				return searchTaskID(searchCriteria.get(key), smtData);
			case TASKDESC:
				return searchTaskDesc(smtData, searchCriteria.get(key));
			case DEADLINE:
				return searchTaskDate(searchCriteria.get(key), smtData);
			default:
				return MessageList.MESSAGE_INVAILD_SEARCH;
			}
		}
		return "";
	}

	/**
	 * This is to check for a list of tasks with given deadLine
	 * 
	 * @param deadLine
	 * @param listTask
	 * @return
	 */
	private static String searchTaskDate(String deadLine,
			Data smtData) {
		if (deadLine == null || deadLine.isEmpty()) {
			return MessageList.MESSAGE_INVAILD_SEARCH_CRITERIA;
		}

		String searchDetails = "";

		for (int i = 0; i < smtData.getSize(); i++) {
			DateTime endDate = DateTimeParser.generateDate(deadLine);

			if (smtData.getATask(i).getTaskEndDateTime().toLocalDate()
					.equals(endDate.toLocalDate()))
				searchDetails += smtData.getATask(i).toString();
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
	private static String searchTaskID(String index, Data smtData) {

		if (!checkInteger(index)) {
			return MessageList.MESSAGE_INVAILD_SEARCH;
		}

		for (int i = 0; i < smtData.getSize(); i++) {
			if (smtData.getATask(i).getTaskId() == Integer.parseInt(index)) {
				return smtData.getATask(i).toString();
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
	private static String searchTaskDesc(Data smtData,
			String wordAbstracted) {

		ArrayList<Task> tempList = new ArrayList<Task>();
		for (int i = 0; i < smtData.getSize(); i++) {
			if (smtData.getATask(i).getTaskDescription().contains(wordAbstracted)) {
				tempList.add(smtData.getATask(i));
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
			return false;
		}

		return true;
	}
	
	private static String checkForValidData(HashMap<String, String> keyFieldsList, Data smtData) {
		if (keyFieldsList == null || keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (smtData == null) {
			return MessageList.MESSAGE_NO_TASK_IN_LIST;
		}

		if (keyFieldsList.size() != 2) {
			return MessageList.MESSAGE_INVAILD_SEARCH;
		}
		return MessageList.MESSAGE_LIST_IS_NOT_EMPTY;
	}
}
