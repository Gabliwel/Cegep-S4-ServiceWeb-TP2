package ca.csfoy.servicesweb.camarchedoc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Rating {

    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "TRAIL") 
    private Trail trail;
    @Column(length = 50, nullable = false)
    private Double note;
    @Column(length = 140)
    private String comment;
    
    public Rating() {}
    
    public Rating(Trail trail, Double note, String comment) {
        this(IdentifiantGenerator.getNextIdAsString(), trail, note, comment);
    }
    
    public Rating(String id, Trail trail, Double note, String comment) {
        this.id = id;
        this.trail = trail;
        this.note = note;
        this.comment = comment;
    }
    
    public String getId() {
        return id;
    }
    
    public Trail getTrail() {
        return trail;
    }
    
    public Double getNote() {
        return note;
    }
    
    public String getComment() {
        return comment;
    }
}
