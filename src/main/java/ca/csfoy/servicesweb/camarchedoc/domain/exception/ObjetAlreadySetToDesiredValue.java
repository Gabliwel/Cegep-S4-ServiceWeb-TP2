package ca.csfoy.servicesweb.camarchedoc.domain.exception;

public class ObjetAlreadySetToDesiredValue extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjetAlreadySetToDesiredValue(String message) {
        super(message);
    }
}
