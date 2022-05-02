package ca.csfoy.servicesweb.camarchedoc.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    
    private final String ANY_STRING = "AAAAA1";
    
    private final String ANY_STRING2 = "AAAAA2";
    
    @Mock
    private UserRepository repo;
    
    @InjectMocks
    private UserDetailsServiceImpl service;
    
    @Test
    void whenLoadByUsernameDontPassReturnsError() {
        Mockito.when(repo.getByEmail(ANY_STRING)).thenReturn(null);
        
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(ANY_STRING));
    }
    
    @Test
    void whenLoadByUsernamePassUserReturnsSpringUserDetails() {
        //Not using mockito to test/coverage cover the private method
        User user = new User("", "", "", ANY_STRING, ANY_STRING2, new Role("1", "USER"), null, null, null);
        Mockito.when(repo.getByEmail(ANY_STRING)).thenReturn(user);
        
        UserDetails usersDetails = service.loadUserByUsername(ANY_STRING);
        
        Assertions.assertEquals(usersDetails.getUsername(), ANY_STRING);
        Assertions.assertEquals(usersDetails.getPassword(), ANY_STRING2);
    }
    
    @Test
    void whenLoadByUsernamePassAdminReturnsSpringUserDetails() {
        //Not using mockito to test/coverage cover the private method
        User user = new User("", "", "", ANY_STRING, ANY_STRING2, new Role("2", "ADMIN"), null, null, null);
        Mockito.when(repo.getByEmail(ANY_STRING)).thenReturn(user);
        
        UserDetails usersDetails = service.loadUserByUsername(ANY_STRING);
        
        Assertions.assertEquals(usersDetails.getUsername(), ANY_STRING);
        Assertions.assertEquals(usersDetails.getPassword(), ANY_STRING2);
    }

}
