package bob.exception;

/**
 * The most general exception used in the Bob application.
 * Serves as the base class for other Bob-specific exceptions.
 */
public class BobException extends Exception{

    /**
     * Constructs a new <code>BobException</code> with the specified detail message.
     * The message is prefixed with a Bob-specific header.
     *
     * @param message The detail message explaining the exception.
     */
    public BobException(String message) {
        super(" What the BOB!!![BobException]\n" + message);
    }
}
