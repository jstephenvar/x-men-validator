package com.meli.challenge.xmenvalidator.exception;

public class ValidatorException extends RuntimeException {
    
    public ValidatorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
