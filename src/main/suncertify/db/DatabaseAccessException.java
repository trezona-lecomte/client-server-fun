package main.suncertify.db;

/**
 * Created by Kieran Trezona-le Comte on 28/01/15.
 */
public class DatabaseAccessException extends Exception {

    private String message;

    /**
     * Creates a new DatabaseAccessException with <code>null<code> as its
     * message.
     */
    public DatabaseAccessException() {
    }

    /**
     * Creates an instance of a DatabaseAccessException with the specified
     * message.
     */
    public DatabaseAccessException(String message) {
        this.message = message;
    }

    /**
     * Returns the detail message string of this DatabaseAccessException.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
