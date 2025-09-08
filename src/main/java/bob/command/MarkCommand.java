package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command to mark a task as done in the task list.
 * Executes by updating the task's status and displaying a message via the UI.
 */
public class MarkCommand extends Command {
    private static final String INTRO = " I'm Marking it. I'm Marking it so good!";
    private final int index;

    /**
     * Constructs a new <code>MarkCommand</code> for the specified task index.
     *
     * @param index The index of the task to mark as done (0-based).
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark command: marks the task as done and displays a confirmation message.
     *
     * @param taskList The <code>TaskList</code> containing the task.
     * @param ui       The <code>Ui</code> instance for displaying messages.
     * @param storage  The <code>Storage</code> instance for persisting changes (unused here).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            Task task = taskList.getTask(index);
            task.markDone();
            ui.showMessage(
                    INTRO,
                    "    " + task
            );
        } catch (BobException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * @inheritDoc
     *
     * @return <code>false</code> as MarkCommand does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
