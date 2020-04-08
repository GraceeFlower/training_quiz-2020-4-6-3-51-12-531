package exception;

public class CarExistingException extends RuntimeException {

    public CarExistingException() {
    }

    public CarExistingException(String message) {
        super(message);
    }

    public CarExistingException(String message, Throwable cause) {
        super(message, cause);
    }
}
