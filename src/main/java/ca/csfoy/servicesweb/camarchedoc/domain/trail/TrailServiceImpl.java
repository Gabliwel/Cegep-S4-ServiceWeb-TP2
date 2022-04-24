package ca.csfoy.servicesweb.camarchedoc.domain.trail;

import org.springframework.stereotype.Service;

import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjetAlreadySetToDesiredValue;

@Service
public class TrailServiceImpl implements TrailService {

    private final TrailRepository repository;
    
    public TrailServiceImpl(TrailRepository repository) {
        this.repository = repository;
    }

    @Override
    public void verifyStatus(String id) {
        Trail trailToSetReady = repository.getById(id);
        //FIXME: Vous brisez le Tell Don't ASK ici, en demandant de l'info à votre trail et en prenant la décision pour elle.
        if (trailToSetReady.getStatus() == TrailStatus.IN_PREPARATION) {
            repository.setTrailToReady(id);
        } else {
            throw new ObjetAlreadySetToDesiredValue("The trail with id (" + id + ") is already set to ready.");
        }
    }

}
