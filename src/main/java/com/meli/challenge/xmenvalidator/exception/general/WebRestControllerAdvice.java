package com.meli.challenge.xmenvalidator.exception.general;

import com.meli.challenge.xmenvalidator.exception.StatsException;
import com.meli.challenge.xmenvalidator.exception.ValidatorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.meli.challenge.xmenvalidator.general.Constants.EX_STATS;
import static com.meli.challenge.xmenvalidator.general.Constants.EX_VALIDATOR;

@Log4j2
@RestControllerAdvice
public class WebRestControllerAdvice {
    
    
    @ExceptionHandler(ValidatorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleValidatorException(ValidatorException ex) {
        final ExceptionResponse response = ExceptionResponse.errorBuilder(EX_VALIDATOR, ex.getLocalizedMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(response.toString().concat(getDeveloperInformation(ex)));
        return response;
    }
    
    @ExceptionHandler(StatsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleStatsException(StatsException ex) {
        final ExceptionResponse response = ExceptionResponse.errorBuilder(EX_STATS, ex.getLocalizedMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(response.toString().concat(getDeveloperInformation(ex)));
        return response;
    }
    
    /**
     *
     * @param ex
     * @return
     */
    private String getDeveloperInformation(RuntimeException ex) {
        StringBuilder stringBuilder = new StringBuilder();
        if (ex.getStackTrace().length != 0) {
            final String classString = ", [ Class: ";
            final String method = " Method: ";
            final String line = " Line: ";
            final String separator = ",";
            final String end = " ]";
            stringBuilder.append(classString).append(ex.getStackTrace()[0].getClassName())
                    .append(separator)
                    .append(method).append(ex.getStackTrace()[0].getMethodName())
                    .append(separator)
                    .append(line).append(ex.getStackTrace()[0].getLineNumber())
                    .append(end);
        }
        return stringBuilder.toString();
    }
}
