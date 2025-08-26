package bob.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import bob.command.AddCommand;
import bob.exception.BobException;
import bob.storage.Storage;
import bob.ui.Ui;

public class TaskListTest {
    private Ui ui = new Ui();
    private Storage storage;

    @Test
    public void taskList_taskNumOutOfRange() {
        TaskList tasks = new TaskList();
        ToDoTask task = new ToDoTask("read book");

        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(tasks, ui, storage);

        int index = 200;
        try {
            Task t = tasks.getTask(index);
        } catch (BobException e) {
            BobException expected = new BobException(" Task number " + (index + 1) + " does not exist!");
            assertEquals(expected.getMessage(), e.getMessage());
        }

    }
}
