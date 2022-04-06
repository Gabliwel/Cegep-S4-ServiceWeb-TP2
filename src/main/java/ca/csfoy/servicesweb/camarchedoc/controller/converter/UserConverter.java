package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

public class UserConverter {
    private final TrailConverter trailConverter;

    public UserConverter(TrailConverter trailConverter) {
        this.trailConverter = trailConverter;
    }

    public UserDto fromUser(User user) {
        return new UserDto(user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getPreferredDifficulty(),
                trailConverter.convertTrailSetFrom(user.getFavoritesTrails()),
                trailConverter.convertTrailSetFrom(user.getTrailsToTry()));
    }

    public User toUserForCreation(UserDto user) {
        return new User(user.firstname,
                user.lastname,
                user.averageDifficulty,
                trailConverter.convertTrailDtosSetFrom(user.favoritesTrails),
                trailConverter.convertTrailDtosSetFrom(user.trailsToTry));
    }

    public User toUser(UserDto user) {
        return new User(user.id,
                user.firstname,
                user.lastname,
                user.averageDifficulty,
                trailConverter.convertTrailDtosSetFrom(user.favoritesTrails),
                trailConverter.convertTrailDtosSetFrom(user.trailsToTry));

    }
}
