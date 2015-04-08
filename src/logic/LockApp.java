//@A0111935L
package logic;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

import utility.MessageList;

public class LockApp {
	private static final int PORT = 9999;
	private static ServerSocket svrSocket;

	//LockApp.checkExistingApp();
	/**
	 * bind to a local adapter to prevent launching same application
	 */
	public static void checkExistingApp() {
		try {
			svrSocket = new ServerSocket(PORT, 0,
					InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }));
		} catch (BindException e) {
			MessageList.printErrorMessageAndExit("Smart Management Tool is already running.");
		} catch (IOException e) {
			MessageList.printErrorMessageAndExit("Unexpected error.");
		}
	}
}
