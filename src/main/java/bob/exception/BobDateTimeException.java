package bob.exception;

public class BobDateTimeException extends RuntimeException {
    public BobDateTimeException(String message) {
        super(" What the Bob!!![BobDateTimeException]\n " + message);
    }
}
