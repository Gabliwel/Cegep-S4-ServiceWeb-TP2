package ca.csfoy.servicesweb.camarchedoc.api.user;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;

@RequestMapping(value = UserResource.RESOURCE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public interface UserResource {
    
    String RESOURCE_PATH = "/users";                                                     
    String RESOURCE_PATH_SIGN_UP = "/signup";
    String PATH_VARIABLE_ID = "id";
    String PATH_WITH_ID = "/{" + PATH_VARIABLE_ID + "}";
    String PATH_FOR_SUGGESTED = PATH_WITH_ID + "/suggested";

    @PostMapping("/create")
    void createUser(@RequestBody FullUserDto user);

    @GetMapping(PATH_WITH_ID)
    UserDto getUser(@PathVariable(PATH_VARIABLE_ID) String id);

    @GetMapping(PATH_FOR_SUGGESTED)
    List<TrailDto> getSuggestedTrails(@PathVariable(PATH_VARIABLE_ID) String userId);

    @PutMapping(PATH_WITH_ID)
    void modifyUser(@RequestBody FullUserDto user, @PathVariable(PATH_VARIABLE_ID) String userId);
    
    @PostMapping("/login")
    TokenDto loginUser(@RequestBody UserCredentialsDto credentials);
}
