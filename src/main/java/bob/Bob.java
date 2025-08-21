package bob;

import bob.command.Command;
import bob.exception.BobDateTimeException;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

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
            }catch (BobInvalidFormatException | BobDateTimeException | BobException e){
                ui.showMessage(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Bob("../savedtasks/task.txt").run();
    }

}