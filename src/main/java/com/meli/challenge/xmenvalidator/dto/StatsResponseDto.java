package com.meli.challenge.xmenvalidator.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

/**
 * Dto for Sequence mapping response
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
@Data
@Builder
public class StatsResponseDto {
    
    @SerializedName("count_mutant_dna")
    String countMutantDna;
    @SerializedName("count_human_dna")
    String countHumanDna;
    String ratio;
}
