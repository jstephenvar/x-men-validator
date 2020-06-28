package com.meli.challenge.xmenvalidator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SequenceDto {
    
    
    private String[] dna;
}
