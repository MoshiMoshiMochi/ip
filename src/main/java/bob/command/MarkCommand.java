package bob.command;

import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

public class MarkCommand implements Command {
    private final int index;

    public MarkCommand(int index){
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task task = taskList.getTask(index);
        task.markDone();
        ui.showMessage(
                " I'm Marking it. I'm Marking it so good!",
                "    " + task
        );
    }

    @Override
    public boolean isExit(){
        return false;
    }
}
