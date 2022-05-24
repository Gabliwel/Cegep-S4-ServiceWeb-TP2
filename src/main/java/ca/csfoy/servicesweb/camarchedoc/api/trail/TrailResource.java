package ca.csfoy.servicesweb.camarchedoc.api.trail;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping(value = TrailResource.RESOURCE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public interface TrailResource {

    String RESOURCE_PATH = "/trails";
    String SEARCH_PATH = "/search";
    String PATH_VARIABLE_ID = "id";
    String PATH_WITH_ID = "/{" + PATH_VARIABLE_ID + "}";
    String QUERY_PARAM_CITY = "city";
    String QUERY_PARAM_DIFFICULTY = "difficulty";
    String READY_PATH = "/ready" + PATH_WITH_ID;

    @Operation(summary = "Obtient un sentier de marche par son identifiant.")
    @ApiResponses(value = { 
                    @ApiResponse(responseCode = "200", description = "Sentier trouvé", content = { 
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrailDto.class)) }), 
                    @ApiResponse(responseCode = "400", description = "Identifiant invalide", content = @Content), 
                    @ApiResponse(responseCode = "404", description = "Sentier introuvable", content = @Content) })
    @GetMapping(TrailResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.OK)
    TrailDtoWithMeteo getById(@PathVariable(TrailResource.PATH_VARIABLE_ID) String id);

    @Operation(summary = "Obtient tous les sentiers de marche disponibles.")
    @ApiResponses(value = { 
                      @ApiResponse(responseCode = "200", description = "Sentiers trouvés", content = { 
                                     @Content(mediaType = "application/json", schema = @Schema(implementation = TrailDto.class)) }) })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<TrailDto> getAll();

    @Operation(summary = "Obtient tous les sentiers de marche disponibles.")
    @ApiResponses(value = { 
                     @ApiResponse(responseCode = "200", description = "Sentiers trouvés", content = { 
                                     @Content(mediaType = "application/json", schema = @Schema(implementation = TrailDto.class)) }) })
    @GetMapping(TrailResource.SEARCH_PATH)
    @ResponseStatus(HttpStatus.OK)
    List<SearchTrailDto> search(@RequestParam(value = QUERY_PARAM_CITY, required = false) String city,
            @RequestParam(value = QUERY_PARAM_DIFFICULTY, required = false) TrailDifficulty difficulty);

    @Operation(summary = "Crée un nouveau sentier de marche avec les informations données.")
    @ApiResponses(value = { 
                    @ApiResponse(responseCode = "201", description = "Sentier créé", content = { 
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrailDto.class)) }), 
                    @ApiResponse(responseCode = "400", description = "Sentier invalide", content = @Content), 
                    @ApiResponse(responseCode = "415", description = "Sentier déjà existant", content = @Content) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TrailDto create(@RequestBody TrailDto dto);

    @Operation(summary = "Modifie un sentier de marche existant avec les informations données.")
    @ApiResponses(value = { 
                    @ApiResponse(responseCode = "204", description = "Sentier créé", content = @Content), 
                    @ApiResponse(responseCode = "400", description = "Sentier invalide", content = @Content), 
                    @ApiResponse(responseCode = "404", description = "Sentier introuvable", content = @Content) })
    @PutMapping(TrailResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable(TrailResource.PATH_VARIABLE_ID) String id, @RequestBody TrailDto dto);

    @Operation(summary = "Supprime un sentier de marche par son identifiant.")
    @ApiResponses(value = { 
                    @ApiResponse(responseCode = "204", description = "Sentier supprimé", content = @Content), 
                    @ApiResponse(responseCode = "400", description = "Identifiant invalide", content = @Content), 
                    @ApiResponse(responseCode = "404", description = "Sentier introuvable", content = @Content) })
    @DeleteMapping(TrailResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable(TrailResource.PATH_VARIABLE_ID) String id);
    
    @Operation(summary = "Modifie un sentier de marche existant pour le rendre prêt.")
    @ApiResponses(value = { 
                      @ApiResponse(responseCode = "204", description = "Sentier créé", content = @Content), 
                      @ApiResponse(responseCode = "400", description = "Sentier invalide", content = @Content), 
                      @ApiResponse(responseCode = "404", description = "Sentier introuvable", content = @Content) })
    @PutMapping(TrailResource.READY_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateToReady(@PathVariable(TrailResource.PATH_VARIABLE_ID) String id);
}
