package ca.csfoy.servicesweb.camarchedoc.security;

import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import ca.csfoy.servicesweb.camarchedoc.domain.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenFactory {
    
    private String secretKey = "tp2KeyForJwtToken";
    private long validityInMilliseconds;
    
    public JwtTokenFactory() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        validityInMilliseconds = 1000 * 60 * 2;
    }
    
    public String getSecretKey() {
        return secretKey;
    }
    
    public String createToken(String email, Role role, String id) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("auth", role);
        claims.put("userId", id);
        Date now = new Date();
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

}
