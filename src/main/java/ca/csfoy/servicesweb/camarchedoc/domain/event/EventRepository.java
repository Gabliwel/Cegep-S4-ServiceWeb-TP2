package ca.csfoy.servicesweb.camarchedoc.domain.event;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.domain.Repository;

public interface EventRepository extends Repository<Event, String> {

    List<Event> getBySearchCriteria(SearchEventCriteria criteria);
}
