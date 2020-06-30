package com.meli.challenge.xmenvalidator.api;

import com.google.gson.Gson;
import com.meli.challenge.xmenvalidator.exception.StatsException;
import com.meli.challenge.xmenvalidator.service.StatsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.meli.challenge.xmenvalidator.general.Constants.MSG_STATS_REQUEST;

@Log4j2
@RestController
@RequestMapping("/monitor")
public class StatsController {
    
    @Autowired
    StatsService statsService;
    
    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStats() {
        
        log.info(MSG_STATS_REQUEST);
        try {
            return ResponseEntity
                    .ok(new Gson().toJson(statsService.getStatistics()));
        } catch (Exception ex) {
            throw new StatsException(ex.getLocalizedMessage(), ex);
        }
    }
}
