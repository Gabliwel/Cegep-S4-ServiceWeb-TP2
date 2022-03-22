package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import org.springframework.context.ApplicationContext;

import ca.csfoy.servicesweb.camarchedoc.api.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.TrailDto;

public class CustomValidatorFactory {

    private final ApplicationContext applicationContext;
    
    public CustomValidatorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    public CustomValidator<TrailDto, String> getTrailValidator() {
        return (CustomValidator<TrailDto, String>) applicationContext.getBean(TrailCustomValidator.class);
    }
    
    public CustomValidator<EventDto, String> getEventValidator() {
        return (CustomValidator<EventDto, String>) applicationContext.getBean(EventCustomValidator.class);
    }
}
