package com.meli.challenge.xmenvalidator.api.integration;

import com.meli.challenge.xmenvalidator.XMenValidatorApplication;
import com.meli.challenge.xmenvalidator.api.StatsController;
import com.meli.challenge.xmenvalidator.exception.StatsException;
import com.meli.challenge.xmenvalidator.exception.general.WebRestControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = XMenValidatorApplication.class)
public class StatsApiControllerTest {
    
    @Mock
    StatsController statsController;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void when_getStats_Success() throws Exception {
        
        mockMvc.perform(
                get("/monitor/stats")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    public void when_getStats_Fail_Throw_Exception() throws Exception {
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(statsController)
                .setControllerAdvice(new WebRestControllerAdvice())
                .build();
        
        Mockito.when(statsController.getStats()).thenThrow(new StatsException("Unexpected Exception",
                new RuntimeException()));
        
        mockMvc.perform(
                get("/monitor/stats")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
