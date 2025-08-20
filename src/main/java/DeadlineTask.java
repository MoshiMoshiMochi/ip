public class DeadlineTask extends Task{
    private String by;

    public DeadlineTask(String description, String by){
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    @Override
    public String toSaveFormat(){
        return TaskType.DEADLINE.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + this.by + " | ";
    }

    @Override
    public String toString(){
        return super.toString() + " (by:" + this.by + ")";
    }
}
