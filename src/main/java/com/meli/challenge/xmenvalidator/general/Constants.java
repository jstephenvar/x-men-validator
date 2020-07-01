package com.meli.challenge.xmenvalidator.general;

/**
 * General params and constants
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
public class Constants {
    
    /**
     * General
     **/
    
    //Minimum values of chains match counter
    public static final int MIN_VALUES_MATCH = 1;
    //Min value for validate a chain
    public static final int MIN_VALUE_CROSS = 4;
    //Swagger base configuration
    public static final String BASE_PACKAGE_CONFIG = "com.meli.challenge.xmenvalidator.api";
    /**
     * Messages Error
     */
    
    public static final String ERROR_SECRET_DECRYPT = "Secrets Manager can't decrypt the protected secret text using " +
            "the provided KMS key. Deal with the exception here, and/or rethrow at your discretion.";
    public static final String ERROR_SECRET_SERVICE_FAIL = "An error occurred on the server side. Deal with the exception " +
            "here, and/or rethrow at your discretion.";
    public static final String ERROR_SECRET_INVALID_PARAM = "You provided an invalid value for a parameter. Deal with" +
            " the exception here, and/or rethrow at your discretion.";
    public static final String ERROR_SECRET_REQUEST = "You provided a parameter value that is not valid for the " +
            "current state of the resource. Deal with the exception here, and/or rethrow at your discretion.";
    public static final String ERROR_SECRET_RESOURCE_NOT_FOUND = " We can't find the resource that you asked for. Deal" +
            " with the exception here, and/or rethrow at your discretion.";
    public static final String ERROR_NULL_DNA = "Value dna can't not be null.";
    /**
     * Messages Exception
     */
    
    public static final String EX_VALIDATOR = "Error during execution of validator request.";
    public static final String EX_STATS = "Error during execution of stats request.";
    public static final String EX_VALIDATOR_SAVING = "Error saving record of dna validation.";
    /**
     * Messages Log Info
     */
    
    public static final String MSG_STATS_REQUEST = "Getting stats request.";
    public static final String MSG_STATS_SERVICE = "Getting statistics service.";
    public static final String MSG_VALIDATOR_REQUEST = "Getting validation request.";
    public static final String MSG_VALIDATOR_SERVICE = "Getting validation service.";
    public static final String MSG_VALIDATOR_SAVING_SUCCESS = "Saved Dna Record Successful";
    
    /**
     * Private constructor by default
     */
    private Constants() {
    
    }
    
}
