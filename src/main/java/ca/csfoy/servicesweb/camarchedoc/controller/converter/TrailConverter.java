package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailStatus;

public class TrailConverter {

    public Trail convertToTrailAtCreationFrom(TrailDto dto) {
        return new Trail(dto.name, dto.description, dto.city, dto.difficulty, dto.openingDate, dto.lastMaintenanceDate);
    }

    public Trail convertToTrailFrom(TrailDto dto) {
        return new Trail(dto.id, dto.name, dto.description, dto.city, dto.difficulty, dto.openingDate, dto.lastMaintenanceDate);
    }

    public TrailDto convertToTrailDtoFrom(Trail trail) {
        return new TrailDto(trail.getId(), trail.getName(), trail.getDescription(), 
                trail.getCity(), trail.getDifficulty(), trail.getOpeningDate(), trail.getLastMaintenanceDate(), trail.getStatus());
    }

    public List<TrailDto> convertTrailListFrom(List<Trail> trails) {
        return trails.stream().map(this::convertToTrailDtoFrom).collect(Collectors.toList());
    }
}
