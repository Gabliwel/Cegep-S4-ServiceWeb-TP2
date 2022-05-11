package ca.csfoy.servicesweb.camarchedoc.domain.badge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Badge {
    
    @Id
    private String id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 150, nullable = false)
    private String description;
    @Column(length = 75, nullable = false)
    private String icone;
    @Enumerated
    private BadgeCategory category;

    public Badge() {}

    public Badge(String id, String name, String descriptions, String icone, BadgeCategory category) {
        this.id = id;
        this.name = name;
        this.description = descriptions;
        this.icone = icone;
        this.category = category;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getIcone() {
        return this.icone;
    }
    
    public BadgeCategory getCategory() {
        return this.category;
    }

}
