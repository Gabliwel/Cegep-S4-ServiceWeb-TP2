package ca.csfoy.servicesweb.camarchedoc;

import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ca.csfoy.servicesweb.camarchedoc.api.EventResource;
import ca.csfoy.servicesweb.camarchedoc.api.HealthResource;
import ca.csfoy.servicesweb.camarchedoc.api.TrailResource;
import ca.csfoy.servicesweb.camarchedoc.controller.EventController;
import ca.csfoy.servicesweb.camarchedoc.controller.HealthController;
import ca.csfoy.servicesweb.camarchedoc.controller.TrailController;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.EventConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.EventCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.TrailCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.domain.EventRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailRepository;
import ca.csfoy.servicesweb.camarchedoc.infra.EventDao;
import ca.csfoy.servicesweb.camarchedoc.infra.EventRepositoryImpl;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailDao;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailRepositoryImpl;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("ca.csfoy.servicesweb.camarchedoc.infra")
public class MainConfig {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private TrailDao trailDao;
    
    @Autowired
    private EventDao eventDao;
    
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
    public HealthResource healthResource() {
        return new HealthController();
    }

    @Bean
    public TrailConverter trailConverter() {
        return new TrailConverter();
    }

    @Bean
    public TrailRepository trailRepository() {
        return new TrailRepositoryImpl(trailDao);
    }

    @Bean
    public TrailResource trailController() {
        return new TrailController(trailRepository(), trailConverter(), validatorFactory());
    }

    @Bean
    public EventConverter eventConverter() {
        return new EventConverter(trailConverter());
    }

    @Bean
    public EventRepository eventRepository() {
        return new EventRepositoryImpl(eventDao, trailDao);
    }

    @Bean
    public EventResource eventController() {
        return new EventController(eventRepository(), eventConverter(), validatorFactory());
    }
}
