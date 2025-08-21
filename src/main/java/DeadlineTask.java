import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task{
    private LocalDateTime by;
    private final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public DeadlineTask(String description, String by) throws BobInvalidFormatException{
        super(description, TaskType.DEADLINE);
        try{
            this.by = LocalDateTime.parse(by, inputFormat);
        }catch (DateTimeParseException e){
            throw new BobInvalidFormatException(CommandFormat.DATETIMEFORMAT);
        }
    }

    @Override
    public String toSaveFormat(){
        return TaskType.DEADLINE.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + this.by.format(inputFormat) + " | ";
    }

    @Override
    public String toString(){
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return super.toString() + " (by:" + this.by.format(outputFormat) + ")";
    }
}
