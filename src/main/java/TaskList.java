public class TaskList {
    private String[] tasks;
    private int taskCount;

    public TaskList(int size){
        tasks = new String[size];
        taskCount = 0;
    }

    public void addTask(String task){
        if (taskCount<100){
            tasks[taskCount] = task;
            taskCount++;
            System.out.println(" added: "+task);
        } else {
            System.out.println("Task List full");
        }
    }

    public void listTask(){
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + ". " + tasks[i]);
        }
    }
}
