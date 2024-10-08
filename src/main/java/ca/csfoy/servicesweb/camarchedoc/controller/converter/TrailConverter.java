package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ca.csfoy.servicesweb.camarchedoc.api.trail.SearchTrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDtoWithMeteo;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;

@Component
public class TrailConverter {

    public Trail convertToTrailAtCreationFrom(TrailDto dto) {
        return new Trail(dto.name, dto.description, dto.city, dto.difficulty, dto.openingDate, dto.lastMaintenanceDate);
    }

    public Trail convertToTrailFrom(TrailDto dto) {
        return new Trail(dto.id, dto.name, dto.description, dto.city, dto.difficulty, dto.openingDate, dto.lastMaintenanceDate, dto.status, dto.averageScore);
    }

    public TrailDto convertToTrailDtoFrom(Trail trail) {
        return new TrailDto(trail.getId(), trail.getName(), trail.getDescription(), 
                trail.getCity(), trail.getDifficulty(), trail.getOpeningDate(), trail
                .getLastMaintenanceDate(), trail.getStatus(), trail.getAverageScore());
    }
    
    public TrailDtoWithMeteo convertToTrailDtoWithMeteoFrom(Trail trail) {
        return new TrailDtoWithMeteo(trail.getId(), trail.getName(), trail.getDescription(), 
                trail.getCity(), trail.getDifficulty(), trail.getOpeningDate(), trail
                .getLastMaintenanceDate(), trail.getStatus(), trail.getAverageScore(), trail.getWeather());
    }
    
    public SearchTrailDto convertToSearchTrailDtoFrom(Trail trail) {
        return new SearchTrailDto(trail.getId(), trail.getName(), trail.getCity(), trail.getDifficulty());
    }

    public List<TrailDto> convertTrailListFrom(List<Trail> trails) {
        return trails.stream().map(this::convertToTrailDtoFrom).collect(Collectors.toList());
    }

    public List<SearchTrailDto> convertSearchTrailListFrom(List<Trail> trails) {
        return trails.stream().map(this::convertToSearchTrailDtoFrom).collect(Collectors.toList());
    }
    
    public Set<TrailDto> convertTrailSetFrom(Set<Trail> trails) {
        if (trails == null || trails.isEmpty()) {
            return null;
        }
        return trails.stream().map(this::convertToTrailDtoFrom).collect(Collectors.toSet());
    }

    public Set<Trail> convertTrailDtosSetFrom(Set<TrailDto> trails) {
        if (trails == null || trails.isEmpty()) {
            return null;
        }
        return trails.stream().map(this::convertToTrailFrom).collect(Collectors.toSet());
    }

}
