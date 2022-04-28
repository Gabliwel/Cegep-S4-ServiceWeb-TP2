package ca.csfoy.servicesweb.camarchedoc.api.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

public class UserDtoForCreate {
    
    @NotBlank (message = "First name must not be blank.", groups = {Default.class})
    public final String firstname;
    @NotBlank (message = "Last name must not be blank.", groups = {Default.class})
    public final String lastname;
    @NotBlank (message = "Email must not be blank.", groups = {Default.class})
    @Email(message = "Invalid email format", groups = {Default.class})
    public final String email;
    @NotBlank (message = "Password must not be blank.", groups = {Default.class})
    @Size(min = 12, message = "Password must have at least 12 caracters.", groups = {Default.class})
    public final String password;
    @NotNull(message = "Average difficulty shouldn't be null", groups = {Default.class})
    public final TrailDifficulty averageDifficulty;

    public UserDtoForCreate(String firstname, String lastname, String email, String password, TrailDifficulty averageDifficulty) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.averageDifficulty = averageDifficulty;
    }
}
