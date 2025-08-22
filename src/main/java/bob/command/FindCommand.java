package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

public class FindCommand implements Command {
    private String description;

    public FindCommand(String description) {
        this.description = description;
    }

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

    @Override
    public boolean isExit() {
        return false;
    }
}
