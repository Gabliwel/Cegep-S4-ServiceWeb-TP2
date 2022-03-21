package ca.csfoy.servicesweb.camarchedoc.api;

import ca.csfoy.servicesweb.camarchedoc.domain.Trail;

public class RatingDto {

    public final String id;
    public final Trail trail;
    public final Double note;
    public final String comment;
    
    public RatingDto(String id, Trail trail, Double note, String comment) {
        this.id = id;
        this.trail = trail;
        this.note = note;
        this.comment = comment;
    }
}
