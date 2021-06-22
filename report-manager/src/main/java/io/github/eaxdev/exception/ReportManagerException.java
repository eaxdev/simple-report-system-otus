package io.github.eaxdev.exception;

public class ReportManagerException extends RuntimeException{

    public ReportManagerException(String message) {
        super(message);
    }

    public ReportManagerException(Throwable cause) {
        super(cause);
    }
}
