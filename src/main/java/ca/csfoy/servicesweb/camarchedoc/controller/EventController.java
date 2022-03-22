package ca.csfoy.servicesweb.camarchedoc.controller;

import java.time.LocalDate;
import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.EventResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.EventConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.EventRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.SearchEventCriteria;

public class EventController implements EventResource {

    private final EventRepository repository;
    private final EventConverter converter;
    private final CustomValidatorFactory validatorFactory;
    
    public EventController(EventRepository repository, EventConverter converter, CustomValidatorFactory validatorFactory) {
        this.repository = repository;
        this.converter = converter;
        this.validatorFactory = validatorFactory;
    }

    @Override
    public EventDto getById(String id) {
        CustomValidator<EventDto, String> validator = validatorFactory.getEventValidator();
        validator.validateId(id);
        validator.verify("Event cannot be obtained. Invalid ID format");
        return converter.convertToEventDtoFrom(repository.getById(id));
    }

    @Override
    public List<EventDto> getAll() {
        return converter.convertEventListFrom(repository.getAll());
    }

    @Override
    public EventDto create(EventDto dto) {
        CustomValidator<EventDto, String> validator = validatorFactory.getEventValidator();
        validator.validate(dto);
        validator.verify("Event cannot be created. Invalid information");
        
        Event event = repository.create(converter.convertToEventAtCreationFrom(dto));
        return converter.convertToEventDtoFrom(event);
    }

    @Override
    public void update(String id, EventDto dto) {
        CustomValidator<EventDto, String> validator = validatorFactory.getEventValidator();
        validator.validate(id, dto);
        validator.verify("Event cannot be created. Invalid information");
        
        repository.modify(id, converter.convertToEventFrom(dto));
    }

    @Override
    public void delete(String id) {
        
        CustomValidator<EventDto, String> validator = validatorFactory.getEventValidator();
        validator.validateId(id);
        validator.verify("Event cannot be obtained. Invalid ID format");
        
        repository.delete(id);
    }

    @Override
    public List<EventDto> search(String trailId, LocalDate startDate) {
        return converter.convertEventListFrom(repository.getBySearchCriteria(new SearchEventCriteria(trailId, startDate)));
    }
}
