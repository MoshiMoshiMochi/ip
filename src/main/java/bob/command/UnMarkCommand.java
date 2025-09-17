package bob.command;

import bob.exception.BobInvalidIndexException;
import bob.personality.Personality;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command to unmark a task as not done in the task list.
 * Executes by updating the task's status and displaying a message via the UI.
 */
public class UnMarkCommand extends Command {
    private static final String INTRO = " You need to BOB mark! BOB for Viltrum!";
    private final int index;

    /**
     * Constructs a new <code>UnMarkCommand</code> for the specified task index.
     *
     * @param index The index of the task to unmark (0-based).
     */
    public UnMarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the unmark command: marks the task as not done and displays a confirmation message.
     *
     * @param tasks    The <code>TaskList</code> containing the task.
     * @param ui       The <code>Ui</code> instance for displaying messages.
     * @param storage  The <code>Storage</code> instance for persisting changes (unused here).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task = tasks.getTask(index);
            task.markUnDone();
            super.saveStorage(tasks, storage);
            ui.showMessage(
                    Personality.UNMARKINTRO.getMessage(),
                    Personality.TAB.getMessage() + task
            );
        } catch (BobInvalidIndexException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * @inheritDoc
     *
     * @return <code>false</code> as UnMarkCommand does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
