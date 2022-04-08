package ca.csfoy.servicesweb.camarchedoc.api.user;

import java.util.Set;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class UserDto {
    
    public static final String ID_VALID_PATTERN = "[0-9]+";
    public static final String INVALID_ID_MESSAGE = "Id must be numbers only.";
    
    @Pattern(regexp = ID_VALID_PATTERN, message = INVALID_ID_MESSAGE, groups = {Default.class})
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
