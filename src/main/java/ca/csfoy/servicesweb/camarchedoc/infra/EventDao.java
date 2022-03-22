package ca.csfoy.servicesweb.camarchedoc.infra;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.csfoy.servicesweb.camarchedoc.domain.Event;

public interface EventDao extends JpaRepository<Event, String> {
    
    @Query("SELECT s FROM Event s WHERE s.startDate = ?1 AND s.trail.id = ?2")
    List<Event> searchAndWithDateAndTrailId(LocalDate startDate, String trailId);

    @Query("SELECT s FROM Event s WHERE s.trail.id = ?1")
    List<Event> searchOnlyWithTrailId(String trailId);

    @Query("SELECT s FROM Event s WHERE s.startDate = ?1")
    List<Event> searchOnlyWithStartDate(LocalDate startDate);
}
