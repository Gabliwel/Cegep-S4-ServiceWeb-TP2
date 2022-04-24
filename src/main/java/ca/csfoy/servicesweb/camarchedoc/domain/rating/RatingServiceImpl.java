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
        //FIXME: Cette validation va dans votre DTO!
        if (ratingToCreate.getNote() <= Rating.MAX_NOTE && ratingToCreate.getNote() >= Rating.MIN_NOTE) {
            ratingRepo.create(ratingToCreate);
            List<Rating> ratings = ratingRepo.getByTrailId(trail.getId());
            //FIXME: ce n'est pas le rôle du service de faire des calculs, c'est son rôle de le déleguer au bon objet (ici Trail)
            //FIXME: C'est un bris du Tell Don't Ask de faire le calcul à la place de l'objet et ensuite de lui dire, voici le résultat!
            Double total = 0.0;
            //FIXME: il existe une lambda pour calculer la moyenne sur une Collection: collection.stream().average().
            for (Rating rating : ratings) {
                total += rating.getNote();
            }
            Double score = (total + trail.getAverageScore()) / (ratings.size() + 1);
            trail.setAverageScore(score);
            trailRepo.modify(trail.getId(), trail);
            //FIXME: Même chose ici, déléguez la responsabilité de déterminer si il faut ajouter la trail aux favoris en donnant le rating.  Votre méthode addFavorite déterminera elle-même si elle fait l'ajout ou non!
            if (ratingToCreate.getNote().equals(Rating.MAX_NOTE)) {
                user.addFavoriteTrail(trail);
                userRepo.save(user);
            }
            return ratingToCreate;
            
        } else {
            throw new ObjectInvalidValueException("Rating note should be between " + Rating.MIN_NOTE.toString() + " and " + Rating.MAX_NOTE.toString()
            + "your note" + ratingToCreate.getNote());
        }
    }

}
