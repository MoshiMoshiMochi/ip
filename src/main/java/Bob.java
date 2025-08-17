import java.util.Scanner;

public class Bob {
    public static void main(String[] args) throws BobException {
        String logo =
                "  ____    _____   ____  \n" +
                " | __ )  |  _  | | __ ) \n" +
                " |  _ \\  | | | | |  _ \\ \n" +
                " | |_) | | |_| | | |_) |\n" +
                " |____/  |_____| |____/ \n";
        String line =
                "____________________________________________________________";
        System.out.println(" Hello from" + logo);
        System.out.println(line);
        System.out.println(" Hows it bobbing dude?! I'm Bob");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        Scanner echo = new Scanner(System.in);
        TaskList taskList = new TaskList(100);

        String addIntro = " Aite. I've bobbed it into the list:";
        String removeIntro = "  BOB!!! I've removed the task:";

        while (true) {
            String s = echo.nextLine();
            try {
                if (s.toUpperCase().equals("BYE")) {
                    break;
                } else if (s.toUpperCase().equals("LIST")) {
                    System.out.println(line);
                    System.out.println(taskList);
                    System.out.println(line);

                } else if (s.toUpperCase().startsWith("MARK ")) {
                    int index = Integer.parseInt(s.split(" ")[1]) - 1;
                    Task task = taskList.getTask(index);
                    task.markDone();
                    System.out.println(line);
                    System.out.println(" I'm Marking it. I'm Marking it so good!");
                    System.out.println("    " + task);
                    System.out.println(line);

                } else if (s.toUpperCase().startsWith("UNMARK ")) {
                    int index = Integer.parseInt(s.split(" ")[1]) - 1;
                    Task task = taskList.getTask(index);
                    task.markUnDone();
                    System.out.println(line);
                    System.out.println(" You need to BOB mark! BOB for Viltrum!");
                    System.out.println("    " + task);
                    System.out.println(line);

                } else if (s.toUpperCase().startsWith("TODO ")) {
                    String description = s.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new BobException(" WHAT THE BOB!!! My Bobther in Christ, you need a description for todo task.\n E.g. todo <description>");
                    }
                    Task task = new ToDoTask(description);
                    taskList.addTask(task);
                    printAction(line, task, taskList.size(), addIntro);

                } else if (s.toUpperCase().startsWith("DEADLINE ")) {
                    // format: deadline return book /by Sunday
                    String[] parts = s.substring(9).split("/by", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new BobException(" WHAT THE BOB!!! My Bobther in Christ, you need a description and due date for deadline task.\n E.g. deadline <description> /by <due date>");
                    }
                    String desc = parts[0].trim();
                    String by = parts[1].trim();
                    Task task = new DeadlineTask(desc, by);
                    taskList.addTask(task);
                    printAction(line, task, taskList.size(), addIntro);

                } else if (s.toUpperCase().startsWith("EVENT ")) {
                    String[] parts = s.substring(6).split("/from|/to");
                    if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                        throw new BobException(" WHAT THE BOB!!! My Bobther in Christ, you need a description, from and to date for event task.\n E.g. event <description> /from <from date> /to <to date>");
                    }
                    String desc = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    Task task = new EventTask(desc, from, to);
                    taskList.addTask(task);
                    printAction(line, task, taskList.size(), addIntro);
                } else if (s.toUpperCase().startsWith("DELETE ")) {
                    try {
                        int index = Integer.parseInt(s.split(" ")[1]) - 1;
                        Task removedTask = taskList.deleteTask(index);
                        printAction(line, removedTask, taskList.size(), removeIntro);
                    } catch (NumberFormatException e) {
                        System.out.println(line);
                        System.out.println(" WHAT THE BOB!!! Invalid task number!");
                        System.out.println(line);
                    } catch (BobException e) {
                        System.out.println(line);
                        System.out.println(e.getMessage());
                        System.out.println(line);
                    }
                } else {
                    throw new BobException(" WHAT THE BOB!!! You just used an unrecognised command!");
                }
            } catch (BobException e){
                System.out.println(line);
                System.out.println(e.getMessage());
                System.out.println(line);
            } catch (NumberFormatException | IndexOutOfBoundsException e){
                System.out.println(line);
                System.out.println(" WHAT THE BOB!!! Invalid Task Number!");
                System.out.println(line);
            }
        }
        System.out.println(line);
        System.out.println(" Bye have a great time!");
        System.out.println(line);
    }

    private static void printAction(String line, Task task, int count, String intro) {
        System.out.println(line);
        System.out.println(intro);
        System.out.println("   " + task);
        System.out.println(" Bobbing heck! You now have " + count + " tasks in the list.");
        System.out.println(line);
    }
}
