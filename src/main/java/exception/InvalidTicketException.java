package exception;

public class InvalidTicketException extends RuntimeException {

    public InvalidTicketException() {
    }

    public InvalidTicketException(String message) {
        super(message);
    }

    public InvalidTicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
