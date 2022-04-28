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

import ca.csfoy.servicesweb.camarchedoc.api.HealthResource;
import ca.csfoy.servicesweb.camarchedoc.api.event.EventResource;
import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingResource;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailResource;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserResource;
import ca.csfoy.servicesweb.camarchedoc.controller.EventController;
import ca.csfoy.servicesweb.camarchedoc.controller.HealthController;
import ca.csfoy.servicesweb.camarchedoc.controller.RatingController;
import ca.csfoy.servicesweb.camarchedoc.controller.TrailController;
import ca.csfoy.servicesweb.camarchedoc.controller.UserController;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.EventConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.RatingConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.UserConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.CustomValidatorFactory;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.EventCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.RatingCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.TrailCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.controller.validation.UserCreateCustomValidator;
import ca.csfoy.servicesweb.camarchedoc.domain.event.EventRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingService;
import ca.csfoy.servicesweb.camarchedoc.domain.rating.RatingServiceImpl;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailService;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailServiceImpl;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;
import ca.csfoy.servicesweb.camarchedoc.infra.EventDao;
import ca.csfoy.servicesweb.camarchedoc.infra.EventRepositoryImpl;
import ca.csfoy.servicesweb.camarchedoc.infra.RatingDao;
import ca.csfoy.servicesweb.camarchedoc.infra.RatingRepositoryImpl;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailDao;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailRepositoryImpl;
import ca.csfoy.servicesweb.camarchedoc.infra.UserDao;
import ca.csfoy.servicesweb.camarchedoc.infra.UserRepositoryImpl;

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
    
    @Autowired
    private RatingDao ratingDao;
    
    @Autowired
    private UserDao userDao;
    
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
    public UserCreateCustomValidator userForCreateValidator() {
        return new UserCreateCustomValidator(validator());
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
        return new TrailController(trailRepository(), trailConverter(), validatorFactory(), trailService());
    }
    
    @Bean
    public TrailService trailService() {
        return new TrailServiceImpl(trailRepository());
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
    
    @Bean
    public RatingConverter ratingConverter() {
        return new RatingConverter(trailConverter(), userConverter());
    }
    
    @Bean
    public RatingRepository ratingRepository() {
        return new RatingRepositoryImpl(ratingDao, trailDao);
    }

    @Bean
    public RatingResource ratingController() {
        return new RatingController(ratingRepository(), ratingConverter(), validatorFactory(), ratingService());
    }
    
    @Bean
    public RatingService ratingService() {
        return new RatingServiceImpl(userRepository(), trailRepository(), ratingRepository());
    }
    
    @Bean
    public UserConverter userConverter() {
        return new UserConverter(trailConverter());
    }
    
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl(userDao, trailDao);
    }

    @Bean
    public UserResource userController() {
        return new UserController(userRepository(), userConverter(), validatorFactory());
    }
}
