package ca.csfoy.servicesweb.camarchedoc.api;

import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;

public class SearchTrailDto {

    public final String id;
    public final String name;
    public final String city;
    public final TrailDifficulty difficulty;

    public SearchTrailDto(String id, String name, String city, TrailDifficulty difficulty) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.difficulty = difficulty;
    }
}
