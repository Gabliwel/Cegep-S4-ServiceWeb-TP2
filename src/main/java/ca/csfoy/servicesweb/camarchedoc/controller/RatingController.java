package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.RatingResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.RatingConverter;
import ca.csfoy.servicesweb.camarchedoc.domain.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.RatingRepository;

public class RatingController implements RatingResource {
    
    private final RatingRepository repository;
    private final RatingConverter converter;
    
    public RatingController(RatingRepository repository, RatingConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public RatingDto getById(String id) {
        return converter.convertToRatingDtoFrom(repository.getById(id));
    }

    @Override
    public List<RatingDto> getAll() {
        return converter.convertToRatingDtoListFrom(repository.getAll());
    }

    @Override
    public List<RatingDto> search(String id) {
        return converter.convertToRatingDtoListFrom(repository.searchByTrailId(id));
    }

    @Override
    public RatingDto create(RatingDto dto) {
        Rating rating = repository.create(converter.convertToRatingAtCreationFrom(dto));
        return converter.convertToRatingDtoFrom(rating);
    }

}
