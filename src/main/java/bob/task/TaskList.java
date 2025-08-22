package bob.task;

import bob.exception.BobException;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public  TaskList(){
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public Task getTask(int index) throws BobException{
        if (!indexInRange(index)) {
            throw new BobException(" Task number " + (index + 1) + " does not exist!");
        }
        return tasks.get(index);
    }

    public int size(){
        return this.tasks.size();
    }

    public Task deleteTask(int index) throws BobException {
        if (!indexInRange(index)) {
            throw new BobException(" Task number " + (index + 1) + " does not exist!");
        }
        return tasks.remove(index);
    }

    public boolean indexInRange(int index) {
        return index >= 0 && index < tasks.size();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< tasks.size(); i++){
            //appends each task added the list
            sb.append(" ").append(i+1).append(".").append(tasks.get(i));
            if (i!=tasks.size()-1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
