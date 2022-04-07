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
        if (trailToSetReady.getStatus() == TrailStatus.IN_PREPARATION) {
            repository.setTrailToReady(id);
        } else {
            throw new ObjetAlreadySetToDesiredValue("The trail with id (" + id + ") is already set to ready.");
        }
    }

}
