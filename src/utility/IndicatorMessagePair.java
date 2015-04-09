//@A0111935L
package utility;
/**
 * This class will stored a boolean and a string which will indicate if it is true or false, it will return a message
 *
 */
public class IndicatorMessagePair {

	private boolean isTrue;
	private String message;
	
	/**
	 * constructor which has parameters
	 * @param isTrue
	 * @param message
	 */
	public IndicatorMessagePair(boolean isTrue, String message) {
		this.isTrue = isTrue;
		this.message = message;
	}
	
	/**
	 * default constructor
	 */
	public IndicatorMessagePair(){
		this.isTrue = false;
		this.message = "";
	}

	/**
	 * isTrue method will return the boolean status
	 * @return the isTrue
	 */
	public boolean isTrue() {
		return isTrue;
	}

	/**
	 * setTrue method will set the boolean
	 * @param isTrue the isTrue to set
	 */
	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	/**
	 * getMessage method will return the message
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * setMessage method set the message
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
