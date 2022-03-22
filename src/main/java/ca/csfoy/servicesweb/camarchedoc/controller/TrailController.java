package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.SearchTrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.TrailResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.domain.SearchTrailCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailRepository;

public class TrailController implements TrailResource {

    private final TrailRepository repository;
    private final TrailConverter converter;
    private final CustomValidatorFactory validatorFactory;

    public TrailController(TrailRepository repository, TrailConverter converter, CustomValidatorFactory validatorFactory) {
        this.repository = repository;
        this.converter = converter;
        this.validatorFactory = validatorFactory;
    }

    @Override
    public TrailDto getById(String id) {
        CustomValidator<TrailDto, String> validator = validatorFactory.getTrailValidator();
        validator.validateId(id);
        validator.verify("Trail cannot be obtained. Invalid ID format");
        return converter.convertToTrailDtoFrom(repository.getById(id));
    }

    @Override
    public List<TrailDto> getAll() {
        return converter.convertTrailListFrom(repository.getAll());
    }

    @Override
    public List<SearchTrailDto> search(String city, TrailDifficulty difficulty) {
        return converter.convertSearchTrailListFrom(repository.getBySearchCriteria(new SearchTrailCriteria(city, difficulty)));
    }

    @Override
    public TrailDto create(TrailDto dto) {
        CustomValidator<TrailDto, String> validator = validatorFactory.getTrailValidator();
        validator.validate(dto);
        validator.verify("Trail cannot be created. Invalid information");
        
        Trail trail = repository.create(converter.convertToTrailAtCreationFrom(dto));
        return converter.convertToTrailDtoFrom(trail);
    }

    @Override
    public void update(String id, TrailDto dto) {
        
        CustomValidator<TrailDto, String> validator = validatorFactory.getTrailValidator();
        validator.validate(id, dto);
        validator.verify("Trail cannot be created. Invalid information");
        
        repository.modify(id, converter.convertToTrailFrom(dto));
    }

    @Override
    public void delete(String id) {
        CustomValidator<TrailDto, String> validator = validatorFactory.getTrailValidator();
        validator.validateId(id);
        validator.verify("Trail cannot be obtained. Invalid ID format");
        repository.delete(id);
    }
    
    @Override
    public void updateToReady(String id) {
        repository.setTrailToReady(id);
    }
}
