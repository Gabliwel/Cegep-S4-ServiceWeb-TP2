package ca.csfoy.servicesweb.camarchedoc.infra;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.csfoy.servicesweb.camarchedoc.domain.Event;

public interface EventDao extends JpaRepository<Event, String> {
    
    @Query("SELECT s FROM Event s WHERE s.startDate = ?1 AND s.trail.id = ?2")
    List<Event> doesExist(LocalDate startDate, String trailId);

}
