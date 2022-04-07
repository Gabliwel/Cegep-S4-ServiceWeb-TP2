package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.time.LocalDate;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.csfoy.servicesweb.camarchedoc.api.event.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class EventCustomValidatorTest {
    
    private Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private EventCustomValidator validator = new EventCustomValidator(defaultValidator);
    
    private final TrailDto ANY_TRAIL_DTO = new TrailDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.", "Toronto", 
            TrailDifficulty.BEGINNER, LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
    
    @Test
    void whenValidatingEventDtoWithValidInputsThenValidationPass() {
        EventDto dto = new EventDto("10", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.",
            LocalDate.now().plusYears(1), ANY_TRAIL_DTO, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingEventDtoWithNullNameThenValidationFails() {
        EventDto dto = new EventDto("3", "", "Promenade en apprenant comment faire un backflip.",
                LocalDate.now().plusYears(1), ANY_TRAIL_DTO, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingEventDtoWithNullDescriptionThenValidationFails() {
        EventDto dto = new EventDto("3", "Promenade des pommes", "",
                LocalDate.now().plusYears(1), ANY_TRAIL_DTO, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingEventDtoWithNullStartingDateThenValidationFails() {
        EventDto dto = new EventDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.",
                null, ANY_TRAIL_DTO, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingEventDtoWithNullAssociatedTrailThenValidationFails() {
        EventDto dto = new EventDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.",
                LocalDate.now().plusYears(1), null, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingEventDtoWithNullOrganizerThenValidationFails() {
        EventDto dto = new EventDto("3", "Promenade des pommes", "Promenade en apprenant comment faire un backflip.",
                LocalDate.now().plusYears(1), ANY_TRAIL_DTO, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }

}
