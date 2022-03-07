package ca.csfoy.servicesweb.camarchedoc.api;

public class SearchTrailDto {

    public final Integer id;
    public final String name;
    public final String city;
    public final Integer difficulty;

    public SearchTrailDto(Integer id, String name, String city, Integer difficulty) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.difficulty = difficulty;
    }
}
