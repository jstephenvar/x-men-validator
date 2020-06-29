package com.meli.challenge.xmenvalidator.service;

import com.meli.challenge.xmenvalidator.dto.StatsResponseDto;
import com.meli.challenge.xmenvalidator.model.ValidationModel;
import com.meli.challenge.xmenvalidator.repository.ValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatsService {
    
    @Autowired
    ValidationRepository validationRepository;
    
    /**
     *
     * @return
     */
    public StatsResponseDto getStatistics() {
        
        AtomicInteger mutants = new AtomicInteger(0);
        AtomicInteger humans = new AtomicInteger(0);
        List<ValidationModel> records = validationRepository.findAll();
        if (!records.isEmpty()) {
            records.forEach(validationModel -> {
                        if ((Boolean.parseBoolean(validationModel.getIsMutant()))) {
                            mutants.getAndIncrement();
                        } else {
                            humans.getAndIncrement();
                        }
                    }
            );
        }
        return StatsResponseDto.builder()
                .countMutantDna(String.valueOf(mutants.get()))
                .countHumanDna(String.valueOf(humans.get()))
                .ratio((mutants.get() != 0 && humans.get() != 0) ?
                        String.valueOf((mutants.get() / (double) humans.get())) :
                        "0.0")
                .build();
    }
    
}
