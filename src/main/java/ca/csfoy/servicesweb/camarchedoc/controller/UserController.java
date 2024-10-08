package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.TokenDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserCredentialsDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.UserConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.BadgeRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;
import ca.csfoy.servicesweb.camarchedoc.security.JwtTokenAuthentificationProvider;

@RestController
public class UserController implements UserResource {

    private final UserRepository repo;
    private final UserConverter converter;
    private final CustomValidatorFactory validatorFactory;
    private final AuthenticationManager authManager;
    private final JwtTokenAuthentificationProvider tokenProvider;
    private final BadgeRepository badgeRepo;

    public UserController(UserRepository repo, UserConverter converter, CustomValidatorFactory validatorFactory,
            AuthenticationManager authManager, JwtTokenAuthentificationProvider tokenProvider, BadgeRepository badgeRepo) {
        this.repo = repo;
        this.converter = converter;
        this.validatorFactory = validatorFactory;
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
        this.badgeRepo = badgeRepo;
    }

    @Override
    public void createUser(FullUserDto user) {
        CustomValidator<FullUserDto, String> validator = validatorFactory.getUserDtoForCreateValidator();
        validator.validate(user);
        validator.verify("User cannot be created.");
        User userDetails = repo.getByEmail(user.email);
        if (userDetails == null) {
            repo.create(converter.toUserForCreation(user));
        } else {
            throw new ObjectAlreadyExistsException("Email(" + user.email + ") has already been taken");
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public UserDto getUser(String id) {
        return converter.fromUser(repo.get(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') and #userId == authentication.principal.id")
    public void modifyUser(FullUserDto user, String userId) {
        CustomValidator<FullUserDto, String> validator = validatorFactory.getUserDtoForCreateValidator();
        validator.validate(userId, user);
        validator.verify("User cannot be modified.");
        User userByEmail = repo.getByEmail(user.email);
        if (Objects.isNull(userByEmail)) {
            User userById = repo.get(userId);
            User userToSave = converter.toUser(user);
            userToSave.setBadges(userById.getBadges());
            repo.save(userId, userToSave);
        } else {
            User userById = repo.get(userId);
            if (!Objects.isNull(userById) && userById.getId().equals(userId) && userById.getEmail().equals(user.email)) {
                User userToSave = converter.toUser(user);
                userToSave.setBadges(userById.getBadges());
                repo.save(userId, converter.toUser(user));
            } else {
                throw new ObjectAlreadyExistsException("Email(" + user.email + ") has already been taken");
            }
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<TrailDto> getSuggestedTrails(String userId) {
        
        Set<TrailDto> trails = converter.fromUser(repo.get(userId)).favoritesTrails;
        if (trails != null && !trails.isEmpty()) {
            return trails.stream().collect(Collectors.toList());
        }
        return new ArrayList<TrailDto>();
    }

    @Override
    public TokenDto loginUser(UserCredentialsDto credentials) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.emailAdress, credentials.password));
        User userDetails = repo.getByEmail(credentials.emailAdress);
        String token = tokenProvider.createToken(userDetails.getEmail(), userDetails.getRole(), userDetails.getId());
        return new TokenDto(token);
    }
}
