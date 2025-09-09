package bob.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.DeadlineTask;
import bob.task.EventTask;
import bob.task.TaskList;
import bob.task.ToDoTask;
import bob.ui.Ui;

public class UpdateCommandTest {
    private Ui ui = new Ui();
    private Storage storage;
    private final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    @Test
    public void updateTaskType_success() {
        //Prepare List
        TaskList tasks = new TaskList();
        ToDoTask task = new ToDoTask("read book");
        tasks.addTask(task);

        EventTask eventTask = new EventTask(
                "event",
                "2025-12-12 1200",
                "2025-12-12 1300"
        );
        UpdateCommand updateEventCmd = new UpdateCommand(
                0,
                "Event",
                "event",
                null,
                "2025-12-12 1200",
                "2025-12-12 1300"
        );
        updateEventCmd.execute(tasks, ui, storage);
        try {
            assertEquals(eventTask.toString(), tasks.getTask(0).toString());
        } catch (BobException e) {
            // shouldn't reach here
        }

        DeadlineTask deadlineTask = new DeadlineTask(
                "deadline",
                "2025-12-12 1200"
        );
        UpdateCommand updateDeadlineCmd = new UpdateCommand(
                0,
                "deAdline",
                "deadline",
                "2025-12-12 1200",
                null,
                null
        );
        updateDeadlineCmd.execute(tasks, ui, storage);
        try {
            assertEquals(deadlineTask.toString(), tasks.getTask(0).toString());
        } catch (BobException e) {
            // shouldn't reach here
        }

        ToDoTask toDoTask = new ToDoTask(
                "todo"
        );
        UpdateCommand updateTodoCmd = new UpdateCommand(
                0,
                "toDO",
                "todo",
                "2025-12-12 1200",
                null,
                null
        );
        updateTodoCmd.execute(tasks, ui, storage);
        try {
            assertEquals(toDoTask.toString(), tasks.getTask(0).toString());
        } catch (BobException e) {
            // shouldn't reach here
        }
    }

    @Test
    public void updateNoType_success() {
        //Prep
        TaskList tasks = new TaskList();
        ToDoTask toDoTask = new ToDoTask(
                "lmao"
        );
        DeadlineTask deadlineTask = new DeadlineTask(
                "lmao",
                "2025-12-12 1200"
        );
        EventTask eventTask = new EventTask(
                "lmao",
                "2025-12-12 1100",
                "2025-12-12 1300"
        );
        tasks.addTask(toDoTask);
        tasks.addTask(deadlineTask);
        tasks.addTask(eventTask);

        // For todo task
        ToDoTask toDoTaskExpected = new ToDoTask(
                "todo"
        );
        UpdateCommand updateTodoCmd = new UpdateCommand(
                0,
                null,
                "todo",
                "2025-12-12 1200",
                null,
                null
        );
        updateTodoCmd.execute(tasks, ui, storage);
        try {
            assertEquals(toDoTaskExpected.toString(), tasks.getTask(0).toString());
        } catch (BobException e) {
            // shouldn't reach here
        }

        // For deadline task
        DeadlineTask deadlineTaskExpected = new DeadlineTask(
                "deadline",
                "2025-12-12 1200"
        );
        UpdateCommand updateDeadlineCmd = new UpdateCommand(
                1,
                null,
                "deadline",
                "2025-12-12 1200",
                null,
                null
        );
        updateDeadlineCmd.execute(tasks, ui, storage);
        try {
            assertEquals(deadlineTaskExpected.toString(), tasks.getTask(1).toString());
        } catch (BobException e) {
            // shouldn't reach here
        }


        EventTask eventTaskExpected = new EventTask(
                "event",
                "2025-12-12 1100",
                "2025-12-12 1400"
        );
        UpdateCommand updateEventCmd = new UpdateCommand(
                2,
                null,
                "event",
                null,
                "2025-12-12 1100",
                "2025-12-12 1400"
        );
        updateEventCmd.execute(tasks, ui, storage);
        try {
            assertEquals(eventTaskExpected.toString(), tasks.getTask(2).toString());
        } catch (BobException e) {
            // shouldn't reach here
        }
    }

    @Test
    public void updateNoType_invalid() {
        //one for each
        //Prep
        TaskList tasks = new TaskList();
        ToDoTask toDoTask = new ToDoTask(
                "lmao"
        );
        DeadlineTask deadlineTask = new DeadlineTask(
                "lmao",
                "2025-12-12 1200"
        );
        EventTask eventTask = new EventTask(
                "lmao",
                "2025-12-12 1100",
                "2025-12-12 1300"
        );
        tasks.addTask(toDoTask);
        tasks.addTask(deadlineTask);
        tasks.addTask(eventTask);
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);


        // For todo task
        UpdateCommand updateTodoCmd = new UpdateCommand(
                0,
                null,
                null,
                "2025-12-12 1200",
                null,
                null
        );
        try {
            // Should fail
            updateTodoCmd.execute(tasks, ui, storage);
        } catch (BobInvalidFormatException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }

        // For deadline task
        UpdateCommand updateDeadlineCmd = new UpdateCommand(
                1,
                null,
                null,
                null,
                null,
                null
        );
        try {
            // Should fail
            updateDeadlineCmd.execute(tasks, ui, storage);
        } catch (BobInvalidFormatException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }

        UpdateCommand updateEventCmd = new UpdateCommand(
                2,
                null,
                null,
                null,
                null,
                null
        );
        try {
            updateEventCmd.execute(tasks, ui, storage);
        } catch (BobInvalidFormatException e) {
            // shouldn't reach here
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }
}
