package com.meli.challenge.xmenvalidator.repository;

import com.meli.challenge.xmenvalidator.model.Validations;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableScan
@Repository
public interface ValidationsRepository extends CrudRepository<Validations, String> {
    
    Optional<Validations> findById(String id);
    List<Validations> findAll();
}
