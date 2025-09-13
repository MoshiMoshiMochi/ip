package bob.command;

import bob.exception.BobDateTimeException;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
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
    private static final String INTRO1 = "BOBBIDY BOB BOB! I've up(bob)ed the provided task. \n Old Task: ";
    private static final String INTRO2 = " Updated Task: ";

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

        try {
            Task selectedTask = tasks.getTask(this.index);

            Task updatedTask;
            // If change of task type
            if (this.taskType != null) {
                CommandType type = CommandType.fromString(this.taskType);
                updatedTask = createNewTask(selectedTask, type);
            } else {
                updatedTask = updateExistingTask(selectedTask);
            }

            // Replaces currently selected task with newly created updatedTask
            assert this.index >= 0 : "Index should be within range in this point";
            tasks.setIndexAt(updatedTask, this.index);

            //Display message
            assert updatedTask != null : "updatedTask should not be null at this point";
            ui.showMessage(
                    INTRO1 + selectedTask,
                    INTRO2 + updatedTask
            );

        } catch (BobInvalidFormatException | BobDateTimeException | BobException e) {
            // Basically only for not found
            ui.showMessage(e.getMessage());
        }

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
            return null;
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
        boolean isDescriptionNull = this.description == null;
        boolean isByNull = this.by == null;
        boolean isFromNull = this.from == null;
        boolean isToNull = this.to == null;

        String newDesc = isDescriptionNull ? task.getDescription() : this.description;

        switch (task.getType()) {
        case TODO: {
            if (isDescriptionNull) {
                // Use correct exception
                throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
            }
            return new ToDoTask(newDesc);
        }
        case DEADLINE: {
            if (isDescriptionNull && isByNull) {
                // Use correct exception
                throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
            }

            // Safe cast since we already know its an event type
            DeadlineTask deadlineTask = (DeadlineTask) task;
            String newBy = isByNull ? deadlineTask.getBy() : this.by;
            return new DeadlineTask(newDesc, newBy);
        }
        case EVENT: {
            if (isDescriptionNull && isFromNull && isToNull) {
                // Use correct exception
                throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
            }

            // Safe cast since we already know it's an event type
            EventTask eventTask = (EventTask) task;
            String newFrom = isFromNull ? eventTask.getFrom() : this.from;
            String newTo = isToNull ? eventTask.getTo() : this.to;
            return new EventTask(newDesc, newFrom, newTo);
        }
        default: {
            // won't reach this point as task is always one of the above types
            return null;
        }
        }
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
