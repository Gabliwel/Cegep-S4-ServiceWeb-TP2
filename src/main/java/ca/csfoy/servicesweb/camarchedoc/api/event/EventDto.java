package ca.csfoy.servicesweb.camarchedoc.api.event;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.springframework.format.annotation.DateTimeFormat;

import ca.csfoy.servicesweb.camarchedoc.api.global.Const;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;

public class EventDto {

    @Pattern(regexp = Const.ID_VALID_PATTERN, message = Const.INVALID_ID_MESSAGE, groups = {Default.class})
    public final String id;
    @NotBlank (message = "Name must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String name;
    @NotBlank (message = "Description must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future (message = "Starting date must be a future date.", groups = {Default.class, CreateGroupValidation.class})
    @NotNull (message = "Starting date must not be null.", groups = {Default.class, CreateGroupValidation.class})
    public final LocalDate startDate;
    @NotNull (message = "Trail must not be null.", groups = {Default.class, CreateGroupValidation.class})
    public final TrailDto trail;
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
