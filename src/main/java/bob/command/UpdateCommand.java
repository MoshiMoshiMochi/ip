package bob.command;

import bob.exception.BobDateTimeException;
import bob.exception.BobInvalidFormatException;
import bob.exception.BobInvalidIndexException;
import bob.personality.Personality;
import bob.storage.Storage;
import bob.task.DeadlineTask;
import bob.task.EventTask;
import bob.task.Task;
import bob.task.TaskList;
import bob.task.ToDoTask;
import bob.ui.Ui;

/**
 * Represents a command to update an existing task in the task list.
 * Updates may include changing the task type, description, or associated fields
 * (e.g., deadline or event timings).
 */
public class UpdateCommand extends Command {
    private final int index;
    private final String taskType;
    private final String description;
    private final String by;
    private final String from;
    private final String to;

    /**
     * Constructs a new {@code UpdateCommand}.
     *
     * @param index       The zero-based index of the task to update.
     * @param taskType    The new task type, or {@code null} if unchanged.
     * @param description The new description, or {@code null} if unchanged.
     * @param by          The new deadline (for DEADLINE tasks), or {@code null} if unchanged.
     * @param from        The new start time (for EVENT tasks), or {@code null} if unchanged.
     * @param to          The new end time (for EVENT tasks), or {@code null} if unchanged.
     */
    public UpdateCommand(int index, String taskType, String description, String by, String from, String to) {
        this.index = index;
        this.taskType = taskType;
        this.description = description;
        this.by = by;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the update command by modifying the specified task in the task list.
     * If a new task type is provided, the task is replaced with a new task of that type.
     * Otherwise, only the relevant fields of the existing task are updated.
     *
     * @param tasks   The current task list.
     * @param ui      The UI handler to display messages to the user.
     * @param storage The storage handler (not used in update).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {


        Task selectedTask = tasks.getTask(this.index);
        Task updatedTask = getUpdatedTask(selectedTask);
        replaceTaskInList(tasks, updatedTask);
        super.saveStorage(tasks, storage);
        showUpdateMessage(ui, selectedTask, updatedTask);

    }

    private Task getUpdatedTask(Task selectedTask) {
        if (this.taskType != null) {
            CommandType type = CommandType.fromString(this.taskType);
            return createNewTask(selectedTask, type);
        } else {
            return updateExistingTask(selectedTask);
        }
    }

    private void replaceTaskInList(TaskList tasks, Task updatedTask) {
        assert this.index >= 0 : "Index should be within range in this point";
        tasks.setIndexAt(updatedTask, this.index);
    }

    private void showUpdateMessage(Ui ui, Task oldTask, Task updatedTask) {
        assert updatedTask != null : "updatedTask should not be null at this point";
        ui.showMessage(
                Personality.UPDATEINTRO1.getMessage(),
                Personality.UPDATEINTRO2.getMessage() + oldTask,
                Personality.UPDATEINTRO3.getMessage() + updatedTask
        );
    }

    /**
     * Creates a new task of the given type, reusing the current description if unchanged.
     *
     * @param task The original task being updated.
     * @param type The new task type (TODO, DEADLINE, EVENT).
     * @return A new {@link Task} of the specified type with updated fields.
     */
    private Task createNewTask(Task task, CommandType type) {
        String newDesc = this.description != null ? this.description : task.getDescription();
        switch (type) {
        case TODO: {
            return new ToDoTask(newDesc);
        }
        case DEADLINE: {
            return new DeadlineTask(newDesc, this.by);
        }
        case EVENT: {
            return new EventTask(newDesc, this.from, this.to);
        }
        default: {
            // will never reach this point as code will task type is already validated
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        }
        }
    }

    /**
     * Updates the fields of the existing task without changing its type.
     * Fields that are {@code null} remain unchanged from the original task.
     *
     * @param task The original task being updated.
     * @return A new {@link Task} with updated fields of the same type.
     * @throws BobInvalidFormatException If no updatable fields are provided.
     */
    private Task updateExistingTask(Task task) {
        // Retain original description if not updated
        boolean isDescriptionNull = this.description == null;
        String newDesc = isDescriptionNull ? task.getDescription() : this.description;

        switch (task.getType()) {
        case TODO: {
            return updateToDoTask(newDesc);
        }
        case DEADLINE: {
            assert task instanceof DeadlineTask : "Task should be a DeadlineTask at this point";
            return updateDeadlineTask(task, newDesc);
        }
        case EVENT: {
            assert task instanceof EventTask : "Task should be an EventTask at this point";
            return updateEventTask(task, newDesc);
        }
        default: {
            // won't reach this point as task is always validated to be one of the above types
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        }
        }
    }

    private ToDoTask updateToDoTask(String newDesc) {
        if (newDesc == null) {
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        }
        return new ToDoTask(newDesc);
    }

    private DeadlineTask updateDeadlineTask(Task task, String newDesc) {
        // Safe cast since we already know it's an event type
        DeadlineTask deadlineTask = (DeadlineTask) task;

        // Retain original by if not updated
        String newBy = this.by == null ? deadlineTask.getBy() : this.by;
        if (this.description == null && this.by == null) {
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        }
        return new DeadlineTask(newDesc, newBy);
    }

    private EventTask updateEventTask(Task task, String newDesc) {
        // Safe cast since we already know it's an event type
        EventTask eventTask = (EventTask) task;

        // Retain original from/to if not updated
        String newFrom = this.from == null ? eventTask.getFrom() : this.from;
        String newTo = this.to == null ? eventTask.getTo() : this.to;

        if (this.description == null && this.from == null && this.to == null) {
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        }
        return new EventTask(newDesc, newFrom, newTo);
    }

    /**
     * @inheritDoc
     *
     * @return <code>false</code> as UpdateCommand does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
