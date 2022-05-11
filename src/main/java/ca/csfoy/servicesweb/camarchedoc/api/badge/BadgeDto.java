package ca.csfoy.servicesweb.camarchedoc.api.badge;

import ca.csfoy.servicesweb.camarchedoc.domain.badge.BadgeCategory;

public class BadgeDto {
    
    // À utiliser si on fait de la création/modification de badge
    
    public final String id;
    public final String name;
    public final String description;
    public final String icone;
    public final BadgeCategory category;

    public BadgeDto(String id, String name, String description, String icone, BadgeCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icone = icone;
        this.category = category;
    }

}
