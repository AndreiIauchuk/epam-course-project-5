package by.epamtc.iovchuk.exception;

public class ResourceManagerException extends RuntimeException {

    private static final long serialVersionUID = -7627246275974482919L;

    public ResourceManagerException() {
        super();
    }

    public ResourceManagerException(String message) {
        super(message);
    }

    public ResourceManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceManagerException(Throwable cause) {
        super(cause);
    }
}