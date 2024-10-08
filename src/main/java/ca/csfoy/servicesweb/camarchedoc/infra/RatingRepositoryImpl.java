package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingRepository;

@Repository
public class RatingRepositoryImpl implements RatingRepository {

    private final RatingDao ratingDao;
    private final TrailDao trailDao;

    public RatingRepositoryImpl(RatingDao ratingDao, TrailDao trailDao) {
        this.ratingDao = ratingDao;
        this.trailDao = trailDao;
    }
    
    @Override
    public Rating getById(String id) {
        Optional<Rating> rating = ratingDao.findById(id);
        if (rating.isPresent()) {
            return rating.get();
        } else {
            throw new ObjectNotFoundException("The rating with id (" + id + ") does not exist.");
        }
    }

    @Override
    public List<Rating> getAll() {
        return ratingDao.findAll();
    }

    @Override
    public List<Rating> searchByTrailId(String id) {
        if (trailDao.findById(id).isPresent()) {
            List<Rating> searchResult = ratingDao.searchByTrailId(id);
            if (!searchResult.isEmpty()) {
                return searchResult;
            } else {
                throw new ObjectNotFoundException("No rating exist for the trail " + id);
            }
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id + ") does not exist");
        }
    }

    @Override
    public Rating create(Rating event) {
        return ratingDao.save(event);
    }

    @Override
    public List<Rating> getByTrailId(String id) {
        if (trailDao.findById(id).isPresent()) {
            return ratingDao.searchByTrailId(id);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id + ") does not exist");
        }
    }
}
