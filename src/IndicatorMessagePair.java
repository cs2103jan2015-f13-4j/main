
public class IndicatorMessagePair {

	boolean isTrue;
	String message;
	
	/**
	 * @param isTrue
	 * @param message
	 */
	public IndicatorMessagePair(boolean isTrue, String message) {
		super();
		this.isTrue = isTrue;
		this.message = message;
	}

	/**
	 * @return the isTrue
	 */
	public boolean isTrue() {
		return isTrue;
	}

	/**
	 * @param isTrue the isTrue to set
	 */
	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
