package bob.task;

import java.util.ArrayList;

import bob.exception.BobException;

/**
 * Stores a list of <code>Task</code> objects and handles operations
 * such as adding, deleting, and retrieving tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

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
        if (!indexInRange(index)) {
            throw new BobException(" Task number " + (index + 1) + " does not exist!");
        }
        return tasks.get(index);
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
        if (!indexInRange(index)) {
            throw new BobException(" Task number " + (index + 1) + " does not exist!");
        }
        return tasks.remove(index);
    }

    /**
     * Checks if index is within the bounds of task list
     *
     * @param index Index to check.
     * @return <code>true</code> if the index is valid, <code>false</code> otherwise.
     */
    public boolean indexInRange(int index) {
        return index >= 0 && index < tasks.size();
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
        for (int i = 0; i < tasks.size(); i++) {
            //appends each task added the list
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i));
            if (i != tasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
