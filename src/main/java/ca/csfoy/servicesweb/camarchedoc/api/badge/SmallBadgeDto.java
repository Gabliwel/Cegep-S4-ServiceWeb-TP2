package ca.csfoy.servicesweb.camarchedoc.api.badge;

import ca.csfoy.servicesweb.camarchedoc.domain.badge.BadgeCategory;

public class SmallBadgeDto {
    
    public final String id;
    public final String name;
    public final BadgeCategory category;

    public SmallBadgeDto(String id, String name, BadgeCategory category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

}
