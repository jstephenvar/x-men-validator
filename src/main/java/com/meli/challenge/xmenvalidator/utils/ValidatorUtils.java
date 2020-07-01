package com.meli.challenge.xmenvalidator.utils;

import lombok.experimental.UtilityClass;

import static com.meli.challenge.xmenvalidator.general.Constants.MIN_VALUES_MATCH;

/**
 * Validator Utils
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
@UtilityClass
public class ValidatorUtils {
    
    /**
     * Validate if value of counter is less or equal than actual value
     *
     * @param actualCounter actual number of matches sequences.
     * @return boolean
     */
    public boolean isLessValueThanMinimum(int actualCounter) {
        return actualCounter <= MIN_VALUES_MATCH;
    }
}
