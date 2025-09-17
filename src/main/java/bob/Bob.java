package bob;

import bob.command.Command;
import bob.exception.BobDateTimeException;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.exception.BobInvalidIndexException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents the main Bob application.
 * Handles initialization of storage, task list, and UI,
 * and provides the main loop to process user commands.
 */
public class Bob {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;
    private String commandType;
    private boolean isExit = false;

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
     * Processes a single user input and returns Bob's response (for GUI).
     *
     * @param input User command string
     * @return Response message from Bob
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            updateBob(c);
            return c.executeAndReturn(this.taskList, this.storage);
        } catch (BobInvalidFormatException | BobDateTimeException | BobInvalidIndexException | BobException e) {
            this.commandType = null; // resets so error label is applied in future uses.
            return e.getMessage();
        }
    }

    private void updateBob(Command command) {
        this.commandType = command.getClass().getSimpleName();
        this.isExit = command.isExit();
    }

    /**
     * Returns the type of the last executed command.
     *
     * @return The command type as a string
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Displays the welcome message and returns it (for GUI).
     *
     * @return Welcome message from Bob
     */
    public String getIntro() {
        this.ui.showWelcome();
        return this.ui.getCollectedMessages();
    }

    /**
     * Checks if Bob is set to exit and initiates a delayed shutdown if so.
     * This allows users to see the exit message before the application closes.
     */
    public void handleExitIfNeeded() {
        if (this.isExit) {
            // to allow user to see the exit message before closing
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.exit(0);
            }).start();
        }
    }

}
