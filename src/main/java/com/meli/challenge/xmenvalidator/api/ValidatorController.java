package com.meli.challenge.xmenvalidator.api;


import com.meli.challenge.xmenvalidator.dto.SequenceDto;
import com.meli.challenge.xmenvalidator.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate")
public class ValidatorController {
    
    @Autowired
    private ValidatorService validatorService;
    
    @PostMapping("/mutant/")
    public ResponseEntity<String> isMutant(final @RequestBody @Validated SequenceDto sequenceDto) {
        
        if (sequenceDto.getDna() != null && validatorService.isMutant(sequenceDto.getDna())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    
}
