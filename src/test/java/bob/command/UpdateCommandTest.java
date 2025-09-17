package bob.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.exception.BobInvalidIndexException;
import bob.storage.Storage;
import bob.task.DeadlineTask;
import bob.task.EventTask;
import bob.task.TaskList;
import bob.task.ToDoTask;
import bob.ui.Ui;

public class UpdateCommandTest {
    private Ui ui = new Ui();
    private Storage storage = new Storage("savedtasks/test.txt");

    // For updating with changing type
    @Test
    public void updateTaskType_fromTodoToEvent_success() throws BobException {
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDoTask("read book"));

        EventTask expected = new EventTask("event", "2025-12-12 1200", "2025-12-12 1300");
        UpdateCommand cmd = new UpdateCommand(
                0,
                "Event",
                "event",
                null,
                "2025-12-12 1200",
                "2025-12-12 1300"
        );
        cmd.execute(tasks, ui, storage);

        try {
            assertEquals(expected.toString(), tasks.getTask(0).toString());
        } catch (BobInvalidIndexException e) {
            // shouldn't reach here
        }

        File file = new File("savedtasks/test.txt");
        file.delete();
    }

    @Test
    public void updateTaskType_fromEventToDeadline_success() throws BobException {
        TaskList tasks = new TaskList();
        tasks.addTask(new EventTask("event", "2025-12-12 1200", "2025-12-12 1300"));

        DeadlineTask expected = new DeadlineTask("deadline", "2025-12-12 1200");
        UpdateCommand cmd = new UpdateCommand(
                0,
                "deAdline",
                "deadline",
                "2025-12-12 1200",
                null,
                null
        );
        cmd.execute(tasks, ui, storage);
        try {
            assertEquals(expected.toString(), tasks.getTask(0).toString());
        } catch (BobInvalidIndexException e) {
            // shouldn't reach here
        }

        File file = new File("savedtasks/test.txt");
        file.delete();
    }

    @Test
    public void updateTaskType_fromDeadlineToTodo_success() throws BobException {
        TaskList tasks = new TaskList();
        tasks.addTask(new DeadlineTask("deadline", "2025-12-12 1200"));

        ToDoTask expected = new ToDoTask("todo");
        UpdateCommand cmd = new UpdateCommand(
                0,
                "toDO",
                "todo",
                "2025-12-12 1200",
                null,
                null
        );
        cmd.execute(tasks, ui, storage);
        try {
            assertEquals(expected.toString(), tasks.getTask(0).toString());
        } catch (BobInvalidIndexException e) {
            // shouldn't reach here
        }

        File file = new File("savedtasks/test.txt");
        file.delete();
    }

    // For updating without changing type
    @Test
    public void updateNoType_todoTask_success() throws BobException {
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDoTask("lmao"));

        ToDoTask expected = new ToDoTask("todo");
        UpdateCommand cmd = new UpdateCommand(0,
                null,
                "todo",
                "2025-12-12 1200",
                null,
                null
        );
        cmd.execute(tasks, ui, storage);

        assertEquals(expected.toString(), tasks.getTask(0).toString());

        File file = new File("savedtasks/test.txt");
        file.delete();
    }

    @Test
    public void updateNoType_deadlineTask_success() throws BobException {
        TaskList tasks = new TaskList();
        tasks.addTask(new DeadlineTask("lmao", "2025-12-12 1200"));

        DeadlineTask expected = new DeadlineTask("deadline", "2025-12-12 1200");
        UpdateCommand cmd = new UpdateCommand(0,
                null,
                "deadline",
                "2025-12-12 1200",
                null,
                null
        );
        cmd.execute(tasks, ui, storage);

        assertEquals(expected.toString(), tasks.getTask(0).toString());

        File file = new File("savedtasks/test.txt");
        file.delete();
    }

    @Test
    public void updateNoType_eventTask_success() throws BobException {
        TaskList tasks = new TaskList();
        tasks.addTask(new EventTask("lmao", "2025-12-12 1100", "2025-12-12 1300"));

        EventTask expected = new EventTask("event", "2025-12-12 1100", "2025-12-12 1400");
        UpdateCommand cmd = new UpdateCommand(
                0,
                null,
                "event",
                null,
                "2025-12-12 1100",
                "2025-12-12 1400"
        );
        cmd.execute(tasks, ui, storage);

        assertEquals(expected.toString(), tasks.getTask(0).toString());

        File file = new File("savedtasks/test.txt");
        file.delete();
    }

    // For updating without changing type - invalid format
    @Test
    public void updateNoType_todoTaskInvalidFormat_throwsException() {
        TaskList tasks = new TaskList();
        tasks.addTask(new ToDoTask("lmao"));

        UpdateCommand cmd = new UpdateCommand(
                0,
                null,
                null,
                "2025-12-12 1200",
                null,
                null
        );

        try {
            cmd.execute(tasks, ui, storage);
        } catch (BobInvalidFormatException e) {
            assertEquals(new BobInvalidFormatException(CommandFormat.UPDATEFORMAT).getMessage(), e.getMessage());
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    @Test
    public void updateNoType_deadlineTaskInvalidFormat_throwsException() {
        TaskList tasks = new TaskList();
        tasks.addTask(new DeadlineTask("lmao", "2025-12-12 1200"));

        UpdateCommand cmd = new UpdateCommand(
                0,
                null,
                null,
                null,
                null,
                null
        );

        try {
            cmd.execute(tasks, ui, storage);
        } catch (BobInvalidFormatException e) {
            assertEquals(new BobInvalidFormatException(CommandFormat.UPDATEFORMAT).getMessage(), e.getMessage());
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    @Test
    public void updateNoType_eventTaskInvalidFormat_throwsException() {
        TaskList tasks = new TaskList();
        tasks.addTask(new EventTask("lmao", "2025-12-12 1100", "2025-12-12 1300"));

        UpdateCommand cmd = new UpdateCommand(
                0,
                null,
                null,
                null,
                null,
                null
        );

        try {
            cmd.execute(tasks, ui, storage);
        } catch (BobInvalidFormatException e) {
            assertEquals(new BobInvalidFormatException(CommandFormat.UPDATEFORMAT).getMessage(), e.getMessage());
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
