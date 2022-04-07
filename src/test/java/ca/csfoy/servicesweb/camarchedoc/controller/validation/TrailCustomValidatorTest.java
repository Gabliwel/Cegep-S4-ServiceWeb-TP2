package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.time.LocalDate;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class TrailCustomValidatorTest {
    
    private static final String ANY_ID = "3";
    private static final String ANY_NAME = "Promenade des pommes";
    private static final String ANY_DESCRIPTION = "Promenade en apprenant comment faire un backflip.";
    private static final String ANY_CITY = "Toronto";
    private static final TrailDifficulty ANY_DIFFICULTY = TrailDifficulty.BEGINNER;
    private static final String ANY_MESSAGE = "test";
    
    private Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private TrailCustomValidator validator = new TrailCustomValidator(defaultValidator);
    
    @Test
    void whenValidatingTrailDtoWithValidInputsThenValidationPass() {
        TrailDto dto = new TrailDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION, ANY_CITY, ANY_DIFFICULTY,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
            validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullNameThenValidationFails() {
        TrailDto dto = new TrailDto(ANY_ID, "", ANY_DESCRIPTION, ANY_CITY, ANY_DIFFICULTY,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullDescriptionThenValidationFails() {
        TrailDto dto = new TrailDto(ANY_ID, ANY_NAME, "", ANY_CITY, ANY_DIFFICULTY,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullCityThenValidationFails() {
        TrailDto dto = new TrailDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION, "", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullDifficultyThenValidationFails() {
        TrailDto dto = new TrailDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION, ANY_CITY, null,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullOpeningDateThenValidationFails() {
        TrailDto dto = new TrailDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION, ANY_CITY, ANY_DIFFICULTY,
            null, LocalDate.now().minusYears(5), null, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullLastMaintenanceDateThenValidationFails() {
        TrailDto dto = new TrailDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION, ANY_CITY, ANY_DIFFICULTY,
            LocalDate.now().minusYears(10), null, null, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingTrailDtoWithLastMaintenanceDateEarlierThanOpeningDateThenValidationFails() {
        TrailDto dto = new TrailDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION, ANY_CITY, ANY_DIFFICULTY,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(15), null, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingTrailDtoWithValidStringDtoThenValidationPass() {
        TrailDto dto = new TrailDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION, ANY_CITY, ANY_DIFFICULTY,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
            validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify(ANY_MESSAGE));
    }

}
