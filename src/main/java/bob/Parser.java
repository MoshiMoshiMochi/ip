package bob;


import bob.command.AddCommand;
import bob.command.ByeCommand;
import bob.command.Command;
import bob.command.CommandFormat;
import bob.command.CommandType;
import bob.command.DeleteCommand;
import bob.command.FindCommand;
import bob.command.ListCommand;
import bob.command.MarkCommand;
import bob.command.UnMarkCommand;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.task.DeadlineTask;
import bob.task.EventTask;
import bob.task.ToDoTask;

/**
 * Parses user input strings into <code>Command</code> objects for execution.
 * Handles validation of input format and throws exceptions for invalid commands.
 */
public class Parser {
    private static CommandType commandType;

    // Exception messages
    private static String INVALID_INDEX_MESSAGE = " Invalid Task number!";
    private static String INVALID_COMMAND_MESSAGE = " Invalid Command!";

    // Add Command delimiters and expected parts
    private static String TODO_DELIMITER = null;
    private static int TODO_EXPECTED = 1;
    private static String DEADLINE_DELIMITER = "/by";
    private static int DEADLINE_EXPECTED = 2;
    private static String EVENT_DELIMITER = "/from|/to";
    private static int EVENT_EXPECTED = 3;

    /**
     * Parses a user input string and returns the corresponding <code>Command</code> object.
     *
     * @param command The raw input string entered by the user.
     * @return The corresponding <code>Command</code> object to execute.
     * @throws BobException              If the command is invalid or contains invalid arguments.
     * @throws BobInvalidFormatException If the command format does not match expected format.
     */
    public static Command parse(String command) throws BobException {
        String[] parts = command.split(" ", 2);
        CommandType type = CommandType.fromString(parts[0]);
        switch (type) {
        case BYE: {
            return new ByeCommand();
        }
        case LIST: {
            return new ListCommand();
        }
        case MARK: {
            String part = parts.length > 1 ? parts[1] : null;
            int markIndex = validateIndex(part, CommandFormat.MARK);
            return new MarkCommand(markIndex);
        }
        case UNMARK: {
            String part = parts.length > 1 ? parts[1] : null;
            int unMarkIndex = validateIndex(part, CommandFormat.UNMARK);
            return new UnMarkCommand(unMarkIndex);
        }
        case TODO: {
            String arg = parts.length > 1 ? parts[1] : null;
            String[] todoParts = validateAdd(
                    arg,
                    TODO_DELIMITER,
                    TODO_EXPECTED,
                    CommandFormat.TODO
            );
            return new AddCommand(
                    new ToDoTask(
                            todoParts[0].trim()
                    )
            );
        }
        case DEADLINE: {
            String arg = parts.length > 1 ? parts[1] : null;
            String[] deadlineParts = validateAdd(
                    arg,
                    DEADLINE_DELIMITER,
                    DEADLINE_EXPECTED,
                    CommandFormat.DEADLINE
            );
            return new AddCommand(
                    new DeadlineTask(
                            deadlineParts[0].trim(),
                            deadlineParts[1].trim()
                    )
            );
        }
        case EVENT: {
            String arg = parts.length > 1 ? parts[1] : null;
            String[] eventParts = validateAdd(
                    arg,
                    EVENT_DELIMITER,
                    EVENT_EXPECTED,
                    CommandFormat.EVENT
            );
            return new AddCommand(
                    new EventTask(
                            eventParts[0].trim(),
                            eventParts[1].trim(),
                            eventParts[2].trim()
                    )
            );
        }
        case DELETE: {
            String part = parts.length > 1 ? parts[1] : null;
            int deleteIndex = validateIndex(part, CommandFormat.DELETE);
            return new DeleteCommand(deleteIndex);
        }
        case FIND: {
            String part = parts.length > 1 ? parts[1] : null;
            validateFind(part, CommandFormat.FIND);
            return new FindCommand(parts[1].trim());
        }
        default: {
            throw new BobException(INVALID_COMMAND_MESSAGE);
        }
        }
    }

    private static int validateIndex(String part, CommandFormat format) throws BobException {
        if (isValidPart(part)) {
            throw new BobInvalidFormatException(format);
        }
        try {
            return Integer.parseInt(part.trim()) - 1;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new BobException(INVALID_INDEX_MESSAGE);
        }
    }

    private static String[] validateAdd(String arg, String delimiter, int expectedParts, CommandFormat format)
            throws BobInvalidFormatException {

        if (isValidPart(arg)) {
            throw new BobInvalidFormatException(format);
        }

        String[] parts = new String[expectedParts];
        if (delimiter != null) {
            parts = arg.split(delimiter);
        } else {
            parts[0] = arg;
        }

        // Checks if required parts exist
        if (parts.length < expectedParts) {
            throw new BobInvalidFormatException(format);
        }

        // Check if empty argument
        for (String part : parts) {
            System.out.println(part);
            if (part.trim().isEmpty()) {
                throw new BobInvalidFormatException(format);
            }
        }
        return parts;
    }

    private static void validateFind(String part, CommandFormat format) {
        if (isValidPart(part)) {
            throw new BobInvalidFormatException(format);
        }
    }

    private static boolean isValidPart(String part) {
        return part == null || part.isBlank();
    }


}
