package bob.command;

import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command that can be executed in the Bob application.
 * Each command defines how it interacts with the task list, UI, and storage.
 */
public interface Command {

    /**
     * Executes this command, performing the associated action,
     * updating the UI, and saving changes to storage if needed.
     *
     * @param taskList The <code>TaskList</code> to operate on.
     * @param ui       The <code>Ui</code> instance for displaying messages.
     * @param storage  The <code>Storage</code> instance for saving/loading tasks.
     */
    void execute(TaskList taskList, Ui ui, Storage storage);

    /**
     * Indicates whether this command exits the application.
     *
     * @return <code>true</code> if the command terminates the program, <code>false</code> otherwise.
     */
    boolean isExit();
}
