package com.meli.challenge.xmenvalidator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.meli.challenge.xmenvalidator.MockUtils.DNA_MATCH;
import static com.meli.challenge.xmenvalidator.MockUtils.DNA_UNMATCH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorServiceTest {
    
    
    private ValidatorService validator;
    
    @BeforeEach
    public void init() {
        validator = new ValidatorService();
    }
    
    @Test
    public void isMutant_Success() {
        assertTrue(validator.isMutant(DNA_MATCH));
    }
    
    @Test
    public void isMutant_Fail() {
        assertFalse(validator.isMutant(DNA_UNMATCH));
    }
    
    @Test
    public void isMutant_Fail_When_Null_Sequence() {
        assertFalse(validator.isMutant(null));
    }
    
    @Test
    public void isMutant_Fail_When_Empty_Sequence() {
        String[] dnaEmpty = new String[]{};
        assertFalse(validator.isMutant(dnaEmpty));
    }
}
