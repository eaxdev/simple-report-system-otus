package io.github.exception;

public class DataModelNotFound extends RuntimeException{

    public DataModelNotFound(int dataModelId) {
        super("Not found data model by id: " + dataModelId);
    }
}
