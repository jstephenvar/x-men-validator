package com.meli.challenge.xmenvalidator.api;


import com.meli.challenge.xmenvalidator.dto.SequenceDto;
import com.meli.challenge.xmenvalidator.exception.ValidatorException;
import com.meli.challenge.xmenvalidator.service.ValidatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.meli.challenge.xmenvalidator.general.Constants.MSG_VALIDATOR_REQUEST;

@Log4j2
@RestController
@RequestMapping("/validate")
public class ValidatorController {
    
    @Autowired
    private ValidatorService validatorService;
    
    @PostMapping("/mutant/")
    public ResponseEntity<String> isMutant(final @RequestBody @Validated SequenceDto sequenceDto) {
        
        log.info(MSG_VALIDATOR_REQUEST);
        try {
            if (sequenceDto.getDna() != null && validatorService.isMutant(sequenceDto.getDna())) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception ex) {
            throw new ValidatorException(ex.getMessage(), ex);
        }
    }
    
}
