import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> tasks;

    public  TaskList(){
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public Task getTask(int index){
        return tasks.get(index);
    }

    public int size(){
        return this.tasks.size();
    }

    public Task deleteTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException(" Task number " + (index + 1) + " does not exist!");
        }
        return tasks.remove(index);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(" The list do be Bobbing my dude!\n");
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
