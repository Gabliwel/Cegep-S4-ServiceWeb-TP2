package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;

public class EventDto {

    public static final String ID_VALID_PATTERN = "[0-9]+";
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future (message = "Starting date must be a future date.", groups = {Default.class, CreateGroupValidation.class})
    @NotNull (message = "Starting date must not be null.", groups = {Default.class, CreateGroupValidation.class})
    public final LocalDate startDate;
    @NotNull (message = "Trail must not be null.", groups = {Default.class, CreateGroupValidation.class})
    public final TrailDto trail;
    @NotNull (message = "Organizer must not be null.", groups = {Default.class, CreateGroupValidation.class})
    @NotEmpty (message = "Organizer must not be empty.", groups = {Default.class, CreateGroupValidation.class})
    @NotBlank (message = "Organizer must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String organizer;

    public EventDto(String id, String name, String description, LocalDate startDate, TrailDto trail, String organizer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.trail = trail;
        this.organizer = organizer;
    }
}
