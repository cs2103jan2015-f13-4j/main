public class RecurringWeek {
	public enum Weekly_Day{
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, UNKNOWN;
	}
	
	
	public static Weekly_Day getDay(String dayOfTheWeek) {

		if (dayOfTheWeek == null) {
			return Weekly_Day.UNKNOWN;
		}
		
		switch (dayOfTheWeek.toLowerCase()) {
		case "monday":
		case "mon":
			return Weekly_Day.MONDAY;
		case "tuesday":
		case "tues":
			return Weekly_Day.TUESDAY;
		case "wednesday": 
		case "wed":
			return Weekly_Day.WEDNESDAY;
		case "thursday": 
		case "thurs":
			return Weekly_Day.THURSDAY;
		case "friday":
		case "fri":
			return Weekly_Day.FRIDAY;
		case "saturday": 
		case "sat":
			return Weekly_Day.SATURDAY;
		case "sunday": 
		case "sun":
			return Weekly_Day.SUNDAY;
		default: 
			return Weekly_Day.UNKNOWN;
		}
	}

}
