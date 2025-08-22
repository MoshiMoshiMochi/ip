package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

public class DeleteCommand implements Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

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

    @Override
    public boolean isExit() {
        return false;
    }
}
