public class Task {
    private String description;
    private boolean isDone;
    private TaskType type;

    public Task(String description, TaskType type){
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public void markDone(){
        this.isDone = true;
    }

    public void markUnDone(){
        this.isDone = false;
    }

    @Override
    public String toString(){
        String check = this.isDone ? "X" : " ";
        return "[" + type.getSymbol() + "]"+ "[" + check + "] " + this.description;
    }
}
