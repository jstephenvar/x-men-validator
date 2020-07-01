package com.meli.challenge.xmenvalidator.service;

import com.meli.challenge.xmenvalidator.dto.StatsResponseDto;
import com.meli.challenge.xmenvalidator.model.ValidationModel;
import com.meli.challenge.xmenvalidator.repository.ValidatorRepository;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.meli.challenge.xmenvalidator.general.Constants.MSG_STATS_SERVICE;

/**
 * Service for Stats
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
@Log4j2
@Service
public class StatsService {
    
    @Autowired
    ValidatorRepository validatorRepository;
    
    /**
     * Get statistics handling with observable
     *
     * @return StatsResponseDto response
     */
    public StatsResponseDto getStatistics() {
        log.info(MSG_STATS_SERVICE);
        return Observable.just(getStatsRecords())
                .singleElement()
                .blockingGet();
    }
    
    /**
     * Get stats records from db
     *
     * @return StatsResponseDto response
     */
    private StatsResponseDto getStatsRecords() {
        AtomicInteger mutants = new AtomicInteger(0);
        AtomicInteger humans = new AtomicInteger(0);
        List<ValidationModel> records = validatorRepository.findAll();
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
        
        BigDecimal ratio = new BigDecimal((mutants.get() != 0 && humans.get() != 0) ?
                String.valueOf((mutants.get() / (double) humans.get())) :
                "0.0");
        
        return StatsResponseDto.builder()
                .countMutantDna(String.valueOf(mutants.get()))
                .countHumanDna(String.valueOf(humans.get()))
                .ratio(ratio.setScale(1, RoundingMode.HALF_EVEN).toPlainString())
                .build();
    }
}
