package io.github.eaxdev.exception;

public class ConnectionNotFound extends RuntimeException {
    public ConnectionNotFound(int connectionId) {
        super("Not found connection by id: " + connectionId);
    }
}
