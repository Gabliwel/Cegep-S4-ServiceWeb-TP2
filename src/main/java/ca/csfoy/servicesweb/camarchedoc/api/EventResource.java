package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import io.swagger.v3.oas.annotations.Operation;

@RequestMapping(value = EventResource.RESOURCE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public interface EventResource {

    String RESOURCE_PATH = "/events";
    String SEARCH_PATH = "/search";
    String PATH_VARIABLE_ID = "id";
    String PATH_WITH_ID = "/{" + PATH_VARIABLE_ID + "}";

    @Operation(summary = "Obtient un événement de marche par son identifiant.")
    @GetMapping(EventResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.OK)
    EventDto getById(@PathVariable(EventResource.PATH_VARIABLE_ID) String id);

    @Operation(summary = "Obtient tous les événements de marche disponibles.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<EventDto> getAll();

    @Operation(summary = "Crée un nouvel événement de marche avec les informations données.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventDto create(@RequestBody EventDto dto);

    @Operation(summary = "Modifie un événement de marche existant avec les informations données.")
    @PutMapping(EventResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable(EventResource.PATH_VARIABLE_ID) String id, @RequestBody EventDto dto);

    @Operation(summary = "Supprime un événement de marche par son identifiant.")
    @DeleteMapping(EventResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable(EventResource.PATH_VARIABLE_ID) String id);
    
    @Operation(summary = "Obtient tout les évenemnts selon le sentier et/ou la date.")
    @GetMapping(EventResource.SEARCH_PATH)
    @ResponseStatus(HttpStatus.OK)
    List<EventDto> search(String trailId, LocalDate startDate);
}
