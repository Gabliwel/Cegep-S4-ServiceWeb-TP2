package ca.csfoy.servicesweb.camarchedoc.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.RatingConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.RatingCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingService;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    @Mock
    private RatingService service;
    
    @Mock
    private RatingRepository repository;
    
    @Mock
    private RatingConverter converter;
    
    @Mock
    private CustomValidatorFactory validatorFactory;
    
    @Mock
    private CustomValidator<RatingDto, String> validator;
    
    @InjectMocks
    private RatingController controller;

    @Test
    void whenCreateWithValidRatingThenRatingIsReturnedAsDto() {
        RatingDto dto = Mockito.mock(RatingDto.class);
        RatingDto returned = Mockito.mock(RatingDto.class);
        Rating rating = Mockito.mock(Rating.class);
        Mockito.when(repository.create(rating)).thenReturn(rating);
        Mockito.when(converter.convertToRatingAtCreationFrom(dto)).thenReturn(rating);
        Mockito.when(converter.convertToRatingDtoFrom(rating)).thenReturn(returned);
        
        RatingCustomValidator eventValidator = Mockito.mock(RatingCustomValidator.class);
        
        Mockito.when(validatorFactory.getRatingValidator()).thenReturn(eventValidator);
        
        RatingDto dtoCreated = controller.create(dto);
        
        Mockito.verify(repository).create(rating);
        Mockito.verify(converter).convertToRatingAtCreationFrom(dto);
        Assertions.assertSame(returned, dtoCreated);        
    }
    
}
