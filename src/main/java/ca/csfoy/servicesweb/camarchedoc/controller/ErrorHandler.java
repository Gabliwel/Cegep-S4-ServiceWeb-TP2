package ca.csfoy.servicesweb.camarchedoc.controller;

import java.time.LocalDateTime;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ca.csfoy.servicesweb.camarchedoc.api.ErrorMessageDto;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectAlreadyExistsException;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.ObjectNotFoundException;

@ControllerAdvice
@ResponseBody
public class ErrorHandler {

    private Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

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
