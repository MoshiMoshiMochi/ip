public class EventTask extends Task {
    private String to;
    private String from;

    public EventTask(String description, String from, String to){
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString(){
        return "[E]" + super.toString() + "(from: " + this.from + " to: " + this.to + ")";
    }
}
