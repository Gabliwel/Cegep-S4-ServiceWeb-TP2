package ca.csfoy.servicesweb.camarchedoc.infra;

import java.time.LocalDate;

import ca.csfoy.servicesweb.camarchedoc.domain.Event;

public interface EventDao extends Dao<Event, String> {
    
    boolean doesExist(LocalDate startDate, String trailId);

}
