import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
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
        TaskList taskList = new TaskList(100);

        while (true){
            String s = echo.nextLine();
            if (s.toUpperCase().equals("BYE")) {
                break;
            } else if (s.toUpperCase().equals("LIST")) {
                System.out.println(line);
                System.out.println(taskList);
                System.out.println(line);

            } else if (s.toUpperCase().startsWith("MARK ")){
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
                Task task = new ToDoTask(description);
                taskList.addTask(task);
                printAdded(line, task, taskList.size());

            } else if (s.toUpperCase().startsWith("DEADLINE ")) {
                // format: deadline return book /by Sunday
                String[] parts = s.substring(9).split("/by", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();
                Task task = new DeadlineTask(desc, by);
                taskList.addTask(task);
                printAdded(line, task, taskList.size());

            } else if (s.toUpperCase().startsWith("EVENT ")) {
                String[] parts = s.substring(6).split("/from|/to");
                String desc = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                Task task = new EventTask(desc, from, to);
                taskList.addTask(task);
                printAdded(line, task, taskList.size());
            }
            else {
                Task task = new Task(s);
                taskList.addTask(task);
                printAdded(line, task, taskList.size());
            }
        }
        System.out.println(line);
        System.out.println(" Bye have a great time!");
        System.out.println(line);
    }

    private static void printAdded(String line, Task task, int count) {
        System.out.println(line);
        System.out.println(" Aite. I've bobbed it into the list:");
        System.out.println("   " + task);
        System.out.println("Bobbing heck! You now have " + count + " tasks in the list.");
        System.out.println(line);
    }
}
