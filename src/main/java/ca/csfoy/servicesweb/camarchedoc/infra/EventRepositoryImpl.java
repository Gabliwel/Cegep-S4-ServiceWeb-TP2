package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;
import java.util.Optional;

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
        Optional<Event> event = eventDao.findById(id);

        if (event.isPresent()) {
            return event.get();
        } else {
            throw new ObjectNotFoundException("The event with id (" + id + ") does not exist.");
        }
    }

    @Override
    public List<Event> getAll() {
        return eventDao.findAll();
    }

    @Override
    public Event create(Event event) {
        String id = event.getTrailId();
        if (eventDao.doesExist(event.getStartDate(), id).isEmpty()) {
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
}
