package com.meli.challenge.xmenvalidator.repository;

import com.meli.challenge.xmenvalidator.exception.ValidatorException;
import com.meli.challenge.xmenvalidator.model.ValidationModel;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

import static com.meli.challenge.xmenvalidator.general.Constants.EX_VALIDATOR_SAVING;
import static com.meli.challenge.xmenvalidator.general.Constants.MSG_VALIDATOR_SAVING_SUCCESS;

/**
 * Validator of implementation bridge handler
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
@Log4j2
public class ValidatorRepositoryImpl {
    
    @Autowired
    ValidatorRepository validatorRepository;
    
    /**
     * Save dna and validation
     *
     * @param dna        String sequence
     * @param validation boolean result
     */
    public void save(String dna, boolean validation) {
        
        Observable.just(validatorRepository
                .save(
                        ValidationModel.builder()
                                .creationDate(LocalDate.now().toString())
                                .dna(dna.toLowerCase(Locale.getDefault()))
                                .isMutant(String.valueOf(validation))
                                .build()))
                .doOnError(throwable -> {
                    throw new ValidatorException(EX_VALIDATOR_SAVING, throwable);
                })
                .doOnComplete(() -> log.info(MSG_VALIDATOR_SAVING_SUCCESS))
                .subscribe();
    }
    
    /**
     * Validate if dna already exist
     *
     * @param dna String sequence
     * @return Optional<ValidationModel> with the result ModelDto
     */
    public Optional<ValidationModel> existDna(String dna) {
        return validatorRepository.findByDna(dna);
    }
    
}
