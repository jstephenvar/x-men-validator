package com.meli.challenge.xmenvalidator.exception;

/**
 * Exception for Validator Handling
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
public class ValidatorException extends RuntimeException {
    
    public ValidatorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
