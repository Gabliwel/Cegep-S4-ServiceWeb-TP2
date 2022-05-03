package ca.csfoy.servicesweb.camarchedoc.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.csfoy.servicesweb.camarchedoc.api.ErrorMessageDto;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    
    private final JwtTokenAuthentificationProvider provider;
    private final ObjectMapper objectMapper;
    
    public JwtTokenFilter(JwtTokenAuthentificationProvider provider, ObjectMapper objectMapper) {
        this.provider = provider;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (Objects.nonNull(token) && provider.validateToken(token)) {
                SecurityContextHolder.getContext().setAuthentication(provider.getAuthentification(token));
            }
        } catch (RuntimeException authException) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ErrorMessageDto error = new ErrorMessageDto(LocalDateTime.now(), 
                    HttpStatus.UNAUTHORIZED.toString(), 
                    UUID.randomUUID().toString(), 
                    authException.getMessage());
            logger.error(error.timestamp + " [" + error.errorIdentifier + "]: {" + error.statusCode + "}" + error.errorMessage);
            response.getOutputStream().println(objectMapper.writeValueAsString(error));
            return;
        }
        filterChain.doFilter(request, response);
    }
    
}
