package bob.command;

import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

public interface Command {
    void execute(TaskList taskList, Ui ui, Storage storage);
    boolean isExit();
}
