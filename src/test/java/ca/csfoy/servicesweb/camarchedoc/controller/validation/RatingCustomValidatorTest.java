package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class RatingCustomValidatorTest {
    
    private static final TrailDto VALID_TRAIL = new TrailDto("999", "Nom", "Desc", "City", TrailDifficulty.BEGINNER,
            LocalDate.now().minusYears(10), LocalDate.now().minusYears(5), null, null);
    private static final UserDto VALID_USER = new UserDto("999", "Nom", "Nom2", TrailDifficulty.BEGINNER,
            Set.of(), Set.of(), Set.of());
    private static final String VALID_ID = "999";
    private static final Double VALID_NOTE = 5.0;
    private static final String VALID_COMMENT = null;
    private static final String ANY_MESSAGE = "test";
    
    private Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private RatingCustomValidator validator = new RatingCustomValidator(defaultValidator);

    @Test
    void whenValidatingRatingDtoWithValidInputsThenValidationPass() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_USER, VALID_TRAIL, VALID_NOTE, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify("test"));
    }
    
    @Test
    void whenValidatingRatingIdWithNullIdThenValidationFails() {
        validator.validateId(null);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingRatingIdWithEmptyIdThenValidationFails() {
        validator.validateId("");
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingRatingIdWithBlankIdThenValidationFails() {
        validator.validateId("   ");
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingRatingDtoWithNullTrailThenValidationFails() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_USER, null, VALID_NOTE, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingRatingDtoWithNullUserThenValidationDoesNotFails() {
        RatingDto dto = new RatingDto(VALID_ID, null, VALID_TRAIL, VALID_NOTE, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertDoesNotThrow(() -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingRatingDtoWithSuperiorNoteThenValidationFails() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_USER, VALID_TRAIL, RatingDto.MAX_NOTE + 0.01, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingRatingDtoWithInferiorNoteThenValidationFails() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_USER, VALID_TRAIL, RatingDto.MIN_NOTE - 0.01, VALID_COMMENT);
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }
    
    @Test
    void whenValidatingRatingDtoWithCommentWithMoreThan140CaractersThenValidationFails() {
        RatingDto dto = new RatingDto(VALID_ID, VALID_USER, VALID_TRAIL, VALID_NOTE, " ".repeat(141));
        validator.validate(dto);
        
        Assertions.assertThrows(InputValidationException.class, () -> validator.verify(ANY_MESSAGE));
    }

}
