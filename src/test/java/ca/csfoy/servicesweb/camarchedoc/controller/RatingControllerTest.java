package ca.csfoy.servicesweb.camarchedoc.controller;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.RatingConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.RatingCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingService;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;
import ca.csfoy.servicesweb.camarchedoc.security.SecurityPrincipal;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {
    
    public final String ANY_ID = "1";

    @Mock
    private RatingService service;
    
    @Mock
    private RatingRepository repository;
    
    @Mock
    private UserRepository userRepo;
    
    @Mock
    private RatingConverter converter;
    
    @Mock
    private CustomValidatorFactory validatorFactory;
    
    @Mock
    private CustomValidator<RatingDto, String> validator;
    
    @Mock
    private Authentication auth;
    
    @InjectMocks
    private RatingController controller;

    @Test
    void whenCreateWithValidRatingThenRatingIsReturnedAsDto() {
        RatingDto dto = Mockito.mock(RatingDto.class);
        User user = Mockito.mock(User.class);
        Rating rating = Mockito.mock(Rating.class);
        TrailDto trail = Mockito.mock(TrailDto.class);
        SecurityPrincipal principal = Mockito.mock(SecurityPrincipal.class);
        RatingCustomValidator eventValidator = Mockito.mock(RatingCustomValidator.class);

        Mockito.when(validatorFactory.getRatingValidator()).thenReturn(eventValidator);
        Mockito.when(service.createRating(rating)).thenReturn(rating);
        Mockito.when(converter.convertToRatingDtoFrom(rating)).thenReturn(dto);
        Mockito.when(dto.getTrail()).thenReturn(trail);
        Mockito.when(dto.getTrailId()).thenReturn(ANY_ID);
        Mockito.when(auth.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.getId()).thenReturn(ANY_ID);
        Mockito.when(userRepo.get(ANY_ID)).thenReturn(user);
        Mockito.when(converter.convertToRatingAtCreationFrom(dto, user)).thenReturn(rating);
        
        SecurityContextHolder.getContext().setAuthentication(auth);
        RatingDto dtoCreated = controller.create(dto);

        Mockito.verify(service).createRating(rating);
        Mockito.verify(converter).convertToRatingAtCreationFrom(dto, user);
        Assertions.assertSame(dto, dtoCreated);
    }
    
}
