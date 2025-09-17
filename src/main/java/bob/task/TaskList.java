package bob.task;

import java.util.ArrayList;
import java.util.List;

import bob.exception.BobException;
import bob.personality.Personality;

/**
 * Stores a list of <code>Task</code> objects and handles operations
 * such as adding, deleting, and retrieving tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty <code>TaskList</code>.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to list
     *
     * @param task that is to be added into tasks
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of the task to retrieve.
     * @return The <code>Task</code> at the given index.
     * @throws BobException If the index is out of range.
     */
    public Task getTask(int index) throws BobException {
        if (!isIndexInRange(index)) {
            throw new BobException(
                    Personality.INDEX_OUT_OF_RANGE_MESSAGE1.getMessage()
                            + (index + 1)
                            + Personality.INDEX_OUT_OF_RANGE_MESSAGE2.getMessage()
            );
        }
        assert index >= 0 : "Index should be >= 0 at this point";
        return this.tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Size of the task list.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Deletes and returns the task at the specified index.
     *
     * @param index Index of the task to delete.
     * @return The deleted <code>Task</code>.
     * @throws BobException If the index is out of range.
     */
    public Task deleteTask(int index) throws BobException {
        if (!isIndexInRange(index)) {
            throw new BobException(
                    Personality.INDEX_OUT_OF_RANGE_MESSAGE1.getMessage()
                            + (index + 1)
                            + Personality.INDEX_OUT_OF_RANGE_MESSAGE2.getMessage()
            );
        }
        assert index >= 0 : "Index should be >= 0 at this point";
        return this.tasks.remove(index);
    }

    /**
     * Checks if index is within the bounds of task list
     *
     * @param index Index to check.
     * @return <code>true</code> if the index is valid, <code>false</code> otherwise.
     */
    private boolean isIndexInRange(int index) {
        return index >= 0 && index < this.tasks.size();
    }

    /**
     * Replaces the task at the specified index with the given task.
     *
     * @param task  The new <code>Task</code> to set.
     * @param index The index at which to set the new task.
     */
    public void setIndexAt(Task task, int index) {
        // Changed method name to improve descriptiveness [Copilot suggestion]
        // Index will be valid because of previous checks
        this.tasks.set(index, task);
    }

    /**
     * Returns a list of all tasks in the task list.
     *
     * @return A new <code>List</code> containing all tasks.
     */
    public List<Task> asList() {
        return new ArrayList<>(this.tasks);
    }

    /**
     * Returns a string representation of the task list.
     * Lists all tasks in order with numbering.
     *
     * @return String representation of the task list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            //appends each task added the list
            sb.append(" ").append(i + 1).append(".").append(this.tasks.get(i));
            if (i != this.tasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
