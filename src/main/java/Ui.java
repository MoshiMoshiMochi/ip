import java.util.Scanner;

public class Ui {
    private final Scanner scanner;
    private static final String addIntro = " Aite. I've bobbed it into the list:";
    private static final String removeIntro = "  BOB!!! I've removed the task:";

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String logo =
                "  ____    _____   ____  \n" +
                        " | __ )  |  _  | | __ ) \n" +
                        " |  _ \\  | | | | |  _ \\ \n" +
                        " | |_) | | |_| | | |_) |\n" +
                        " |____/  |_____| |____/ \n";
        showLine();
        System.out.println(" Hello from\n" + logo);
        showLine();
        System.out.println(" Hows it bobbing dude?! I'm Bob");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showMessage(String... messages) {
        showLine();
        for (String msg : messages) {
            System.out.println(msg);
        }
        showLine();
    }

    public void prepareMessage(CommandType type, Task task, int count){
        String intro;
        switch (type){
            case DELETE:{
                intro = removeIntro;
                break;
            }
            default: {
                intro = addIntro;
            }
        }
        showMessage(
                intro,
                "   " + task,
                " Bobbing heck! You now have " + count + " tasks in the list."
        );
    }


}
