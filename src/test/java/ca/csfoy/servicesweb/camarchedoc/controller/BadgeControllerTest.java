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

import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDtoWithoutDesc;
import ca.csfoy.servicesweb.camarchedoc.api.badge.SmallBadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.BadgeConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.BadgeCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.FullUserCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.Badge;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.BadgeRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class BadgeControllerTest {
    
    public final String ANY_ID = "911";
    
    @Mock
    private BadgeRepository badgeRepo;
    
    @Mock
    private BadgeConverter badgeConverter;
    
    @Mock
    private CustomValidatorFactory validatorFactory;
    
    @Mock
    private UserRepository userRepo;
    
    @InjectMocks
    private BadgeController controller;
    
    @Test
    void whenGetByIdWithValidIdThenBadgeIsReturned() {
        Badge badge = Mockito.mock(Badge.class);
        BadgeDtoWithoutDesc finalBadge = Mockito.mock(BadgeDtoWithoutDesc.class);
        CustomValidator<BadgeDto, String> validator = Mockito.mock(BadgeCustomValidator.class);
        Mockito.when(validatorFactory.getBadgeValidator()).thenReturn(validator);
        Mockito.when(badgeRepo.getById(ANY_ID)).thenReturn(badge);
        Mockito.when(badgeConverter.badgeToDto(badge)).thenReturn(finalBadge);
        
        BadgeDtoWithoutDesc returnedBadge = controller.getById(ANY_ID);
        
        Assertions.assertSame(finalBadge, returnedBadge);
    }
    
    @Test
    void whenGetByAllThenBadgesAreReturned() {
        Badge badge = Mockito.mock(Badge.class);
        SmallBadgeDto finalBadge = Mockito.mock(SmallBadgeDto.class);
        List<Badge> listBadge = List.of(badge);
        List<SmallBadgeDto> listDto = List.of(finalBadge);
        
        Mockito.when(badgeRepo.getAll()).thenReturn(listBadge);
        Mockito.when(badgeConverter.badgeListToDtoList(listBadge)).thenReturn(listDto);
        
        List<SmallBadgeDto> returnedBadges = controller.getAll();
        
        Assertions.assertSame(listDto, returnedBadges);
    }
    
    @Test
    void whenGiveBadgeWithValidBadgeAndUserThenBadgeIsAddedAndUserIsSave() {
        Badge badge = Mockito.mock(Badge.class);
        User user = Mockito.mock(User.class);
        CustomValidator<BadgeDto, String> validator = Mockito.mock(BadgeCustomValidator.class);
        CustomValidator<FullUserDto, String> userValidator = Mockito.mock(FullUserCustomValidator.class);
        Mockito.when(validatorFactory.getBadgeValidator()).thenReturn(validator);
        Mockito.when(validatorFactory.getUserDtoForCreateValidator()).thenReturn(userValidator);
        Mockito.when(badgeRepo.getById(ANY_ID)).thenReturn(badge);
        Mockito.when(userRepo.get(ANY_ID)).thenReturn(user);
        
        controller.giveBadge(ANY_ID, ANY_ID);
        
        Mockito.verify(user).addBagde(badge);
        Mockito.verify(userRepo).save(ANY_ID, user);
    }
}
