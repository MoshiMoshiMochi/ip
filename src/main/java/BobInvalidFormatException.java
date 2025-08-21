public class BobInvalidFormatException extends RuntimeException {
    public BobInvalidFormatException(CommandFormat type) {
        super(" What the Bob!!![BobInvalidFormatException]\n Invalid Command Format! Expected: " + type.getFormat());
    }
}
