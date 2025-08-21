import java.io.IOException;
import java.util.Scanner;

public class Bob {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.load();
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String command = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(command);
                c.execute(taskList, ui, storage);
                isExit = c.isExit();
            }catch (BobInvalidFormatException| BobDateTimeException |BobException e){
                ui.showMessage(e.getMessage());
            }

//            String s = echo.nextLine();
//            String[] parts = s.split(" ", 2); //1. Command 2. Description ....
//            CommandType commandType = CommandType.fromString(parts[0]);
//
//            try {
//                switch (commandType) {
//                    case BYE: {
////                        printAction(line, " Bye have a great time!");
//                        ui.showMessage(" Bye have a great time!");
//                        return;
//                    }
//                    case LIST: {
////                        printAction(line, taskList.toString());
//                        ui.showMessage(taskList.toString());
//                        break;
//                    }
//                    case MARK: {
//                        int index = Integer.parseInt(parts[1]) - 1;
//                        Task task = taskList.getTask(index);
//                        task.markDone();
////                        printAction(line,
////                                " I'm Marking it. I'm Marking it so good!",
////                                "    " + task);
//                        ui.showMessage(
//                                " I'm Marking it. I'm Marking it so good!",
//                                "    " + task
//                        );
//                        break;
//                    }
//                    case UNMARK: {
//                        int index = Integer.parseInt(parts[1]) - 1;
//                        Task task = taskList.getTask(index);
//                        task.markUnDone();
////                        printAction(line,
////                                " You need to BOB mark! BOB for Viltrum!",
////                                "    " + task);
//                        ui.showMessage(
//                                " You need to BOB mark! BOB for Viltrum!",
//                                "    " + task
//                        );
//                        break;
//                    }
//                    case TODO: {
//                        if (parts.length < 2 || parts[1].trim().isEmpty()) {
//                            throw new BobInvalidFormatException(CommandFormat.TODO);
//                        }
//
//                        Task task = new ToDoTask(parts[1].trim());
//                        taskList.addTask(task);
//                        //printAction(line, task, taskList.size(), addIntro);
//                        ui.prepareMessage(
//                                CommandType.TODO,
//                                task,
//                                taskList.size()
//                        );
//                        break;
//                    }
//                    case DEADLINE: {
//                        if (parts.length < 2) {
//                            throw new BobInvalidFormatException(CommandFormat.DEADLINE);
//                        }
//                        String[] deadlineParts = parts[1].split("/by", 2);
//                        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
//                            throw new BobInvalidFormatException(CommandFormat.DEADLINE);
//                        }
//
//                        Task task = new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim());
//                        taskList.addTask(task);
//                        //printAction(line, task, taskList.size(), addIntro);
//                        ui.prepareMessage(
//                                CommandType.DEADLINE,
//                                task,
//                                taskList.size()
//                        );
//                        break;
//                    }
//                    case EVENT: {
//                        if (parts.length < 2) {
//                            throw new BobInvalidFormatException(CommandFormat.EVENT);
//                        }
//                        String[] eventParts = parts[1].split("/from|/to");
//                        if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
//                            throw new BobInvalidFormatException(CommandFormat.EVENT);
//                        }
//
//                        EventTask task = new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
//                        taskList.addTask(task);
//                        //printAction(line, task, taskList.size(), addIntro);
//                        ui.prepareMessage(
//                                CommandType.EVENT,
//                                task,
//                                taskList.size()
//                        );
//                        break;
//                    }
//                    case DELETE: {
//                        if (parts.length < 2) {
//                            throw new BobInvalidFormatException(CommandFormat.DELETE);
//                        }
//                        int index = Integer.parseInt(s.split(" ")[1]) - 1;
//                        Task removedTask = taskList.deleteTask(index);
//                        //printAction(line, removedTask, taskList.size(), removeIntro);
//                        ui.prepareMessage(
//                                CommandType.DELETE,
//                                removedTask,
//                                taskList.size()
//                        );
//                        break;
//                    }
//
//                    default: {
//                        throw new BobException(" You just used an unrecognised command!");
//                    }
//                }
//                storage.save(taskList);
//            } catch (BobDateTimeException | BobInvalidFormatException | BobException e) {
//                ui.showMessage(e.getMessage());
//            } catch (NumberFormatException | IndexOutOfBoundsException e) {
//                ui.showMessage(" WHAT THE BOB!!!\n Invalid Task Number!");
//            }
        }
    }

    public static void main(String[] args) {
        new Bob("./savedtasks/task.txt").run();
    }

}