public class Parser {
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
            case MARK:{
                if(parts.length<2){
                    throw new BobInvalidFormatException(CommandFormat.MARK);
                }
                try{
                    int index = Integer.parseInt(parts[1]) - 1;
                    return new MarkCommand(index);
                } catch (NumberFormatException e){
                    throw new BobException(" Invalid Task number!");
                }
            }
            case UNMARK:{
                if(parts.length<2){
                    throw new BobInvalidFormatException(CommandFormat.UNMARK);
                }
                try{
                    int index = Integer.parseInt(parts[1]) - 1;
                    return new UnMarkCommand(index);
                }catch (NumberFormatException e){
                    throw new BobException(" Invalid Task number!");
                }
            }
            case TODO: {
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    throw new BobInvalidFormatException(CommandFormat.TODO);
                }
                return new AddCommand(new ToDoTask(parts[1].trim()));
            }
            case DEADLINE: {
                if (parts.length < 2) {
                    throw new BobInvalidFormatException(CommandFormat.DEADLINE);
                }
                String[] deadlineParts = parts[1].split("/by", 2);
                if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
                    throw new BobInvalidFormatException(CommandFormat.DEADLINE);
                }
                return new AddCommand(new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim()));
            }
            case EVENT: {
                if (parts.length < 2) {
                    throw new BobInvalidFormatException(CommandFormat.EVENT);
                }
                String[] eventParts = parts[1].split("/from|/to");
                if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
                    throw new BobInvalidFormatException(CommandFormat.EVENT);
                }
                return new AddCommand(new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()));
            }
            case DELETE: {
                if (parts.length < 2) {
                    throw new BobInvalidFormatException(CommandFormat.DELETE);
                }
                try {
                    int index = Integer.parseInt(parts[1].trim()) - 1;
                    return new DeleteCommand(index);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    throw new BobException(" Invalid Task number!");
                }
            }
            default: {
                throw new BobException(" You just used an unrecognised command!");
            }
        }
    }
}
