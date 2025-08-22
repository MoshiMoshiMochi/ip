package bob.task;

import bob.exception.BobDateTimeException;
import bob.exception.BobInvalidFormatException;

/**
 * Represents a task in the Bob task manager.
 * A <code>Task</code> has a description, completion status, and type.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Constructs a new task with the specified description and type.
     * The task is initially marked as not done.
     *
     * @param description Description of the task.
     * @param type        Type of the task.
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }


    /**
     * Marks task as done by updating isDone to true
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * UnMarks task as UnDone by updating isDone to false
     */
    public void markUnDone() {
        this.isDone = false;
    }

    /**
     * String format to save each respective task that inherits from task
     */
    public abstract String toSaveFormat();

    /**
     * Creates a <code>Task</code> object from a saved line in a file.
     * Returns null if the line is corrupted or cannot be parsed.
     *
     * @param line A line representing a task in saved file format.
     * @return The corresponding <code>Task</code> object, or null if parsing fails.
     */
    public static Task fromSaveFormat(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String desc = parts[2];

            switch (type) {
            case "T":
                ToDoTask toDoTask = new ToDoTask(desc);
                if (isDone) {
                    toDoTask.markDone();
                }
                return toDoTask;
            case "D":
                DeadlineTask Deadlinetask = new DeadlineTask(desc, parts[3]);
                if (isDone) {
                    Deadlinetask.markDone();
                }
                return Deadlinetask;
            case "E":
                EventTask EventTask = new EventTask(desc, parts[3], parts[4]);
                if (isDone) {
                    EventTask.markDone();
                }
                return EventTask;
            default:
                return null; // corrupted line
            }
        } catch (BobInvalidFormatException | BobDateTimeException e) {
            System.out.println(
                    " Failed to Load: \n "
                            + line + "\n "
                            + e.getMessage() + "\n"
            );
            return null; // corrupted line
        }
    }

    public String getDescription() {
        return this.description;
    }

    public TaskType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        String check = this.isDone ? "X" : " ";
        return "[" + type.getSymbol() + "]" + "[" + check + "] " + this.description;
    }
}
