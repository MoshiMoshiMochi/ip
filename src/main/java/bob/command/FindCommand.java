package bob.command;

import bob.personality.Personality;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command that searches for tasks in the task list
 * containing a given keyword in their description.
 * <p>
 * When executed, it filters tasks by iteratign throught the task
 * list and checking if the keyword is present in their description,
 * finally displaying all the tasks that matches the description
 * provided to the user via the {@link Ui}.
 * </p>
 */
public class FindCommand extends Command {
    private final String description;

    /**
     * Constructs a {@code FindCommand} with the specified search keyword.
     *
     * @param description The keyword to search for within task descriptions.
     */
    public FindCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the find command by searching through the given {@link TaskList}
     * for tasks whose descriptions contain the description specified during
     * construction. Displays the results to the user through the {@link Ui}.
     * <p>
     * If no matching tasks is found, a message is shown indicating that no
     * results were found. Otherwise, the list of matching tasks is displayed.
     * </p>
     *
     * @param tasks   List of tasks to search within.
     * @param ui      Ui instance for displaying messages.
     * @param storage The storage system (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchingTasks = getMatchingTasks(tasks);
        displayMatchingTasks(ui, matchingTasks);
    }

    private TaskList getMatchingTasks(TaskList tasks) {
        // Filters tasks whose descriptions contain the specified keyword
        TaskList matchingTasks = new TaskList();
        for (Task task : tasks.asList()) {
            if (task.getDescription().contains(this.description)) {
                matchingTasks.addTask(task);
            }
        }

        return matchingTasks;
    }

    private void displayMatchingTasks(Ui ui, TaskList matchingTasks) {
        if (matchingTasks.size() == 0) {
            ui.showMessage(
                    Personality.FINDINTRO_FAILURE.getMessage() + this.description,
                    Personality.FINDOUTRO_FAILURE.getMessage());
        } else {
            ui.showMessage(
                    Personality.FINDINTRO_SUCCESS_1.getMessage() + matchingTasks.size()
                            + Personality.FINDINTRO_SUCCESS_2.getMessage() + this.description,
                    matchingTasks.toString(),
                    Personality.FINDOUTRO_SUCCESS.getMessage()
            );
        }
    }

    /**
     * @inheritDoc
     *
     * @return {@code false}, since the find command does not exit the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
