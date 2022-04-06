package ca.csfoy.servicesweb.camarchedoc.domain.rating;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectInvalidValueException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

public class RatingServiceImpl implements RatingService {
    
    private final UserRepository userRepo;
    private final TrailRepository trailRepo;
    private final RatingRepository ratingRepo;
    
    public RatingServiceImpl(UserRepository userRepo, TrailRepository trailRepo, RatingRepository ratingRepo) {
        this.userRepo = userRepo;
        this.trailRepo = trailRepo;
        this.ratingRepo = ratingRepo;
    }

    @Override
    public Rating createRating(Rating ratingToCreate) {
        User user = userRepo.get(ratingToCreate.getUser().getId());
        Trail trail = trailRepo.getById(ratingToCreate.getTrail().getId());
        if (ratingToCreate.getNote() <= Rating.MAX_NOTE && ratingToCreate.getNote() >= Rating.MIN_NOTE) {
            ratingRepo.create(ratingToCreate);
            List<Rating> ratings = ratingRepo.getByTrailId(trail.getId());
            Double total = 0.0;
            for (Rating rating : ratings) {
                total += rating.getNote();
            }
            Double score = (total + trail.getAverageScore()) / (ratings.size() + 1);
            trail.setAverageScore(score);
            trailRepo.modify(trail.getId(), trail);
            if (ratingToCreate.getNote().equals(Rating.MAX_NOTE)) {
                user.addFavoriteTrail(trail);
                userRepo.save(user);
            }
            return ratingToCreate;
            
        } else {
            throw new ObjectInvalidValueException("Rating note should be between " + Rating.MIN_NOTE.toString() + " and " + Rating.MAX_NOTE.toString());
        }
    }

}
