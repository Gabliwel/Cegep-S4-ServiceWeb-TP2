package ca.csfoy.servicesweb.camarchedoc.domain;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;

import ca.csfoy.servicesweb.camarchedoc.api.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;

public class EventMother {
    
    public static final String ANY_ID = RandomStringUtils.randomNumeric(2);
    public static final String ANY_NAME = "Event Name " + ANY_ID;
    public static final String ANY_DESCRIPTION = "Event Description " + ANY_ID;
    public static final LocalDate ANY_STARTING_DATE = LocalDate.now().plusDays(3);
    public static final String ANY_ORGANIZER = "Event Organizer " + ANY_ID;
    
    public static EventDto getEventDtoWithId(String id) {
        return new EventDto(id, ANY_NAME, ANY_DESCRIPTION, ANY_STARTING_DATE, null, ANY_ORGANIZER);
    }
    
    public static EventDto getEventDtoWithId(String id, TrailDto trail) {
        return new EventDto(id, ANY_NAME, ANY_DESCRIPTION, ANY_STARTING_DATE, trail, ANY_ORGANIZER);
    }
    
    public static Event getEventWithAutomaticId() {
        return new Event(ANY_NAME, ANY_DESCRIPTION, ANY_STARTING_DATE, null, ANY_ORGANIZER);
    }
    
    public static Event getEventWithId(String id, Trail trail) {
        return new Event(id, ANY_NAME, ANY_DESCRIPTION, ANY_STARTING_DATE, trail, ANY_ORGANIZER);
    }
}
