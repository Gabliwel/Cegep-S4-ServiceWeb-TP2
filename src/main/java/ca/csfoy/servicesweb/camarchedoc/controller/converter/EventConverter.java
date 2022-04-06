package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import ca.csfoy.servicesweb.camarchedoc.api.event.EventDto;
import ca.csfoy.servicesweb.camarchedoc.domain.event.Event;

public class EventConverter {
    
    private final TrailConverter trailConverter;
    
    public EventConverter(TrailConverter trailConverter) {
        this.trailConverter = trailConverter;
    }

    public Event convertToEventAtCreationFrom(EventDto dto) {
        return new Event(dto.name, dto.description, dto.startDate, trailConverter.convertToTrailFrom(dto.trail), dto.organizer);
    }

    public Event convertToEventFrom(EventDto dto) {
        return new Event(dto.id, dto.name, dto.description, dto.startDate, trailConverter.convertToTrailFrom(dto.trail), dto.organizer);
    }

    public EventDto convertToEventDtoFrom(Event event) {
        return new EventDto(event.getId(), 
                event.getName(), 
                event.getDescription(), 
                event.getStartDate(), 
                trailConverter.convertToTrailDtoFrom(event.getTrail()), 
                event.getOrganizer());
    }

    public List<EventDto> convertEventListFrom(List<Event> events) {
        return events.stream().map(this::convertToEventDtoFrom).collect(Collectors.toList());
    }
}
