package ca.csfoy.servicesweb.camarchedoc.domain;

import java.time.LocalDate;

public class Trail {

    private String id;
    private String name;
    private String description;
    private String city;
    private TrailDifficulty difficulty;
    private LocalDate openingDate;
    private LocalDate lastMaintenanceDate;

    public Trail(String name, String description, String city, TrailDifficulty difficulty) {
        this(IdentifiantGenerator.getNextIdAsString(), name, description, city, difficulty, LocalDate.now(), LocalDate.now());
    }

    public Trail(String name, String description, String city, TrailDifficulty difficulty, LocalDate openingDate, LocalDate lastMaintenanceDate) {
        this(IdentifiantGenerator.getNextIdAsString(), name, description, city, difficulty, openingDate, lastMaintenanceDate);
    }

    public Trail(String id, String name, String description, String city, TrailDifficulty difficulty) {
        this(id, name, description, city, difficulty, LocalDate.now(), LocalDate.now());
    }    

    public Trail(String id, String name, String description, String city, TrailDifficulty difficulty, LocalDate openingDate, LocalDate lastMaintenanceDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.difficulty = difficulty;
        this.openingDate = openingDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public TrailDifficulty getDifficulty() {
        return difficulty;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }
}
