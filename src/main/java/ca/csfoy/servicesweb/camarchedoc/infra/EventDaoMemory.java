package ca.csfoy.servicesweb.camarchedoc.infra;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.csfoy.servicesweb.camarchedoc.domain.Event;

public class EventDaoMemory implements EventDao {

    private Map<String, Event> events;

    public EventDaoMemory() {
        this.events = new HashMap<>();
    }

    @Override
    public boolean doesExist(String id) {
        return events.containsKey(id);
    }

    @Override
    public boolean doesExist(LocalDate startDate, String trailId) {
        long count = events.values().stream()
                .filter(event -> event.getStartDate().equals(startDate))
                .filter(event -> event.getTrailId().equals(trailId))
                .count();
        
        return count != 0;        
    }

    @Override
    public Event selectById(String id) {
        return events.get(id);
    }

    @Override
    public List<Event> selectAll() {
        return events.values().stream().collect(Collectors.toList());
    }

    @Override
    public Event insert(Event event) {
        events.put(event.getId(), event);
        return event;
    }

    @Override
    public void update(String id, Event event) {
        events.put(id, event);
    }

    @Override
    public void delete(String id) {
        events.remove(id);
    }
}
