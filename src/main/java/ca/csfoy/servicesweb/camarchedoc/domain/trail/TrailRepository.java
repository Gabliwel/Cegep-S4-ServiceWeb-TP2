package ca.csfoy.servicesweb.camarchedoc.domain.trail;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.domain.Repository;

public interface TrailRepository extends Repository<Trail, String> {
    
    List<Trail> getBySearchCriteria(SearchTrailCriteria criteria);

    void setTrailToReady(String id);

}
