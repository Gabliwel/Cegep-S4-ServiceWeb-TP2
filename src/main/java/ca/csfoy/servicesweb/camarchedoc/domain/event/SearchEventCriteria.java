package ca.csfoy.servicesweb.camarchedoc.domain.event;

import java.time.LocalDate;

public class SearchEventCriteria {
    
    private String trailId;
    private LocalDate startDate;
    
    public SearchEventCriteria(String trailId, LocalDate startDate) {
        this.trailId = trailId;
        this.startDate = startDate;
    }

    public String getTrailId() {
        return trailId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
