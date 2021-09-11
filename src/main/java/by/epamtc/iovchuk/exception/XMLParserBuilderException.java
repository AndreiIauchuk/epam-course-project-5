package by.epamtc.iovchuk.exception;

public class XMLParserBuilderException extends Exception {

    private static final long serialVersionUID = 2637340997476614500L;

    public XMLParserBuilderException() {
        super();
    }

    public XMLParserBuilderException(String message) {
        super(message);
    }

    public XMLParserBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLParserBuilderException(Throwable cause) {
        super(cause);
    }
}