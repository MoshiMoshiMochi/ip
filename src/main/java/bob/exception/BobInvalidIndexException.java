package bob.exception;

import bob.personality.Personality;

public class BobInvalidIndexException extends RuntimeException {
    public BobInvalidIndexException(String message) {
        super(Personality.BOBINVALIDINDEXEXCEPTION.getMessage() + message);
    }
}
