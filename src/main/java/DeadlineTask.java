import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task{
    private LocalDate by;

    public DeadlineTask(String description, String by){
        super(description, TaskType.DEADLINE);
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.by = LocalDate.parse(by, inputFormat);
    }

    @Override
    public String toSaveFormat(){
        return TaskType.DEADLINE.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + this.by + " | ";
    }

    public static DeadlineTask fromSaveFormat(boolean isDone, String desc, String by){
        // parse line back to DeadlineTask
        DeadlineTask task = new DeadlineTask(desc, by);
        if (isDone) {
            task.markDone();
        }
        return task;
    }

    @Override
    public String toString(){
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return super.toString() + " (by:" + this.by.format(outputFormat) + ")";
    }
}
