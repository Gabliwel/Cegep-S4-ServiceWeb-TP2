package ca.csfoy.servicesweb.camarchedoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ca.csfoy.servicesweb.camarchedoc.api.EventResource;
import ca.csfoy.servicesweb.camarchedoc.api.HealthResource;
import ca.csfoy.servicesweb.camarchedoc.api.RatingResource;
import ca.csfoy.servicesweb.camarchedoc.api.TrailResource;
import ca.csfoy.servicesweb.camarchedoc.controller.EventController;
import ca.csfoy.servicesweb.camarchedoc.controller.HealthController;
import ca.csfoy.servicesweb.camarchedoc.controller.RatingController;
import ca.csfoy.servicesweb.camarchedoc.controller.TrailController;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.EventConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.RatingConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.domain.EventRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.RatingRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailRepository;
import ca.csfoy.servicesweb.camarchedoc.infra.EventDao;
import ca.csfoy.servicesweb.camarchedoc.infra.EventRepositoryImpl;
import ca.csfoy.servicesweb.camarchedoc.infra.RatingDao;
import ca.csfoy.servicesweb.camarchedoc.infra.RatingRepositoryImpl;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailDao;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailRepositoryImpl;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("ca.csfoy.servicesweb.camarchedoc.infra")
public class MainConfig {
    
    @Autowired
    private TrailDao trailDao;
    
    @Autowired
    private EventDao eventDao;
    
    @Autowired
    private RatingDao ratingDao;

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
        return new TrailController(trailRepository(), trailConverter());
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
        return new EventController(eventRepository(), eventConverter());
    }
    
    @Bean
    public RatingConverter ratingConverter() {
        return new RatingConverter(trailConverter());
    }
    
    @Bean
    public RatingRepository ratingRepository() {
        return new RatingRepositoryImpl(ratingDao, trailDao);
    }

    @Bean
    public RatingResource ratingController() {
        return new RatingController(ratingRepository(), ratingConverter());
    }
}
