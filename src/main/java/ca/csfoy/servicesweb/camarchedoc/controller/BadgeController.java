package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDtoWithoutDesc;
import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeResource;
import ca.csfoy.servicesweb.camarchedoc.api.badge.SmallBadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.BadgeConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.Badge;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.BadgeRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

@RestController
public class BadgeController implements BadgeResource {
    
    private final BadgeRepository badgeRepo;
    private final BadgeConverter badgeConverter;
    private final CustomValidatorFactory validatorFactory;
    private final UserRepository userRepo;
    
    public BadgeController(BadgeRepository badgeRepo, BadgeConverter badgeConverter, CustomValidatorFactory validatorFactory, UserRepository userRepo) {
        this.badgeRepo = badgeRepo;
        this.badgeConverter = badgeConverter;
        this.validatorFactory = validatorFactory;
        this.userRepo = userRepo;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public BadgeDtoWithoutDesc getById(String id) {
        CustomValidator<BadgeDto, String> validator = validatorFactory.getBadgeValidator();
        validator.validateId(id);
        validator.verify("Badge cannot be obtained. Invalid ID format");
        return badgeConverter.badgeToDto(badgeRepo.getById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<SmallBadgeDto> getAll() {
        return badgeConverter.badgeListToDtoList(badgeRepo.getAll());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void giveBadge(String id, String userId) {
        CustomValidator<BadgeDto, String> validator = validatorFactory.getBadgeValidator();
        validator.validateId(id);
        validator.verify("Badge cannot be obtained. Invalid ID format");
        CustomValidator<FullUserDto, String> userValidator = validatorFactory.getUserDtoForCreateValidator();
        userValidator.validateId(userId);
        userValidator.verify("User cannot be obtained. Invalid ID format");
        Badge badge = badgeRepo.getById(id);
        User user = userRepo.get(userId);
        if (badge != null && user != null) {
            user.addBagde(badge);
            userRepo.save(userId, user);
        }
    }

}
