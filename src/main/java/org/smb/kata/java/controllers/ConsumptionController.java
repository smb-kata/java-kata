package org.smb.kata.java.controllers;

import lombok.extern.slf4j.Slf4j;
import org.smb.kata.java.entities.serviceEntities.ConsolidatedConsumption;
import org.smb.kata.java.services.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ConsumptionController {
    @Autowired
    private ConsumptionService consumptionService;

    @GetMapping( value = "/getConsumptionByTimeAndMeter")
    public ResponseEntity<ConsolidatedConsumption> getConsumptionByTimeAndMeter()
    {
        log.info("getConsumptionByTimeAndMeter invoked!");

        try {
            ConsolidatedConsumption consolidatedConsumption = consumptionService.loadConsumptionOrderByTimeAndMeter();
            if( consolidatedConsumption == null ||  consolidatedConsumption.consumptionList == null || consolidatedConsumption.consumptionList.size() == 0)
            {
                log.info("getConsumptionByTimeAndMeter return no content!");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(consolidatedConsumption, HttpStatus.OK);
        }
        catch (Exception exception)
        {
            log.error("getConsumptionByTimeAndMeter got error {}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping( value = "/getConsumptionByMeterId/{meterId}")
    public ResponseEntity<ConsolidatedConsumption> getConsumptionByMeterId(@PathVariable("meterId") String meterId)
    {
        log.info("getConsumptionByMeterId invoked!");
        try {

            if(meterId == null || meterId.isBlank())
            {
                log.info("getConsumptionByMeterId return bad request!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ConsolidatedConsumption consolidatedConsumption = consumptionService.loadConsumptionOrderByTimeAndMeterId(meterId);
            if( consolidatedConsumption == null ||  consolidatedConsumption.consumptionList == null || consolidatedConsumption.consumptionList.size() == 0)
            {
                log.info("getConsumptionByMeterId return no content!");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(consolidatedConsumption, HttpStatus.OK);
        }
        catch (Exception exception)
        {
            log.error("getConsumptionByMeterId got error {}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
