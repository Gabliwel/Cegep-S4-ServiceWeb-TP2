package ca.csfoy.servicesweb.camarchedoc.domain.trail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjetAlreadySetToDesiredValue;

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
        Mockito.when(trail.getStatus()).thenReturn(TrailStatus.IN_PREPARATION);
        
        service.verifyStatus(ANY_TRAIL_ID);
        
        Mockito.verify(repo).setTrailToReady(ANY_TRAIL_ID);
    }
    
    @Test
    public void verifyStatusifTrailExisttrailIsInReadythenVerifyFail() {
        Trail trail = Mockito.mock(Trail.class);
        Mockito.when(repo.getById(ANY_TRAIL_ID)).thenReturn(trail);
        Mockito.when(trail.getStatus()).thenReturn(TrailStatus.READY);
        
        Assertions.assertThrows(ObjetAlreadySetToDesiredValue.class, () -> service.verifyStatus(ANY_TRAIL_ID));
    }
    
}
