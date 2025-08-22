package bob.command;

import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command to exit the Bob application.
 * Executes by saving the task list and displaying a farewell message.
 */
public class ByeCommand implements Command {

    /**
     * Executes the bye command: saves the task list to storage
     * and displays a goodbye message via the UI.
     *
     * @param tasks The <code>TaskList</code> to save.
     * @param ui The <code>Ui</code> instance for displaying messages.
     * @param storage The <code>Storage</code> instance for persisting tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        storage.save(tasks);
        ui.showMessage(" Bye have a great time!");
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return <code>true</code> as ByeCommand terminates the program.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
