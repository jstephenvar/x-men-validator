package com.meli.challenge.xmenvalidator.api.integration;

import com.google.gson.Gson;
import com.meli.challenge.xmenvalidator.XMenValidatorApplication;
import com.meli.challenge.xmenvalidator.api.ValidatorController;
import com.meli.challenge.xmenvalidator.dto.SequenceDto;
import com.meli.challenge.xmenvalidator.exception.ValidatorException;
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

import static com.meli.challenge.xmenvalidator.MockUtils.DNA_MATCH;
import static com.meli.challenge.xmenvalidator.MockUtils.DNA_UNMATCH;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = XMenValidatorApplication.class)
public class ValidatorApiControllerTest {
    
    @Mock
    ValidatorController validatorController;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void when_isMutant_Validation_Success_True() throws Exception {
        
        SequenceDto sequenceDto = SequenceDto.builder()
                .dna(DNA_MATCH)
                .build();
        
        mockMvc.perform(
                post("/validate/mutant/")
                        .content(new Gson().toJson(sequenceDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    public void when_isMutant_Validation_Success_False() throws Exception {
        
        SequenceDto sequenceDto = SequenceDto.builder()
                .dna(DNA_UNMATCH)
                .build();
        
        mockMvc.perform(
                post("/validate/mutant/")
                        .content(new Gson().toJson(sequenceDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    
    @Test
    public void when_isMutant_Fail_Throw_Exception() throws Exception {
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(validatorController)
                .setControllerAdvice(new WebRestControllerAdvice())
                .build();
        
        Mockito.when(validatorController.isMutant(any())).thenThrow(new ValidatorException("Unexpected Exception",
                new RuntimeException()));
        
        SequenceDto sequenceDto = SequenceDto.builder()
                .dna(DNA_UNMATCH)
                .build();
              
        mockMvc.perform(
                post("/validate/mutant/")
                        .content(new Gson().toJson(sequenceDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
        
    }
}
