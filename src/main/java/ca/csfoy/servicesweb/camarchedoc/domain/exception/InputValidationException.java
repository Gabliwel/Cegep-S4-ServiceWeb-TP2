package ca.csfoy.servicesweb.camarchedoc.domain.exception;

import java.util.List;

public class InputValidationException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private List<String> violations;
    
    public InputValidationException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }
    
    public List<String> getViolations() {
        return violations;
    }

}
