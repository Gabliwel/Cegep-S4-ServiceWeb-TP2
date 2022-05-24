package ca.csfoy.servicesweb.camarchedoc.controller;

import java.time.LocalDateTime;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ca.csfoy.servicesweb.camarchedoc.api.ErrorMessageDto;
import ca.csfoy.servicesweb.camarchedoc.api.MutipleErrorMessageDto;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectInvalidValueException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjetAlreadySetToDesiredValue;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

@ControllerAdvice
@ResponseBody
public class ErrorHandler {

    private Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessageDto badCredentialsException(BadCredentialsException ex) {
        String errorIdentifier = ex.hashCode() + "";
        logger.error(LocalDateTime.now().toString() 
                + " [" + errorIdentifier + "]: " 
                + ex.getMessage() 
                + ExceptionUtils.getStackTrace(ex));
        return new ErrorMessageDto(LocalDateTime.now(), HttpStatus.FORBIDDEN.toString(), errorIdentifier.toString(), 
                "Connection error: Invalid credentials.");
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessageDto accessDeniedException(AccessDeniedException ex) {
        String errorIdentifier = ex.hashCode() + "";
        logger.error(LocalDateTime.now().toString() 
                + " [" + errorIdentifier + "]: " 
                + ex.getMessage() 
                + ExceptionUtils.getStackTrace(ex));
        return new ErrorMessageDto(LocalDateTime.now(), HttpStatus.FORBIDDEN.toString(), errorIdentifier.toString(), 
                "Access Denied: User does not have sufficient permission to access this route.");
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto resourceNotFoundException(ObjectNotFoundException ex) {
        String errorIdentifier = ex.hashCode() + "";
        logger.error(LocalDateTime.now().toString() 
                + " [" + errorIdentifier + "]: " 
                + ex.getMessage() 
                + ExceptionUtils.getStackTrace(ex));
        return new ErrorMessageDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.toString(), errorIdentifier.toString(), ex.getMessage());
    }
    
    @ExceptionHandler(InputValidationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public MutipleErrorMessageDto inputValidationException(InputValidationException ex) {
        String errorIdentifier = ex.hashCode() + "";
        logger.error(LocalDateTime.now().toString() 
                + " [" + errorIdentifier + "]: " 
                + ex.getMessage() 
                + ExceptionUtils.getStackTrace(ex));
        
        return new MutipleErrorMessageDto(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                errorIdentifier.toString(), ex.getMessage(), ex.getViolations());
    
    }
    
    @ExceptionHandler(ObjetAlreadySetToDesiredValue.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto resourceAlreadySetToDesiredValue(ObjetAlreadySetToDesiredValue ex) {
        String errorIdentifier = ex.hashCode() + "";
        logger.error(LocalDateTime.now().toString() 
                + " [" + errorIdentifier + "]: " 
                + ex.getMessage() 
                + ExceptionUtils.getStackTrace(ex));
        return new ErrorMessageDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.toString(), errorIdentifier.toString(), ex.getMessage());
    }
    
    @ExceptionHandler(ObjectInvalidValueException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessageDto resourceInvalidValueException(ObjectInvalidValueException ex) {
        String errorIdentifier = ex.hashCode() + "";
        logger.error(LocalDateTime.now().toString() 
                + " [" + errorIdentifier + "]: " 
                + ex.getMessage() 
                + ExceptionUtils.getStackTrace(ex));
        return new ErrorMessageDto(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.toString(), errorIdentifier.toString(), ex.getMessage());
    }
    
    @ExceptionHandler(ObjectAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessageDto resourceAlreadyExistsException(ObjectAlreadyExistsException ex) {
        String errorIdentifier = ex.hashCode() + "";
        logger.error(LocalDateTime.now().toString() 
                + " [" + errorIdentifier + "]: " 
                + ex.getMessage() 
                + ExceptionUtils.getStackTrace(ex));
        return new ErrorMessageDto(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.toString(), errorIdentifier.toString(), ex.getMessage());
    }
    
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto defaultCaseForUnknownException(Throwable ex) {
        String errorIdentifier = ex.hashCode() + "";
        logger.error(LocalDateTime.now().toString() 
                + " [" + errorIdentifier + "]: " 
                + ex.getMessage() 
                + ExceptionUtils.getStackTrace(ex));
        return new ErrorMessageDto(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), errorIdentifier.toString(), ex.getMessage());
    }    
}
