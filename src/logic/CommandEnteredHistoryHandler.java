package logic;

import java.util.ArrayList;

public class CommandEnteredHistoryHandler {

	private static ArrayList<String> listPastCommandEntered = new ArrayList<String>();
	private static int currentIndex = -1;

	public static ArrayList<String> getListPastCommandEntered() {
		return listPastCommandEntered;
	}

	public static void setListPastCommandEntered(ArrayList<String> listPastCommandEntered) {
		CommandEnteredHistoryHandler.listPastCommandEntered = listPastCommandEntered;
	}

	public static int getCurrentIndex() {
		return currentIndex;
	}

	public static void setCurrentIndex(int currentIndex) {
		CommandEnteredHistoryHandler.currentIndex = currentIndex;
	}

	public static void newCommandEntered(String cmd) {
		listPastCommandEntered.add(cmd);
		currentIndex = listPastCommandEntered.size() - 1;
	}
	
	public static int getPrevCmd() {
		if(currentIndex > 0) {
			return currentIndex--;
		}
		return currentIndex;
	}
	
	public static int getAfterCmd() {
		if(currentIndex < listPastCommandEntered.size()) {
			return ++currentIndex;
		}
		return currentIndex;
	}
	
	public static String retrieveCommand(int index) {
		if(index >= listPastCommandEntered.size() || index < 0){
			return "";
		}
		return listPastCommandEntered.get(index);
	}
}
