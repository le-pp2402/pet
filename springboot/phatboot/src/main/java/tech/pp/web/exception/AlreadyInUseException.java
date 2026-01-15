package tech.pp.web.exception;

public class AlreadyInUseException extends RuntimeException {
    public AlreadyInUseException(String resource, String location) {
        super(resource + " already in use: " + location);
    }
}
