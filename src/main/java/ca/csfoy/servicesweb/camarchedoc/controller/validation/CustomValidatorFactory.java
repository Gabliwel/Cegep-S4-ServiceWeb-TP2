package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import org.springframework.context.ApplicationContext;

import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.event.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;

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
    
    public CustomValidator<RatingDto, String> getRatingValidator() {
        return (CustomValidator<RatingDto, String>) applicationContext.getBean(RatingCustomValidator.class);
    }
    
    public CustomValidator<FullUserDto, String> getUserDtoForCreateValidator() {
        return (CustomValidator<FullUserDto, String>) applicationContext.getBean(FullUserCustomValidator.class);
    }
    
    public CustomValidator<BadgeDto, String> getBadgeValidator() {
        return (CustomValidator<BadgeDto, String>) applicationContext.getBean(BadgeCustomValidator.class);
    }
}
