package com.meli.challenge.xmenvalidator.exception;

/**
 * Exception for Stats Handling
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
public class StatsException extends RuntimeException {
    
    public StatsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
