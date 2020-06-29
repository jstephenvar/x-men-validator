package com.meli.challenge.xmenvalidator.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsResponseDto {
    
    @SerializedName("count_mutant_dna")
    String countMutantDna;
    @SerializedName("count_human_dna")
    String countHumanDna;
    String ratio;
}
