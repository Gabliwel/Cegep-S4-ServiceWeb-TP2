package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.csfoy.servicesweb.camarchedoc.domain.SearchTrailCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;

public interface TrailDao extends JpaRepository<Trail, String> {

    @Query("SELECT s FROM Trail s WHERE s.name = ?1 AND s.city = ?2")
    List<Trail> doesExist(String name, String city);

    //quoi faire????
    List<Trail> search(SearchTrailCriteria criteria);
}
