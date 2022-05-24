package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

@Component
public class RatingConverter {
    
    private final TrailConverter trailConverter;
    private final UserConverter userConverter;
    
    public RatingConverter(TrailConverter trailConverter, UserConverter userConverter) {
        this.trailConverter = trailConverter;
        this.userConverter = userConverter;
    }

    public RatingDto convertToRatingDtoFrom(Rating rating) {
        return new RatingDto(rating.getId(), userConverter.fromUser(rating.getUser()), trailConverter.convertToTrailDtoFrom(rating.getTrail()), 
                rating.getNote(), rating.getComment());
    }

    public List<RatingDto> convertToRatingDtoListFrom(List<Rating> all) {
        return all.stream().map(this::convertToRatingDtoFrom).collect(Collectors.toList());
    }

    public Rating convertToRatingAtCreationFrom(RatingDto dto, User user) {
        return new Rating(user, trailConverter.convertToTrailFrom(dto.trail), dto.note, dto.comment);
    }

}
