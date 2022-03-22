package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDateTime;
import java.util.List;

public class MutipleErrorMessageDto {

    public final LocalDateTime timestamp;
    public final String statusCode;
    public final String errorIdentifier;
    public final String errorMessage;
    public final List<String> validationMessages;
    
    public MutipleErrorMessageDto(LocalDateTime timestamp, String statusCode, String errorIdentifier, String errorMessage, List<String> validationMessages) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.errorIdentifier = errorIdentifier;
        this.errorMessage = errorMessage;
        this.validationMessages = validationMessages;
    }    
}
