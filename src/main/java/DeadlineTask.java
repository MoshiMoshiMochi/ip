import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task{
    private LocalDate by;

    public DeadlineTask(String description, String by) throws BobException{
        super(description, TaskType.DEADLINE);
        try{
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.by = LocalDate.parse(by, inputFormat);
        }catch (DateTimeParseException e){
            throw new BobException("Please use yyyy-MM-dd HHmm (e.g., 2019-12-02 1800).");
        }
    }

    @Override
    public String toSaveFormat(){
        return TaskType.DEADLINE.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + this.by + " | ";
    }

//    public static DeadlineTask fromSaveFormat(boolean isDone, String desc, String by) throws BobException{
//        // parse line back to DeadlineTask
//        try{
//            DeadlineTask task = new DeadlineTask(desc, by);
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
        return super.toString() + " (by:" + this.by.format(outputFormat) + ")";
    }
}
