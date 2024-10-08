package ca.csfoy.servicesweb.camarchedoc.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import ca.csfoy.servicesweb.camarchedoc.domain.user.Role;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenAuthentificationProvider {
    
    private final UserDetailsService userDetails;
    private final JwtTokenFactory factory;
    
    public JwtTokenAuthentificationProvider(UserDetailsService userDetails, JwtTokenFactory factory) {
        this.userDetails = userDetails;
        this.factory = factory;
    }
    
    public String createToken(String email, Role role, String id) {
        return factory.createToken(email, role, id);
    }
    
    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(factory.getSecretKey()).parseClaimsJws(token).getBody();
        return true;
    }
    
    public Authentication getAuthentification(String token) {
        String email = Jwts.parser()
                .setSigningKey(factory.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        String id = Jwts.parser()
                .setSigningKey(factory.getSecretKey())
                .parseClaimsJws(token)
                .getBody().get("userId", String.class);
        UserDetails user = userDetails.loadUserByUsername(email);
        SecurityPrincipal principal = new SecurityPrincipal(user.getUsername(), id);
        return new UsernamePasswordAuthenticationToken(principal, user.getPassword(), user.getAuthorities());
    }

}
