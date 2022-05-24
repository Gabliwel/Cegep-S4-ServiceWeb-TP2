package ca.csfoy.servicesweb.camarchedoc.api.badge;

import ca.csfoy.servicesweb.camarchedoc.domain.badge.BadgeCategory;

public class BadgeDtoWithoutDesc {
    
    public final String id;
    public final String name;
    public final String icone;
    public final BadgeCategory category;

    public BadgeDtoWithoutDesc(String id, String name, String icone, BadgeCategory category) {
        this.id = id;
        this.name = name;
        this.icone = icone;
        this.category = category;
    }
}
