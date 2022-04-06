package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;

public interface RatingDao extends JpaRepository<Rating, String> {

    @Query("SELECT s FROM Rating s WHERE s.trail.id = ?1")
    List<Rating> searchByTrailId(String trailId);
}
