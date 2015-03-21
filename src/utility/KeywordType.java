package utility;

public class KeywordType {
	
	public enum List_Keywords{
		FROM, BY, TASKID, EVERY, TASKDESC, TASKSTART, TASKEND, SCHEDULE, TODAY, TODO, COMPLETED, PENDING, DESCRIPTION, STARTDATE, DEADLINE, TASKSTATUS, FIELD
	}
	
	/**
	 * This method is to determine the type of keyword that the parameter string has received
	 * @param keywordTypeString the string to be converted
	 * @return keyword a keyword
	 */
	public static List_Keywords getKeyword(String keywordTypeString){
		if (keywordTypeString == null) {
			return List_Keywords.FIELD;
		}

		switch(keywordTypeString.toLowerCase()){
		case "from":
			return List_Keywords.FROM;
		case "by":
			return List_Keywords.BY;
		case "taskid":
			return List_Keywords.TASKID;
		case "every":
			return List_Keywords.EVERY;
		case "taskdesc":
			return List_Keywords.TASKDESC;
		case "taskstart":
			return List_Keywords.TASKSTART;
		case "taskend":
			return List_Keywords.TASKEND;
		case "schedule":
			return List_Keywords.SCHEDULE;
		case "today":
			return List_Keywords.TODAY;
		case "todo":
			return List_Keywords.TODO;
		case "completed":
			return List_Keywords.COMPLETED;
		case "pending":
			return List_Keywords.PENDING;
		case "description":
			return List_Keywords.DESCRIPTION;
		case "startdate":
			return List_Keywords.STARTDATE;
		case "deadline":
			return List_Keywords.DEADLINE;
		case "taskstatus":
			return List_Keywords.TASKSTATUS;
		default:
			return List_Keywords.FIELD;
		}
	}
	
	
	/**
	 * This method will check whether the given parameter has a match with the list of keyword
	 * @param input the string to compare
	 * @return true if there is a match, else is false
	 */
	public static boolean contains(String input){
		for (List_Keywords aKey : List_Keywords.values()) {
	        if (aKey.name().equals(input.toUpperCase())) {
	            return true;
	        }
	    }

	    return false;
	}
}
