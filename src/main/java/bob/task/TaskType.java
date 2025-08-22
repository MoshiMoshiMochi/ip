package bob.task;

/**
 * Represents the type of a task in the Bob task manager.
 * Each type has a corresponding symbol used for storage and display.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");


    private final String symbol;

    /**
     * Constructs a TaskType with the specified symbol.
     *
     * @param symbol Symbol representing the task type.
     */
    TaskType(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol associated with this task type.
     *
     * @return String symbol of the task type.
     */
    public String getSymbol() {
        return this.symbol;
    }
}
