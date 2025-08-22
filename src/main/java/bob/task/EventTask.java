package bob.task;

import bob.command.CommandFormat;
import bob.exception.BobDateTimeException;
import bob.exception.BobInvalidFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventTask extends Task {
    private LocalDateTime to;
    private LocalDateTime from;

    public EventTask(String description, String from, String to) throws BobDateTimeException, BobInvalidFormatException {
        super(description, TaskType.EVENT);
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            this.from = LocalDateTime.parse(from, inputFormat);
            this.to = LocalDateTime.parse(to, inputFormat);
            if (this.to.isBefore(this.from)) {
                throw new BobDateTimeException("To Date needs to be larger than From Date");
            }
        } catch (DateTimeParseException e) {
            throw new BobInvalidFormatException(CommandFormat.DATETIMEFORMAT);
        }
    }

    @Override
    public String toSaveFormat() {
        return TaskType.EVENT.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return super.toString() + " (from: " + this.from.format(outputFormat) + " to: " + this.to.format(outputFormat) + ")";
    }
}
