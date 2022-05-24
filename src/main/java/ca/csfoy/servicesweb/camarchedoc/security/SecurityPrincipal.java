package ca.csfoy.servicesweb.camarchedoc.security;

public class SecurityPrincipal {
    private final String email;
    private final String id;
    
    public SecurityPrincipal(String email, String id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

}
