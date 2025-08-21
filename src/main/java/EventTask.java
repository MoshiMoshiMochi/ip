import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventTask extends Task {
    private LocalDate to;
    private LocalDate from;

    public EventTask(String description, String from, String to) throws BobException{
        super(description, TaskType.EVENT);
        try{
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.from = LocalDate.parse(from, inputFormat);
            this.to = LocalDate.parse(to, inputFormat);
        }catch (DateTimeParseException e){
            throw new BobException("Please use yyyy-MM-dd HHmm (e.g., 2019-12-02 1800).");
        }
    }

    @Override
    public String toSaveFormat(){
        return TaskType.EVENT.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + from + " | " + to;
    }

//    public static EventTask fromSaveFormat(boolean isDone, String desc, String from, String to) throws BobException{
//        try{
//            EventTask task = new EventTask(desc, from, to);
//            if (isDone) {
//                task.markDone();
//            }
//            return task;
//        }catch (BobException e){
//            throw e;
//        }
//    }

    @Override
    public String toString(){
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return super.toString() + "(from: " + this.from.format(outputFormat) + " to: " + this.to.format(outputFormat) + ")";
    }
}
