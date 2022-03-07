package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.EventResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.EventConverter;
import ca.csfoy.servicesweb.camarchedoc.domain.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.EventRepository;

public class EventController implements EventResource {

    private final EventRepository repository;
    private final EventConverter converter;

    public EventController(EventRepository repository, EventConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public EventDto getById(String id) {
        return converter.convertToEventDtoFrom(repository.getById(id));
    }

    @Override
    public List<EventDto> getAll() {
        return converter.convertEventListFrom(repository.getAll());
    }

    @Override
    public EventDto create(EventDto dto) {
        Event event = repository.create(converter.convertToEventAtCreationFrom(dto));
        return converter.convertToEventDtoFrom(event);
    }

    @Override
    public void update(String id, EventDto dto) {
        repository.modify(id, converter.convertToEventFrom(dto));
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}
