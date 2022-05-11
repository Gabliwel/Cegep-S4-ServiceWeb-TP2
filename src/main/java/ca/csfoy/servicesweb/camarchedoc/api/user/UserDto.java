package ca.csfoy.servicesweb.camarchedoc.api.user;

import java.util.Set;

import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class UserDto {
    
    public final String id;
    public final String firstname;
    public final String lastname;   
    public final TrailDifficulty averageDifficulty;
    public final Set<TrailDto> favoritesTrails;
    public final Set<TrailDto> trailsToTry;
    public final Set<BadgeDto> badges;

    public UserDto(String id, String firstname, String lastname, TrailDifficulty averageDifficulty, 
            Set<TrailDto> favoritesTrails, Set<TrailDto> trailsToTry, Set<BadgeDto> badges) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.averageDifficulty = averageDifficulty;
        this.favoritesTrails = favoritesTrails;
        this.trailsToTry = trailsToTry;
        this.badges = badges;
    }
}
