package ca.csfoy.servicesweb.camarchedoc.api.trail;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.springframework.format.annotation.DateTimeFormat;

import ca.csfoy.servicesweb.camarchedoc.api.global.Const;
import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailStatus;

public class TrailDto {

    @Pattern(regexp = Const.ID_VALID_PATTERN, message = Const.INVALID_ID_MESSAGE, groups = {Default.class})
    public final String id;
    @NotBlank (message = "Name must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String name;
    @NotBlank (message = "Description must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String description;
    @NotBlank (message = "City must not be blank.", groups = {Default.class, CreateGroupValidation.class})
    public final String city;
    @NotNull (message = "Difficulty must not be null.", groups = {Default.class, CreateGroupValidation.class})
    public final TrailDifficulty difficulty;
    @NotNull (message = "Opening date must not be null.", groups = {Default.class, CreateGroupValidation.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past (message = "Opening date must be a past date.", groups = {Default.class, CreateGroupValidation.class})
    public final LocalDate openingDate;
    @NotNull (message = "Last maintenance date must not be null.", groups = {Default.class, CreateGroupValidation.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past (message = "Last maintenance date must be a past date.", groups = {Default.class, CreateGroupValidation.class})
    public final LocalDate lastMaintenanceDate;
    public final TrailStatus status;
    public final Double averageScore;
    public TrailWeatherInfoDto weatherInfo;

    public TrailDto(String id, String name, String description, String city, TrailDifficulty difficulty, LocalDate openingDate, 
            LocalDate lastMaintenanceDate, TrailStatus status, Double averageScore) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.difficulty = difficulty;
        this.openingDate = openingDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.status = status;
        this.averageScore = averageScore;
    }
    
    public TrailDto(String id, String name, String description, String city, TrailDifficulty difficulty, LocalDate openingDate, 
            LocalDate lastMaintenanceDate, TrailStatus status, Double averageScore, TrailWeatherInfoDto weatherInfoDto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.difficulty = difficulty;
        this.openingDate = openingDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.status = status;
        this.averageScore = averageScore;
        this.weatherInfo = weatherInfoDto;
    }
}
