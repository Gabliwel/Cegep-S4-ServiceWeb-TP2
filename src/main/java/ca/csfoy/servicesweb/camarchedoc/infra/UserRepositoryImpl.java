package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDao dao;
    private final TrailDao trailDao;

    public UserRepositoryImpl(UserDao dao, TrailDao trailDao) {
        this.dao = dao;
        this.trailDao = trailDao;
    }

    @Override
    public void create(User user) {
        if (!trailDoesExist(user.getFavoritesTrails()) || !trailDoesExist(user.getTrailsToTry())) {
            throw new ObjectNotFoundException("Trail(s) dont exist for the user with id (" + user.id + "), and therefore cannot be created.");
        } else {
            dao.save(user);
        }
    }

    @Override
    public void save(String id, User user) {
        if (!dao.findById(id).isEmpty()) {
            if (!trailDoesExist(user.getFavoritesTrails()) || !trailDoesExist(user.getTrailsToTry())) {
                throw new ObjectNotFoundException("Favorite(s) trail(s) dont exist for the user with id (" + user.id + "), and therefore cannot be modified.");
            } else {
                dao.save(user);
            }
        } else {
            throw new ObjectNotFoundException("The user with id (" + user.id + ") does not exist, and therefore cannot be modified.");
        }
    }

    @Override
    public User get(String id) {
        Optional<User> user = dao.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ObjectNotFoundException("User (id:" + id + ") does not exist.");
    }
    
    public Boolean trailDoesExist(Set<Trail> trails) {
        if (trails != null && !trails.isEmpty()) {
            for (Trail trail : trails) {
                if (trailDao.findById(trail.getId()).isEmpty()) {
                    return false;
                }
            } 
        }
        return true;
    }

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }
}
