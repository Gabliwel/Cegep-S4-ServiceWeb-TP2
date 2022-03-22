package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.time.LocalDate;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.csfoy.servicesweb.camarchedoc.api.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

public class RatingCustomValidatorTest {
    
    private Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private RatingCustomValidator validator = new RatingCustomValidator(defaultValidator);
    
    private final TrailDto VALID_TRAIL = new TrailDto("999", "Nom", "Desc", "City", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null);
    private final String VALID_ID = "999";
    private final Double VALID_NOTE = 5.0;
    private final String VALID_COMMENT = null;
    
    @Test
    void whenValidatingRatingDtoWithValidInputsThenValidationPass() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_TRAIL, VALID_NOTE, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingRatingIdWithNullIdThenValidationFails() {
        validator.validateId(null);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingRatingIdWithEmptyIdThenValidationFails() {
        validator.validateId("");
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingRatingIdWithBlankIdThenValidationFails() {
        validator.validateId("   ");
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingRatingDtoWithNullTrailThenValidationFails() {
        RatingDto dto = new RatingDto(VALID_ID, null, VALID_NOTE, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingRatingDtoWithSuperiorNoteThenValidationFails() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_TRAIL, RatingDto.MAX_NOTE + 0.01, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingRatingDtoWithInferiorNoteThenValidationFails() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_TRAIL, RatingDto.MIN_NOTE - 0.01, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingRatingDtoWithCommentWithMoreThan140CaractersThenValidationFails() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_TRAIL, VALID_NOTE, " ".repeat(141));
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify("test"));
    }

}
