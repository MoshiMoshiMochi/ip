package bob.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import bob.command.AddCommand;
import bob.exception.BobInvalidIndexException;
import bob.personality.Personality;
import bob.storage.Storage;
import bob.ui.Ui;

public class TaskListTest {
    private Ui ui = new Ui();
    private Storage storage = new Storage("savedtasks/test.txt");

    @Test
    public void taskList_taskNumOutOfRange() {
        TaskList tasks = new TaskList();
        ToDoTask task = new ToDoTask("read book");

        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(tasks, ui, storage);

        int index = 200;
        try {
            Task t = tasks.getTask(index);
        } catch (BobInvalidIndexException e) {
            BobInvalidIndexException expected = new BobInvalidIndexException(
                    Personality.INDEX_OUT_OF_RANGE_MESSAGE1.getMessage()
                            + (index + 1)
                            + Personality.INDEX_OUT_OF_RANGE_MESSAGE2.getMessage());
            assertEquals(expected.getMessage(), e.getMessage());
        }

    }
}
