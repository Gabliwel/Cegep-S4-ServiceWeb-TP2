package ca.csfoy.servicesweb.camarchedoc.domain;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;

public interface TrailRepository extends Repository<Trail, String> {
    
    List<Trail> getBySearchCriteria(SearchTrailCriteria criteria);

    void setTrailToReady(String id);

}
