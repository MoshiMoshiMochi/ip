package bob.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bob.command.CommandFormat;
import bob.exception.BobInvalidFormatException;

/**
 * Represents a deadline task in the Bob task manager.
 * A <code>DeadlineTask</code> has a description and a due datetime (<code>by</code>),
 * and can be marked done or undone.
 */
public class DeadlineTask extends Task {
    private LocalDateTime by;
    private final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Constructs a new <code>DeadlineTask</code> with a description and due datetime.
     *
     * @param description Description of the deadline task.
     * @param by Due datetime in "yyyy-MM-dd HHmm" format.
     * @throws BobInvalidFormatException if the datetime string cannot be parsed.
     */
    public DeadlineTask(String description, String by) throws BobInvalidFormatException {
        super(description, TaskType.DEADLINE);
        try {
            this.by = LocalDateTime.parse(by, inputFormat);
        } catch (DateTimeParseException e) {
            throw new BobInvalidFormatException(CommandFormat.DATETIMEFORMAT);
        }
    }

    /**
     * Returns a string representation of this task suitable for saving to a file.
     *
     * @return String in the save format for <code>DeadlineTask</code>.
     */
    @Override
    public String toSaveFormat() {
        return TaskType.DEADLINE.getSymbol()
                + " | "
                + (this.isDone ? "1" : "0")
                + " | "
                + this.description
                + " | "
                + this.by.format(inputFormat)
                + " | ";
    }

    /**
     * Returns a string representation of <code>by</code>
     *
     * @return String in the input format for <code>by</code>.
     */
    public String getBy() {
        return this.by.format(inputFormat);
    }

    /**
     * Returns a human-readable string representation of this deadline task,
     * including the due datetime formatted as "MMM dd yyyy HHmm".
     *
     * @return String representation of the deadline task.
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return super.toString() + " (by:" + this.by.format(outputFormat) + ")";
    }
}
