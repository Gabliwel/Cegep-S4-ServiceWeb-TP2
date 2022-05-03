package ca.csfoy.servicesweb.camarchedoc.domain.user;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import ca.csfoy.servicesweb.camarchedoc.domain.rating.Rating;
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
    @Column(length = 100, nullable = false)
    public String email;
    @Column(length = 100, nullable = false)
    public String password;
    @OneToOne
    @JoinColumn(name = "ROLE_ID") 
    public Role role;
    @Enumerated
    public TrailDifficulty preferredDifficulty;
    @ManyToMany(targetEntity = Trail.class, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_FAVORITE_TRAILS", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "TRAIL_ID"))
    public Set<Trail> favoritesTrails;
    @ManyToMany(targetEntity = Trail.class, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_TRAILS_TO_TRY", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "TRAIL_ID"))
    public Set<Trail> trailsToTry;

    public User() {}
    
    public User(String id, String firstname, String lastname, String email, String password, Role role, TrailDifficulty preferredDifficulty, 
            Set<Trail> favoritesTrails, Set<Trail> trailsToTry) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.preferredDifficulty = preferredDifficulty;
        this.favoritesTrails = favoritesTrails;
        this.trailsToTry = trailsToTry;
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

    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public Role getRole() {
        return role;
    }
    
    public boolean isAdmin() {
        return role.getRoleName().equals("ADMIN");
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

    public void addFavoriteTrail(Trail trail, Rating rating) {
        if (rating.getNote().equals(Rating.MAX_NOTE)) {
            if (this.favoritesTrails == null) {
                favoritesTrails = new HashSet<Trail>();
            }
            this.favoritesTrails.add(trail);
        }

    }

}

