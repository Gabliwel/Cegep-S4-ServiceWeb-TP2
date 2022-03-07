package ca.csfoy.servicesweb.camarchedoc.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HealthControllerTest {

    @Test
    void whenGetHealthIndicatorThenIndicatorReturned() {
        HealthController controller = new HealthController();

        String health = controller.getHealth();

        Assertions.assertEquals("Up and Running - no pun intended.", health);
    }

    @Test
    void whenGettingRootUrlThenWelcomeMessageReturned() {
        HealthController controller = new HealthController();

        String welcome = controller.getBase();

        Assertions.assertEquals("Bienvenue à Ça marche, Doc!", welcome);
    }
}
