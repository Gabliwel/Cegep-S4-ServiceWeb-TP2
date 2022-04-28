package ca.csfoy.servicesweb.camarchedoc.api.user;

import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import ca.csfoy.servicesweb.camarchedoc.api.global.Const;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class UserDto {
    
    @Pattern(regexp = Const.ID_VALID_PATTERN, message = Const.INVALID_ID_MESSAGE, groups = {Default.class})
    public final String id;
    public final String firstname;
    public final String lastname;
    public final TrailDifficulty averageDifficulty;
    public final Set<TrailDto> favoritesTrails;
    public final Set<TrailDto> trailsToTry;

    public UserDto(String id, String firstname, String lastname, TrailDifficulty averageDifficulty, 
            Set<TrailDto> favoritesTrails, Set<TrailDto> trailsToTry) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.averageDifficulty = averageDifficulty;
        this.favoritesTrails = favoritesTrails;
        this.trailsToTry = trailsToTry;
    }
}
