package bob.exception;

import bob.command.CommandFormat;
import bob.personality.Personality;

/**
 * Exception thrown when a command or input does not follow the expected format.
 */
public class BobInvalidFormatException extends RuntimeException {

    /**
     * Constructs a new <code>BobInvalidFormatException</code> for the specified command format.
     *
     * @param type The expected <code>CommandFormat</code> that was violated.
     */
    public BobInvalidFormatException(CommandFormat type) {
        super(Personality.BOBINVALIDFORMATEXCEPTION.getMessage() + type.getFormat());
    }
}
