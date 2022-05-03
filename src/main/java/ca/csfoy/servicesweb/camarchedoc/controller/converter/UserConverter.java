package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.user.Role;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

@Component
public class UserConverter {
    private final TrailConverter trailConverter;
    private final PasswordEncoder passwordEncoder;

    public UserConverter(TrailConverter trailConverter, PasswordEncoder passwordEncoder) {
        this.trailConverter = trailConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto fromUser(User user) {
        return new UserDto(user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getPreferredDifficulty(),
                trailConverter.convertTrailSetFrom(user.getFavoritesTrails()),
                trailConverter.convertTrailSetFrom(user.getTrailsToTry()));
    }

    public User toUserForCreation(FullUserDto user) {
        return new User(IdentifiantGenerator.getNextIdAsString(),
                user.firstname,
                user.lastname,
                user.email,
                passwordEncoder.encode(user.password),
                new Role("2", "USER"),
                user.averageDifficulty,
                trailConverter.convertTrailDtosSetFrom(user.favoritesTrails),
                trailConverter.convertTrailDtosSetFrom(user.trailsToTry));
    }

    public User toUser(FullUserDto user) {
        return new User(user.id,
                user.firstname,
                user.lastname,
                user.email,
                passwordEncoder.encode(user.password),
                new Role("2", "USER"),
                user.averageDifficulty,
                trailConverter.convertTrailDtosSetFrom(user.favoritesTrails),
                trailConverter.convertTrailDtosSetFrom(user.trailsToTry));
    }
    
    public User toUser(UserDto user) {
        return new User(user.id,
                user.firstname,
                user.lastname,
                "",
                "",
                new Role("2", "USER"),
                user.averageDifficulty,
                trailConverter.convertTrailDtosSetFrom(user.favoritesTrails),
                trailConverter.convertTrailDtosSetFrom(user.trailsToTry));
    }
}
