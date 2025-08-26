package bob.command;

import bob.exception.BobDateTimeException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command to list all tasks in the task list.
 * Executes by displaying all tasks via the UI.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command: displays all tasks in the task list
     * using the UI.
     *
     * @param tasks   The <code>TaskList</code> containing tasks to display.
     * @param ui      The <code>Ui</code> instance for displaying messages.
     * @param storage The <code>Storage</code> instance for persistence (unused here).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            ui.showMessage(
                    " The list do be Bobbing my dude!",
                    tasks.toString());
        } catch (BobDateTimeException | BobInvalidFormatException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return <code>false</code> as ListCommand does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
