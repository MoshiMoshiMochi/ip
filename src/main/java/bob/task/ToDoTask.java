package bob.task;

/**
 * Represents a ToDo task in Bob task manager
 * A <code>ToDoTask</code> only requires description
 */
public class ToDoTask extends Task {
    public ToDoTask(String description) {
        super(description, TaskType.TODO);
    }

    /**
     *  Save format of <code>ToDoTask</code> into file
     */
    @Override
    public String toSaveFormat() {
        return TaskType.TODO.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + " | ";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
