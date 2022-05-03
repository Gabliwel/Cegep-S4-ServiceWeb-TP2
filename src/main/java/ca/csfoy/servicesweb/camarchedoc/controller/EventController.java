package ca.csfoy.servicesweb.camarchedoc.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import ca.csfoy.servicesweb.camarchedoc.api.event.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.event.EventResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.EventConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.event.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.event.EventRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.event.SearchEventCriteria;

@RestController
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public EventDto getById(String id) {
        CustomValidator<EventDto, String> validator = validatorFactory.getEventValidator();
        validator.validateId(id);
        validator.verify("Event cannot be obtained. Invalid ID format");
        return converter.convertToEventDtoFrom(repository.getById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<EventDto> getAll() {
        return converter.convertEventListFrom(repository.getAll());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public EventDto create(EventDto dto) {
        CustomValidator<EventDto, String> validator = validatorFactory.getEventValidator();
        validator.validate(dto);
        validator.verify("Event cannot be created. Invalid information");
        
        Event event = repository.create(converter.convertToEventAtCreationFrom(dto));
        return converter.convertToEventDtoFrom(event);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(String id, EventDto dto) {
        CustomValidator<EventDto, String> validator = validatorFactory.getEventValidator();
        validator.validate(id, dto);
        validator.verify("Event cannot be created. Invalid information");
        
        repository.modify(id, converter.convertToEventFrom(dto));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(String id) {
        
        CustomValidator<EventDto, String> validator = validatorFactory.getEventValidator();
        validator.validateId(id);
        validator.verify("Event cannot be obtained. Invalid ID format");
        
        repository.delete(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<EventDto> search(String trailId, LocalDate startDate) {
        return converter.convertEventListFrom(repository.getBySearchCriteria(new SearchEventCriteria(trailId, startDate)));
    }
}
