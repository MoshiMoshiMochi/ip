package bob.command;

import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

public class FindCommand implements Command{
    private String description;

    public FindCommand(String description){
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchingTasks = new TaskList();

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
