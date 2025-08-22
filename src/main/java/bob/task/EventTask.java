package bob.task;

import bob.command.CommandFormat;
import bob.exception.BobDateTimeException;
import bob.exception.BobInvalidFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task in the Bob task manager.
 * An <code>EventTask</code> has a description, a start datetime (<code>from</code>)
 * and an end datetime (<code>to</code>), and can be marked done or undone.
 */
public class EventTask extends Task {
    private LocalDateTime to;
    private LocalDateTime from;

    /**
     * Constructs a new <code>EventTask</code> with a description, start datetime, and end datetime.
     *
     * @param description Description of the event task.
     * @param from Start datetime in "yyyy-MM-dd HHmm" format.
     * @param to End datetime in "yyyy-MM-dd HHmm" format; must not be before <code>from</code>.
     * @throws BobDateTimeException if <code>to</code> is before <code>from</code>.
     * @throws BobInvalidFormatException if the datetime strings cannot be parsed.
     */
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

    /**
     * Returns a string representation of this task suitable for saving to a file.
     *
     * @return String in the save format for <code>EventTask</code>.
     */
    @Override
    public String toSaveFormat() {
        return TaskType.EVENT.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + from + " | " + to;
    }

    /**
     * Returns a human-readable string representation of this event task,
     * including start and end datetimes formatted as "MMM dd yyyy HHmm".
     *
     * @return String representation of the event task.
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return super.toString() + " (from: " + this.from.format(outputFormat) + " to: " + this.to.format(outputFormat) + ")";
    }
}
