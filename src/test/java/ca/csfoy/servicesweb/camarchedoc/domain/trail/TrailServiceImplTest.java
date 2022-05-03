package ca.csfoy.servicesweb.camarchedoc.domain.trail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class TrailServiceImplTest {
    
    public static final String ANY_TRAIL_ID = "3";
    
    @Mock
    private TrailRepository repo;
    
    @InjectMocks
    private TrailServiceImpl service;
    
    @Test
    public void verifyStatusifTrailExisttrailIsInPreparationthenVerifySuccessfull() {
        Trail trail = Mockito.mock(Trail.class);
        Mockito.when(repo.getById(ANY_TRAIL_ID)).thenReturn(trail);
        
        service.verifyStatus(ANY_TRAIL_ID);
        
    }
    
    @Test
    public void verifyStatusifTrailExisttrailIsInReadythenVerifyFail() {
        Mockito.when(repo.getById(ANY_TRAIL_ID)).thenReturn(null);
        
        Assertions.assertThrows(ObjectNotFoundException.class, () -> service.verifyStatus(ANY_TRAIL_ID));
    }
    
}
