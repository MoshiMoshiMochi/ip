package bob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import bob.command.CommandFormat;
import bob.exception.BobDateTimeException;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import bob.personality.Personality;

public class ParserTest {
    @Test
    public void parse_invalidTodo_throwsBobInvalidFormatException() {
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.TODO);
        try {
            Parser.parse("TODO ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_invalidDeadline_throwsBobInvalidFormatException() {
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.DEADLINE);
        try {
            Parser.parse("deadline ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_invalidEvent_throwsBobInvalidFormatException() {
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.EVENT);
        try {
            Parser.parse("Event ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_invalidMark_throwsBobInvalidFormatException() {
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.MARK);
        try {
            Parser.parse("mark");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_invalidUnmark_throwsBobInvalidFormatException() {
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.UNMARK);
        try {
            Parser.parse("unmark");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_invalidDelete_throwsBobInvalidFormatException() {
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.DELETE);
        try {
            Parser.parse("delete");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_invalidTaskNumber_throwsBobException() {
        BobException expected = new BobException(Personality.INVALID_INDEX_MESSAGE.getMessage());
        try {
            Parser.parse("Mark hello");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_unrecognisedCommand_throwsBobException() {
        BobException expected = new BobException(Personality.INVALID_COMMAND_MESSAGE.getMessage());
        try {
            Parser.parse("hello ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_invalidFind_throwsBobInvalidFormatException() {
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.FIND);
        try {
            Parser.parse("find ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_invalidUpdate_throwsBobInvalidFormatException() {
        BobInvalidFormatException expected = new BobInvalidFormatException(CommandFormat.UPDATEFORMAT);
        try {
            Parser.parse("update ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expected.getMessage(), e.getMessage());
        }
    }
}
