package bob.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import bob.exception.BobDateTimeException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.DeadlineTask;
import bob.task.EventTask;
import bob.task.TaskList;
import bob.task.TaskType;
import bob.task.ToDoTask;
import bob.ui.Ui;

public class AddCommandTest {
    private Ui ui = new Ui();
    private Storage storage;
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    @Test
    public void addTodoTask_success() {

        TaskList tasks = new TaskList();
        ToDoTask task = new ToDoTask("read book");
        AddCommand cmd = new AddCommand(task);

        cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertEquals(
                "[" + TaskType.TODO.getSymbol() + "]" + "[ ] read book",
                task.toString());
    }

    @Test
    public void addDeadlineTask_success() {

        TaskList tasks = new TaskList();
        DeadlineTask task = new DeadlineTask("read book", "2025-12-12 1200");
        AddCommand cmd = new AddCommand(task);

        cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertEquals(
                "[" + TaskType.DEADLINE.getSymbol()
                        + "]" + "[ ] read book (by:Dec 12 2025 1200)",
                task.toString());
    }

    @Test
    public void addEventTask_success() {

        TaskList tasks = new TaskList();
        EventTask task = new EventTask("read book", "2025-12-12 1200", "2025-12-12 1300");
        AddCommand cmd = new AddCommand(task);

        cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertEquals(
                "[" + TaskType.EVENT.getSymbol() + "]" + "[ ] read book (from: Dec 12 2025 1200 to: Dec 12 2025 1300)",
                task.toString());
    }

    @Test
    public void addDeadlineTask_invalidDateFormat() {

        try {
            DeadlineTask task = new DeadlineTask("read book", "2025-12-12");
        } catch (BobInvalidFormatException e) {
            BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.DATETIMEFORMAT);
            assertEquals(expected.getMessage(), e.getMessage());
        }

    }

    @Test
    public void addEventTask_invalidDateFormat() {

        try {
            EventTask task = new EventTask("read book", "2025-12-12", "2025-12-12");
        } catch (BobInvalidFormatException e) {
            BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.DATETIMEFORMAT);
            assertEquals(expected.getMessage(), e.getMessage());
        }

    }

    @Test
    public void addEventTask_throwsDateTimeException() {
        try {
            EventTask task = new EventTask("read book", "2025-12-12 1200", "2025-12-12 1100");
        } catch (BobDateTimeException e) {
            BobDateTimeException expected = new BobDateTimeException("To Date needs to be larger than From Date");
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }
}
