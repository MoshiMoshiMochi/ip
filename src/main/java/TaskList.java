import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(int size){
        this.tasks = new ArrayList<Task>();
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
