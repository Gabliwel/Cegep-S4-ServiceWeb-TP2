package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.UserConverter;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

public class UserController implements UserResource {

    private final UserRepository repo;
    private final UserConverter converter;
    private final TrailConverter trailConverter;

    public UserController(UserRepository repo, UserConverter converter, TrailConverter trailConverter) {
        this.repo = repo;
        this.converter = converter;
        this.trailConverter = trailConverter;
    }

    @Override
    public void createUser(UserDto user) {
        System.out.println(user.firstname + " " + user.lastname + " " + user.id + " " + user.averageDifficulty );
        repo.create(converter.toUserForCreation(user));
    }

    @Override
    public UserDto getUser(String id) {
        return converter.fromUser(repo.get(id));
    }

    @Override
    public void modifyUser(UserDto user) {
        repo.save(converter.toUser(user));
    }

    @Override
    public List<TrailDto> getNewTrails(String userId) {
        // FIXME: Cette méthode à implémenter
        return null;
    }

    @Override
    public List<TrailDto> getSuggestedTrails(String userId) {
        // FIXME: Cette méthode à implémenter
        return null;
    }
}

