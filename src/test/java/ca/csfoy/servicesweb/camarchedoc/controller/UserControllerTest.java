package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDtoForCreate;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.UserConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    
    private static final String ANY_ID = "1";
    
    @Mock
    private UserRepository repo;
    
    @Mock
    private UserConverter converter;
    
    @Mock
    private CustomValidatorFactory validatorFactory;
    
    @Mock
    private CustomValidator<UserDtoForCreate, String> validator;
    
    @InjectMocks
    private UserController controller;
    
    @Test
    void whenCreatedWithValidObjectThenDomainObjectCreated() {
        UserDtoForCreate dto = Mockito.mock(UserDtoForCreate.class);
        User user = Mockito.mock(User.class);
        Mockito.when(validatorFactory.getUserDtoForCreateValidator()).thenReturn(validator);
        Mockito.when(converter.toUserForCreation(dto)).thenReturn(user);
        
        controller.createUser(dto);
        
        Mockito.verify(repo).create(user);
    }
    
    @Test
    void whenGetByIdWithValidIdThenDomainObjectReturnedAsDto() {
        UserDto dto = Mockito.mock(UserDto.class);
        User user = Mockito.mock(User.class);
        Mockito.when(repo.get(ANY_ID)).thenReturn(user);
        Mockito.when(converter.fromUser(user)).thenReturn(dto);
        
        UserDto dtoReturned = controller.getUser(ANY_ID);
        
        Mockito.verify(repo).get(ANY_ID);
        Assertions.assertSame(dto, dtoReturned);
    }
    
    @Test
    void whenModifyWithValidObjectThenDomainObjectIsModified() {
        UserDto dto = Mockito.mock(UserDto.class);
        User user = Mockito.mock(User.class);
        Mockito.when(converter.toUser(dto)).thenReturn(user);
        
        controller.modifyUser(dto);
        
        Mockito.verify(repo).save(user);
    }
    
    @Test
    void whenGetSuggestedTrailsFromUserWithSuggestedTrailsThenRetunsList() {
        Set<TrailDto> set = new HashSet<TrailDto>();
        set.add(Mockito.mock(TrailDto.class));
        UserDto dto = new UserDto(ANY_ID, null, null, null, set, null);
        User user = Mockito.mock(User.class);
        
        Mockito.when(repo.get(ANY_ID)).thenReturn(user);
        Mockito.when(converter.fromUser(user)).thenReturn(dto);
        
        List<TrailDto> trails = controller.getSuggestedTrails(ANY_ID);
        
        Mockito.verify(repo).get(ANY_ID);
        Assertions.assertEquals(trails.size(), set.size());
    }
    
    @Test
    void whenGetSuggestedTrailsFromUserWithNoSuggestedTrailsThenRetunsEmptyList() {
        Set<TrailDto> set = new HashSet<TrailDto>();
        UserDto dto = new UserDto(ANY_ID, null, null, null, set, null);
        User user = Mockito.mock(User.class);
        
        Mockito.when(repo.get(ANY_ID)).thenReturn(user);
        Mockito.when(converter.fromUser(user)).thenReturn(dto);
        
        List<TrailDto> trails = controller.getSuggestedTrails(ANY_ID);
        
        Mockito.verify(repo).get(ANY_ID);
        Assertions.assertEquals(trails.size(), set.size());
    }

}
