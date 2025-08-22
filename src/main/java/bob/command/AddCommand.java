package bob.command;

import bob.exception.BobDateTimeException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

public class AddCommand implements Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.addTask(task);
            ui.prepareMessage(CommandType.UNKNOWN, task, tasks.size());
        } catch (BobDateTimeException | BobInvalidFormatException e) {
            ui.showMessage(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
