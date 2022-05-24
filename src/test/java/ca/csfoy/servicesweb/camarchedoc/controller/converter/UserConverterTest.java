package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.Badge;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

public class UserConverterTest {

    @Test
    void whenConvertingDtoWithoutIdOnCreationThenUserCreatedWithGivenFieldsAndGeneratedId() {
        Integer nextId = IdentifiantGenerator.getNextId();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        FullUserDto dto = new FullUserDto("id", "firstName", "lastName", "email", "password", TrailDifficulty.BEGINNER, Set.of(), Set.of(), Set.of());
        UserConverter converter = new UserConverter(new TrailConverter(), passwordEncoder, new BadgeConverter());

        User user = converter.toUserForCreation(dto);

        Assertions.assertEquals((nextId + 1) + "", user.getId());
        Assertions.assertEquals(dto.firstname, user.getFirstname());
        Assertions.assertEquals(dto.lastname, user.getLastname());
        Assertions.assertEquals(dto.email, user.getEmail());
        Assertions.assertTrue(passwordEncoder.matches(dto.password, user.getPassword()));
        Assertions.assertEquals(dto.averageDifficulty, user.getPreferredDifficulty());
        Assertions.assertFalse(user.isAdmin());
    }
    
    @Test
    void whenConvertingDtoWithIdThenUserCreatedWithGivenFields() {
        Set<TrailDto> set = Set.of(new TrailDto("5", null, null, null, null, null, null, null, null));
        Set<BadgeDto> set2 = Set.of(new BadgeDto("5", null, null, null, null));
        UserDto dto = new UserDto("id", "firstName", "lastName", TrailDifficulty.BEGINNER, set, set, set2);
        UserConverter converter = new UserConverter(new TrailConverter(), new BCryptPasswordEncoder(), new BadgeConverter());

        User user = converter.toUser(dto);

        Assertions.assertEquals(dto.id, user.getId());
        Assertions.assertEquals(dto.firstname, user.getFirstname());
        Assertions.assertEquals(dto.lastname, user.getLastname());
        Assertions.assertEquals(dto.averageDifficulty, user.getPreferredDifficulty());
        Assertions.assertEquals(dto.favoritesTrails.iterator().next().id, user.getFavoritesTrails().iterator().next().getId());
        Assertions.assertEquals(dto.trailsToTry.iterator().next().id, user.getTrailsToTry().iterator().next().getId());
        Assertions.assertEquals(null, user.getBadges());
    }
    
    @Test
    void whenConvertingUserThenDtoCreatedWithGivenFields() {
        Set<Trail> set = Set.of(new Trail("5", null, null, null, null, null, null, null, null));
        Set<Badge> set2 = Set.of(new Badge("5", null, null, null, null));
        User user = new User("id", "firstName", "lastName", "", "", null, TrailDifficulty.BEGINNER, set, set, set2);
        UserConverter converter = new UserConverter(new TrailConverter(), new BCryptPasswordEncoder(), new BadgeConverter());

        UserDto dto = converter.fromUser(user);

        Assertions.assertEquals(dto.id, user.getId());
        Assertions.assertEquals(dto.firstname, user.getFirstname());
        Assertions.assertEquals(dto.lastname, user.getLastname());
        Assertions.assertEquals(dto.averageDifficulty, user.getPreferredDifficulty());
        Assertions.assertEquals(dto.favoritesTrails.iterator().next().id, user.getFavoritesTrails().iterator().next().getId());
        Assertions.assertEquals(dto.trailsToTry.iterator().next().id, user.getTrailsToTry().iterator().next().getId());
        Assertions.assertEquals(dto.badges, user.getBadges());
    }
}
