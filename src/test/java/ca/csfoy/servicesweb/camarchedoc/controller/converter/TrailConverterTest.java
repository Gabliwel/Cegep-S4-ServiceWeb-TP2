package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

class TrailConverterTest {

    @Test
    void whenConvertingDtoWithoutIdOnCreationThenTrailCreatedWithGivenFieldsAndGeneratedId() {
        Integer nextId = IdentifiantGenerator.getNextId();
        TrailDto dto = new TrailDto(null, "name", "description", "city", TrailDifficulty.BEGINNER, null, null, null, null);
        TrailConverter converter = new TrailConverter();

        Trail trail = converter.convertToTrailAtCreationFrom(dto);

        Assertions.assertEquals((nextId + 1) + "", trail.getId());
        Assertions.assertEquals(dto.name, trail.getName());
        Assertions.assertEquals(dto.description, trail.getDescription());
        Assertions.assertEquals(dto.city, trail.getCity());
        Assertions.assertEquals(dto.difficulty, trail.getDifficulty());
    }

    @Test
    void whenConvertingDtoWithIdThenTrailCreatedWithGivenFieldsIncludingId() {
        TrailDto dto = new TrailDto("1", "name", "description", "city", TrailDifficulty.BEGINNER, null, null, null, null);
        TrailConverter converter = new TrailConverter();

        Trail trail = converter.convertToTrailFrom(dto);

        Assertions.assertEquals(dto.id, trail.getId());
        Assertions.assertEquals(dto.name, trail.getName());
        Assertions.assertEquals(dto.description, trail.getDescription());
        Assertions.assertEquals(dto.city, trail.getCity());
        Assertions.assertEquals(dto.difficulty, trail.getDifficulty());
    }

    @Test
    void whenConvertingDomainObjectThenDtoCreatedWithGivenFieldsIncludingId() {
        Trail trail = new Trail("name", "description", "city", TrailDifficulty.BEGINNER, null, null);
        TrailConverter converter = new TrailConverter();

        TrailDto dto = converter.convertToTrailDtoFrom(trail);

        Assertions.assertEquals(trail.getId(), dto.id);
        Assertions.assertEquals(trail.getName(), dto.name);
        Assertions.assertEquals(trail.getDescription(), dto.description);
        Assertions.assertEquals(trail.getCity(), dto.city);
        Assertions.assertEquals(trail.getDifficulty(), dto.difficulty);
    }
    
    @Test
    void whenConvertingListOfDomainObjetsThenListOfDtosIsReturned() {
        Trail trail1 = new Trail("name1", "description1", "city1", TrailDifficulty.BEGINNER, null, null);
        Trail trail2 = new Trail("name2", "description2", "city2", TrailDifficulty.BEGINNER, null, null);
        TrailConverter converter = new TrailConverter();
        
        List<TrailDto> dtos = converter.convertTrailListFrom(List.of(trail1, trail2));
        
        Assertions.assertEquals(2, dtos.size());
    }

}
