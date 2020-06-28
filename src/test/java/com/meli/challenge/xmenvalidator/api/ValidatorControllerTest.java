package com.meli.challenge.xmenvalidator.api;


import com.meli.challenge.xmenvalidator.dto.SequenceDto;
import com.meli.challenge.xmenvalidator.service.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.meli.challenge.xmenvalidator.MockUtils.DNA_MATCH;
import static com.meli.challenge.xmenvalidator.MockUtils.DNA_UNMATCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ValidatorControllerTest {
    
    @InjectMocks
    ValidatorController validatorController;
    
    @Mock
    ValidatorService validatorService;
    
    @Mock
    SequenceDto sequenceDto;
    
    @BeforeEach
    void init() {
        initMocks(this);
    }
    
    @Test
    public void given_sequence_isMutant_Success() {
        when(validatorService.isMutant(any())).thenReturn(true);
        ResponseEntity<String> response = validatorController.isMutant(SequenceDto.builder().dna(DNA_MATCH).build());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
    
    @Test
    public void given_sequence_isMutant_Forbidden() {
        ResponseEntity<String> response = validatorController.isMutant(SequenceDto.builder().dna(DNA_UNMATCH).build());
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
    }
    
    @Test
    public void given_sequence_isMutant_NullSequence_Forbidden() {
        ResponseEntity<String> response = validatorController.isMutant(SequenceDto.builder().build());
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
    }
    
}
