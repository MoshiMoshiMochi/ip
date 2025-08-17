public class DeadlineTask extends Task{
    private String by;

    public DeadlineTask(String description, String by){
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    @Override
    public String toString(){
        return super.toString() + " (by:" + this.by + ")";
    }
}
