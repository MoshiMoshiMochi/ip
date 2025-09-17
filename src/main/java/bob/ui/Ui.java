package bob.ui;

import bob.command.CommandType;
import bob.personality.Personality;
import bob.task.Task;

/**
 * Represents the Ui of the application.
 * Responsible for displaying messages, reading user input and formatting outputs
 */

public class Ui {
    private final StringBuilder buffer; // collect messages for GUI

    /**
     * Constructs a new <code>Ui</code> instance with a scanner for user input.
     */
    public Ui() {
        buffer = new StringBuilder();
    }

    /**
     * Displays a welcome message including the Bob ASCII logo.
     * BOB BOB BOB!!!!!!!!!
     */
    public void showWelcome() {
        showMessage(
                Personality.GREETING1.getMessage(),
                Personality.GREETING2.getMessage()
        );
    }

    /**
     * Displays a horizontal line to the console for formatting purposes.
     */
    public void showLine() {
        System.out.println(Personality.LINE.getMessage());
    }

    /**
     * Displays one or more messages to the console, separated by a line.
     *
     * @param messages One or more messages to display.
     */
    public void showMessage(String... messages) {
        for (String msg : messages) {
            System.out.println(msg);
            buffer.append(msg).append("\n");
        }
        showLine();
    }

    /**
     * Prepares and displays a message after adding or deleting a task.
     *
     * @param type  The type of command (e.g., ADD or DELETE).
     * @param task  The task that was added or deleted.
     * @param count The current number of tasks in the list.
     */
    public void prepareMessage(CommandType type, Task task, int count) {
        String intro;
        switch (type) {
        case DELETE: {
            intro = Personality.REMOVEINTRO.getMessage();
            break;
        }
        default: {
            intro = Personality.ADDINTRO.getMessage();
        }
        }
        showMessage(
                intro,
                Personality.TAB.getMessage() + task,
                Personality.ADDDELETEOUTRO_1.getMessage() + count + Personality.ADDDELETEOUTRO_2.getMessage()
        );
    }

    /**
     * Returns collected messages for GUI (and clears buffer).
     */
    public String getCollectedMessages() {
        String result = buffer.toString().trim();
        buffer.setLength(0); // clear
        return result;
    }


}
