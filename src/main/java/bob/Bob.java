package bob;

import bob.command.Command;
import bob.exception.BobDateTimeException;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents the main Bob application.
 * Handles initialization of storage, task list, and UI,
 * and provides the main loop to process user commands.
 */
public class Bob {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private String commandType;

    /**
     * Constructs a <code>Bob</code> instance with the specified file path for storage.
     *
     * @param filePath The path to the file where tasks are saved and loaded.
     */
    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.load();
    }

    /**
     * Runs the main application loop, reading user commands,
     * executing them, and handling exceptions until exit.
     */
    public void run() {
        boolean isExit = false;

        while (!isExit) {
            try {
                String command = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(command);
                c.execute(taskList, ui, storage);
                isExit = c.isExit();
            } catch (BobInvalidFormatException | BobDateTimeException | BobException e) {
                ui.showMessage(e.getMessage());
            }
        }
    }

    /**
     * Processes a single user input and returns Bob's response (for GUI).
     *
     * @param input User command string
     * @return Response message from Bob
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            commandType = c.getClass().getSimpleName();
            return c.executeAndReturn(taskList, storage);
        } catch (BobInvalidFormatException | BobDateTimeException | BobException e) {
            return e.getMessage();
        }
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * Used to display intro message (for GUI)
     *
     * @return
     */
    public String getIntro() {
        ui.showWelcome();
        return ui.getCollectedMessages();
    }

    /**
     * Entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Bob("../savedtasks/task.txt").run();
    }

}
