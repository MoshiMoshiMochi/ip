package bob.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import bob.exception.BobException;
import bob.exception.BobInvalidIndexException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.task.TaskType;
import bob.task.ToDoTask;
import bob.ui.Ui;

public class MarkCommandTest {
    private Ui ui = new Ui();
    private Storage storage = new Storage("savedtasks/test.txt");

    @Test
    public void task_markUnMark_success() {
        TaskList tasks = storage.load();
        ToDoTask task = new ToDoTask("read book");

        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(tasks, ui, storage);

        MarkCommand markCommand = new MarkCommand(0);
        markCommand.execute(tasks, ui, storage);

        //Check if Task is Marked
        try {
            assertEquals(
                    "[" + TaskType.TODO.getSymbol() + "]" + "[X] read book",
                    tasks.getTask(0).toString());

            UnMarkCommand unMarkCommand = new UnMarkCommand(0);
            unMarkCommand.execute(tasks, ui, storage);

            //Check if UnMark
            assertEquals(
                    "[" + TaskType.TODO.getSymbol() + "]" + "[ ] read book",
                    tasks.getTask(0).toString());
        } catch (BobInvalidIndexException e) {
            //It should not reach here!!!!!!!!!!!!!!
        }

        File file = new File("savedtasks/test.txt");
        file.delete();

    }
}
