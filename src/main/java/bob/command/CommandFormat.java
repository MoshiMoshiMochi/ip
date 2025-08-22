package bob.command;

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

    CommandFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
