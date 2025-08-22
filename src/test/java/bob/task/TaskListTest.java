package bob.task;

import bob.command.AddCommand;
import bob.command.MarkCommand;
import bob.command.UnMarkCommand;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    private Ui ui = new Ui();
    private Storage storage;

    @Test
    public void TaskList_TaskNumDNE(){
        TaskList tasks = new TaskList();
        ToDoTask task = new ToDoTask("read book");

        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(tasks, ui, storage);

        int index = 200;
        try {
            Task t = tasks.getTask(index);
        }catch (BobException e){
            BobException expected = new BobException(" Task number " + (index + 1) + " does not exist!");
            assertEquals(expected.getMessage(), e.getMessage());
        }

    }
}
