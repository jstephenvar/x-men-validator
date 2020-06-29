package com.meli.challenge.xmenvalidator.service;

import com.meli.challenge.xmenvalidator.model.ValidationModel;
import com.meli.challenge.xmenvalidator.repository.ValidationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

import static com.meli.challenge.xmenvalidator.general.Constants.MIN_VALUE_CROSS;
import static com.meli.challenge.xmenvalidator.general.Constants.SEQUENCES;

@Service
public class ValidatorService {
    
    @Autowired
    ValidationRepositoryImpl validationRepository;
    
    /**
     * Validate is mutant according to the dna sequence
     *
     * @param dna sequence string array
     * @return boolean
     */
    @Cacheable("dna")
    public boolean isMutant(String[] dna) {
        
        int countMutantChain = 0;
        Optional<ValidationModel> exist = validationRepository.existDna(Arrays.toString(dna).toLowerCase());
        if (!exist.isPresent()) {
            if (dna != null && dna.length != 0) {
                countMutantChain += validateHorizontal(dna);
                countMutantChain += validateVertical(dna);
                //If counter is over the rule there is not need to perform the cross operation
                if (countMutantChain <= 1) {
                    countMutantChain += validateCross(dna);
                }
            }
            
            validationRepository.save(Arrays.toString(dna), countMutantChain > 1);
        } else {
            return Boolean.parseBoolean(exist.get().getIsMutant());
        }
        
        return (countMutantChain > 1);
    }
    
    /**
     * Validate horizontal axe
     *
     * @param dna sequence string array
     * @return Integer
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
     * @return Integer
     */
    private int validateVertical(String[] dna) {
        int counter = 0;
        for (String row : dna) {
            StringBuilder column = new StringBuilder();
            for (int i = 0; i < row.length(); i++) {
                column.append(row.charAt(i));
            }
            counter = validateSequence(column.toString(), counter);
        }
        return counter;
    }
    
    /**
     * Validate cross axe
     *
     * @param dna sequence string array
     * @return Integer
     */
    private int validateCross(String[] dna) {
        int counter = 0;
        if (dna.length >= MIN_VALUE_CROSS) {
            //cross left
            counter = validateCrossLeftToRight(dna);
            //cross right
            counter += validateCrossRightToLeft(dna);
            
        }
        return counter;
    }
    
    /**
     * Validate from left to right transversal
     *
     * @param dna sequence string array
     * @return Integer
     */
    private int validateCrossLeftToRight(String[] dna) {
        return validateLeft(dna);
    }
    
    /**
     * Validate left transversal
     *
     * @param dna sequence string array
     * @return Integer
     */
    private int validateLeft(String[] dna) {
        int c = 0;
        int count = dna.length + dna[0].length() - 1;
        int sequenceValid = 0;
        int i = 0;
        int j = 0;
        //There can be at most  m + n -1 diagonals to be printed
        while (c < count) {
            //Start printing diagonals from i and j
            sequenceValid += getLeftTraverse(i, j, dna);
            if (i < dna.length - 1) {
                //We increment row index until we reach the max number of rows
                i++;
            } else if (j <= dna[0].length() - 1) {
                //We are at maximum index of row; so its time to increment col index
                //We increment column index until we reach the max number of columns
                j++;
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
     * @return Integer
     */
    private int getLeftTraverse(int i, int j, String[] dna) {
        StringBuilder sequence = new StringBuilder();
        int total = 0;
        while (i >= 0 && j < dna[0].length()) {
            sequence.append(dna[i].charAt(j));
            i--;
            j++;
        }
        if (sequence.toString().length() >= MIN_VALUE_CROSS) {
            total = validateSequence(sequence.toString(), 0);
        }
        return total;
    }
    
    /**
     * Validate from right to left transversal
     *
     * @param dna sequence string array
     * @return Integer
     */
    private int validateCrossRightToLeft(String[] dna) {
        return validateRight(dna);
    }
    
    /**
     * Validate right transversal
     *
     * @param dna sequence string array
     * @return Integer
     */
    private int validateRight(String[] dna) {
        int c = 0;
        int count = dna.length + dna[0].length() - 1;
        int sequenceValid = 0;
        int i = 0;
        int j = dna[0].length() - 1;
        while (c < count) {
            //Start printing diagonals from i and j
            sequenceValid += getRightTraverse(i, j, dna);
            if (i < dna.length - 1) {
                //We increment row index until we reach the max number of rows
                i++;
            } else if (j <= dna[0].length() - 1) {
                //We are at maximum index of row; so its time to increment col index
                //We increment column index until we reach the max number of columns
                j--;
            }
            c++;
        }
        return sequenceValid;
    }
    
    /**
     * Get right transversal
     *
     * @param i   int iteration i value
     * @param j   int iteration j value
     * @param dna sequence string array
     * @return Integer
     */
    private int getRightTraverse(int i, int j, String[] dna) {
        StringBuilder sequence = new StringBuilder();
        int total = 0;
        while (i >= 0 && j >= 0 && i < dna[0].length()) {
            sequence.append(dna[i].charAt(j));
            i--;
            j--;
        }
        if (sequence.toString().length() >= MIN_VALUE_CROSS) {
            total = validateSequence(sequence.toString(), 0);
        }
        return total;
    }
    
    /**
     * @param chain
     * @param counter
     * @return
     */
    private int validateSequence(String chain, int counter) {
        if (chain.length() >= MIN_VALUE_CROSS) {
            for (int i = 0; i < chain.length() - 3; i++) {
                for (String sequence : SEQUENCES) {
                    if (chain.substring(i, 4 + i).equalsIgnoreCase(sequence)) {
                        counter++;
                    }
                }
            }
        } else {
            //throw exception length string dna is not fix to evaluate vertical.
        }
        return counter;
    }
}
