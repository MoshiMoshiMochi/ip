public class Task {
    private String description;
    private boolean isDone;

    public Task(String description){
        this.description = description;
        this.isDone = false;
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
        return "[" + check + "] " + this.description;
    }
}
