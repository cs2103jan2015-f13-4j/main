//@author A0112501E
package logic;

import java.util.ArrayList;
import java.util.Map;

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

/**
 * This class is use to search task by 1 Task ID, search 2 Task Description and
 * search 3 Task Date
 * 
 */
public class SearchHandler {

	private static final int KEYWORD = 1;
	private static final int NUMBER_OF_ELEMENTS = 2;
	private static final int LENGTH_OF_ARGUMENTS = 1;
	private static final int EXCEEDED_CRITERIA_LENGTH = 2;
	private static final int NO_MATCH = 0;

	// Declaration for Logger
	private static Logger taskLogger = TaskLogging.getInstance();

	/**
	 * 
	 * @param keyFieldsList
	 * @param listTask
	 * @return
	 */
	public static String executeSearch(Map<String, String> keyFieldsList,
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
			Map<String, String> searchCriteria) {

		if (!searchCriteria
				.containsKey(CommandType.Command_Types.SEARCH.name())) {
			return MessageList.MESSAGE_INVALID_SEARCH;
		}

		String[] searchList = searchCriteria.get(
				CommandType.Command_Types.SEARCH.name()).split(" ");
		if (searchList.length < NUMBER_OF_ELEMENTS) {
			return MessageList.MESSAGE_INVALID_SEARCH_CRITERIA;
		}

		KeywordType.List_Keywords getKey = KeywordType
				.getKeywordSearchWithIndexNum(searchList[0]);

		switch (getKey) {
		case TASKID:
			return searchTaskID(searchList, smtData);
		case TASKDESC:
			return searchTaskDesc(smtData, searchList);
		case BY:
			return searchTaskDate(searchList, smtData);
		default:
			return MessageList.MESSAGE_INVALID_SEARCH;
		}

	}

	/**
	 * This is to check for a list of tasks with given deadline
	 * 
	 * @param deadLine
	 * @param listTask
	 * @return
	 */
	private static String searchTaskDate(String[] deadLine, Data smtData) {
		if (deadLine == null || deadLine.length <= LENGTH_OF_ARGUMENTS
				|| deadLine.length > EXCEEDED_CRITERIA_LENGTH) {
			return MessageList.MESSAGE_INVALID_SEARCH_CRITERIA;
		}

		String searchDetails = "";
		DateTime endDate = DateTimeParser.generateDate(deadLine[1]);

		if (endDate == null) {
			return MessageList.MESSAGE_INVALID_SEARCH_CRITERIA;
		}

		for (int i = 0; i < smtData.getSize(); i++) {
			if (smtData.getATask(i).getTaskEndDateTime() != null
					&& smtData.getATask(i).getTaskEndDateTime().toLocalDate()
							.equals(endDate.toLocalDate())
					&& smtData.getATask(i).getDeadLineStatus())
				searchDetails += smtData.getATask(i).toString() + "\n";
		}
		if (!searchDetails.isEmpty()) {
			taskLogger.log(Level.INFO, "Search By Task Date");
			return searchDetails;
		} else {
			return String.format(MessageList.MESSAGE_NO_MATCH_FOUND_BY_DATE,
					deadLine[1]);
		}
	}

	/**
	 * Search Task by the task index
	 * 
	 * @param index
	 * @param listTask
	 * @return
	 */
	private static String searchTaskID(String[] index, Data smtData) {

		if (index.length <= LENGTH_OF_ARGUMENTS
				|| index.length > EXCEEDED_CRITERIA_LENGTH
				|| !checkInteger(index[1])) {
			return MessageList.MESSAGE_INVALID_SEARCH;
		}

		for (int i = 0; i < smtData.getSize(); i++) {
			if (smtData.getATask(i).getTaskId() == Integer.parseInt(index[1])) {
				return smtData.getATask(i).toString();
			}
		}

		// To log search operation
		taskLogger.log(Level.INFO, "Search By Task ID");
		return String
				.format(MessageList.MESSAGE_NO_MATCH_FOUND_BY_ID, index[1]);
	}

	/**
	 * Search task by the task description
	 * 
	 * @param listTask
	 * @param wordAbstracted
	 * @return
	 */
	private static String searchTaskDesc(Data smtData, String[] wordList) {
		if (wordList.length < NUMBER_OF_ELEMENTS) {
			return MessageList.MESSAGE_INVALID_SEARCH;
		}

		String wordAbstracted = mergeStringInArray(wordList);
		ArrayList<Task> tempList = new ArrayList<Task>();
		for (int i = 0; i < smtData.getSize(); i++) {
			if (smtData.getATask(i).getTaskDescription()
					.contains(wordAbstracted)) {
				tempList.add(smtData.getATask(i));
			}
		}
		if (tempList.size() == NO_MATCH) {
			return String.format(MessageList.MESSAGE_NO_MATCH_FOUND_BY_DESC,
					wordAbstracted);
		}
		// To log search operation
		taskLogger.log(Level.INFO, "Search By Task Description");
		return displayTaskDetails(tempList);

	}

	// CHECK CORRECT NOT THE COMMENT
	/**
	 * concatinate the string together
	 * 
	 * @param wordList
	 * @return
	 */
	private static String mergeStringInArray(String[] wordList) {
		String mergedString = new String();
		for (int i = 1; i < wordList.length; i++) {
			mergedString += wordList[i] + " ";
		}

		return mergedString.trim();
	}

	/**
	 * returning converted array list in String
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

	/**
	 * This is to check if the search is valid
	 * 
	 * @param keyFieldsList
	 * @param smtData
	 * @return
	 */
	private static String checkForValidData(Map<String, String> keyFieldsList,
			Data smtData) {
		if (keyFieldsList == null) {
			assert false : "The data object is null";
		}

		if (smtData == null) {
			assert false : "The mapped object is null";
		}
		if (keyFieldsList.isEmpty()) {
			return MessageList.MESSAGE_NULL;
		}

		if (keyFieldsList.size() != KEYWORD) {
			return MessageList.MESSAGE_INVALID_SEARCH;
		}
		return MessageList.MESSAGE_LIST_IS_NOT_EMPTY;
	}
}
