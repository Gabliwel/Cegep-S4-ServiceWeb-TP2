package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.RatingConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingService;

@RestController
public class RatingController implements RatingResource {
    
    private final RatingRepository repository;
    private final RatingConverter converter;
    private final CustomValidatorFactory validatorFactory;
    private final RatingService ratingService;
    
    public RatingController(RatingRepository repository, RatingConverter converter, CustomValidatorFactory validatorFactory, RatingService ratingService) {
        this.repository = repository;
        this.converter = converter;
        this.validatorFactory = validatorFactory;
        this.ratingService = ratingService;
    }

    @Override
    public RatingDto getById(String id) {
        CustomValidator<RatingDto, String> validator = validatorFactory.getRatingValidator();
        validator.validateId(id);
        validator.verify("Rating cannot be obtained. Invalid ID format");
        return converter.convertToRatingDtoFrom(repository.getById(id));
    }

    @Override
    public List<RatingDto> getAll() {
        return converter.convertToRatingDtoListFrom(repository.getAll());
    }

    @Override
    public List<RatingDto> search(String id) {
        CustomValidator<RatingDto, String> validator = validatorFactory.getRatingValidator();
        validator.validateId(id);
        validator.verify("Ratings cannot be obtained. Invalid ID format");
        return converter.convertToRatingDtoListFrom(repository.searchByTrailId(id));
    }

    @Override
    public RatingDto create(RatingDto dto) {
        CustomValidator<RatingDto, String> validator = validatorFactory.getRatingValidator();
        validator.validate(dto);
        if (dto.trail != null) { 
            validator.validateId(dto.trail.id); 
        }
        if (dto.user != null) {
            validator.validateId(dto.user.id);
        }
        validator.verify("Rating cannot be created. Invalid format");
        Rating rating = ratingService.createRating(converter.convertToRatingAtCreationFrom(dto));
        return converter.convertToRatingDtoFrom(rating);
    }

}
