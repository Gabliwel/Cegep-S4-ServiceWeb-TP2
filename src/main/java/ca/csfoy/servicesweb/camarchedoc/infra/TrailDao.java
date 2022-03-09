package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.domain.SearchTrailCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;

public interface TrailDao extends Dao<Trail, String> {

    boolean doesExist(String name, String city);

    List<Trail> search(SearchTrailCriteria criteria);
    
    void setTrailReady(String id);
}
