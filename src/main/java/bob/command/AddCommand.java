package bob.command;

import bob.exception.BobDateTimeException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command to add a task to the task list.
 * Executes by adding the specified task and updating the UI.
 */
public class AddCommand implements Command {
    private final Task task;

    /**
     * Constructs a new <code>AddCommand</code> for the specified task.
     *
     * @param task The <code>Task</code> to be added.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add command: adds the task to the task list
     * and displays a message via the UI.
     *
     * @param tasks The <code>TaskList</code> to add the task to.
     * @param ui The <code>Ui</code> instance for displaying messages.
     * @param storage The <code>Storage</code> instance for persisting tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try{
            tasks.addTask(task);
            ui.prepareMessage(CommandType.UNKNOWN, task, tasks.size());
        }catch (BobDateTimeException | BobInvalidFormatException e){
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return <code>false</code> as AddCommand does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

}
