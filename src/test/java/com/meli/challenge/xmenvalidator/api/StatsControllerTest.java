package com.meli.challenge.xmenvalidator.api;

import com.google.gson.Gson;
import com.meli.challenge.xmenvalidator.dto.StatsResponseDto;
import com.meli.challenge.xmenvalidator.exception.StatsException;
import com.meli.challenge.xmenvalidator.service.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StatsControllerTest {
    
    @InjectMocks
    StatsController statsController;
    
    @Mock
    StatsService statsService;
    
    @BeforeEach
    void init() {
        initMocks(this);
    }
    
    @Test
    public void get_Stats_Request_Success() {
        ResponseEntity<String> response = statsController.getStats();
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
    
    @Test
    public void get_Stats_Request_Equals_Response() {
        StatsResponseDto statsResponseDto = StatsResponseDto.builder()
                .countHumanDna("1")
                .countMutantDna("1")
                .ratio("1.0")
                .build();
        
        when(statsService.getStatistics()).thenReturn(statsResponseDto);
        ResponseEntity<String> response = statsController.getStats();
        assertEquals(new Gson().toJson(statsResponseDto), response.getBody());
    }
    
    @Test
    public void get_Stats_Request_Exception() {
        when(statsService.getStatistics()).thenThrow(new RuntimeException(""));
        assertThrows(StatsException.class, () -> statsController.getStats());
        
    }
}
