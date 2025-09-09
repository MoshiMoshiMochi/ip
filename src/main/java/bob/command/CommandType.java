package bob.command;

/**
 * Represents the type of command issued by the user in the Bob application.
 * Used to identify and process user inputs accordingly.
 */
public enum CommandType {
    TODO,
    DEADLINE,
    EVENT,
    LIST,
    MARK,
    UNMARK,
    DELETE,
    BYE,
    FIND,
    UPDATE,
    UNKNOWN;

    /**
     * Converts a string input to the corresponding <code>CommandType</code>.
     * Returns <code>UNKNOWN</code> if the input does not match any valid command type.
     *
     * @param input The string representation of the command.
     * @return The corresponding <code>CommandType</code>, or <code>UNKNOWN</code> if invalid.
     */
    public static CommandType fromString(String input) {
        try {
            return CommandType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
