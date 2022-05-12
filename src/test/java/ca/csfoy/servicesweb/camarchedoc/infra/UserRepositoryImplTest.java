package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTest {
    
    public static final String ANY_ID = "1";
    
    @Mock
    private UserDao dao;
    
    @Mock
    private TrailDao trailDao;
    
    @InjectMocks
    private UserRepositoryImpl repo;
    
    @Test
    void whenCreateUserWithNonExistingTrailsThenUserIsNotCreated() {
        User user = Mockito.mock(User.class);
        Trail trail1 = Mockito.mock(Trail.class);
        Set<Trail> lst = Set.of(trail1);
        Optional<Trail> optional = Optional.empty();
        Mockito.when(user.getFavoritesTrails()).thenReturn(lst);
        Mockito.when(trail1.getId()).thenReturn(ANY_ID);
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(optional);
        
        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.create(user));
        
        Mockito.verify(user).getFavoritesTrails();
    }
    
    @Test
    void whenCreateUserWithExistingTrailInTrailsToTryThenUserIsCreated() {
        User user = Mockito.mock(User.class);
        Trail trail1 = Mockito.mock(Trail.class);
        Set<Trail> lst = Set.of(trail1);
        Optional<Trail> optional = Optional.of(Mockito.mock(Trail.class));
        Mockito.when(user.getFavoritesTrails()).thenReturn(lst);
        Mockito.when(user.getTrailsToTry()).thenReturn(lst);
        Mockito.when(trail1.getId()).thenReturn(ANY_ID);
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(optional);
        
        repo.create(user);
        
        Mockito.verify(user).getFavoritesTrails();
        Mockito.verify(user).getTrailsToTry();
        Mockito.verify(dao).save(user);
    }
    
    @Test
    void whenModifyUserWithNonExistingTrailsThenUserIsNotModified() {
        User user = Mockito.mock(User.class);
        Trail trail1 = Mockito.mock(Trail.class);
        Set<Trail> lst = Set.of(trail1);
        Optional<Trail> optional = Optional.empty();
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(dao.findById(ANY_ID)).thenReturn(optionalUser);
        Mockito.when(user.getFavoritesTrails()).thenReturn(lst);
        Mockito.when(trail1.getId()).thenReturn(ANY_ID);
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(optional);
        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.save(ANY_ID, user));
        
        Mockito.verify(user).getFavoritesTrails();
    }
    
    @Test
    void whenModifyUserWithNonExistingUserThenUserIsNotModified() {
        User user = Mockito.mock(User.class);
        Optional<User> optionalUser = Optional.empty();
        Mockito.when(dao.findById(ANY_ID)).thenReturn(optionalUser);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.save(ANY_ID, user));
        
        Mockito.verify(dao).findById(ANY_ID);
    }
    
    @Test
    void whenModifieUserWithExistingTrailInTrailsToTryThenUserIsModified() {
        User user = Mockito.mock(User.class);
        Trail trail1 = Mockito.mock(Trail.class);
        Set<Trail> lst = Set.of(trail1);
        Optional<Trail> optional = Optional.of(Mockito.mock(Trail.class));
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(dao.findById(ANY_ID)).thenReturn(optionalUser);
        Mockito.when(user.getFavoritesTrails()).thenReturn(lst);
        Mockito.when(user.getTrailsToTry()).thenReturn(lst);
        Mockito.when(trail1.getId()).thenReturn(ANY_ID);
        Mockito.when(trailDao.findById(ANY_ID)).thenReturn(optional);
        
        repo.save(ANY_ID, user);
        
        Mockito.verify(user).getFavoritesTrails();
        Mockito.verify(user).getTrailsToTry();
        Mockito.verify(dao).save(user);
    }
    
    @Test
    void whenGetUserWithExistingIdThenUserIsReturned() {
        User user = Mockito.mock(User.class);
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(dao.findById(ANY_ID)).thenReturn(optionalUser);
        
        User result = repo.get(ANY_ID);
        
        Assertions.assertSame(user, result);
    }
    
    @Test
    void whenGetUserWithNotExistingIdThenUserIsNotReturned() {
        Optional<User> optionalUser = Optional.empty();
        Mockito.when(dao.findById(ANY_ID)).thenReturn(optionalUser);
        
        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.get(ANY_ID));
    }
}
