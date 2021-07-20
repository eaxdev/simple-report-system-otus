package io.github.eaxdev.exception;

public class ReportNotFound extends RuntimeException{

    public ReportNotFound(int reportId) {
        super("Not found report by id: " + reportId);
    }
}
