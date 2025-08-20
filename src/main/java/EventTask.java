public class EventTask extends Task {
    private String to;
    private String from;

    public EventTask(String description, String from, String to){
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveFormat(){
        return TaskType.EVENT.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + from + " | " + to;
    }

    @Override
    public String toString(){
        return super.toString() + "(from: " + this.from + " to: " + this.to + ")";
    }
}
