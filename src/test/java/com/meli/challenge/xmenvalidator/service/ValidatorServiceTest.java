package com.meli.challenge.xmenvalidator.service;

import com.meli.challenge.xmenvalidator.model.ValidationModel;
import com.meli.challenge.xmenvalidator.repository.ValidatorRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.meli.challenge.xmenvalidator.MockUtils.DNA_MATCH;
import static com.meli.challenge.xmenvalidator.MockUtils.DNA_UNMATCH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ValidatorServiceTest {
    
    @InjectMocks
    private ValidatorService validatorService;
    
    @Mock
    private ValidatorRepositoryImpl validatorRepository;
    
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
    
    @Test
    public void isMutant_False_When_Dna_Exist() {
        when(validatorRepository.existDna(anyString())).thenReturn(Optional.of(ValidationModel.builder()
                .isMutant("true").build()));
        assertTrue(validatorService.isMutant(DNA_MATCH));
    }
}
