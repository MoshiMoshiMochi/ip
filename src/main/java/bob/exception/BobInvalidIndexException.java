package bob.exception;

import bob.personality.Personality;

/**
 * Exception thrown when an invalid index is provided for operations
 * that require a valid task index.
 */
public class BobInvalidIndexException extends RuntimeException {
    public BobInvalidIndexException(String message) {
        super(Personality.BOBINVALIDINDEXEXCEPTION.getMessage() + message);
    }
}
