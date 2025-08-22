package bob.storage;

import bob.exception.BobException;
import bob.task.Task;
import bob.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles saving and loading of tasks to and from a file.
 * Provides methods to persist a <code>TaskList</code> and retrieve it later.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a <code>Storage</code> object with the specified file path.
     *
     * @param filePath The path of the file to save/load tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves all tasks from the given <code>TaskList</code> into the file.
     * Creates parent directories if they do not exist.
     *
     * @param tasks The <code>TaskList</code> to save.
     */
    public void save(TaskList tasks){
        try{
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // create ./data/ if it doesn’t exist
            }

            FileWriter fw = new FileWriter(file);
            for(int i=0; i < tasks.size(); i++) {
                try{
                    fw.write(tasks.getTask(i).toSaveFormat() + System.lineSeparator());
                } catch (BobException e){
                    //It should never reach here since i will always be within the range of tasks.size
                }
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the file into a <code>TaskList</code>.
     * Returns an empty list if the file does not exist or lines are invalid.
     *
     * @return The <code>TaskList</code> containing tasks loaded from the file.
     */
    public TaskList load() {
        TaskList tasks = new TaskList();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks; // no tasks on first run
        }

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task task = Task.fromSaveFormat(line);
                if (task != null) {
                    tasks.addTask(task);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }


}
