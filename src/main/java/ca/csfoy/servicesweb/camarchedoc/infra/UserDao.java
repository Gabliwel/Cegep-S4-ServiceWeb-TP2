package ca.csfoy.servicesweb.camarchedoc.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

public interface UserDao extends JpaRepository<User, String> {
    
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User getByEmail(String emailAddress);
}

