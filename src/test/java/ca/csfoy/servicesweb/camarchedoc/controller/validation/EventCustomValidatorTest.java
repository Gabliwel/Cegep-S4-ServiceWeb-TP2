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
    
    private static final String ANY_MESSAGE = "test";
    private static final String ANY_ID = "3";
    private static final String ANY_NAME = "Promenade des pommes";
    private static final String ANY_DESCRIPTION = "Promenade en apprenant comment faire un backflip.";
    
    private Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private EventCustomValidator validator = new EventCustomValidator(defaultValidator);
    
    private final TrailDto anyTrailDto = new TrailDto("ANY_ID", ANY_NAME, ANY_DESCRIPTION, "Toronto", 
            TrailDifficulty.BEGINNER, LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
    
    @Test
    void whenValidatingEventDtoWithValidInputsThenValidationPass() {
        EventDto dto = new EventDto("10", ANY_NAME, ANY_DESCRIPTION,
            LocalDate.now().plusYears(1), anyTrailDto, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingEventIdWithNullIdThenValidationFails() {
        validator.validateId(null);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingEventIdWithEmptyIdThenValidationFails() {
        validator.validateId("");
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingEventIdWithBlankIdThenValidationFails() {
        validator.validateId("   ");
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingEventDtoWithNullNameThenValidationFails() {
        EventDto dto = new EventDto(ANY_ID, "", ANY_DESCRIPTION,
                LocalDate.now().plusYears(1), anyTrailDto, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingEventDtoWithNullDescriptionThenValidationFails() {
        EventDto dto = new EventDto(ANY_ID, ANY_NAME, "",
                LocalDate.now().plusYears(1), anyTrailDto, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingEventDtoWithNullStartingDateThenValidationFails() {
        EventDto dto = new EventDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION,
                null, anyTrailDto, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingEventDtoWithNullAssociatedTrailThenValidationFails() {
        EventDto dto = new EventDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION,
                LocalDate.now().plusYears(1), null, "René Deschamps");
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingEventDtoWithNullOrganizerThenValidationFails() {
        EventDto dto = new EventDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION,
                LocalDate.now().plusYears(1), anyTrailDto, null);
            validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }

}
