package ca.csfoy.servicesweb.camarchedoc.api.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;

import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RequestMapping(value = UserResource.RESOURCE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public interface UserResource {
    
    String RESOURCE_PATH = "/users";                                                     
    String RESOURCE_PATH_SIGN_UP = "/signup";
    String PATH_VARIABLE_ID = "id";
    String PATH_WITH_ID = "/{" + PATH_VARIABLE_ID + "}";
    String PATH_FOR_OPENINGS = PATH_WITH_ID + "/openings";
    String PATH_FOR_SUGGESTED = PATH_WITH_ID + "/suggested";

    @PostMapping
    void createUser(@RequestBody UserDto user);

    @GetMapping(PATH_WITH_ID)
    UserDto getUser(@PathVariable(PATH_VARIABLE_ID) String id);

    @GetMapping(PATH_FOR_OPENINGS)
    List<TrailDto> getNewTrails(@PathVariable(PATH_VARIABLE_ID) String userId);

    @GetMapping(PATH_FOR_SUGGESTED)
    List<TrailDto> getSuggestedTrails(@PathVariable(PATH_VARIABLE_ID) String userId);

    @PutMapping
    void modifyUser(@RequestBody UserDto user);
}
