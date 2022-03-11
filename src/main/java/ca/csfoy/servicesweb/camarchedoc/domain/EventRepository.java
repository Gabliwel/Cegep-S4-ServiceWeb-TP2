package ca.csfoy.servicesweb.camarchedoc.domain;

import java.util.List;

public interface EventRepository extends Repository<Event, String> {

    List<Event> getBySearchCriteria(SearchEventCriteria criteria);
}
