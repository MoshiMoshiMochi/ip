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
public class FindCommand implements Command {
    private String description;

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
     * @param tasks     List of tasks to search within.
     * @param ui        Ui instance for displaying messages.
     * @param storage   The storage system (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchingTasks = new TaskList();
        for (int i = 0; i < tasks.size(); i++) {
            try {
                Task task = tasks.getTask(i);
                if (task.getDescription().contains(this.description)) {
                    matchingTasks.addTask(task);
                }
            } catch (BobException e) {
                //Will never reach this point because i will always be in the bounds of tasks
            }
        }

        if (matchingTasks.size() == 0) {
            ui.showMessage(
                    " NOOO BOB! No Bobbing tasks within the list\n matches the description for: " +
                    this.description + "\n\n Maybe trying another Bobbing description!");
        } else {
            String intro =
                    " BOB YEA! Here is/are the " +
                    matchingTasks.size() +
                    " task(s)\n based on the given description: " +
                    this.description;
            String outro =
                    "\n I've been a good BOB";
            ui.showMessage(
                    intro,
                    matchingTasks.toString(),
                    outro
            );
        }

    }

    /**
     * Indicates whether this command causes the program to exit.
     *
     * @return {@code false}, since the find command does not exit the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
