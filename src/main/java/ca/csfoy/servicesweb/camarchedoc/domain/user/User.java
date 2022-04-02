package ca.csfoy.servicesweb.camarchedoc.domain.user;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ca.csfoy.servicesweb.camarchedoc.domain.IdentifiantGenerator;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

@Entity
public class User {

    @Id
    public String id;
    @Column(length = 50, nullable = false)
    public String firstname;
    @Column(length = 50, nullable = false)
    public String lastname;
    @Enumerated
    public TrailDifficulty preferredDifficulty;
    ///////////////////////////////////////////////////////////////qqqqqqqqqqqqqqqqqquuestion prof pour les join et inverse join
    @ManyToMany(targetEntity = Trail.class, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_FAVORITE_TRAILS", joinColumns = @JoinColumn(name = "ID"), inverseJoinColumns = @JoinColumn(name = "TRAIL_ID"))
    public Set<Trail> favoritesTrails;
    @ManyToMany(targetEntity = Trail.class, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_TRAILS_TO_TRY", joinColumns = @JoinColumn(name = "ID"), inverseJoinColumns = @JoinColumn(name = "TRAIL_ID"))
    public Set<Trail> trailsToTry;

    public User(String id, String firstname, String lastname, TrailDifficulty preferredDifficulty, Set<Trail> favoritesTrails, Set<Trail> trailsToTry) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.preferredDifficulty = preferredDifficulty;
        this.favoritesTrails = favoritesTrails;
        this.trailsToTry = trailsToTry;
    }

    public User(String firstname, String lastname, TrailDifficulty preferredDifficulty, Set<Trail> favoritesTrails, Set<Trail> trailsToTry) {
        this(IdentifiantGenerator.getNextIdAsString(), firstname, lastname, preferredDifficulty, favoritesTrails, trailsToTry);
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public TrailDifficulty getPreferredDifficulty() {
        return this.preferredDifficulty;
    }

    public Set<Trail> getFavoritesTrails() {
        return favoritesTrails;
    }

    public Set<Trail> getTrailsToTry() {
        return trailsToTry;
    }
}

