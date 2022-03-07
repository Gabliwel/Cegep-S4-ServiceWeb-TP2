package ca.csfoy.servicesweb.camarchedoc.infra;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.domain.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
class EventRepositoryImplTest {

    public static final String ANY_ID = "1";
    public static final LocalDate ANY_DATE = LocalDate.now();

    @Mock
    private EventDao eventDao;

    @Mock
    private TrailDao trailDao;

    @InjectMocks
    private EventRepositoryImpl repo;

    @Test
    void whenGetByIdWithExistingIdThenEventWithCorrespondingIdIsReturned() {
        Event event = Mockito.mock(Event.class);
        Mockito.when(eventDao.selectById(ANY_ID)).thenReturn(event);

        Event eventReturned = repo.getById(ANY_ID);

        Assertions.assertSame(event, eventReturned);
    }

    @Test
    void whenGetByIdWithNonExistingIdThenEventWithCorrespondingIdIsReturned() {
        Mockito.when(eventDao.selectById(ANY_ID)).thenReturn(null);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.getById(ANY_ID));
    }

    @Test
    void whenGetAllEventsThenAllEventsReturned() {
        Event event1 = Mockito.mock(Event.class);
        Event event2 = Mockito.mock(Event.class);
        Mockito.when(eventDao.selectAll()).thenReturn(List.of(event1, event2));

        List<Event> events = repo.getAll();

        Assertions.assertEquals(List.of(event1, event2), events);
    }

    @Test
    void whenCreateEventWithExistingTrailThenEventIsCreated() {
        Event event1 = Mockito.mock(Event.class);
        Mockito.when(event1.getTrailId()).thenReturn(ANY_ID);
        Mockito.when(trailDao.doesExist(ANY_ID)).thenReturn(true);

        repo.create(event1);

        Mockito.verify(eventDao).insert(event1);
    }

    @Test
    void whenCreateEventWithNonExistingTrailThenEventIsNotCreated() {
        Event event1 = Mockito.mock(Event.class);
        Mockito.when(event1.getTrailId()).thenReturn(ANY_ID);
        Mockito.when(trailDao.doesExist(ANY_ID)).thenReturn(false);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.create(event1));
    }

    @Test
    void whenCreateEventWithExistingTrailAndDateInRepoThenEventIsNotCreated() {
        Event event1 = Mockito.mock(Event.class);
        Mockito.when(event1.getTrailId()).thenReturn(ANY_ID);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_DATE);
        Mockito.when(eventDao.doesExist(ANY_DATE, ANY_ID)).thenReturn(true);

        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> repo.create(event1));
    }

    @Test
    void whenModifyExistingEventThenEventIsModified() {
        Event event1 = Mockito.mock(Event.class);
        Mockito.when(eventDao.doesExist(ANY_ID)).thenReturn(true);

        repo.modify(ANY_ID, event1);

        Mockito.verify(eventDao).update(ANY_ID, event1);
    }

    @Test
    void whenModifyNonExistingEventThenEventIsNotModified() {
        Event event1 = Mockito.mock(Event.class);
        Mockito.when(eventDao.doesExist(ANY_ID)).thenReturn(false);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.modify(ANY_ID, event1));
    }

    @Test
    void whenDeleteExistingEventThenEventIsDeleted() {
        Mockito.when(eventDao.doesExist(ANY_ID)).thenReturn(true);

        repo.delete(ANY_ID);

        Mockito.verify(eventDao).delete(ANY_ID);
    }

    @Test
    void whenDeleteNonExistingEventThenEventIsNotDeleted() {
        Mockito.when(eventDao.doesExist(ANY_ID)).thenReturn(false);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.delete(ANY_ID));
    }
}
