package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDtoForCreate;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.UserConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

public class UserController implements UserResource {

    private final UserRepository repo;
    private final UserConverter converter;
    private final CustomValidatorFactory validatorFactory;

    public UserController(UserRepository repo, UserConverter converter, CustomValidatorFactory validatorFactory) {
        this.repo = repo;
        this.converter = converter;
        this.validatorFactory = validatorFactory;
    }

    @Override
    public void createUser(UserDtoForCreate user) {
        CustomValidator<UserDtoForCreate, String> validator = validatorFactory.getUserDtoForCreateValidator();
        validator.validate(user);
        validator.verify("User cannot be created.");
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
    public List<TrailDto> getSuggestedTrails(String userId) {
        
        Set<TrailDto> trails = converter.fromUser(repo.get(userId)).favoritesTrails;
        if (trails != null && !trails.isEmpty()) {
            return trails.stream().collect(Collectors.toList());
        }
        return new ArrayList<TrailDto>();
    }
}

