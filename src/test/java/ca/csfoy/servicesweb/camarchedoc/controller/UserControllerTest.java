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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.TokenDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserCredentialsDto;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.UserConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;
import ca.csfoy.servicesweb.camarchedoc.security.JwtTokenAuthentificationProvider;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    
    private static final String ANY_ID = "1";
    private static final String ANY_STRING = "AAAAAAAAAAAA";
    
    @Mock
    private UserRepository repo;
    
    @Mock
    private UserConverter converter;
    
    @Mock
    private CustomValidatorFactory validatorFactory;
    
    @Mock
    private CustomValidator<FullUserDto, String> validator;
    
    @Mock
    private AuthenticationManager authManager;
    
    @Mock 
    private JwtTokenAuthentificationProvider tokenProvider;
    
    @InjectMocks
    private UserController controller;
    
    @Test
    void whenCreatedWithValidObjectThenDomainObjectCreated() {
        FullUserDto dto = Mockito.mock(FullUserDto.class);
        User user = Mockito.mock(User.class);
        Mockito.when(validatorFactory.getUserDtoForCreateValidator()).thenReturn(validator);
        Mockito.when(converter.toUserForCreation(dto)).thenReturn(user);
        Mockito.when(repo.getByEmail(user.email)).thenReturn(null);
        
        controller.createUser(dto);
        
        Mockito.verify(repo).create(user);
    }
    
    @Test
    void whenCreatedWithValidObjectWithEmailAlreadyUsedThenDomainObjectNotCreated() {
        FullUserDto dto = Mockito.mock(FullUserDto.class);
        User user = Mockito.mock(User.class);
        Mockito.when(validatorFactory.getUserDtoForCreateValidator()).thenReturn(validator);
        Mockito.when(repo.getByEmail(user.email)).thenReturn(user);
        
        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> controller.createUser(dto));
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
        FullUserDto dto = Mockito.mock(FullUserDto.class);
        User user = Mockito.mock(User.class);
        Mockito.when(converter.toUser(dto)).thenReturn(user);
        Mockito.when(validatorFactory.getUserDtoForCreateValidator()).thenReturn(validator);
        
        controller.modifyUser(dto, ANY_ID);
        
        Mockito.verify(repo).save(ANY_ID, user);
    }
    
    @Test
    void whenModifyWithValidObjectThatEmailIsAlreadyUsedThenDomainObjectIsNotModified() {
        FullUserDto dto = Mockito.mock(FullUserDto.class);
        User user = Mockito.mock(User.class);
        User userById = Mockito.mock(User.class);
        Mockito.when(validatorFactory.getUserDtoForCreateValidator()).thenReturn(validator);
        Mockito.when(repo.getByEmail(user.email)).thenReturn(user);
        
        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> controller.modifyUser(dto, ANY_ID));

        Mockito.verify(repo).getByEmail(user.email);
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
    
    @Test
    void whenLoginWithValidCredentialsReturnsToken() {
        UserCredentialsDto credentials = new UserCredentialsDto(ANY_STRING, ANY_STRING);
        User user = Mockito.mock(User.class);
        Mockito.when(repo.getByEmail(ANY_STRING)).thenReturn(user);
        
        TokenDto token = controller.loginUser(credentials);
        
        Mockito.verify(authManager).authenticate(new UsernamePasswordAuthenticationToken(credentials.emailAdress, credentials.password));
    }

}
