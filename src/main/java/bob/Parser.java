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
import bob.command.UpdateCommand;
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
    private static final String INVALID_INDEX_MESSAGE = " Invalid Task number!";
    private static final String INVALID_COMMAND_MESSAGE = " Invalid Command!";

    // Add Command delimiters and expected parts
    private static final String TODO_DELIMITER = null;
    private static final int TODO_EXPECTED = 1;
    private static final String DEADLINE_DELIMITER = "/by";
    private static final int DEADLINE_EXPECTED = 2;
    private static final String EVENT_DELIMITER = "/from|/to";
    private static final int EVENT_EXPECTED = 3;

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
            validateFind(part);
            return new FindCommand(parts[1].trim());
        }
        case UPDATE: {
            String part = parts.length > 1 ? parts[1] : null;
            return validateUpdate(part);
        }
        default: {
            throw new BobException(INVALID_COMMAND_MESSAGE);
        }
        }
    }

    private static int validateIndex(String part, CommandFormat format) throws BobException {
        if (isNotValidPart(part)) {
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

        if (isNotValidPart(arg)) {
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

    private static void validateFind(String part) {
        if (isNotValidPart(part)) {
            throw new BobInvalidFormatException(CommandFormat.FIND);
        }
    }

    private static UpdateCommand validateUpdate(String part) {
        if (isNotValidPart(part)) {
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT); //replace with corrected version later
        }
        String[] parts = part.trim().split(" ", 2);
        int index = -1; // Temp index value
        try {
            index = validateIndex(parts[0], CommandFormat.UPDATEFORMAT);
        } catch (BobException e) {
            // if invalid index
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        }
        System.out.println("pass validate index");

        assert index >= 0 : "Index should be >= 0 at this point";
        String args = parts.length > 1 ? parts[1] : "";
        String[] fieldArgs = parseUpdate(args);

        System.out.println("Pass validateUpdate");
        return new UpdateCommand(index, fieldArgs[0], fieldArgs[1], fieldArgs[2], fieldArgs[3], fieldArgs[4]);
    }

    private static String[] parseUpdate(String args) {
        String taskType = null;
        String desc = null;
        String by = null;
        String from = null;
        String to = null;

        System.out.println("args: " + args);

        // Find if change of task type
        if (args.contains("/t")) {
            String[] split = args.split("/t", 2);
            System.out.println("split[1]: " + split[1]);

            String afterT = split[1].trim();
            System.out.println("afterT:" + afterT);

            String[] parts = afterT.split("\\s+", 2);
            taskType = parts[0].trim();
            System.out.println("taskType updated: " + taskType);

            //sets the remainder args to contain
            args = split[1].replaceFirst(taskType, "").trim();
        }

        // Parse fields based on delimiters
        if (args.contains("/d")) {
            desc = helpExtractField(args, "/d");
        }
        if (args.contains("/by")) {
            by = helpExtractField(args, "/by");
            System.out.println("updated by: " + by);
        }
        if (args.contains("/from")) {
            from = helpExtractField(args, "/from");
        }
        if (args.contains("/to")) {
            to = helpExtractField(args, "/to");
        }

        if (taskType != null) {
            validateUpdateRequiredFields(taskType, by, from, to);
        }

        System.out.println("Pass parseUpdate");
        return new String[]{taskType, desc, by, from, to};
    }

    private static String helpExtractField(String args, String delimiter) {
        String[] parts = args.split(delimiter, 2);
        if (parts.length < 2) {
            return null;
        }
        System.out.println("delimiter: " + delimiter);
        System.out.println("parts[1]: " + parts[1]);
        String field = parts[1].split("/", 2)[0].trim();
        System.out.println("field: " + field);
        return field.isEmpty() ? null : field;
    }

    private static void validateUpdateRequiredFields(String taskType, String by, String from, String to)
            throws BobInvalidFormatException {
        CommandType type = CommandType.fromString(taskType);
        System.out.println("COMMANDTYPE: " + type.toString());
        switch (type) {
        case TODO: {
            // Nothing required
            break;
        }
        case DEADLINE: {
            if (by == null) {
                System.out.println("Aint no ways it here");
                throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
            }
            break;
        }
        case EVENT: {
            if (from == null && to == null) {
                // replace with custom CommandFormat for all listed Task types
                System.out.println("WHAT ITS AN EVENT");
                System.out.println(type);
                throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
            }
            break;
        }
        default: {
            System.out.println("Hoow did it reach here lmao");
        }
        }

    }

    private static boolean isNotValidPart(String part) {
        return part == null || part.isBlank();
    }


}
