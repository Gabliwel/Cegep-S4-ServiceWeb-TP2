package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailStatus;

public class TrailDto {
    
    public static final String ID_VALID_PATTERN = "^[0-9]$";
    public static final String INVALID_ID_MESSAGE = "Id must be numbers only.";

    @Pattern(regexp = ID_VALID_PATTERN, message = INVALID_ID_MESSAGE, groups = {Default.class})
    public final String id;
    @NotNull (message = "Name must not be null.", groups = {Default.class, CreateGroupValidation.class})
    @NotEmpty (message = "Name must not be empty.", groups = {Default.class, CreateGroupValidation.class})
    @NotBlank (message = "Name must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String name;
    @NotNull (message = "Description must not be null.", groups = {Default.class, CreateGroupValidation.class})
    @NotEmpty (message = "Description must not be empty.", groups = {Default.class, CreateGroupValidation.class})
    @NotBlank (message = "Description must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String description;
    @NotNull (message = "City must not be null.", groups = {Default.class, CreateGroupValidation.class})
    @NotEmpty (message = "City must not be empty.", groups = {Default.class, CreateGroupValidation.class})
    @NotBlank (message = "City must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String city;
    @NotNull (message = "Difficulty must not be null.", groups = {Default.class, CreateGroupValidation.class})
    public final TrailDifficulty difficulty;
    @NotNull (message = "Opening date must not be null.", groups = {Default.class, CreateGroupValidation.class})
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past (message = "Opening date must be a past date.", groups = {Default.class, CreateGroupValidation.class})
    public final LocalDate openingDate;
    @NotNull (message = "Last maintenance date must not be null.", groups = {Default.class, CreateGroupValidation.class})
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past (message = "Last maintenance date must be a past date.", groups = {Default.class, CreateGroupValidation.class})
    public final LocalDate lastMaintenanceDate;
    public final TrailStatus status;

    public TrailDto(String id, String name, String description, String city, TrailDifficulty difficulty, LocalDate openingDate, 
            LocalDate lastMaintenanceDate, TrailStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.difficulty = difficulty;
        this.openingDate = openingDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.status = status;
    }
}
