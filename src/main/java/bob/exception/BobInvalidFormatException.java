package bob.exception;

import bob.command.CommandFormat;

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
        super(" What the Bob!!![BobInvalidFormatException]\n Invalid Command Format! Expected: " + type.getFormat());
    }
}
