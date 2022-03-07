package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.domain.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.EventRepository;
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
        Event event = eventDao.selectById(id);

        if (event != null) {
            return event;
        } else {
            throw new ObjectNotFoundException("The event with id (" + id + ") does not exist.");
        }
    }

    @Override
    public List<Event> getAll() {
        return eventDao.selectAll();
    }

    @Override
    public Event create(Event event) {
        String id = event.getTrailId();
        if (!eventDao.doesExist(event.getStartDate(), id)) {
            if (trailDao.doesExist(id)) {
                return eventDao.insert(event);
            } else {
                throw new ObjectNotFoundException("The trail for this event (id:" + id + ") does not exist.");
            }
        } else {
            throw new ObjectAlreadyExistsException("An event already exists for this trail and date.");
        }
    }

    @Override
    public void modify(String id, Event event) {
        if (eventDao.doesExist(id)) {
            eventDao.update(id, event);
        } else {
            throw new ObjectNotFoundException("The event with id (" + id + ") does not exist, and therefore cannot be modified.");
        }
    }

    @Override
    public void delete(String id) {
        if (eventDao.doesExist(id)) {
            eventDao.delete(id);
        } else {
            throw new ObjectNotFoundException("The event with id (" + id + ") does not exist, and therefore cannot be deleted.");
        }
    }
}
