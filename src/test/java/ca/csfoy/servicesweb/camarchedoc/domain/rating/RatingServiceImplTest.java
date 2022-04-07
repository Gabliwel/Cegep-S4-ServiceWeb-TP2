package ca.csfoy.servicesweb.camarchedoc.domain.rating;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectInvalidValueException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)

public class RatingServiceImplTest {
    
    public static final String ANY_TRAIL_ID = "3";
    
    public static final Double ANY_NOTE = 1.0;
    
    @Mock
    private UserRepository userRepo;
    
    @Mock
    private TrailRepository trailRepo;
    
    @Mock
    private RatingRepository repo;
    
    @InjectMocks
    private RatingServiceImpl service;
    
    @Test
    public void throwExceptionifRatingIsNotValid() {
        Trail trail = Mockito.mock(Trail.class);
        User user = Mockito.mock(User.class);
        Rating rating = Mockito.mock(Rating.class);
        Mockito.when(rating.getUser()).thenReturn(user);
        Mockito.when(rating.getTrail()).thenReturn(trail);

        Assertions.assertThrows(ObjectInvalidValueException.class, () -> service.createRating(rating));
    }
    
}
