package com.meli.challenge.xmenvalidator.service;

import com.meli.challenge.xmenvalidator.model.ValidationModel;
import com.meli.challenge.xmenvalidator.repository.ValidatorRepositoryImpl;
import com.meli.challenge.xmenvalidator.utils.ValidatorUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.meli.challenge.xmenvalidator.general.Constants.MIN_VALUE_CROSS;
import static com.meli.challenge.xmenvalidator.general.Constants.MSG_VALIDATOR_SERVICE;


/**
 * Service for Validator
 *
 * @author johan.vargas
 * @version 0.0.2-SNAPSHOT
 */
@Log4j2
@Service
public class ValidatorService {
    
    /**
     * Variables for validation messages directionals
     */
    private static final String VALIDATE_HORIZONTAL_MSG = "Validate Horizontal.";
    private static final String VALIDATE_VERTICAL_MSG = "Validate Vertical.";
    private static final String VALIDATE_CROSS_AXES_MSG = "Validate Cross Axes";
    //Final validation chain from dna
    private static final List<String> Sequences = Arrays.asList("AAAA", "CCCC", "GGGG", "TTTT");
    
    
    @Autowired
    ValidatorRepositoryImpl validatorRepository;
    
    /**
     * Validate is mutant according to the dna sequence
     *
     * @param dna sequence string array
     * @return boolean validation
     */
    @Cacheable("dna")
    public boolean isMutant(String[] dna) {
        
        log.info(MSG_VALIDATOR_SERVICE);
        
        int countMutantChain = 0;
        Optional<ValidationModel> exist = validatorRepository.existDna(Arrays.toString(dna).toLowerCase(Locale.getDefault()));
        if (!exist.isPresent()) {
            if (dna != null && dna.length != 0) {
                log.info(VALIDATE_HORIZONTAL_MSG);
                countMutantChain += validateHorizontal(dna);
                log.info(VALIDATE_VERTICAL_MSG);
                countMutantChain += validateVertical(dna);
                //If counter is over the rule there is not need to perform the cross operation
                if (ValidatorUtils.isLessValueThanMinimum(countMutantChain)) {
                    log.info(VALIDATE_CROSS_AXES_MSG);
                    countMutantChain += validateCross(dna);
                }
            }
            
            validatorRepository.save(Arrays.toString(dna), countMutantChain > 1);
        } else {
            return Boolean.parseBoolean(exist.get().getIsMutant());
        }
        
        return (countMutantChain > 1);
    }
    
    /**
     * Validate horizontal axe
     *
     * @param dna sequence string array
     * @return Integer match found
     */
    private int validateHorizontal(String[] dna) {
        int counter = 0;
        for (String row : dna) {
            counter = validateSequence(row, counter);
        }
        return counter;
    }
    
    /**
     * Validate vertical axe
     *
     * @param dna sequence string array
     * @return Integer match found
     */
    private int validateVertical(String[] dna) {
        int counter = 0;
        int columnStart = 0;
        while (columnStart < dna.length) {
            StringBuilder column = new StringBuilder();
            for (String row : dna) {
                for (int j = columnStart; j < columnStart + 1; j++) {
                    column.append(row.charAt(j));
                }
            }
            counter += validateSequence(column.toString(), counter);
            columnStart++;
        }
        
        return counter;
    }
    
    /**
     * Validate cross axe
     *
     * @param dna sequence string array
     * @return Integer match found
     */
    private int validateCross(String[] dna) {
        int counter = 0;
        if (dna.length >= MIN_VALUE_CROSS) {
            //cross left
            counter = validateCrossLeftToRight(dna);
            //cross right
            counter += validateCrossRightToLeft(dna, counter);
        }
        return counter;
    }
    
    /**
     * Validate from left to right transversal
     *
     * @param dna sequence string array
     * @return Integer match found
     */
    private int validateCrossLeftToRight(String[] dna) {
        return validateDirection(dna, true);
    }
    
    /**
     * Validate from right to left transversal
     *
     * @param dna sequence string array
     * @return Integer match found
     */
    private int validateCrossRightToLeft(String[] dna, int actualCounter) {
        //If actual counter is greater than 1 don't perform operation
        if (ValidatorUtils.isLessValueThanMinimum(actualCounter)) {
            return validateDirection(dna, false);
        }
        return 0;
    }
    
    /**
     * Validate left transversal
     *
     * @param dna sequence string array
     * @return Integer match found
     */
    private int validateDirection(String[] dna, boolean isLeftToRight) {
        int c = 0;
        int count = dna.length + dna[0].length() - 1;
        int sequenceValid = 0;
        int i = 0;
        int j = 0;
        if (!isLeftToRight) {
            j = dna[0].length() - 1;
        }
        //There can be at most  m + n -1 diagonals to be printed
        while (c < count) {
            //Start printing diagonals from i and j
            sequenceValid += getTraverse(i, j, dna, isLeftToRight);
            if (i < dna.length - 1) {
                //We increment row index until we reach the max number of rows
                i++;
            } else if (j <= dna[0].length() - 1) {
                //We are at maximum index of row; so its time to increment col index
                //We increment column index until we reach the max number of columns
                if (isLeftToRight) {
                    j++;
                } else {
                    j--;
                }
            }
            c++;
        }
        return sequenceValid;
    }
    
    /**
     * Get left transversal
     *
     * @param i   int iteration i value
     * @param j   int iteration j value
     * @param dna sequence string array
     * @return Integer match found
     */
    private int getTraverse(int i, int j, String[] dna, boolean isLeftToRight) {
        StringBuilder sequence = new StringBuilder();
        int total = 0;
        if (isLeftToRight) {
            while (i >= 0 && j < dna[0].length()) {
                sequence.append(dna[i].charAt(j));
                i--;
                j++;
            }
        } else {
            while (i >= 0 && j >= 0 && i < dna[0].length()) {
                sequence.append(dna[i].charAt(j));
                i--;
                j--;
            }
        }
        if (sequence.toString().length() >= MIN_VALUE_CROSS) {
            total = validateSequence(sequence.toString(), 0);
        }
        return total;
    }
    
    /**
     * Validate sequence dna if match according with business rules of this sequence
     * ["AAAA", "CCCC", "GGGG", "TTTT"]
     *
     * @param chain   String sequence
     * @param counter int counter
     * @return Integer match found
     */
    private int validateSequence(String chain, int counter) {
        if (chain.length() >= MIN_VALUE_CROSS) {
            for (int i = 0; i < chain.length() - 3; i++) {
                for (String sequence : Sequences) {
                    if (chain.substring(i, 4 + i).equalsIgnoreCase(sequence)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
}
