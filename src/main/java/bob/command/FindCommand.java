package bob.command;

import bob.exception.BobException;
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

    private static final String INTRO_SUCCESS_1 = " BOB YEA! Here is/are the ";
    private static final String INTRO_SUCCESS_2 = " task(s)\n based on the given description: ";
    private static final String OUTRO_SUCCESS = "\n I've been a good BOB";

    private static final String INTRO_FAILURE = " NOOO BOB! No Bobbing tasks within the list"
            + "\n matches the description for: ";
    private static final String OUTRO_FAILURE = "\n Maybe trying another Bobbing description!";

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

        if (matchingTasks.size() == 0) {
            ui.showMessage(
                    INTRO_FAILURE + this.description,
                    OUTRO_FAILURE);
        } else {
            ui.showMessage(
                    INTRO_SUCCESS_1 + matchingTasks.size() + INTRO_SUCCESS_2 + this.description,
                    matchingTasks.toString(),
                    OUTRO_SUCCESS
            );
        }

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
