package bob.ui;

import java.util.Scanner;

import bob.command.CommandType;
import bob.task.Task;

/**
 * Represents the Ui of the application.
 * Responsible for displaying messages, reading user input and formatting outputs
 */

public class Ui {
    private static final String ADDINTRO = " Aite. I've bobbed it into the list:";
    private static final String REMOVEINTRO = "  BOB!!! I've removed the task:";
    private final Scanner scanner;

    /**
     * Constructs a new <code>Ui</code> instance with a scanner for user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays a welcome message including the Bob ASCII logo.
     * BOB BOB BOB!!!!!!!!!
     */
    public void showWelcome() {
        String logo = "  ____    _____   ____  \n"
                + " | __ )  |  _  | | __ ) \n"
                + " |  _ \\  | | | | |  _ \\ \n"
                + " | |_) | | |_| | | |_) |\n"
                + " |____/  |_____| |____/ \n";
        //showLine();
        System.out.println(" Hello from\n" + logo);
        showLine();
        System.out.println(" Hows it bobbing dude?! I'm Bob");
        System.out.println(" What can I do for you?");
        showLine();
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The next line of user input.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a horizontal line to the console for formatting purposes.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays one or more messages to the console, separated by a line.
     *
     * @param messages One or more messages to display.
     */
    public void showMessage(String... messages) {
        for (String msg : messages) {
            System.out.println(msg);
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
            intro = REMOVEINTRO;
            break;
        }
        default: {
            intro = ADDINTRO;
        }
        }
        showMessage(
                intro,
                "   " + task,
                " Bobbing heck! You now have " + count + " tasks in the list."
        );
    }


}
