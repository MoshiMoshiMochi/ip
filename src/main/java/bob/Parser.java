package bob;

import java.util.Map;
import java.util.function.Function;

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
import bob.personality.Personality;
import bob.task.DeadlineTask;
import bob.task.EventTask;
import bob.task.Task;
import bob.task.ToDoTask;
import javafx.util.Pair;

/**
 * Parses user input strings into <code>Command</code> objects for execution.
 * Handles validation of input format and throws exceptions for invalid commands.
 */
public class Parser {
    // Add Command delimiters and expected parts
    private static final String TODO_DELIMITER = null;
    private static final int TODO_EXPECTED = 1;
    private static final String DEADLINE_DELIMITER = "/by";
    private static final int DEADLINE_EXPECTED = 2;
    private static final String EVENT_DELIMITER = "/from|/to";
    private static final int EVENT_EXPECTED = 3;

    // Update Command delimiters and expected parts [CoPilot suggestion to use a Map instead of array]
    private static final Map<String, Integer> UPDATE_FIELD_MAP = Map.of(
            "/d", 0,
            "/by", 1,
            "/from", 2,
            "/to", 3
    );

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
        case BYE:
            return new ByeCommand();
        case LIST:
            return new ListCommand();
        case MARK:
            return createIndexCommand(parts, CommandFormat.MARK, MarkCommand::new);
        case UNMARK:
            return createIndexCommand(parts, CommandFormat.UNMARK, UnMarkCommand::new);
        case DELETE:
            return createIndexCommand(parts, CommandFormat.DELETE, DeleteCommand::new);
        case TODO:
            return createAddCommand(
                    parts,
                    TODO_DELIMITER,
                    TODO_EXPECTED,
                    CommandFormat.TODO,
                    arr -> new ToDoTask(arr[0].trim())
            );
        case DEADLINE:
            return createAddCommand(
                    parts,
                    DEADLINE_DELIMITER,
                    DEADLINE_EXPECTED,
                    CommandFormat.DEADLINE,
                    arr -> new DeadlineTask(arr[0].trim(), arr[1].trim())
            );
        case EVENT:
            return createAddCommand(
                    parts,
                    EVENT_DELIMITER,
                    EVENT_EXPECTED,
                    CommandFormat.EVENT,
                    arr -> new EventTask(arr[0].trim(), arr[1].trim(), arr[2].trim())
            );
        case FIND:
            String findArg = getArgumentString(parts);
            validateFind(findArg);
            return new FindCommand(findArg.trim());
        case UPDATE:
            String updateArg = getArgumentString(parts);
            return validateUpdate(updateArg);
        default:
            throw new BobException(Personality.INVALID_COMMAND_MESSAGE.getMessage());
        }
    }

    // Helper for index-based commands [CoPilot suggestion to abstract to reduce reuse of code]
    private static Command createIndexCommand(
            String[] parts, CommandFormat format, Function<Integer, Command> constructor) throws BobException {
        String arg = getArgumentString(parts);
        int idx = validateIndex(arg, format);
        return constructor.apply(idx);
    }

    // Helper for add commands [CoPilot suggestion to abstract to reduce reuse of code]
    private static Command createAddCommand(
            String[] parts,
            String delimiter,
            int expected,
            CommandFormat format,
            Function<String[], Task> taskCreator
    ) throws BobInvalidFormatException {
        String arg = getArgumentString(parts);
        String[] args = validateAdd(arg, delimiter, expected, format);
        return new AddCommand(taskCreator.apply(args));
    }

    private static String getArgumentString(String[] parts) {
        return parts.length > 1 ? parts[1] : null;
    }

    private static int validateIndex(String part, CommandFormat format) throws BobException {
        if (isNotValidPart(part)) {
            throw new BobInvalidFormatException(format);
        }
        try {
            return Integer.parseInt(part.trim()) - 1;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new BobException(Personality.INVALID_INDEX_MESSAGE.getMessage());
        }
    }

    /**
     * Validates and splits the argument for add commands (todo, deadline, event).
     *
     * @param arg           The argument string to validate and split.
     * @param delimiter     The delimiter to split the argument.
     * @param expectedParts The expected number of parts after splitting.
     * @param format        The command format for error reporting.
     * @return The split parts of the argument.
     * @throws BobInvalidFormatException If validation fails.
     */
    private static String[] validateAdd(String arg, String delimiter, int expectedParts, CommandFormat format)
            throws BobInvalidFormatException {

        if (isNotValidPart(arg)) {
            throw new BobInvalidFormatException(format);
        }
        // Splits argument based on delimiter
        String[] parts = delimiter != null ? arg.split(delimiter) : new String[]{arg};
        // Checks if required parts exist
        if (parts.length < expectedParts) {
            throw new BobInvalidFormatException(format);
        }
        // Check if empty argument
        for (String part : parts) {
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
        assert index >= 0 : "Index should be >= 0 at this point";

        String args = parts.length > 1 ? parts[1] : "";
        String[] fieldArgs = parseUpdate(args);

        // Checks if at least one field argument is used
        if (isAllNull(fieldArgs)) {
            // throw error if null
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        }

        return new UpdateCommand(
                index,
                fieldArgs[0], // taskType
                fieldArgs[1], // description
                fieldArgs[2], // by
                fieldArgs[3], // from
                fieldArgs[4] // to
        );
    }

    // Helper method to check if all fields are null [CoPilot suggestion]
    // Abstracted to reduce complexity of validateUpdate method
    private static boolean isAllNull(String[] fields) {
        for (String field : fields) {
            if (field != null) {
                return false;
            }
        }
        return true;
    }

    private static String[] parseUpdate(String args) {
        // Extract task type if present [CoPilot suggestion to abstract to separate method]
        Pair<String, String> result = extractTaskType(args);
        String taskType = result.getKey();
        args = result.getValue();

        // Parse fields based on delimiters [CoPilot suggestion to abstract to separate method]
        String[] fieldValues = fieldExtractors(args);

        if (taskType != null) {
            validateUpdateRequiredFields(taskType, fieldValues[1], fieldValues[2], fieldValues[3]);
        }

        return new String[]{taskType, fieldValues[0], fieldValues[1], fieldValues[2], fieldValues[3]};
    }

    private static Pair<String, String> extractTaskType(String args) {
        if (args.contains("/t")) {
            String[] split = args.split("/t", 2);
            String afterT = split[1].trim();
            String[] parts = afterT.split("\\s+", 2);
            String taskType = parts[0].trim();
            String newArgs = split[1].replaceFirst(taskType, "").trim();
            return new Pair<>(taskType, newArgs);
        }
        return new Pair<>(null, args);
    }

    private static String[] fieldExtractors(String args) {
        String[] fieldValues = new String[UPDATE_FIELD_MAP.size()];
        for (Map.Entry<String, Integer> entry : UPDATE_FIELD_MAP.entrySet()) {
            if (args.contains(entry.getKey())) {
                fieldValues[entry.getValue()] = helpExtractField(args, entry.getKey());
            }
        }
        return fieldValues;
    }

    private static String helpExtractField(String args, String delimiter) {
        String[] parts = args.split(delimiter, 2);
        if (parts.length < 2) {
            return null;
        }

        String field = parts[1].split("/", 2)[0].trim();
        return field.isEmpty() ? null : field;
    }

    private static void validateUpdateRequiredFields(String taskType, String by, String from, String to)
            throws BobInvalidFormatException {

        CommandType type = CommandType.fromString(taskType);
        switch (type) {
        case TODO: {
            // Nothing required
            break;
        }
        case DEADLINE: {
            if (by == null) {
                throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
            }
            break;
        }
        case EVENT: {
            if (from == null && to == null) {
                // replace with custom CommandFormat for all listed Task types
                throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
            }
            break;
        }
        default: {
            throw new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        }
        }

    }

    private static boolean isNotValidPart(String part) {
        return part == null || part.isBlank();
    }


}
