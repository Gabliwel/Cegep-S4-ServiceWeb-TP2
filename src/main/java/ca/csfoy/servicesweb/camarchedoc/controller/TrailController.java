package ca.csfoy.servicesweb.camarchedoc.controller;

import java.util.List;

import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.TrailResource;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.domain.SearchTrailCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailRepository;

public class TrailController implements TrailResource {

    private final TrailRepository repository;
    private final TrailConverter converter;

    public TrailController(TrailRepository repository, TrailConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public TrailDto getById(String id) {
        return converter.convertToTrailDtoFrom(repository.getById(id));
    }

    @Override
    public List<TrailDto> getAll() {
        return converter.convertTrailListFrom(repository.getAll());
    }

    @Override
    public List<TrailDto> search(String city, TrailDifficulty difficulty) {
        return converter.convertTrailListFrom(repository.getBySearchCriteria(new SearchTrailCriteria(city, difficulty)));
    }

    @Override
    public TrailDto create(TrailDto dto) {
        Trail trail = repository.create(converter.convertToTrailAtCreationFrom(dto));
        return converter.convertToTrailDtoFrom(trail);
    }

    @Override
    public void update(String id, TrailDto dto) {
        repository.modify(id, converter.convertToTrailFrom(dto));
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}
