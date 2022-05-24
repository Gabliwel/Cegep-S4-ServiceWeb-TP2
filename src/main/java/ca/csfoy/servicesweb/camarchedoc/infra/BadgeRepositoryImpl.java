package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ca.csfoy.servicesweb.camarchedoc.domain.badge.Badge;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.BadgeRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

@Repository
public class BadgeRepositoryImpl implements BadgeRepository {

    private final BadgeDao dao;
    
    public BadgeRepositoryImpl(BadgeDao dao) {
        this.dao = dao;
    }
    
    @Override
    public Badge getById(String id) {
        Optional<Badge> badge = dao.findById(id);

        if (badge.isPresent()) {
            return badge.get();
        } else {
            throw new ObjectNotFoundException("The badge with id (" + id + ") does not exist.");
        }
    }

    @Override
    public List<Badge> getAll() {
        return dao.findAll();
    }

    @Override
    public Badge create(Badge event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void modify(String id, Badge event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }

}
