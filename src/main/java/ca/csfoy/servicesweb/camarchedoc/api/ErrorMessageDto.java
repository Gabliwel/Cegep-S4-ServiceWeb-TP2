package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDateTime;

public class ErrorMessageDto {

    public final LocalDateTime timestamp;
    public final String statusCode;
    public final String errorIdentifier;
    public final String errorMessage;
    
    public ErrorMessageDto(LocalDateTime timestamp, String statusCode, String errorIdentifier, String errorMessage) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.errorIdentifier = errorIdentifier;
        this.errorMessage = errorMessage;
    }    
}
