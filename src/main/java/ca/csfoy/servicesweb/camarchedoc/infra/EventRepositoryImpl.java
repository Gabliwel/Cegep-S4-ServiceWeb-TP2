package ca.csfoy.servicesweb.camarchedoc.infra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.csfoy.servicesweb.camarchedoc.domain.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.EventRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.SearchEventCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

public class EventRepositoryImpl implements EventRepository {

    private final EventDao eventDao;
    private final TrailDao trailDao;

    public EventRepositoryImpl(EventDao eventDao, TrailDao trailDao) {
        this.eventDao = eventDao;
        this.trailDao = trailDao;
    }

    @Override
    public Event getById(String id) {
        Optional<Event> event = eventDao.findById(id);

        if (event.isPresent()) {
            return event.get();
        } else {
            throw new ObjectNotFoundException("The event with id (" + id + ") does not exist.");
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> notPassedEvents = new ArrayList<Event>();
        List<Event> eventsDaos = eventDao.findAll();
        for (Event eventDao : eventsDaos) {
            if(eventDao.getStartDate().isAfter(LocalDate.now()))
             {
                notPassedEvents.add(eventDao);
            }
        }
        
        return notPassedEvents;
    }

    @Override
    public Event create(Event event) {
        String id = event.getTrailId();
        if (eventDao.searchAndWithDateAndTrailId(event.getStartDate(), id).isEmpty()) {
            if (!trailDao.findById(id).isEmpty()) {
                return eventDao.save(event);
            } else {
                throw new ObjectNotFoundException("The trail for this event (id:" + id + ") does not exist.");
            }
        } else {
            throw new ObjectAlreadyExistsException("An event already exists for this trail and date.");
        }
    }

    @Override
    public void modify(String id, Event event) {
        if (!eventDao.findById(id).isEmpty()) {
            eventDao.save(event);
        } else {
            throw new ObjectNotFoundException("The event with id (" + id + ") does not exist, and therefore cannot be modified.");
        }
    }

    @Override
    public void delete(String id) {
        if (eventDao.findById(id).isPresent()) {
            Event e = eventDao.getById(id);
            eventDao.delete(e);
        } else {
            throw new ObjectNotFoundException("The event with id (" + id + ") does not exist, and therefore cannot be deleted.");
        }
    }

    @Override
    public List<Event> getBySearchCriteria(SearchEventCriteria criteria) {
        if (criteria.getTrailId() != "") {
            if(criteria.getStartDate() != null) {
                return eventDao.searchAndWithDateAndTrailId(criteria.getStartDate(), criteria.getTrailId());
            } else {
                return eventDao.searchOnlyWithTrailId(criteria.getTrailId());
            }
        } else {
            if(criteria.getStartDate() != null) {
                return eventDao.searchOnlyWithStartDate(criteria.getStartDate());
                
            } else {
                return this.getAll();
            }
        }
    }
}
