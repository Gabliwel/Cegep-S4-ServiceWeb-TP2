package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;

public interface TrailDao extends JpaRepository<Trail, String> {

    @Query("SELECT s FROM Trail s WHERE s.name = ?1 AND s.city = ?2")
    List<Trail> doesExist(String name, String city);
    
    @Query("SELECT s FROM Trail s WHERE s.city = ?1 AND s.difficulty = ?2")
    List<Trail> fullSearch(String city, TrailDifficulty difficulty);
    
    @Query("SELECT s FROM Trail s WHERE s.city = ?1")
    List<Trail> searchOnlyWithCity(String city);
    
    @Query("SELECT s FROM Trail s WHERE s.difficulty = ?1")
    List<Trail> searchOnlyWithDifficulty(TrailDifficulty difficulty);
}
