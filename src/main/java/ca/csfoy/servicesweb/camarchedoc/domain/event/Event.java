package ca.csfoy.servicesweb.camarchedoc.domain.event;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;

@Entity
public class Event {

    @Id
    private String id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate startDate;
    @OneToOne
    @JoinColumn(name = "TRAIL") 
    private Trail trail;
    @Column(length = 50, nullable = false)
    private String organizer;
    
    public Event() {}

    public Event(String name, String description, LocalDate startDate, Trail trail, String organizer) {
        this.id = IdentifiantGenerator.getNextIdAsString();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.trail = trail;
        this.organizer = organizer;
    }

    public Event(String id, String name, String description, LocalDate startDate, Trail trail, String organizer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.trail = trail;
        this.organizer = organizer;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public Trail getTrail() {
        return trail;
    }

    public String getTrailId() {
        if (Objects.nonNull(trail)) {
            return trail.getId();
        }

        return "0";
    }

    public String getOrganizer() {
        return organizer;
    }
}
