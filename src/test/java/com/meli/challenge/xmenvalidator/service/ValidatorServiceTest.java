package com.meli.challenge.xmenvalidator.service;

import com.meli.challenge.xmenvalidator.repository.ValidationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.meli.challenge.xmenvalidator.MockUtils.DNA_MATCH;
import static com.meli.challenge.xmenvalidator.MockUtils.DNA_UNMATCH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

public class ValidatorServiceTest {
    
    @InjectMocks
    private ValidatorService validatorService;
    
    @Mock
    private ValidationsRepository validationsRepository;
    
    @BeforeEach
    public void init() {
        initMocks(this);
    }
    
    @Test
    public void isMutant_Success() {
        assertTrue(validatorService.isMutant(DNA_MATCH));
    }
    
    @Test
    public void isMutant_Fail() {
        assertFalse(validatorService.isMutant(DNA_UNMATCH));
    }
    
    @Test
    public void isMutant_Fail_When_Null_Sequence() {
        assertFalse(validatorService.isMutant(null));
    }
    
    @Test
    public void isMutant_Fail_When_Empty_Sequence() {
        String[] dnaEmpty = new String[]{};
        assertFalse(validatorService.isMutant(dnaEmpty));
    }
}
