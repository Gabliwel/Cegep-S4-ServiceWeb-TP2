package ca.csfoy.servicesweb.camarchedoc.domain.trail;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailWeatherInfoDto;

@Service
public class TrailWeatherInfo {
    
    private static final String API_KEY = "5a7a9ae4a7d90cb817d7de02de18cd83";
    private static final String QUERY = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public TrailWeatherInfo(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    public TrailWeatherInfoDto getWeatherInfo(String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        
        String url = String.format(QUERY, city, API_KEY);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        try {
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            JsonNode jsonNode = objectMapper.readValue(result.getBody(), JsonNode.class);
            JsonNode temp = jsonNode.at("/main/temp");
            JsonNode feelsLike = jsonNode.at("/main/feels_like");
            JsonNode speed = jsonNode.at("/wind/speed");
            JsonNode weather = jsonNode.at("/weather");
            JsonNode weatherDesc = weather.findValue("description");
            
            double tempDouble = Math.round(temp.asDouble() - 273.15);
            double feelsLikeDouble = Math.round(feelsLike.asDouble() - 273.15);
            return new TrailWeatherInfoDto(tempDouble, feelsLikeDouble, speed.asDouble(), weatherDesc.asText());
        } catch (Throwable e) {
            e.printStackTrace();
            return new TrailWeatherInfoDto(0.0, 0.0, 0.0, "Non-disponible");
        }
    }

}
