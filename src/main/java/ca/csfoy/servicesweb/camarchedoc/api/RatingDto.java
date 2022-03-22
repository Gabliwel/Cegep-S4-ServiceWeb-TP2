package ca.csfoy.servicesweb.camarchedoc.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;

public class RatingDto {

    public static final Double MIN_NOTE = 1.0;
    public static final Double MAX_NOTE = 5.0;
    public static final String ID_VALID_PATTERN = "[0-9]+";
    public static final String INVALID_ID_MESSAGE = "Id must be numbers only.";

    @Pattern(regexp = ID_VALID_PATTERN, message = INVALID_ID_MESSAGE, groups = {Default.class})
    public final String id;
    @NotNull (message = "Trail must not be null.", groups = {Default.class, CreateGroupValidation.class})
    public final TrailDto trail;
    public final Double note;
    @Size(max = 140, message = "Comment must have less than 140 caracters.", groups = {Default.class, CreateGroupValidation.class})
    public final String comment;
    
    public RatingDto(String id, TrailDto trail, Double note, String comment) {
        this.id = id;
        this.trail = trail;
        this.note = note;
        this.comment = comment;
    }
}
