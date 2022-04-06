package ca.csfoy.servicesweb.camarchedoc.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

public interface UserDao extends JpaRepository<User, String> {
}

