package bob.command;

import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.task.TaskType;
import bob.task.ToDoTask;
import bob.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkCommandTest {
    private Ui ui = new Ui();
    private Storage storage;

    @Test
    public void Task_Mark_UnMark_Success(){
        TaskList tasks = new TaskList();
        ToDoTask task = new ToDoTask("read book");

        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(tasks, ui, storage);

        MarkCommand markCommand = new MarkCommand(0);
        markCommand.execute(tasks, ui, storage);

        //Check if Task is Marked
        try {
            assertEquals("[" + TaskType.TODO.getSymbol() + "]" + "[X] read book", tasks.getTask(0).toString());

            UnMarkCommand unMarkCommand = new UnMarkCommand(0);
            unMarkCommand.execute(tasks, ui, storage);

            //Check if UnMark
            assertEquals("[" + TaskType.TODO.getSymbol() + "]" + "[ ] read book", tasks.getTask(0).toString());
        }catch (BobException e){
            //It should not reach here!!!!!!!!!!!!!!
        }

    }
}
