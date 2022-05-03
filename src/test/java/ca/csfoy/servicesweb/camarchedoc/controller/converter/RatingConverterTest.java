package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

public class RatingConverterTest {
    
    public static final String ANY_ID = "1";
    
    public static final RatingConverter CONVERTER = 
            new RatingConverter(new TrailConverter(), new UserConverter(new TrailConverter(), new BCryptPasswordEncoder()));
    
    @Test
    void whenConvertingDtoWithoutIdOnCreationThenRatingCreatedWithGivenFieldsAndGeneratedId() {
        Integer nextId = IdentifiantGenerator.getNextId();
        RatingDto dto = new RatingDto(null,
                new UserDto("1", "Bob", "JSP", TrailDifficulty.BEGINNER, Set.of(), Set.of()),
                new TrailDto("1", "name", "description", "city", TrailDifficulty.BEGINNER, null, null, null, null),
                5.0, "comment");

        Rating result = CONVERTER.convertToRatingAtCreationFrom(dto);

        Assertions.assertEquals((nextId + 1) + "", result.getId());
        Assertions.assertEquals(dto.user.id, result.getUser().getId());
        Assertions.assertEquals(dto.trail.id, result.getTrail().getId());
        Assertions.assertEquals(dto.note, result.getNote());
        Assertions.assertEquals(dto.comment, result.getComment());
    }
    
    @Test
    void whenConvertingDomainObjectThenDtoCreatedWithGivenFieldsIncludingId() {
        Rating rating = new Rating( 
                new User(ANY_ID, "Bob", "JSP", "", "", null, TrailDifficulty.BEGINNER, Set.of(), Set.of()),
                new Trail(ANY_ID, "name", "description", "city", TrailDifficulty.BEGINNER, null, null, null, null),
                5.0, "comment");

        RatingDto result = CONVERTER.convertToRatingDtoFrom(rating);

        Assertions.assertEquals(result.id, rating.getId());
        Assertions.assertEquals(result.user.id, rating.getUser().getId());
        Assertions.assertEquals(result.trail.id, rating.getTrail().getId());
        Assertions.assertEquals(result.note, rating.getNote());
        Assertions.assertEquals(result.comment, rating.getComment());
    }
    
    @Test
    void whenConvertingListOfDomainObjetsThenListOfDtosIsReturned() {
        Rating rating1 = new Rating(null, 
                new User(ANY_ID, "Bob", "JSP", "", "", null, TrailDifficulty.BEGINNER, Set.of(), Set.of()),
                new Trail("1", "name", "description", "city", TrailDifficulty.BEGINNER, null, null, null, null),
                5.0, "comment");
        Rating rating2 = new Rating(null, 
                new User(ANY_ID, "Bob", "JSP", "", "", null, TrailDifficulty.BEGINNER, Set.of(), Set.of()),
                new Trail(ANY_ID, "name", "description", "city", TrailDifficulty.BEGINNER, null, null, null, null),
                5.0, "comment");

        List<RatingDto> result = CONVERTER.convertToRatingDtoListFrom(List.of(rating1, rating2));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(result.get(0).id, rating1.getId());
        Assertions.assertEquals(result.get(1).id, rating2.getId());
    }

}
