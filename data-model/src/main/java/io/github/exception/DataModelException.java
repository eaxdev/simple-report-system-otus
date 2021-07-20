package io.github.exception;

public class DataModelException extends RuntimeException{

    public DataModelException(String message) {
        super(message);
    }

    public DataModelException(Throwable cause) {
        super(cause);
    }
}
