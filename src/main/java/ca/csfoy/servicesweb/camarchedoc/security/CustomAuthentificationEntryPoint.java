package ca.csfoy.servicesweb.camarchedoc.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.csfoy.servicesweb.camarchedoc.api.ErrorMessageDto;

@Component
public class CustomAuthentificationEntryPoint extends BasicAuthenticationEntryPoint {
    
    //Non utilisé désormais

    private final ObjectMapper objectMapper;
    private Logger logger = LoggerFactory.getLogger(CustomAuthentificationEntryPoint.class);

    public CustomAuthentificationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void commence(HttpServletRequest request, 
            HttpServletResponse response, 
            AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ErrorMessageDto error = new ErrorMessageDto(LocalDateTime.now(), 
                HttpStatus.UNAUTHORIZED.toString(), 
                UUID.randomUUID().toString(), 
                authException.getMessage());
        logger.error(error.timestamp + " [" + error.errorIdentifier + "]: {" + error.statusCode + "}" + error.errorMessage);
        response.getOutputStream().println(objectMapper.writeValueAsString(error));
    }
    
    @Override
    public void afterPropertiesSet() {
        setRealmName("camarchedoc");
        super.afterPropertiesSet();
    }
    
}
