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

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class RatingRepositoryImplTest {
    
    public static final String ANY_ID = "1";
    
    @Mock
    private RatingDao ratingDao;

    @Mock
    private TrailDao trailDao;

    @InjectMocks
    private RatingRepositoryImpl repo;

    @Test
    void whenGetByIdWithExistingIdThenRatingWithCorrespondingIdIsReturned() {
        Rating rating = Mockito.mock(Rating.class);
        Optional<Rating> lst = Optional.of(rating);
        Mockito.when(ratingDao.findById(ANY_ID)).thenReturn(lst);

        Rating result = repo.getById(ANY_ID);

        Assertions.assertSame(rating, result);
    }
    
    @Test
    void whenGetByIdWithNonExistingIdThenErrorIsTrown() {
        Optional<Rating> lst = Optional.empty();
        Mockito.when(ratingDao.findById(ANY_ID)).thenReturn(lst);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.getById(ANY_ID));
    }
    
    @Test
    void whenGetAllReturnsAllRating() {
        List<Rating> lst = List.of(Mockito.mock(Rating.class));
        Mockito.when(ratingDao.findAll()).thenReturn(lst);
        
        List<Rating> result = repo.getAll();
        
        Assertions.assertSame(result, lst);
    }
    
    @Test
    void whenSearchByTrailIdWithNonExistingTrailIdThenErrorIsTrown() {
        Optional<Trail> lst = Optional.empty();
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst);
        
        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.searchByTrailId(ANY_ID));
        
        Mockito.verify(trailDao).findById(ANY_ID);
    }
    
    @Test
    void whenSearchByTrailIdWitExistingTrailIdWithNoRatingThenErrorIsTrown() {
        Optional<Trail> lst = Optional.of(Mockito.mock(Trail.class));
        List<Rating> lst2 = List.of();
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst);
        Mockito.when(ratingDao.searchByTrailId(ANY_ID)).thenReturn(lst2);
        
        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.searchByTrailId(ANY_ID));
        
        Mockito.verify(ratingDao).searchByTrailId(ANY_ID);
    }
    
    @Test
    void whenSearchByTrailIdWitExistingTrailIdWithRatingsThenRatingsAreReturned() {
        Optional<Trail> lst = Optional.of(Mockito.mock(Trail.class));
        List<Rating> lst2 = List.of(Mockito.mock(Rating.class));
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(lst);
        Mockito.when(ratingDao.searchByTrailId(ANY_ID)).thenReturn(lst2);
        
        List<Rating> ratings = repo.searchByTrailId(ANY_ID);
        
        Assertions.assertSame(ratings, lst2);
        Mockito.verify(ratingDao).searchByTrailId(ANY_ID);
    }
    
    @Test
    void whenCreateReturnsCreatedRating() {
        Rating rating = Mockito.mock(Rating.class);
        Mockito.when(ratingDao.save(rating)).thenReturn(rating);
        
        Rating result = repo.create(rating);
        
        Assertions.assertSame(result, rating);
    }
}
