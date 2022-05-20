package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

public class BadgeCustomValidatorTest {
    
    private static final String ANY_VALID_ID = "3";
    private static final String ANY_INVALID_ID = "$#@ABC123";
    private static final String ANY_MSG = "JSPPPPP1234567890";
    
    private final Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private BadgeCustomValidator validator = new BadgeCustomValidator(defaultValidator);

    @Test
    void whenVerifyAndIdIsValidThenDoesNotThrowError() {
        validator.validateId(ANY_VALID_ID);
        Assertions.assertDoesNotThrow(() -> validator.verify(ANY_MSG));
    }
    
    @Test
    void whenVerifyAndIdIsNullThenDoesThrowError() {
        validator.validateId(null);
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MSG));
    }
    
    @Test
    void whenVerifyAndIdIsBlankThenDoesThrowError() {
        validator.validateId("  ");
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MSG));
    }
    
    @Test
    void whenVerifyAndIdIsInvalidThenDoesThrowError() {
        validator.validateId(ANY_INVALID_ID);
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MSG));
    }
    
    @Test
    void validateObjectIsUnsupported() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> validator.validate(null));
    }
    
    @Test
    void validateObjectAndIsIsUnsupported() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> validator.validate(null, null));
    }
}
