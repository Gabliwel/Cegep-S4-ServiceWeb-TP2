package ca.csfoy.servicesweb.camarchedoc.domain.exception;

public class ObjectInvalidValueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectInvalidValueException(String message) {
        super(message);
    }
}