package ca.csfoy.servicesweb.camarchedoc.api.trail;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrailWeatherInfoDto {
    
    @JsonProperty("temp")
    public final Double temp;
    @JsonProperty("feels_like")
    public final Double feelLike;
    @JsonProperty("wind_speed")
    public final Double windSpeed;
    @JsonProperty("description")
    public final String description;

    public TrailWeatherInfoDto(Double temp, Double feelLike, Double windSpeed, String description) {
        this.temp = temp;
        this.feelLike = feelLike;
        this.windSpeed = windSpeed;
        this.description = description;
    }

}
