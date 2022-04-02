package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.Optional;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private final UserDao dao;

    public UserRepositoryImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void create(User user) {
        dao.save(user);
    }

    @Override
    public void save(User user) {
        dao.save(user);
    }

    @Override
    public User get(String id) {
        Optional<User> user = dao.findById(id);
        if (user.isPresent()) {
            return user.get();
        }

        throw new ObjectNotFoundException("User (id:" + id + ") does not exist.");
    }
}

