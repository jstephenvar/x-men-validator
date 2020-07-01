package com.meli.challenge.xmenvalidator.exception.general;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Handle responses with a error builder spec.
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    
    private int status;
    private List<MiddlewareError> errors;
    
    /**
     * Create specific ResponseEntity object according to ResponseAPI with HTTPStatus with error
     *
     * @param customMessage    error message from the developer site
     * @param exceptionMessage error message from the system
     * @param code             Http status cde
     * @return ResponseEntity with error status
     */
    public static ExceptionResponse errorBuilder(String customMessage, String exceptionMessage, HttpStatus code) {
        final MiddlewareError middlewareError = MiddlewareError
                .builder()
                .developerMessage(customMessage)
                .internalMessage(exceptionMessage)
                .build();
        
        return ExceptionResponse.builder()
                .status(code.value())
                .errors(Collections.singletonList(middlewareError))
                .build();
    }
    
    @Builder
    @Getter
    @ToString
    public static class MiddlewareError {
        
        @SerializedName("internal-message")
        private final String internalMessage;
        @SerializedName("developer-message")
        private final String developerMessage;
    }
}
