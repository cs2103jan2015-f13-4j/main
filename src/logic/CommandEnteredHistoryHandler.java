//@author A0112978W
package logic;

import java.util.ArrayList;

/**
 * This class will keep track of the commands entered by the User.
 * In the application, User can make use of 'Shift' + 'Left' or 'Right' arrow keys
 * to navigate through the list of entered commands.
 */
public class CommandEnteredHistoryHandler {

	// ArrayList to store the commands entered by the User
	private static ArrayList<String> listPastCommandEntered = new ArrayList<String>();
	private static int currentIndex = -1;

	/**
	 * This method will get the List of Commands Entered
	 * 
	 * @return The List of Commands Entered.
	 */
	public static ArrayList<String> getListPastCommandEntered() {
		return listPastCommandEntered;
	}

	/**
	 * This method sets a new List of Commands Entered
	 * 
	 * @param listPastCommandEntered
	 * 			This is the new List of Commands Entered.
	 */
	public static void setListPastCommandEntered(ArrayList<String> listPastCommandEntered) {
		CommandEnteredHistoryHandler.listPastCommandEntered = listPastCommandEntered;
	}

	/**
	 * This method will get the Current Index
	 * 
	 * @return The Current Index.
	 */
	public static int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * This method sets a new Current Index
	 * 
	 * @param currentIndex
	 * 			This is the new Current Index.
	 */
	public static void setCurrentIndex(int currentIndex) {
		CommandEnteredHistoryHandler.currentIndex = currentIndex;
	}

	/**
	 * This method will store the command entered into List of Commands Entered
	 * and set the currentIndex to the size of List of Commands Entered
	 * 
	 * @param cmd
	 * 			This is the Command Entered.
	 */
	public static void newCommandEntered(String cmd) {
		listPastCommandEntered.add(cmd);
		currentIndex = listPastCommandEntered.size();
	}
	
	/**
	 * This method will get the previous command entered
	 * 
	 * @return The Previous Command.
	 */
	public static int getPrevCmd() {
		if(currentIndex > 0) {
			return --currentIndex;
		}
		return currentIndex;
	}
	
	/**
	 * This method will get the next command entered
	 * 
	 * @return The Next Command.
	 */
	public static int getAfterCmd() {
		if(currentIndex < listPastCommandEntered.size()) {
			return ++currentIndex;
		}
		return currentIndex;
	}
	
	/**
	 * This method will get the command entered at a specific index
	 * 
	 * @param index
	 * 			This is the index of the command.
	 * 
	 * @return The Command Entered at the specific index.
	 */
	public static String retrieveCommand(int index) {
		if(index >= listPastCommandEntered.size() || index < 0) {
			return "";
		}
		return listPastCommandEntered.get(index);
	}
}
