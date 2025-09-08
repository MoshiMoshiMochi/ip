package bob.command;

import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.DeadlineTask;
import bob.task.EventTask;
import bob.task.Task;
import bob.task.TaskList;
import bob.task.ToDoTask;
import bob.ui.Ui;

public class UpdateCommand extends Command {
    private final int index;
    private final String taskType;
    private final String description;
    private final String by;
    private final String from;
    private final String to;

    public UpdateCommand(int index, String taskType, String description, String by, String from, String to) {
        this.index = index;
        this.taskType = taskType;
        this.description = description;
        this.by = by;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("--------------------------------------");
        System.out.println("tasktype: " + taskType);
        System.out.println("description: " + description);
        System.out.println("by: " + by);
        System.out.println("fr: " + from);
        System.out.println("to: " + to);

        try {
            Task selectedTask = tasks.getTask(this.index);
            boolean isTaskTypeNull = this.taskType == null;

            Task updatedTask;
            // If change of task type
            if (this.taskType != null) {
                CommandType type = CommandType.fromString(this.taskType);
                updatedTask = createNewTask(selectedTask, type);
            } else {
                updatedTask = updateExistingTask(selectedTask);
            }
            assert this.index >= 0 : "Index should be within range in this point";
            tasks.setIndex(updatedTask, this.index);

            ui.showMessage("Pass update");

        } catch (BobException e) {
            // Basically only for not found
            ui.showMessage(e.getMessage());
        }

    }

    private Task createNewTask(Task task, CommandType type) {
        String newDesc = this.description != null ? this.description :  task.getDescription();
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

    private Task updateExistingTask(Task task) {
        boolean isDescriptionNull = this.description == null;
        boolean isByNull = this.by == null;
        boolean isFromNull = this.from == null;
        boolean isToNull = this.to == null;

        String newDesc = isDescriptionNull ? task.getDescription() : this.description;

        System.out.println("new description: " + this.description);
        System.out.println("by: "+this.by);
        System.out.println("from: "+this.from);
        System.out.println("to: "+this.to);

        switch (task.getType()) {
        case TODO: {
            if (isDescriptionNull) {
                // Use correct exception
                throw new BobInvalidFormatException(CommandFormat.FIND);
            }
            return new ToDoTask(newDesc);
        }
        case DEADLINE: {
            if (isDescriptionNull && isByNull) {
                // Use correct exception
                throw new BobInvalidFormatException(CommandFormat.FIND);
            }

            // Safe cast since we already know its an event type
            DeadlineTask deadlineTask = (DeadlineTask) task;
            String newBy = isByNull ? deadlineTask.getBy() : this.by;
            return new DeadlineTask(newDesc, newBy);
        }
        case EVENT: {
            if (isDescriptionNull && isFromNull  && isToNull) {
                // Use correct exception
                throw new BobInvalidFormatException(CommandFormat.FIND);
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

    @Override
    public boolean isExit() {
        return false;
    }
}
