package bob.command;

import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command that can be executed in the Bob application.
 * Each command defines how it interacts with the task list, UI, and storage.
 */
public abstract class Command {

    /**
     * Executes this command, performing the associated action,
     * updating the UI, and saving changes to storage if needed.
     *
     * @param taskList The <code>TaskList</code> to operate on.
     * @param ui       The <code>Ui</code> instance for displaying messages.
     * @param storage  The <code>Storage</code> instance for saving/loading tasks.
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage);


    /**
     * Indicates whether this command exits the application.
     *
     * @return <code>true</code> if the command terminates the program, <code>false</code> otherwise.
     */
    public abstract boolean isExit();


    /**
     * Executes this command in GUI mode (returns a response string).
     */
    public String executeAndReturn(TaskList taskList, Storage storage) {
        // Default: use a StringBuilder to collect responses
        Ui tempUi = new Ui();
        this.execute(taskList, tempUi, storage);
        return tempUi.getCollectedMessages();
    }

    /**
     * Saves the current state of the task list to storage.
     *
     * @param tasks   The <code>TaskList</code> to save.
     * @param storage The <code>Storage</code> instance for persisting tasks.
     */
    public void saveStorage(TaskList tasks, Storage storage) {
        storage.save(tasks);
    }
}
