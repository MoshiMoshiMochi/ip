package bob;

import bob.command.Command;
import bob.command.CommandFormat;
import bob.exception.BobDateTimeException;
import bob.exception.BobException;
import bob.exception.BobInvalidFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void Paser_InValid_Command(){

        BobInvalidFormatException expected_todo = new BobInvalidFormatException(CommandFormat.TODO);
        BobInvalidFormatException expected_deadline = new BobInvalidFormatException(CommandFormat.DEADLINE);
        BobInvalidFormatException expected_event = new BobInvalidFormatException(CommandFormat.EVENT);
        BobInvalidFormatException expected_mark = new BobInvalidFormatException(CommandFormat.MARK);
        BobInvalidFormatException expected_unmark = new BobInvalidFormatException(CommandFormat.UNMARK);
        BobInvalidFormatException expected_delete = new BobInvalidFormatException(CommandFormat.DELETE);
        BobInvalidFormatException expected_find = new BobInvalidFormatException(CommandFormat.FIND);
        BobException expected_invalidnum = new BobException(" Invalid Task number!");
        BobException expected_unrecognisedcommand = new BobException(" You just used an unrecognised command!");


        try {
            Command todo_cmd = Parser.parse("TODO ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_todo.getMessage(), e.getMessage());
        }

        try {
            Command deadline_cmd = Parser.parse("deadline ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_deadline.getMessage(), e.getMessage());
        }

        try {
            Command event_cmd = Parser.parse("Event ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_event.getMessage(), e.getMessage());
        }

        try {
            Command mark_cmd = Parser.parse("mark");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_mark.getMessage(), e.getMessage());
        }

        try {
            Command unmark_cmd = Parser.parse("unmark");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_unmark.getMessage(), e.getMessage());
        }

        try {
            Command delete_cmd = Parser.parse("delete");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_delete.getMessage(), e.getMessage());
        }

        try {
            Command invalid_cmd = Parser.parse("Mark hello");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_invalidnum.getMessage(), e.getMessage());
        }

        try {
            Command invalid_cmd = Parser.parse("hello ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_unrecognisedcommand.getMessage(), e.getMessage());
        }

        try {
            Command find_cmd = Parser.parse("find ");
        } catch (BobInvalidFormatException | BobException | BobDateTimeException e){
            assertEquals(expected_find.getMessage(), e.getMessage());
        }
    }
}
