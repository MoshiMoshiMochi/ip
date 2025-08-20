import java.io.IOException;
import java.util.Scanner;

public class Bob {
    private Storage storage;
    private TaskList taskList;

    public Bob(String filePath){
        storage = new Storage(filePath);
        taskList = storage.load();
    }

    public void run(){
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


        String addIntro = " Aite. I've bobbed it into the list:";
        String removeIntro = "  BOB!!! I've removed the task:";

        while (true) {
            String s = echo.nextLine();
            String[] parts = s.split(" ", 2); //1. Command 2. Description ....
            CommandType commandType = CommandType.fromString(parts[0]);

            try {
                switch (commandType) {
                    case BYE: {
                        System.out.println(line);
                        System.out.println(" Bye have a great time!");
                        System.out.println(line);
                        return;
                    }
                    case LIST: {
                        System.out.println(line);
                        System.out.println(taskList);
                        System.out.println(line);
                        break;
                    }
                    case MARK: {
                        int index = Integer.parseInt(parts[1]) - 1;
                        Task task = taskList.getTask(index);
                        task.markDone();
                        System.out.println(line);
                        System.out.println(" I'm Marking it. I'm Marking it so good!");
                        System.out.println("    " + task);
                        System.out.println(line);
                        break;
                    }
                    case UNMARK: {
                        int index = Integer.parseInt(parts[1]) - 1;
                        Task task = taskList.getTask(index);
                        task.markUnDone();
                        System.out.println(line);
                        System.out.println(" You need to BOB mark! BOB for Viltrum!");
                        System.out.println("    " + task);
                        System.out.println(line);
                        break;
                    }
                    case TODO: {
                        if (parts.length < 2) {
                            throw new BobException(" WHAT THE BOB!!! Invalid format");
                        }
                        if (parts.length < 2 || parts[1].trim().isEmpty()) {
                            throw new BobException(" WHAT THE BOB!!! My Bobther in Christ, you need a description for todo task.\n " +
                                    "E.g. todo <description>");
                        }
                        Task task = new ToDoTask(parts[1].trim());
                        taskList.addTask(task);
                        printAction(line, task, taskList.size(), addIntro);
                        break;
                    }
                    case DEADLINE: {
                        if (parts.length < 2) {
                            throw new BobException(" WHAT THE BOB!!! Invalid format");
                        }
                        String[] deadlineParts = parts[1].split("/by", 2);
                        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
                            throw new BobException(" WHAT THE BOB!!! My Bobther in Christ, you need a description and due date for deadline task.\n " +
                                    "E.g. deadline <description> /by <due date>");
                        }
                        Task task = new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim());
                        taskList.addTask(task);
                        printAction(line, task, taskList.size(), addIntro);
                        break;
                    }
                    case EVENT: {
                        if (parts.length < 2) {
                            throw new BobException(" WHAT THE BOB!!! Invalid format");
                        }
                        String[] eventParts = parts[1].split("/from|/to");
                        if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
                            throw new BobException(" WHAT THE BOB!!! My Bobther in Christ, you need a description, from and to date for event task.\n E.g. event <description> /from <from date> /to <to date>");
                        }
                        Task task = new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                        taskList.addTask(task);
                        printAction(line, task, taskList.size(), addIntro);
                        break;
                    }
                    case DELETE: {
                        if (parts.length < 2) {
                            throw new BobException(" WHAT THE BOB!!! No task index given for delete.");
                        }
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
                        break;
                    }

                    default: {
                        throw new BobException(" WHAT THE BOB!!! You just used an unrecognised command!");
                    }
                }
                storage.save(taskList);
            } catch (BobException e) {
                System.out.println(line);
                System.out.println(e.getMessage());
                System.out.println(line);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println(line);
                System.out.println(" WHAT THE BOB!!! Invalid Task Number!");
                System.out.println(line);
            }
        }
    }

    public static void main(String[] args) {
        new Bob("./savedtasks/task.txt").run();
    }

    private static void printAction(String line, Task task, int count, String intro) {
        System.out.println(line);
        System.out.println(intro);
        System.out.println("   " + task);
        System.out.println(" Bobbing heck! You now have " + count + " tasks in the list.");
        System.out.println(line);
    }
}
