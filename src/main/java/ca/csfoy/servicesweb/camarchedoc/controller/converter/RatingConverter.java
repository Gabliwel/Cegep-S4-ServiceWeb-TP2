package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import ca.csfoy.servicesweb.camarchedoc.api.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.domain.Rating;

public class RatingConverter {

    public RatingDto convertToRatingDtoFrom(Rating rating) {
        return new RatingDto(rating.getId(), rating.getTrail(), rating.getNote(), rating.getComment());
    }

    public List<RatingDto> convertToRatingDtoListFrom(List<Rating> all) {
        return all.stream().map(this::convertToRatingDtoFrom).collect(Collectors.toList());
    }

    public Rating convertToRatingAtCreationFrom(RatingDto dto) {
        return new Rating(dto.trail, dto.note, dto.comment);
    }

}
