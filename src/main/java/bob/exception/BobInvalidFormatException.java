package bob.exception;

import bob.command.CommandFormat;

public class BobInvalidFormatException extends RuntimeException {
    public BobInvalidFormatException(CommandFormat type) {
        super(" What the Bob!!![BobInvalidFormatException]\n Invalid Command Format! Expected: " + type.getFormat());
    }
}
