package ca.csfoy.servicesweb.camarchedoc.api;

public class RatingDto {

    public final String id;
    public final TrailDto trail;
    public final Double note;
    public final String comment;
    
    public RatingDto(String id, TrailDto trail, Double note, String comment) {
        this.id = id;
        this.trail = trail;
        this.note = note;
        this.comment = comment;
    }
}
