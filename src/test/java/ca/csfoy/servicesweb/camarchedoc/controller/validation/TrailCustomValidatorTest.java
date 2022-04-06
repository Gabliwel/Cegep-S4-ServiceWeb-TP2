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
    
    private Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private TrailCustomValidator validator = new TrailCustomValidator(defaultValidator);
    
    @Test
    void whenValidatingTrailDtoWithValidInputsThenValidationPass() {
        TrailDto dto = new TrailDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.", "Toronto", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null);
            validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullNameThenValidationFails() {
        TrailDto dto = new TrailDto("3", "", "Promenade en apprenant comment faire un backflip.", "Toronto", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullDescriptionThenValidationFails() {
        TrailDto dto = new TrailDto("3", "Promenade des pommes", "", "Toronto", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullCityThenValidationFails() {
        TrailDto dto = new TrailDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.", "", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullDifficultyThenValidationFails() {
        TrailDto dto = new TrailDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.", "Toronto", null,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullOpeningDateThenValidationFails() {
        TrailDto dto = new TrailDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.", "Toronto", TrailDifficulty.BEGINNER,
            null, LocalDate.now().minusYears(5), null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingTrailDtoWithNullLastMaintenanceDateThenValidationFails() {
        TrailDto dto = new TrailDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.", "Toronto", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), null, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingTrailDtoWithLastMaintenanceDateEarlierThanOpeningDateThenValidationFails() {
        TrailDto dto = new TrailDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.", "Toronto", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(15), null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }

}
