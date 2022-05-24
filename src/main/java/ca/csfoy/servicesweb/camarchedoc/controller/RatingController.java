package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.RatingConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingService;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;
import ca.csfoy.servicesweb.camarchedoc.security.SecurityPrincipal;

@RestController
public class RatingController implements RatingResource {
    
    private final RatingRepository repository;
    private final RatingConverter converter;
    private final CustomValidatorFactory validatorFactory;
    private final RatingService ratingService;
    private final UserRepository userRepo;
    
    public RatingController(RatingRepository repository, RatingConverter converter, CustomValidatorFactory validatorFactory, RatingService ratingService,
            UserRepository userRepo) {
        this.repository = repository;
        this.converter = converter;
        this.validatorFactory = validatorFactory;
        this.ratingService = ratingService;
        this.userRepo = userRepo;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public RatingDto getById(String id) {
        CustomValidator<RatingDto, String> validator = validatorFactory.getRatingValidator();
        validator.validateId(id);
        validator.verify("Rating cannot be obtained. Invalid ID format");
        return converter.convertToRatingDtoFrom(repository.getById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostFilter("filterObject.user.id == authentication.principal.id")
    public List<RatingDto> getAll() {
        return converter.convertToRatingDtoListFrom(repository.getAll());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<RatingDto> search(String id) {
        CustomValidator<RatingDto, String> validator = validatorFactory.getRatingValidator();
        validator.validateId(id);
        validator.verify("Ratings cannot be obtained. Invalid ID format");
        return converter.convertToRatingDtoListFrom(repository.searchByTrailId(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public RatingDto create(RatingDto dto) {
        CustomValidator<RatingDto, String> validator = validatorFactory.getRatingValidator();
        validator.validate(dto);
        if (!Objects.isNull(dto.getTrail())) { 
            validator.validateId(dto.getTrailId()); 
        }
        validator.verify("Rating cannot be created. Invalid format");
        SecurityPrincipal principal = getPrincipal();
        String userId = principal.getId();
        User user = userRepo.get(userId);
        Rating rating = ratingService.createRating(converter.convertToRatingAtCreationFrom(dto, user));
        return converter.convertToRatingDtoFrom(rating);
    }
    
    SecurityPrincipal getPrincipal() {
        return (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
