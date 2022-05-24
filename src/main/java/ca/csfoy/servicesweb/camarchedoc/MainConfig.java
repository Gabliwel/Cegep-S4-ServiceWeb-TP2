package ca.csfoy.servicesweb.camarchedoc;

import javax.validation.Validation;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.EventCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.FullUserCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.RatingCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.TrailCustomValidator;

@Configuration
@EnableJpaAuditing
@ComponentScan({"infra"})
@EnableJpaRepositories("ca.csfoy.servicesweb.camarchedoc.infra")
public class MainConfig {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Bean
    public CustomValidatorFactory validatorFactory() {
        return new CustomValidatorFactory(applicationContext);
    }
    
    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @Bean
    @Scope("prototype")
    public TrailCustomValidator trailValidator() {
        return new TrailCustomValidator(validator());
    }
    
    @Bean
    @Scope("prototype")
    public EventCustomValidator eventValidator() {
        return new EventCustomValidator(validator());
    }
    
    @Bean
    @Scope("prototype")
    public RatingCustomValidator ratingValidator() {
        return new RatingCustomValidator(validator());
    }
    
    @Bean
    @Scope("prototype")
    public FullUserCustomValidator userForCreateValidator() {
        return new FullUserCustomValidator(validator());
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
