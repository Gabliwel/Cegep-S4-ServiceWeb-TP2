package ca.csfoy.servicesweb.camarchedoc.domain.trail;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjetAlreadySetToDesiredValue;

@Entity
public class Trail {

    @Id
    private String id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String description;
    @Column(length = 50, nullable = false)
    private String city;
    @Enumerated
    private TrailDifficulty difficulty;
    @Column(nullable = false)
    private LocalDate openingDate;
    @Column(nullable = true)
    private LocalDate lastMaintenanceDate;
    @Enumerated
    private TrailStatus status;
    private Double averageScore;

    public Trail() {}

    public Trail(String name, String description, String city, TrailDifficulty difficulty) {
        this(IdentifiantGenerator.getNextIdAsString(), name, description, city, difficulty, LocalDate.now(), LocalDate.now(), TrailStatus.IN_PREPARATION, 0.0);
    }

    public Trail(String name, String description, String city, TrailDifficulty difficulty, LocalDate openingDate, LocalDate lastMaintenanceDate) {
        this(IdentifiantGenerator.getNextIdAsString(), name, description, city, difficulty, openingDate, lastMaintenanceDate, TrailStatus.IN_PREPARATION, 0.0);
    }

    public Trail(String id, String name, String description, String city, TrailDifficulty difficulty, TrailStatus status, Double averageScore) {
        this(id, name, description, city, difficulty, LocalDate.now(), LocalDate.now(), status, averageScore);
    }    

    public Trail(String id, String name, String description, String city, TrailDifficulty difficulty, LocalDate openingDate, 
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
    
    public TrailStatus getStatus() {
        return status;
    }
    
    public void setTrailready() {
        if (this.status == TrailStatus.IN_PREPARATION) {
            this.status = TrailStatus.READY;
        } else {
                throw new ObjetAlreadySetToDesiredValue("The trail with id (" + id + ") is already set to ready.");
        }
    }
    
    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double newScore) {
        this.averageScore = newScore;
    }

}
