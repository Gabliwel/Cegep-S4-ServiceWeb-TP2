package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.SearchTrailCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

public class TrailRepositoryImpl implements TrailRepository {

    private final TrailDao trailDao;

    public TrailRepositoryImpl(TrailDao trailDao) {
        this.trailDao = trailDao;
    }

    @Override
    public Trail getById(String id) {
        if (trailDao.doesExist(id)) {
            return trailDao.selectById(id);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id.toString() + ") does not exist.");
        }
    }

    @Override
    public List<Trail> getAll() {
        return trailDao.selectAll();
    }

    @Override
    public Trail create(Trail trail) {
        if (!trailDao.doesExist(trail.getName(), trail.getCity())) {
            return trailDao.insert(trail);
        } else {
            throw new ObjectAlreadyExistsException("A trail named \'" + trail + "\' in city \'" + trail.getCity() + "\' already exists.");
        }
    }

    @Override
    public void modify(String id, Trail trail) {
        if (trailDao.doesExist(id)) {
            trailDao.update(id, trail);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id + ") does not exist, and therefore cannot be modified.");
        }
    }

    @Override
    public void delete(String id) {
        if (trailDao.doesExist(id)) {
            trailDao.delete(id);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id.toString() + ") does not exist, and therefore cannot be deleted.");
        }
    }

    @Override
    public List<Trail> getBySearchCriteria(SearchTrailCriteria criteria) {        
        return trailDao.search(criteria);
    }

    @Override
    public void setTrailToReady(String id) {
        if (trailDao.doesExist(id)) {
            trailDao.setTrailReady(id);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id + ") does not exist, and therefore cannot be modified.");
        }
    }
}
