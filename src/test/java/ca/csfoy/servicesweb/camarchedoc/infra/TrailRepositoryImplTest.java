package ca.csfoy.servicesweb.camarchedoc.infra;

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

import ca.csfoy.servicesweb.camarchedoc.domain.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
class TrailRepositoryImplTest {

    public static final String ANY_ID = "1";
    public static final String ANY_NAME = "Name";
    public static final String ANY_CITY = "City";

    @Mock
    private TrailDao trailDao;

    @InjectMocks
    private TrailRepositoryImpl repo;

    @Test
    void whenGetByIdWithExistingIdThenTrailWithCorrespondingIdIsReturned() {
        Trail trail = Mockito.mock(Trail.class);
        Optional<Trail> lst = Optional.of(Mockito.mock(Trail.class));
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst);
        Mockito.when(trailDao.getById(ANY_ID)).thenReturn(trail);

        Trail trailReturned = repo.getById(ANY_ID);

        Mockito.verify(trailDao).findById(ANY_ID);
        Assertions.assertSame(trail, trailReturned);
    }

    @Test
    void whenGetByIdWithNonExistingIdThenTrailWithCorrespondingIdIsReturned() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.getById(ANY_ID));
    }

    @Test
    void whenGetAllTrailsThenAllTrailsReturned() {
        Trail trail1 = Mockito.mock(Trail.class);
        Trail trail2 = Mockito.mock(Trail.class);
        Mockito.when(trailDao.findAll()).thenReturn(List.of(trail1, trail2));

        List<Trail> trails = repo.getAll();

        Assertions.assertEquals(List.of(trail1, trail2), trails);
    }

    @Test
    void whenCreateTrailWithSameNameAndCityThenEventIsNotCreated() {
        Trail trail1 = Mockito.mock(Trail.class);
        List<Trail> lst = List.of(Mockito.mock(Trail.class));
        Mockito.when(trail1.getName()).thenReturn(ANY_NAME);
        Mockito.when(trail1.getCity()).thenReturn(ANY_CITY);
        Mockito.when(trailDao.doesExist(ANY_NAME, ANY_CITY)).thenReturn(lst);

        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> repo.create(trail1));
    }

    @Test
    void whenModifyExistingEventThenEventIsModified() {
        Trail trail1 = Mockito.mock(Trail.class);
        Optional<Trail> lst = Optional.of(Mockito.mock(Trail.class));
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst);

        repo.modify(ANY_ID, trail1);

        Mockito.verify(trailDao).save(trail1);
    }

    @Test
    void whenModifyNonExistingEventThenEventIsNotModified() {
        Trail trail1 = Mockito.mock(Trail.class);
        Optional<Trail> lst = Optional.empty();
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.modify(ANY_ID, trail1));
    }

    @Test
    void whenDeleteExistingEventThenEventIsDeleted() {
        Trail trail1 = Mockito.mock(Trail.class);
        Optional<Trail> lst = Optional.of(Mockito.mock(Trail.class));
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst);
        Mockito.when(trailDao.getById(ANY_ID)).thenReturn(trail1);

        repo.delete(ANY_ID);

        Mockito.verify(trailDao).delete(trail1);
    }

    @Test
    void whenDeleteNonExistingEventThenEventIsNotDeleted() {
        Optional<Trail> lst = Optional.empty();
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.delete(ANY_ID));
    }

}
