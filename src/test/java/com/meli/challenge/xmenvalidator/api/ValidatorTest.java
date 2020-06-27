package com.meli.challenge.xmenvalidator.api;

import org.junit.jupiter.api.Test;

import javax.xml.validation.Validator;

public class ValidatorTest {
    
    private static final String[] dnaMatch = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    private static final String[] dnaUnmatch = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "GTAGTA", "TCACTG"};
    
    @Test
    public void isMutant_Success() {
        
        Validator validator = new Validator();
        
        assertTrue(validator.isMutant(dna));
    }
    
    
}
