//@author A0111935L
package utility;

/**
 * This class contains the list of keywords
 * 
 */
public class KeywordType {

	public enum List_Keywords {
		FROM, TO, BY, EVERY, TASKDESC, COMPLETE, INCOMPLETE, FIELD, TASKID, ON
	}

	public enum List_SearchKeywords {
		ALL, COMPLETED, COMP, PENDING, PEND, DESCRIPTION, DESC, DEADLINE, STARTTIME, TODAY, TDY, TOMORROW, TMR, YESTERDAY, YTD, MONDAY, MON, TUESDAY, TUE, WEDNESDAY, WED, THURSDAY, THU, FRIDAY, FRI, SATURDAY, SAT, SUNDAY, SUN, THISWEEK, THISWK, LASTWEEK, LASTWK, NEXTWEEK, NEXTWK, BLOCK
	}

	/**
	 * This method is to determine the type of keyword that the parameter string
	 * has received
	 * 
	 * @param keywordTypeString
	 *            the string to be converted
	 * @return keyword a keyword
	 */
	public static List_Keywords getKeyword(String keywordTypeString) {
		if (keywordTypeString == null) {
			return List_Keywords.FIELD;
		}

		switch (keywordTypeString.toLowerCase()) {
		case "from":
			return List_Keywords.FROM;
		case "to":
			return List_Keywords.TO;
		case "by":
			return List_Keywords.BY;
		case "taskid":
			return List_Keywords.TASKID;
		case "every":
			return List_Keywords.EVERY;
		case "taskdesc":
			return List_Keywords.TASKDESC;
		case "complete":
			return List_Keywords.COMPLETE;
		case "incomplete":
			return List_Keywords.INCOMPLETE;
		case "on":
			return List_Keywords.ON;
		default:
			return List_Keywords.FIELD;
		}
	}

	/**
	 * getKeywordSearchWithIndexNum method will treat a number as an keyword
	 * @param keywordTypeString the string to compare
	 * @return the keyword
	 */
	public static List_Keywords getKeywordSearchWithIndexNum(
			String keywordTypeString) {
		if (keywordTypeString == null) {
			return List_Keywords.FIELD;
		}

		switch (keywordTypeString.toLowerCase()) {
		case "1":
			return List_Keywords.TASKID;
		case "2":
			return List_Keywords.TASKDESC;
		case "3":
			return List_Keywords.BY;
		default:
			return List_Keywords.FIELD;
		}
	}

	/**
	 * This method will check whether the given parameter has a match with the
	 * list of keyword
	 * 
	 * @param input
	 *            the string to compare
	 * @return true if there is a match, else is false
	 */
	public static boolean contains(String input) {
		for (List_Keywords aKey : List_Keywords.values()) {
			if (aKey.name().equals(input.toUpperCase())) {
				return true;
			}
		}

		return false;
	}
}
