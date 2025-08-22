package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

public class UnMarkCommand implements Command {
    private final int index;

    public UnMarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            Task task = taskList.getTask(index);
            task.markUnDone();
            ui.showMessage(
                    " You need to BOB mark! BOB for Viltrum!",
                    "    " + task
            );
        } catch (BobException e) {
            ui.showMessage(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
