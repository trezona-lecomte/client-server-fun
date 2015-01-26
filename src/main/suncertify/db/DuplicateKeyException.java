package main.suncertify.db;

/**
 *
 * @author 91023151
 */
public class DuplicateKeyException extends Exception {

	private static final long serialVersionUID = 4895806584647173815L;
	private String message;

	/**
	 * Creates a new DuplicateKeyException with <code>null<code> as its
	 * message.
	 */
	public DuplicateKeyException() {
	}

	/**
	 * Creates an instance of a DuplicateKeyException with the specified
	 * message.
	 */
	public DuplicateKeyException(String message) {
		this.message = message;
	}

	/**
	 * Returns the detail message string of this DuplicateKeyException.
	 */
	@Override
	public String getMessage() {
		return message;
	}
}