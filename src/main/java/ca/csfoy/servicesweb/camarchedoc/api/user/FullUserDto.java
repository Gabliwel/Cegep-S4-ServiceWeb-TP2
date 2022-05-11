package ca.csfoy.servicesweb.camarchedoc.api.user;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import ca.csfoy.servicesweb.camarchedoc.api.global.Const;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.Badge;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class FullUserDto {
    
    @Pattern(regexp = Const.ID_VALID_PATTERN, message = Const.INVALID_ID_MESSAGE, groups = {Default.class})
    public final String id;
    @NotBlank (message = "First name must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String firstname;
    @NotBlank (message = "Last name must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String lastname;
    @NotBlank (message = "Email must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    @Email(message = "Invalid email format", groups = {Default.class, CreateGroupValidation.class})
    public final String email;
    @NotBlank (message = "Password must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    @Size(min = 12, message = "Password must have at least 12 caracters.", groups = {Default.class, CreateGroupValidation.class})
    public final String password;
    @NotNull(message = "Average difficulty shouldn't be null", groups = {Default.class, CreateGroupValidation.class})
    public final TrailDifficulty averageDifficulty;
    // We allow these lists to be null/empty, so no validation is done 
    public final Set<TrailDto> favoritesTrails;
    public final Set<TrailDto> trailsToTry;
    public final Set<Badge> badges;

    public FullUserDto(String id, String firstname, String lastname, String email, String password, TrailDifficulty averageDifficulty,
            Set<TrailDto> favoritesTrails, Set<TrailDto> trailsToTry, Set<Badge> badges) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.averageDifficulty = averageDifficulty;
        this.favoritesTrails = favoritesTrails;
        this.trailsToTry = trailsToTry;
        this.badges = badges;
    }
}
