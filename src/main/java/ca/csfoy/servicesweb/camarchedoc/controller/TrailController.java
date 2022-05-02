package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import ca.csfoy.servicesweb.camarchedoc.api.trail.SearchTrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.SearchTrailCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailService;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailStatus;

@RestController
public class TrailController implements TrailResource {

    private final TrailRepository repository;
    private final TrailConverter converter;
    private final CustomValidatorFactory validatorFactory;
    private final TrailService service;

    public TrailController(TrailRepository repository, TrailConverter converter, CustomValidatorFactory validatorFactory, TrailService service) {
        this.repository = repository;
        this.converter = converter;
        this.validatorFactory = validatorFactory;
        this.service = service;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public TrailDto getById(String id) {
        CustomValidator<TrailDto, String> validator = validatorFactory.getTrailValidator();
        validator.validateId(id);
        validator.verify("Trail cannot be obtained. Invalid ID format");
        return converter.convertToTrailDtoFrom(repository.getById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<TrailDto> getAll() {
        List<TrailDto> lst = converter.convertTrailListFrom(repository.getAll());
        List<TrailDto> finalLst = new ArrayList<TrailDto>();
        for (TrailDto trail : lst) {
            if (trail.status == TrailStatus.READY) {
                finalLst.add(trail);
            }
        }
        return finalLst;
    }

    @Override
    public List<SearchTrailDto> search(String city, TrailDifficulty difficulty) {
        return converter.convertSearchTrailListFrom(repository.getBySearchCriteria(new SearchTrailCriteria(city, difficulty)));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TrailDto create(TrailDto dto) {
        CustomValidator<TrailDto, String> validator = validatorFactory.getTrailValidator();
        validator.validate(dto);
        validator.verify("Trail cannot be created. Invalid information");
        
        Trail trail = repository.create(converter.convertToTrailAtCreationFrom(dto));
        return converter.convertToTrailDtoFrom(trail);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(String id, TrailDto dto) {
        
        CustomValidator<TrailDto, String> validator = validatorFactory.getTrailValidator();
        validator.validate(id, dto);
        validator.verify("Trail cannot be updated. Invalid information");
        
        repository.modify(id, converter.convertToTrailFrom(dto));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(String id) {
        CustomValidator<TrailDto, String> validator = validatorFactory.getTrailValidator();
        validator.validateId(id);
        validator.verify("Trail cannot be deleted. Invalid ID format");
        repository.delete(id);
    }
    
    @Override
    public void updateToReady(String id) {
        service.verifyStatus(id);
    }
}
