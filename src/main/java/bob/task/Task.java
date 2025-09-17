package bob.task;

import java.time.format.DateTimeFormatter;

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
    protected static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected static final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

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

            assert type != "" : "Loaded type should not be empty. Row will not be loaded";

            switch (type) {
            case "T":
                ToDoTask toDoTask = new ToDoTask(desc);
                if (isDone) {
                    toDoTask.markDone();
                }
                return toDoTask;
            case "D":
                DeadlineTask deadlineTask = new DeadlineTask(desc, parts[3]);
                if (isDone) {
                    deadlineTask.markDone();
                }
                return deadlineTask;
            case "E":
                EventTask eventTask = new EventTask(desc, parts[3], parts[4]);
                if (isDone) {
                    eventTask.markDone();
                }
                return eventTask;
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

    /**
     * Returns a string representation of <code>description</code>
     *
     * @return String in the input format for <code>description</code>.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a <code>TaskType</code> representation of the given <code>Task</code>
     *
     * @return <code>TaskType</code> representation of the given <code>Task</code>.
     */
    public TaskType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        String check = this.isDone ? "X" : " ";
        return "[" + type.getSymbol() + "]" + "[" + check + "] " + this.description;
    }
}
