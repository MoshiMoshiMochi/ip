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
        System.out.println(" Hello from\n" + logo);
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
                        printAction(line, " Bye have a great time!");
                        return;
                    }
                    case LIST: {
                        printAction(line, taskList.toString());
                        break;
                    }
                    case MARK: {
                        int index = Integer.parseInt(parts[1]) - 1;
                        Task task = taskList.getTask(index);
                        task.markDone();
                        printAction(line,
                                " I'm Marking it. I'm Marking it so good!",
                                "    " + task);
                        break;
                    }
                    case UNMARK: {
                        int index = Integer.parseInt(parts[1]) - 1;
                        Task task = taskList.getTask(index);
                        task.markUnDone();
                        printAction(line,
                                " You need to BOB mark! BOB for Viltrum!",
                                "    " + task);
                        break;
                    }
                    case TODO: {
                        if (parts.length < 2 || parts[1].trim().isEmpty()) {
                            throw new BobInvalidFormatException(CommandFormat.TODO);
                        }

                        Task task = new ToDoTask(parts[1].trim());
                        taskList.addTask(task);
                        printAction(line, task, taskList.size(), addIntro);
                        break;
                    }
                    case DEADLINE: {
                        if (parts.length < 2) {
                            throw new BobInvalidFormatException(CommandFormat.DEADLINE);
                        }
                        String[] deadlineParts = parts[1].split("/by", 2);
                        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
                            throw new BobInvalidFormatException(CommandFormat.DEADLINE);
                        }

                        Task task = new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim());
                        taskList.addTask(task);
                        printAction(line, task, taskList.size(), addIntro);

                        break;
                    }
                    case EVENT: {
                        if (parts.length < 2) {
                            throw new BobInvalidFormatException(CommandFormat.EVENT);
                        }
                        String[] eventParts = parts[1].split("/from|/to");
                        if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
                            throw new BobInvalidFormatException(CommandFormat.EVENT);
                        }

                        EventTask task = new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                        taskList.addTask(task);
                        printAction(line, task, taskList.size(), addIntro);

                        break;
                    }
                    case DELETE: {
                        if (parts.length < 2) {
                            throw new BobInvalidFormatException(CommandFormat.DELETE);
                        }
                        int index = Integer.parseInt(s.split(" ")[1]) - 1;
                        Task removedTask = taskList.deleteTask(index);
                        printAction(line, removedTask, taskList.size(), removeIntro);
                        break;
                    }

                    default: {
                        throw new BobException(" You just used an unrecognised command!");
                    }
                }
                storage.save(taskList);
            } catch (BobDateTimeException | BobInvalidFormatException | BobException e){
                printAction(line, e.getMessage());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                printAction(line, " WHAT THE BOB!!!\n Invalid Task Number!");
            }
        }
    }

    public static void main(String[] args) {
        new Bob("./savedtasks/task.txt").run();
    }

    private static void printAction(String line, String... messages) {
        System.out.println(line);
        for (String msg : messages) {
            System.out.println(msg);
        }
        System.out.println(line);
    }


    private static void printAction(String line, Task task, int count, String intro) {
        printAction(line,
                intro,
                "   " + task,
                " Bobbing heck! You now have " + count + " tasks in the list.");
    }
}
