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

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.TrailCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailRepository;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
class TrailControllerTest {
    
    private static final String ANY_ID = "1";

    @Mock
    private TrailRepository repository;
    
    @Mock
    private TrailConverter converter;
    
    @Mock
    private CustomValidatorFactory validatorFactory;
    
    @Mock
    private CustomValidator<TrailDto, String> validator;
    
    @InjectMocks
    private TrailController controller;

    @Test
    void whenGetByIdWithValidIdThenDomainObjectReturnedAsConvertedDto() {
        TrailDto dto = Mockito.mock(TrailDto.class);
        Trail trail = Mockito.mock(Trail.class);
        TrailCustomValidator trailValidator = Mockito.mock(TrailCustomValidator.class);
        
        Mockito.when(validatorFactory.getTrailValidator()).thenReturn(trailValidator);
        Mockito.when(repository.getById(ANY_ID)).thenReturn(trail);
        Mockito.when(converter.convertToTrailDtoFrom(trail)).thenReturn(dto);
        TrailDto dtoReturned = controller.getById(ANY_ID);
        
        Mockito.verify(repository).getById(ANY_ID);
        Mockito.verify(converter).convertToTrailDtoFrom(trail);
        Assertions.assertSame(dto, dtoReturned);
    }
    
    @Test
    void whenGetAllThenListOfDomainObjectsReturnedAsConvertedListOfDtos() {
        TrailDto dto1 = Mockito.mock(TrailDto.class);
        TrailDto dto2 = Mockito.mock(TrailDto.class);
        Trail trail1 = Mockito.mock(Trail.class);
        Trail trail2 = Mockito.mock(Trail.class);
        Mockito.when(repository.getAll()).thenReturn(List.of(trail1, trail2));
        Mockito.when(converter.convertTrailListFrom(List.of(trail1, trail2))).thenReturn(List.of(dto1, dto2));
        
        List<TrailDto> dtosReturned = controller.getAll();
        
        Mockito.verify(repository).getAll();
        Mockito.verify(converter).convertTrailListFrom(List.of(trail1, trail2));
        Assertions.assertEquals(List.of(dto1, dto2), dtosReturned);        
    }
    
    @Test
    void whenCreateWithValidEventThenEventIsReturnedAsDto() {
        TrailDto dto = Mockito.mock(TrailDto.class);
        TrailDto returned = Mockito.mock(TrailDto.class);
        Trail trail = Mockito.mock(Trail.class);
        Trail created = Mockito.mock(Trail.class);
        TrailCustomValidator trailValidator = Mockito.mock(TrailCustomValidator.class);
        
        Mockito.when(validatorFactory.getTrailValidator()).thenReturn(trailValidator);
        Mockito.when(converter.convertToTrailAtCreationFrom(dto)).thenReturn(trail);
        Mockito.when(repository.create(trail)).thenReturn(created);
        Mockito.when(converter.convertToTrailDtoFrom(created)).thenReturn(returned);
        
        TrailDto dtoCreated = controller.create(dto);
        
        Mockito.verify(repository).create(trail);
        Mockito.verify(converter).convertToTrailAtCreationFrom(dto);
        Mockito.verify(converter).convertToTrailDtoFrom(created);
        Assertions.assertSame(returned, dtoCreated);        
    }
    
    @Test
    void whenUpdateWithValidEventThenEventIsUpdated() {
        TrailDto dto = Mockito.mock(TrailDto.class);
        Trail trail = Mockito.mock(Trail.class);
        TrailCustomValidator trailValidator = Mockito.mock(TrailCustomValidator.class);
        
        Mockito.when(validatorFactory.getTrailValidator()).thenReturn(trailValidator);
        Mockito.when(converter.convertToTrailFrom(dto)).thenReturn(trail);
        
        controller.update(ANY_ID, dto);
        
        Mockito.verify(repository).modify(ANY_ID, trail);
    }
    
    @Test
    void whenDeleteWithValidIdThenEventIsDeleted() {      
        TrailCustomValidator trailValidator = Mockito.mock(TrailCustomValidator.class);
        
        Mockito.when(validatorFactory.getTrailValidator()).thenReturn(trailValidator);
        controller.delete(ANY_ID);
        
        Mockito.verify(repository).delete(ANY_ID);
    }
}
