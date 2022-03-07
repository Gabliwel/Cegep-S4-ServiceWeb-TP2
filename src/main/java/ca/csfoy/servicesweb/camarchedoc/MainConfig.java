package ca.csfoy.servicesweb.camarchedoc;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.csfoy.servicesweb.camarchedoc.api.EventResource;
import ca.csfoy.servicesweb.camarchedoc.api.HealthResource;
import ca.csfoy.servicesweb.camarchedoc.api.TrailResource;
import ca.csfoy.servicesweb.camarchedoc.controller.EventController;
import ca.csfoy.servicesweb.camarchedoc.controller.HealthController;
import ca.csfoy.servicesweb.camarchedoc.controller.TrailController;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.EventConverter;
import ca.csfoy.servicesweb.camarchedoc.controller.converter.TrailConverter;
import ca.csfoy.servicesweb.camarchedoc.domain.Event;
import ca.csfoy.servicesweb.camarchedoc.domain.EventRepository;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailRepository;
import ca.csfoy.servicesweb.camarchedoc.infra.EventDao;
import ca.csfoy.servicesweb.camarchedoc.infra.EventDaoMemory;
import ca.csfoy.servicesweb.camarchedoc.infra.EventRepositoryImpl;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailDao;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailDaoMemory;
import ca.csfoy.servicesweb.camarchedoc.infra.TrailRepositoryImpl;

@Configuration
public class MainConfig {

    private Trail trail1 = new Trail("1", "Le sentier des amis", "Grand sentier débutant", "Québec", TrailDifficulty.FAMILY);
    private Trail trail2 = new Trail("2", "Le sentier des experts", "Grand sentier expert", "Québec", TrailDifficulty.EXPERT);
    private Trail trail3 = new Trail("3", "Le petit sentier", "Petit sentier débutant-intermédiaire", "Québec", TrailDifficulty.INTERMEDIATE);
    
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
        TrailRepository repo = new TrailRepositoryImpl(trailDao());
        repo.create(trail1);
        repo.create(trail2);
        repo.create(trail3);
        return repo;
    }

    @Bean
    public TrailDao trailDao() {
        return new TrailDaoMemory();
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
        EventRepository repo = new EventRepositoryImpl(eventDao(), trailDao());
        repo.create(new Event("1", "Marche1", "Grande première marche", LocalDate.now().plusDays(10), trail1, "Kate Everdeen"));
        repo.create(new Event("2", "Marche2", "Grande deuxième marche", LocalDate.now().plusDays(11), trail2, "Jules Machin"));
        repo.create(new Event("3", "Marche3", "Grande troisième marche", LocalDate.now().plusDays(12), trail3, "Tiger Dancause"));
        return repo;
    }

    @Bean
    public EventDao eventDao() {
        return new EventDaoMemory();
    }

    @Bean
    public EventResource eventController() {
        return new EventController(eventRepository(), eventConverter());
    }
}
