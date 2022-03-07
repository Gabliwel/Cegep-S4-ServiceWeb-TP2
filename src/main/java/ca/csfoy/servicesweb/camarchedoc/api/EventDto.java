package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDate;

public class EventDto {

    public final String id;
    public final String name;
    public final String description;
    public final LocalDate startDate;
    public final TrailDto trail;
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
