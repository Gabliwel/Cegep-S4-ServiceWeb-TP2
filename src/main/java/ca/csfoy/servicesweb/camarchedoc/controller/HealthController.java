package ca.csfoy.servicesweb.camarchedoc.controller;

import ca.csfoy.servicesweb.camarchedoc.api.HealthResource;

public class HealthController implements HealthResource {

    @Override
    public String getHealth() {
        return "Up and Running - no pun intended.";
    }

    @Override
    public String getBase() {
        return "Bienvenue à Ça marche, Doc!";
    }
}
