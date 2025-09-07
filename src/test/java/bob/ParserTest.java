package bob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import bob.command.Command;
import bob.command.CommandFormat;
import bob.exception.BobDateTimeException;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;

public class ParserTest {
    @Test
    public void praser_command_inValid() {

        BobInvalidFormatException expectedTodo = new BobInvalidFormatException(CommandFormat.TODO);
        BobInvalidFormatException expectedDeadline = new BobInvalidFormatException(CommandFormat.DEADLINE);
        BobInvalidFormatException expectedEvent = new BobInvalidFormatException(CommandFormat.EVENT);
        BobInvalidFormatException expectedMark = new BobInvalidFormatException(CommandFormat.MARK);
        BobInvalidFormatException expectedUnmark = new BobInvalidFormatException(CommandFormat.UNMARK);
        BobInvalidFormatException expectedDelete = new BobInvalidFormatException(CommandFormat.DELETE);
        BobInvalidFormatException expectedFind = new BobInvalidFormatException(CommandFormat.FIND);
        BobException expectedInvalidnum = new BobException(" Invalid Task number!");
        BobException expectedUnrecognisedcommand = new BobException(" Invalid Command!");


        try {
            Command todoCmd = Parser.parse("TODO ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedTodo.getMessage(), e.getMessage());
        }

        try {
            Command deadlineCmd = Parser.parse("deadline ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedDeadline.getMessage(), e.getMessage());
        }

        try {
            Command eventCmd = Parser.parse("Event ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedEvent.getMessage(), e.getMessage());
        }

        try {
            Command markCmd = Parser.parse("mark");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedMark.getMessage(), e.getMessage());
        }

        try {
            Command unmarkCmd = Parser.parse("unmark");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedUnmark.getMessage(), e.getMessage());
        }

        try {
            Command deleteCmd = Parser.parse("delete");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedDelete.getMessage(), e.getMessage());
        }

        try {
            Command invalidCmd = Parser.parse("Mark hello");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedInvalidnum.getMessage(), e.getMessage());
        }

        try {
            Command invalidCmd = Parser.parse("hello ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedUnrecognisedcommand.getMessage(), e.getMessage());
        }

        try {
            Command findCmd = Parser.parse("find ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e) {
            assertEquals(expectedFind.getMessage(), e.getMessage());
        }
    }
}
