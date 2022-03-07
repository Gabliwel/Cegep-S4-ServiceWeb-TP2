package ca.csfoy.servicesweb.camarchedoc.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Event {

    private String id;
    private String name;
    private String description;
    private LocalDate startDate;
    private Trail trail;
    private String organizer;

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
