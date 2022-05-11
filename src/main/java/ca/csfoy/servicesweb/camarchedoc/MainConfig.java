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

import ca.csfoy.servicesweb.camarchedoc.controller.validation.BadgeCustomValidator;
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
    @Scope("prototype")
    public BadgeCustomValidator badgeValidator() {
        return new BadgeCustomValidator(validator());
    }
    
    /*
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
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public JwtTokenFilter tokenFilter() {
        return new JwtTokenFilter(tokenProvider(), new ObjectMapper());
    }
    
    @Bean
    public JwtTokenAuthentificationProvider tokenProvider() {
        return new JwtTokenAuthentificationProvider(userDetailsService(), new JwtTokenFactory());
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
        return new UserConverter(trailConverter(), passwordEncoder());
    }
    
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl(userDao, trailDao);
    }

    //@Bean
    //public UserResource userController() {
     //   return new UserController(userRepository(), userConverter(), validatorFactory(), authManager, tokenProvider());
    //}*/
}
