package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

public class UserConverterTest {

    @Test
    void whenConvertingDtoWithoutIdOnCreationThenUserCreatedWithGivenFieldsAndGeneratedId() {
        Integer nextId = IdentifiantGenerator.getNextId();
        Set<TrailDto> set = Set.of(new TrailDto("5", null, null, null, null, null, null, null));
        UserDto dto = new UserDto("id", "firstName", "lastName", TrailDifficulty.BEGINNER, set, set);
        UserConverter converter = new UserConverter(new TrailConverter());

        User user = converter.toUserForCreation(dto);

        Assertions.assertEquals((nextId + 1) + "", user.getId());
        Assertions.assertEquals(dto.firstname, user.getFirstname());
        Assertions.assertEquals(dto.lastname, user.getLastname());
        Assertions.assertEquals(dto.averageDifficulty, user.getPreferredDifficulty());
        Assertions.assertEquals(dto.favoritesTrails.iterator().next().id, user.getFavoritesTrails().iterator().next().getId());
        Assertions.assertEquals(dto.trailsToTry.iterator().next().id, user.getTrailsToTry().iterator().next().getId());
    }
    
    @Test
    void whenConvertingDtoWithIdThenUserCreatedWithGivenFields() {
        Set<TrailDto> set = Set.of(new TrailDto("5", null, null, null, null, null, null, null));
        UserDto dto = new UserDto("id", "firstName", "lastName", TrailDifficulty.BEGINNER, set, set);
        UserConverter converter = new UserConverter(new TrailConverter());

        User user = converter.toUser(dto);

        Assertions.assertEquals(dto.id, user.getId());
        Assertions.assertEquals(dto.firstname, user.getFirstname());
        Assertions.assertEquals(dto.lastname, user.getLastname());
        Assertions.assertEquals(dto.averageDifficulty, user.getPreferredDifficulty());
        Assertions.assertEquals(dto.favoritesTrails.iterator().next().id, user.getFavoritesTrails().iterator().next().getId());
        Assertions.assertEquals(dto.trailsToTry.iterator().next().id, user.getTrailsToTry().iterator().next().getId());
    }
    
    @Test
    void whenConvertingUserThenzDtoCreatedWithGivenFields() {
        Set<Trail> set = Set.of(new Trail("5", null, null, null, null, null, null, null));
        User user = new User("id", "firstName", "lastName", TrailDifficulty.BEGINNER, set, set);
        UserConverter converter = new UserConverter(new TrailConverter());

        UserDto dto = converter.fromUser(user);

        Assertions.assertEquals(dto.id, user.getId());
        Assertions.assertEquals(dto.firstname, user.getFirstname());
        Assertions.assertEquals(dto.lastname, user.getLastname());
        Assertions.assertEquals(dto.averageDifficulty, user.getPreferredDifficulty());
        Assertions.assertEquals(dto.favoritesTrails.iterator().next().id, user.getFavoritesTrails().iterator().next().getId());
        Assertions.assertEquals(dto.trailsToTry.iterator().next().id, user.getTrailsToTry().iterator().next().getId());
    }
}
