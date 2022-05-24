package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class FullUserCustomValidatorTest {
    
 private static final String ANY_MESSAGE = "test";
    
    private Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private FullUserCustomValidator validator = new FullUserCustomValidator(defaultValidator);

    @Test
    void whenValidatingFullUserDtoWithValidInputsThenValidationPass() {
        FullUserDto dto = new FullUserDto("15", "Jeassn", "Passul", "jeanpasul@gmail.com", "allo123testallo", TrailDifficulty.BEGINNER, null, null, null);
        validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingFullUserIdWithNullIdThenValidationFails() {
        validator.validateId(null);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingFullUserIdWithEmptyIdThenValidationFails() {
        validator.validateId("");
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingFullUserIdWithBlankIdThenValidationFails() {
        validator.validateId("   ");
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingFullUserDtoWithNullFirstNameThenValidationFails() {
        FullUserDto dto = new FullUserDto("14", "", "Passul", "jeanpasul@gmail.com", "allo123testallo", TrailDifficulty.BEGINNER, null, null, null);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingFullUserDtoWithNullLastNameThenValidationFails() {
        FullUserDto dto = new FullUserDto("15", "Jean", "", "jeanpaul@gmail.com", "allo1234testallo", TrailDifficulty.BEGINNER, null, null, null);
         validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingFullUserDtoWithNullEmailThenValidationFails() {
        FullUserDto dto = new FullUserDto("15", "Jean", "Paul", "", "allo123testallo", TrailDifficulty.BEGINNER, null, null, null);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingFullUserDtoWithNullPasswordThenValidationFails() {
        FullUserDto dto = new FullUserDto("15", "Jeassn", "Passul", "jeanpasul@gmail.com", "", TrailDifficulty.BEGINNER, null, null, null);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingFullUserDtoWithShortPasswordThenValidationFails() {
        FullUserDto dto = new FullUserDto("15", "Jeassn", "Passul", "jeanpasul@gmail.com", "alloalloall", TrailDifficulty.BEGINNER, null, null, null);
         validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }

}
