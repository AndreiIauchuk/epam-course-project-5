package by.epamtc.iovchuk.exception;

public class XMLTagNotSupportedException extends RuntimeException {

    public XMLTagNotSupportedException() {
        super("Given tag is not supported!");
    }

    public XMLTagNotSupportedException(String message) {
        super(message);
    }

    public XMLTagNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLTagNotSupportedException(Throwable cause) {
        super(cause);
    }
}