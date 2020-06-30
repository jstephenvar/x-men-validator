package com.meli.challenge.xmenvalidator.service;

import com.meli.challenge.xmenvalidator.dto.StatsResponseDto;
import com.meli.challenge.xmenvalidator.model.ValidationModel;
import com.meli.challenge.xmenvalidator.repository.ValidatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StatsServiceTest {
    
    @InjectMocks
    private StatsService statsService;
    
    @Mock
    private ValidatorRepository validatorRepository;
    
    @BeforeEach
    public void init() {
        initMocks(this);
    }
    
    @Test
    public void getStatistics_Success() {
        
        StatsResponseDto statsResponseDto = StatsResponseDto.builder()
                .countHumanDna("1")
                .countMutantDna("1")
                .ratio("1.0")
                .build();
        
        when(validatorRepository.findAll()).thenReturn(Arrays.asList(ValidationModel.builder()
                        .id("1234")
                        .dna("ABCD")
                        .isMutant("false")
                        .creationDate("2045-05-12")
                        .build(),
                ValidationModel.builder()
                        .id("1233")
                        .dna("AAAA")
                        .isMutant("true")
                        .creationDate("2045-05-12")
                        .build()));
        assertEquals(statsResponseDto, statsService.getStatistics());
    }
    
    
    @Test
    public void getStatistics_EmptyRecords() {
        
        StatsResponseDto statsResponseDto = StatsResponseDto.builder()
                .countHumanDna("0")
                .countMutantDna("0")
                .ratio("0.0")
                .build();
        when(validatorRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(statsResponseDto, statsService.getStatistics());
    }
    
    @Test
    public void getStatistics_Failure() {
        
        when(validatorRepository.findAll()).thenReturn(null);
        assertThrows(Exception.class, () -> {
            statsService.getStatistics();
        });
    }
    
    
}
