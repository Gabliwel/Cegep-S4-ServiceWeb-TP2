package ca.csfoy.servicesweb.camarchedoc.domain;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;

import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;

public class TrailMother {
    
    public static final String ANY_ID = RandomStringUtils.randomNumeric(2);
    public static final String ANY_NAME = "Trail Name " + ANY_ID;
    public static final String ANY_DESCRIPTION = "Trail Description " + ANY_ID;
    public static final String ANY_CITY = "Québec";
    public static final TrailDifficulty ANY_DIFFICULTY = TrailDifficulty.BEGINNER;
    public static final LocalDate ANY_OPENING_DATE = LocalDate.now().minusDays(30);
    public static final LocalDate ANY_MAINTENANCE_DATE = LocalDate.now().minusDays(10);
    public static final String ANY_ORGANIZER = "Event Organizer " + ANY_ID;
    public static final TrailStatus ANY_STATUS = TrailStatus.IN_PREPARATION;
    
    public static TrailDto getEventDtoWithId(String id) {
        return new TrailDto(ANY_ID, ANY_NAME, ANY_DESCRIPTION, ANY_CITY, ANY_DIFFICULTY, ANY_OPENING_DATE, ANY_MAINTENANCE_DATE, ANY_STATUS);
    }
    
    public static Trail getEventWithAutomaticId() {
        return new Trail(ANY_NAME, ANY_DESCRIPTION, ANY_CITY, ANY_DIFFICULTY, ANY_OPENING_DATE, ANY_MAINTENANCE_DATE);
    }

}
