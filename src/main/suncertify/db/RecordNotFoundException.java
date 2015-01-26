package main.suncertify.db;

/**
*
* @author 91023151
*/
public class RecordNotFoundException extends Exception {

    private static final long serialVersionUID = 2823045322464184130L;
	private String message;

	/**
	 * Creates a new RecordNotFoundException with <code>null<code> as its
	 * message.
	 */
	public RecordNotFoundException() {
	};

	/**
	 * Creates an instance of a RecordNotFoundException with the specified
	 * message.
	 */
	public RecordNotFoundException(String message) {
	    this.message = message;
	};

	/**
	 * Returns the detail message string of this RecordNotFoundException.
	 */
	@Override
	public String getMessage() {
	    return message;
	};

}
