package com.meli.challenge.xmenvalidator.general;

import java.util.Arrays;
import java.util.List;

public class Constants {
    
    /**
     * General
     **/
    
    //Final validation chain from dna
    public static final List<String> SEQUENCES = Arrays.asList("AAAA", "CCCC", "GGGG", "TTTT");
    //Min value for validate a chain
    public static final int MIN_VALUE_CROSS = 4;
    
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
    
}
