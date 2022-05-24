package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.csfoy.servicesweb.camarchedoc.domain.badge.Badge;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

@Tag("Unitaire")
@ExtendWith(MockitoExtension.class)
public class BadgeRepositoryImplTest {
    
    public static final String ANY_ID = "1";

    @Mock
    private BadgeDao dao;
    
    @InjectMocks
    private BadgeRepositoryImpl repo;
    
    @Test
    void whenGetByIdWithExistingIdThenBadgeWithCorrespondingIdIsReturned() {
        Badge badge = Mockito.mock(Badge.class);
        Optional<Badge> lst = Optional.of(badge);
        Mockito.when(dao.findById(ANY_ID)).thenReturn(lst);

        Badge badgeReturned = repo.getById(ANY_ID);

        Mockito.verify(dao).findById(ANY_ID);
        Assertions.assertSame(badge, badgeReturned);
    }
    
    @Test
    void whenGetByIdWithNonExistingIdThenErrorIsThrown() {
        Optional<Badge> lst = Optional.empty();
        Mockito.when(dao.findById(ANY_ID)).thenReturn(lst);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> repo.getById(ANY_ID));
    }
    
    @Test
    void whenGetAllReturnsAllBadge() {
        Badge badge = Mockito.mock(Badge.class);
        List<Badge> lst = List.of(badge);
        Mockito.when(dao.findAll()).thenReturn(lst);

        List<Badge> badges = repo.getAll();
        
        Assertions.assertSame(lst, badges);
    }
    
}
