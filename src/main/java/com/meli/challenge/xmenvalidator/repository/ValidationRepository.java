package com.meli.challenge.xmenvalidator.repository;

import com.meli.challenge.xmenvalidator.model.ValidationModel;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableScan
@Repository
public interface ValidationRepository extends CrudRepository<ValidationModel, String> {
    
    Optional<ValidationModel> findById(String id);
    
    List<ValidationModel> findAll();
    
    Optional<ValidationModel> findByDna(String dna);
}
