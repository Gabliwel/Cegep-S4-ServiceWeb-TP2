package ca.csfoy.servicesweb.camarchedoc.domain.rating;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.user.User;

@Entity
public class Rating {
    
    public static final Double MIN_NOTE = 1.0;
    public static final Double MAX_NOTE = 5.0;

    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "USER") 
    private User user;
    @OneToOne
    @JoinColumn(name = "TRAIL") 
    private Trail trail;
    @Column(length = 50, nullable = false)
    private Double note;
    @Column(length = 140)
    private String comment;
    
    public Rating() {}
    
    public Rating(User user, Trail trail, Double note, String comment) {
        this(IdentifiantGenerator.getNextIdAsString(), user, trail, note, comment);
    }
    
    public Rating(String id, User user, Trail trail, Double note, String comment) {
        this.id = id;
        this.user = user;
        this.trail = trail;
        this.note = note;
        this.comment = comment;
    }
    
    public String getId() {
        return id;
    }
    
    public User getUser() {
        return user;
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
