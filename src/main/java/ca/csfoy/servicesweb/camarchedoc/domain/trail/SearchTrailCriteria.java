package ca.csfoy.servicesweb.camarchedoc.domain.trail;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class SearchTrailCriteria {
    
    private String city;
    private TrailDifficulty difficulty;
    
    public SearchTrailCriteria(String city, TrailDifficulty difficulty) {
        this.city = StringUtils.isBlank(city) ? "" : city;
        this.difficulty = difficulty;
    }

    public String getCity() {
        return city;
    }

    public TrailDifficulty getDifficulty() {
        return difficulty;
    }
    
    public boolean shouldIncludeCity() {
        return "".contentEquals(city);
    }
    
    public boolean shouldIncludeDifficulty() {
        return Objects.isNull(difficulty);
    }
}
