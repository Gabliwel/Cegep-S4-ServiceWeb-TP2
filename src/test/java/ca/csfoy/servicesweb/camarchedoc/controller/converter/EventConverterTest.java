package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.api.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.EventMother;
import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
class EventConverterTest {
    
    @Mock
    private TrailConverter trailConverter;
    
    @InjectMocks
    private EventConverter converter;

    @Test
    void whenConvertingDtoWithoutIdOnCreationThenEventCreatedWithGivenFieldsAndGeneratedId() {
        Integer nextId = IdentifiantGenerator.getNextId();
        TrailDto trailDto = Mockito.mock(TrailDto.class);
        Trail trail = Mockito.mock(Trail.class);
        Mockito.when(trailConverter.convertToTrailFrom(trailDto)).thenReturn(trail);
        EventDto dto = EventMother.getEventDtoWithId(null, trailDto);

        Event event = converter.convertToEventAtCreationFrom(dto);

        Assertions.assertEquals((nextId + 1) + "", event.getId());
        Assertions.assertEquals(dto.name, event.getName());
        Assertions.assertEquals(dto.description, event.getDescription());
        Assertions.assertEquals(dto.startDate, event.getStartDate());
        Assertions.assertEquals(dto.organizer, event.getOrganizer());
        Assertions.assertEquals(trail, event.getTrail());
    }

    @Test
    void whenConvertingDtoWithIdThenEventCreatedWithGivenFieldsIncludingId() {
        TrailDto trailDto = Mockito.mock(TrailDto.class);
        Trail trail = Mockito.mock(Trail.class);
        Mockito.when(trailConverter.convertToTrailFrom(trailDto)).thenReturn(trail);
        EventDto dto = EventMother.getEventDtoWithId("1", trailDto);

        Event event = converter.convertToEventFrom(dto);

        Assertions.assertEquals(dto.id, event.getId());
        Assertions.assertEquals(dto.name, event.getName());
        Assertions.assertEquals(dto.description, event.getDescription());
        Assertions.assertEquals(dto.startDate, event.getStartDate());
        Assertions.assertEquals(dto.organizer, event.getOrganizer());
        Assertions.assertEquals(trail, event.getTrail());
    }

    @Test
    void whenConvertingDomainObjectthendtoCreatedWithGivenFieldsIncludingId() {
        Trail trail = Mockito.mock(Trail.class);
        Event event = EventMother.getEventWithId("1", trail);
        TrailDto trailDto = Mockito.mock(TrailDto.class);
        Mockito.when(trailConverter.convertToTrailDtoFrom(trail)).thenReturn(trailDto);

        EventDto dto = converter.convertToEventDtoFrom(event);

        Assertions.assertEquals(event.getId(), dto.id);
        Assertions.assertEquals(event.getName(), dto.name);
        Assertions.assertEquals(event.getDescription(), dto.description);
        Assertions.assertEquals(event.getStartDate(), dto.startDate);
        Assertions.assertEquals(event.getOrganizer(), dto.organizer);
        Assertions.assertEquals(trailDto, dto.trail);
    }
    
    @Test
    void whenConvertingListOfDomainObjetsThenListOfDtosIsReturned() {
        Event event1 = EventMother.getEventWithAutomaticId();
        Event event2 = EventMother.getEventWithAutomaticId();
        
        List<EventDto> dtos = converter.convertEventListFrom(List.of(event1, event2));

        Assertions.assertEquals(2, dtos.size());
    }
}
