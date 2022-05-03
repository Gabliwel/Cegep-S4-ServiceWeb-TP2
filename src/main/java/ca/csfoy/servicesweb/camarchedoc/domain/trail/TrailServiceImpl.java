package ca.csfoy.servicesweb.camarchedoc.domain.trail;

import org.springframework.stereotype.Service;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

@Service
public class TrailServiceImpl implements TrailService {

    private final TrailRepository repository;
    
    public TrailServiceImpl(TrailRepository repository) {
        this.repository = repository;
    }

    @Override
    public void verifyStatus(String id) {
        if (repository.getById(id) != null) {
            Trail trailToSetReady = repository.getById(id);
            trailToSetReady.setTrailready();
            repository.modify(id, trailToSetReady);
        } else {
            throw new ObjectNotFoundException("The trail with id (" + id + ") does not exist, and therefore cannot be modified.");
        }
    }

}
