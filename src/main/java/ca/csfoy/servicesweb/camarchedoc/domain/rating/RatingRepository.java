package ca.csfoy.servicesweb.camarchedoc.domain.rating;

import java.util.List;

public interface RatingRepository {
    Rating getById(String id);

    List<Rating> getAll();
    
    List<Rating> searchByTrailId(String id);

    Rating create(Rating event);

}
