package ca.csfoy.servicesweb.camarchedoc.api.badge;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping(value = BadgeResource.RESOURCE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public interface BadgeResource {
    
    String RESOURCE_PATH = "/badges";
    String PATH_VARIABLE_ID = "id";
    String PATH_WITH_ID = "/{" + PATH_VARIABLE_ID + "}";
    String QUERY_PARAM_USER = "addTo";
    
    @Operation(summary = "Obtient un badge par son identifiant.")
    @GetMapping(BadgeResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.OK)
    BadgeDtoWithoutDesc getById(@PathVariable(BadgeResource.PATH_VARIABLE_ID) String id);
    
    @Operation(summary = "Obtient tous les badges disponibles.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<SmallBadgeDto> getAll();
    
    @Operation(summary = "Modifie un user existant avec un badge donné existant.")
    @PutMapping(BadgeResource.PATH_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void giveBadge(@PathVariable(BadgeResource.PATH_VARIABLE_ID) String id, 
            @RequestParam(value = BadgeResource.QUERY_PARAM_USER, required = false) String userId);   
}
