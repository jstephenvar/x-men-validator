package com.meli.challenge.xmenvalidator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static com.meli.challenge.xmenvalidator.general.Constants.ERROR_NULL_DNA;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SequenceDto {
    
    @NotNull(message = ERROR_NULL_DNA)
    private String[] dna;
}
