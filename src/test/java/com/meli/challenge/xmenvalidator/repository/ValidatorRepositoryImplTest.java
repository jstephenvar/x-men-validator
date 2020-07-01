package com.meli.challenge.xmenvalidator.repository;

import com.meli.challenge.xmenvalidator.exception.ValidatorException;
import com.meli.challenge.xmenvalidator.model.ValidationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ValidatorRepositoryImplTest {
    
    @InjectMocks
    private ValidatorRepositoryImpl validatorRepositoryImpl;
    
    @Mock
    private ValidatorRepository validatorRepository;
    
    @BeforeEach
    public void init() {
        initMocks(this);
    }
    
    @Test
    public void saveRecord_Success() {
        when(validatorRepository.save(any())).thenReturn(ValidationModel.builder().build());
        validatorRepositoryImpl.save("", true);
        assertTrue(true);
    }
    
    @Test
    public void saveRecord_Fail_Throw_Exception() {
        when(validatorRepository.save(any())).thenThrow(new ValidatorException("", new RuntimeException()));
        assertThrows(ValidatorException.class, () -> validatorRepositoryImpl.save("", true));
    }
    
    @Test
    public void existDna_Success() {
        when(validatorRepository.findByDna(any())).thenReturn(Optional.of(ValidationModel.builder().build()));
        Optional<ValidationModel> validationModel = validatorRepositoryImpl.existDna("");
        assertEquals(ValidationModel.builder().build(), validationModel.orElse(null));
    }
    
    @Test
    public void existDna_Fail_Not_Exist() {
        when(validatorRepository.findByDna(any())).thenReturn(Optional.empty());
        Optional<ValidationModel> validationModel = validatorRepositoryImpl.existDna("");
        assertNull(validationModel.orElse(null));
    }
    
    
    
}
