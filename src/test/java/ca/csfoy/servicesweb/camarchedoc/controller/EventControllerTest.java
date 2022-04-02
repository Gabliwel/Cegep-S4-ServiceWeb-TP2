package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.api.event.EventDto;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.EventConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.EventCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.domain.event.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.event.EventRepository;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
class EventControllerTest {
    
    private static final String ANY_ID = "1";

    @Mock
    private EventRepository repository;
    
    @Mock
    private EventConverter converter;
    
    @Mock
    private CustomValidatorFactory validatorFactory;
    
    @Mock
    private CustomValidator<EventDto, String> validator;
    
    @InjectMocks
    private EventController controller;

    @Test
    void whenGetByIdWithValidIdThenDomainObjectReturnedAsConvertedDto() {
        EventDto dto = Mockito.mock(EventDto.class);
        Event event = Mockito.mock(Event.class);
        Mockito.when(repository.getById(ANY_ID)).thenReturn(event);
        Mockito.when(converter.convertToEventDtoFrom(event)).thenReturn(dto);
        
        EventCustomValidator eventValidator = Mockito.mock(EventCustomValidator.class);
        
        Mockito.when(validatorFactory.getEventValidator()).thenReturn(eventValidator);
        
        EventDto dtoReturned = controller.getById(ANY_ID);
        
        Mockito.verify(repository).getById(ANY_ID);
        Mockito.verify(converter).convertToEventDtoFrom(event);
        Assertions.assertSame(dto, dtoReturned);
    }
    
    @Test
    void whenGetAllThenListOfDomainObjectsReturnedAsConvertedListOfDtos() {
        EventDto dto1 = Mockito.mock(EventDto.class);
        EventDto dto2 = Mockito.mock(EventDto.class);
        Event event1 = Mockito.mock(Event.class);
        Event event2 = Mockito.mock(Event.class);
        Mockito.when(repository.getAll()).thenReturn(List.of(event1, event2));
        Mockito.when(converter.convertEventListFrom(List.of(event1, event2))).thenReturn(List.of(dto1, dto2));
        
        List<EventDto> dtosReturned = controller.getAll();
        
        Mockito.verify(repository).getAll();
        Mockito.verify(converter).convertEventListFrom(List.of(event1, event2));
        Assertions.assertEquals(List.of(dto1, dto2), dtosReturned);        
    }
    
    @Test
    void whenCreateWithValidEventThenEventIsReturnedAsDto() {
        EventDto dto = Mockito.mock(EventDto.class);
        EventDto returned = Mockito.mock(EventDto.class);
        Event event = Mockito.mock(Event.class);
        Mockito.when(repository.create(event)).thenReturn(event);
        Mockito.when(converter.convertToEventAtCreationFrom(dto)).thenReturn(event);
        Mockito.when(converter.convertToEventDtoFrom(event)).thenReturn(returned);
        
        EventCustomValidator eventValidator = Mockito.mock(EventCustomValidator.class);
        
        Mockito.when(validatorFactory.getEventValidator()).thenReturn(eventValidator);
        
        EventDto dtoCreated = controller.create(dto);
        
        Mockito.verify(repository).create(event);
        Mockito.verify(converter).convertToEventAtCreationFrom(dto);
        Assertions.assertSame(returned, dtoCreated);        
    }
    
    @Test
    void whenUpdateWithValidEventThenEventIsUpdated() {
        EventDto dto = Mockito.mock(EventDto.class);
        Event event = Mockito.mock(Event.class);
        Mockito.when(converter.convertToEventFrom(dto)).thenReturn(event);
        
        EventCustomValidator eventValidator = Mockito.mock(EventCustomValidator.class);
        
        Mockito.when(validatorFactory.getEventValidator()).thenReturn(eventValidator);
        
        controller.update(ANY_ID, dto);
        
        Mockito.verify(repository).modify(ANY_ID, event);
    }
    
    @Test
    void whenDeleteWithValidIdThenEventIsDeleted() {
        
        EventCustomValidator eventValidator = Mockito.mock(EventCustomValidator.class);
        
        Mockito.when(validatorFactory.getEventValidator()).thenReturn(eventValidator);
        
        controller.delete(ANY_ID);
        
        Mockito.verify(repository).delete(ANY_ID);
    }
}
