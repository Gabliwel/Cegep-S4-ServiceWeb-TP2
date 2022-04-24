package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.SearchTrailCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailRepository;

public class TrailRepositoryImpl implements TrailRepository {

    private final TrailDao trailDao;

    public TrailRepositoryImpl(TrailDao trailDao) {
        this.trailDao = trailDao;
    }

    @Override
    public Trail getById(String id) {
        if (trailDao.findById(id).isPresent()) {
            return trailDao.getById(id);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id.toString() + ") does not exist.");
        }
    }

    @Override
    public List<Trail> getAll() {
        return trailDao.findAll();
    }

    @Override
    public Trail create(Trail trail) {
        if (trailDao.doesExist(trail.getName(), trail.getCity()).isEmpty()) {
            return trailDao.save(trail);
        } else {
            throw new ObjectAlreadyExistsException("A trail named \'" + trail + "\' in city \'" + trail.getCity() + "\' already exists.");
        }
    }

    @Override
    public void modify(String id, Trail trail) {
        if (!trailDao.findById(id).isEmpty()) {
            trailDao.save(trail);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id + ") does not exist, and therefore cannot be modified.");
        }
    }

    @Override
    public void delete(String id) {
        if (!trailDao.findById(id).isEmpty()) {
            Trail t = trailDao.getById(id);
            trailDao.delete(t);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id.toString() + ") does not exist, and therefore cannot be deleted.");
        }
    }

    @Override
    public List<Trail> getBySearchCriteria(SearchTrailCriteria criteria) { 
        if (!criteria.getCity().isBlank()) {
            if (criteria.getDifficulty() != null) {
                return trailDao.fullSearch(criteria.getCity(), criteria.getDifficulty());
            } else {
                return trailDao.searchOnlyWithCity(criteria.getCity());
            }
        } else {
            if (criteria.getDifficulty() != null) {
                return trailDao.searchOnlyWithDifficulty(criteria.getDifficulty());
            } else {
                return this.getAll();
            }
        }
    }

    @Override
    public void setTrailToReady(String id) {
    	//FIXME: Vous allez déjà chercher votre trail dans votre service, la vérification de l'existance devrait se faire là.
        if (trailDao.existsById(id)) {
            Trail trailToSetReady = trailDao.getById(id);
            //FIXME: Ceci est définitivement à mettre dans le service, le repo n'est pas supposé modifié les objets de domaine.
            trailToSetReady.setTrailready();
            //FIXME: Vous faites un simple 'save' comme dans la méthode 'modify', pourquoi ne pas appeler cette méthode dans votre service à la place et deleter cette fonction-ci au complet?
            trailDao.save(trailToSetReady);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id + ") does not exist, and therefore cannot be modified.");
        }
    }
}
