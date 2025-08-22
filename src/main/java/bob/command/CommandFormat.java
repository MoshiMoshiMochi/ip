package bob.command;

/**
 * Represents the expected input format for each command in the Bob application.
 * Each enum constant defines the syntax the user should follow for that command.
 */
public enum CommandFormat {
    LIST("list"),
    MARK("mark <task no.>"),
    UNMARK("unmark <task no.>"),
    TODO("todo <desc>"),
    DEADLINE("deadline <desc> /by <date>"),
    EVENT("event <desc> /from <date> /to <date>"),
    DELETE("delete <task no.>"),
    DATETIMEFORMAT("<yyyy-mm-dd HHmm>");

    private final String format;

    /**
     * Constructs a <code>CommandFormat</code> with the specified input syntax.
     *
     * @param format The string representing the command format.
     */
    CommandFormat(String format) {
        this.format = format;
    }

    /**
     * Returns the string format of the command.
     *
     * @return The expected input syntax for this command.
     */
    public String getFormat() {
        return format;
    }
}
