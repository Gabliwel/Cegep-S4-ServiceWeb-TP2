package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDate;

import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailStatus;

public class TrailDto {

    public final String id;
    public final String name;
    public final String description;
    public final String city;
    public final TrailDifficulty difficulty;
    public final LocalDate openingDate;
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
