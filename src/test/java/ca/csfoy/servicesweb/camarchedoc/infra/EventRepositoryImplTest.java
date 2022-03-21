package ca.csfoy.servicesweb.camarchedoc.infra;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.domain.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.SearchEventCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
class EventRepositoryImplTest {

    public static final String ANY_ID = "1";
    public static final LocalDate ANY_AFTER_DATE = LocalDate.now().plusDays(1);
    public static final LocalDate ANY_BEFORE_DATE = LocalDate.now().minusDays(1);

    @Mock
    private EventDao eventDao;

    @Mock
    private TrailDao trailDao;

    @InjectMocks
    private EventRepositoryImpl repo;

    @Test
    void whenGetByIdWithExistingIdThenEventWithCorrespondingIdIsReturned() {
        Event event = Mockito.mock(Event.class);
        Optional<Event> lst = Optional.of(event);
        Mockito.when(eventDao.findById(ANY_ID)).thenReturn(lst);

        Event eventReturned = repo.getById(ANY_ID);

        Assertions.assertSame(event, eventReturned);
    }

    @Test
    void whenGetByIdWithNonExistingIdThenEventWithCorrespondingIdIsReturned() {
        Optional<Event> lst = Optional.empty();
        Mockito.when(eventDao.findById(ANY_ID)).thenReturn(lst);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.getById(ANY_ID));
    }

    @Test
    void whenGetAllEventsThenAllEventsAfterCurrentDateReturned() {
        Event event1 = Mockito.mock(Event.class);
        Event event2 = Mockito.mock(Event.class);
        List<Event> lst = List.of(event1, event2);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_AFTER_DATE);
        Mockito.when(event2.getStartDate()).thenReturn(ANY_AFTER_DATE);
        Mockito.when(eventDao.findAll()).thenReturn(lst);

        List<Event> events = repo.getAll();

        Mockito.verify(eventDao).findAll();
        Assertions.assertEquals(lst, events);
    }
    
    void whenGetAllEventsThenAllEventsBeforeCurrentDateNotReturned() {
        Event event1 = Mockito.mock(Event.class);
        Event event2 = Mockito.mock(Event.class);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_BEFORE_DATE);
        Mockito.when(event2.getStartDate()).thenReturn(ANY_BEFORE_DATE);
        Mockito.when(eventDao.findAll()).thenReturn(List.of(event1, event2));

        List<Event> events = repo.getAll();

        Mockito.verify(eventDao).findAll();
        Assertions.assertEquals(0, events.size());
    }

    @Test
    void whenCreateEventWithExistingTrailThenEventIsCreated() {
        Event event1 = Mockito.mock(Event.class);
        Mockito.when(event1.getTrailId()).thenReturn(ANY_ID);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_AFTER_DATE);
        List<Event> lst = List.of();
        Mockito.when(eventDao.searchAndWithDateAndTrailId(event1.getStartDate(), ANY_ID)).thenReturn(lst);
        Optional<Trail> lst2 = Optional.of(Mockito.mock(Trail.class));
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst2);

        repo.create(event1);

        Mockito.verify(eventDao).save(event1);
    }

    @Test
    void whenCreateEventWithNonExistingTrailThenEventIsNotCreated() {
        Event event1 = Mockito.mock(Event.class);
        Mockito.when(event1.getTrailId()).thenReturn(ANY_ID);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_AFTER_DATE);
        List<Event> lst = List.of();
        Mockito.when(eventDao.searchAndWithDateAndTrailId(event1.getStartDate(), ANY_ID)).thenReturn(lst);
        Optional<Trail> lst2 = Optional.empty();
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst2);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.create(event1));
    }

    @Test
    void whenCreateEventWithExistingTrailAndDateInRepoThenEventIsNotCreated() {
        Event event1 = Mockito.mock(Event.class);
        Mockito.when(event1.getTrailId()).thenReturn(ANY_ID);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_AFTER_DATE);
        List<Event> lst = List.of(Mockito.mock(Event.class));
        Mockito.when(eventDao.searchAndWithDateAndTrailId(event1.getStartDate(), ANY_ID)).thenReturn(lst);

        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> repo.create(event1));
    }

    @Test
    void whenModifyExistingEventThenEventIsModified() {
        Event event1 = Mockito.mock(Event.class);
        Optional<Event> lst = Optional.of(event1);
        Mockito.when(eventDao.findById(ANY_ID)).thenReturn(lst);

        repo.modify(ANY_ID, event1);

        Mockito.verify(eventDao).save(event1);
    }

    @Test
    void whenModifyNonExistingEventThenEventIsNotModified() {
        Event event1 = Mockito.mock(Event.class);
        Optional<Event> lst = Optional.empty();
        Mockito.when(eventDao.findById(ANY_ID)).thenReturn(lst);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.modify(ANY_ID, event1));
    }

    @Test
    void whenDeleteExistingEventThenEventIsDeleted() {
        Event event1 = Mockito.mock(Event.class);
        Optional<Event> lst = Optional.of(event1);
        Mockito.when(eventDao.findById(ANY_ID)).thenReturn(lst);
        Mockito.when(eventDao.getById(ANY_ID)).thenReturn(event1);

        repo.delete(ANY_ID);

        Mockito.verify(eventDao).delete(event1);
    }

    @Test
    void whenDeleteNonExistingEventThenEventIsNotDeleted() {
        Optional<Event> lst = Optional.empty();
        Mockito.when(eventDao.findById(ANY_ID)).thenReturn(lst);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.delete(ANY_ID));
    }
    
    @Test
    void whenSearchWithNotingReturnsEveryNotPassedEvents() {
        SearchEventCriteria criteria = new SearchEventCriteria("", null);
        Event event1 = Mockito.mock(Event.class);
        Event event2 = Mockito.mock(Event.class);
        List<Event> lst = List.of(event1, event2);
        Mockito.when(eventDao.findAll()).thenReturn(lst);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_AFTER_DATE);
        Mockito.when(event2.getStartDate()).thenReturn(ANY_BEFORE_DATE);
        
        List<Event> result = repo.getBySearchCriteria(criteria);
        
        Mockito.verify(eventDao).findAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertSame(event1, result.get(0));
    }
    
    @Test
    void whenSearchWithOnlyTrailIdReturnsNotPassedEvents() {
        SearchEventCriteria criteria = new SearchEventCriteria("a", null);
        Event event1 = Mockito.mock(Event.class);
        Event event2 = Mockito.mock(Event.class);
        List<Event> lst = List.of(event1, event2);
        Mockito.when(eventDao.searchOnlyWithTrailId(criteria.getTrailId())).thenReturn(lst);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_AFTER_DATE);
        Mockito.when(event2.getStartDate()).thenReturn(ANY_BEFORE_DATE);
        
        List<Event> result = repo.getBySearchCriteria(criteria);
        
        Mockito.verify(eventDao).searchOnlyWithTrailId(criteria.getTrailId());
        Assertions.assertEquals(1, result.size());
        Assertions.assertSame(event1, result.get(0));
    }
    
    @Test
    void whenSearchWithOnlyStartDatedReturnsNotPassedEvents() {
        SearchEventCriteria criteria = new SearchEventCriteria("", LocalDate.now());
        Event event1 = Mockito.mock(Event.class);
        Event event2 = Mockito.mock(Event.class);
        List<Event> lst = List.of(event1, event2);
        Mockito.when(eventDao.searchOnlyWithStartDate(criteria.getStartDate())).thenReturn(lst);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_AFTER_DATE);
        Mockito.when(event2.getStartDate()).thenReturn(ANY_BEFORE_DATE);
        
        List<Event> result = repo.getBySearchCriteria(criteria);
        
        Mockito.verify(eventDao).searchOnlyWithStartDate(criteria.getStartDate());
        Assertions.assertEquals(1, result.size());
        Assertions.assertSame(event1, result.get(0));
    }
    
    @Test
    void whenSearchWithStartDatedAndTrailIdReturnsNotPassedEvents() {
        SearchEventCriteria criteria = new SearchEventCriteria("a", LocalDate.now());
        Event event1 = Mockito.mock(Event.class);
        Event event2 = Mockito.mock(Event.class);
        List<Event> lst = List.of(event1, event2);
        Mockito.when(eventDao.searchAndWithDateAndTrailId(criteria.getStartDate(), criteria.getTrailId())).thenReturn(lst);
        Mockito.when(event1.getStartDate()).thenReturn(ANY_AFTER_DATE);
        Mockito.when(event2.getStartDate()).thenReturn(ANY_BEFORE_DATE);
        
        List<Event> result = repo.getBySearchCriteria(criteria);
        
        Mockito.verify(eventDao).searchAndWithDateAndTrailId(criteria.getStartDate(), criteria.getTrailId());
        Assertions.assertEquals(1, result.size());
        Assertions.assertSame(event1, result.get(0));
    }
}


