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
    FIND("find <desc>"),
    DATETIMEFORMAT("<yyyy-mm-dd HHmm>"),
    UPDATEFORMAT("\n update <task no.> with at least one optional field listed below \n"
            + "\n /t <task type> for changing task types \n"
            + "Types to choose from: todo / deadline / event \n"
            + "\n /d <description> for changing description of task \n"
            + "\n /by <date> for changing due datetime for deadline task "
            + "\n (Required if changing to deadline task i.e. /t deadline) \n"
            + "\n /from <date> for changing by datetime for Event task "
            + "\n (Required if changing to event task i.e. /t event) \n"
            + "\n /to <date> for changing by datetime for Event task "
            + "\n (Required if changing to event task i.e. /t event) \n"
    ),;

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
