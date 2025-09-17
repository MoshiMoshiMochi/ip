package bob.exception;

import bob.personality.Personality;

/**
 * Exception thrown when a date or time input is invalid or inconsistent.
 */
public class BobDateTimeException extends RuntimeException {

    /**
     * Constructs a new <code>BobDateTimeException</code> with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public BobDateTimeException(String message) {
        super(Personality.BOBDATETIMEEXCEPTION.getMessage() + message);
    }
}
