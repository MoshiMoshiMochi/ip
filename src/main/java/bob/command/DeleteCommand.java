package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 * Executes by removing the task at the specified index and updating the UI.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a new <code>DeleteCommand</code> for the specified task index.
     *
     * @param index The index of the task to delete (0-based).
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete command: removes the task from the task list
     * and displays a message via the UI.
     *
     * @param taskList The <code>TaskList</code> from which to delete the task.
     * @param ui       The <code>Ui</code> instance for displaying messages.
     * @param storage  The <code>Storage</code> instance for persisting changes.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            Task task = taskList.deleteTask(index);
            ui.prepareMessage(
                    CommandType.DELETE,
                    task,
                    taskList.size()
            );
        } catch (BobException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return <code>false</code> as DeleteCommand does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
