package ca.csfoy.servicesweb.camarchedoc.api.rating;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping(value = RatingResource.RESOURCE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public interface RatingResource {

    String RESOURCE_PATH = "/ratings";
    String PATH_VARIABLE_ID = "id";

    String SEARCH_PATH = "/trails";
    String PATH_WITH_ID = "/{" + PATH_VARIABLE_ID + "}";
    
    @Operation(summary = "Obtient un appreciation de marche par son identifiant.")
    @ApiResponses(value = { 
                        @ApiResponse(responseCode = "200", description = "appreciation trouvé", content = { 
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = RatingDto.class)) }), 
                        @ApiResponse(responseCode = "400", description = "Identifiant invalide", content = @Content),
                        @ApiResponse(responseCode = "404", description = "appreciation introuvable", content = @Content) })
    @GetMapping(RatingResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.OK)
    RatingDto getById(@PathVariable(RatingResource.PATH_VARIABLE_ID) String id);

    @Operation(summary = "Obtient tous les appreciations de marche disponibles.")
    @ApiResponses(value = { 
                       @ApiResponse(responseCode = "200", description = "appreciations trouvés", content = { 
                                       @Content(mediaType = "application/json", schema = @Schema(implementation = RatingDto.class)) }) })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<RatingDto> getAll();

    @Operation(summary = "Obtient tous les appreciations de marche disponibles.")
    @ApiResponses(value = { 
                        @ApiResponse(responseCode = "200", description = "appreciations trouvés", content = { 
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = RatingDto.class)) }) })
    @GetMapping(RatingResource.SEARCH_PATH + RatingResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.OK)
    List<RatingDto> search(@PathVariable(RatingResource.PATH_VARIABLE_ID) String id);

    @Operation(summary = "Crée une nouvelle appreciation de marche avec les informations données.")
    @ApiResponses(value = { 
                        @ApiResponse(responseCode = "201", description = "appreciation créé", content = { 
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = RatingDto.class)) }), 
                        @ApiResponse(responseCode = "400", description = "appreciation invalide", content = @Content), 
                        @ApiResponse(responseCode = "415", description = "appreciation déjà existant", content = @Content) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RatingDto create(@RequestBody RatingDto dto);
}
