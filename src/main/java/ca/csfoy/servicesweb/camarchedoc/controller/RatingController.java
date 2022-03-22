package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.RatingResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.RatingConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.RatingRepository;

public class RatingController implements RatingResource {
    
    private final RatingRepository repository;
    private final RatingConverter converter;
    private final CustomValidatorFactory validatorFactory;
    
    public RatingController(RatingRepository repository, RatingConverter converter, CustomValidatorFactory validatorFactory) {
        this.repository = repository;
        this.converter = converter;
        this.validatorFactory = validatorFactory;
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
        validator.verify("Rating cannot be created. Invalid format");
        Rating rating = repository.create(converter.convertToRatingAtCreationFrom(dto));
        return converter.convertToRatingDtoFrom(rating);
    }

}
