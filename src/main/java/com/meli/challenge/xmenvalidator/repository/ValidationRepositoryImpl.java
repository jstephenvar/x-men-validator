package com.meli.challenge.xmenvalidator.repository;

import com.meli.challenge.xmenvalidator.model.ValidationModel;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

public class ValidationRepositoryImpl {
    
    @Autowired
    ValidationRepository validationRepository;
    
    /**
     * @param dna
     * @param validation
     */
    public void save(String dna, boolean validation) {
        
        Observable.just(validationRepository
                .save(
                        ValidationModel.builder()
                                .creationDate(LocalDate.now().toString())
                                .dna(dna)
                                .isMutant(String.valueOf(validation))
                                .build()))
                .doOnComplete(() -> System.out.println("final"))
                .subscribe();
    }
    
    /**
     * @param dna
     * @return
     */
    public Optional<ValidationModel> existDna(String dna) {
        return validationRepository.findByDna(dna);
    }
    
    
}
