package org.smb.kata.java.controllers;

import lombok.extern.slf4j.Slf4j;
import org.smb.kata.java.entities.serviceEntities.EnergyConsumption;
import org.smb.kata.java.services.EnergyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EnergyConsumptionController {
    @Autowired
    private EnergyConsumptionService energyConsumptionService;


    @GetMapping( value = "/getEnergyConsumptionByBuildingId/{buildingId}")
    public ResponseEntity<List<EnergyConsumption>> getEnergyConsumptionByTimeAndBuildingId(@PathVariable("buildingId") String buildingId)
    {
        log.info("getEnergyConsumptionByTimeAndBuildingId invoked!");
        try {
            if(buildingId == null || buildingId.isEmpty())
            {
                log.info("getEnergyConsumptionByTimeAndBuildingId return bad request!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<EnergyConsumption> energyConsumptionList = energyConsumptionService.loadConsumptionOrderByTimeAndBuildingId(buildingId);
            if( energyConsumptionList == null ||  energyConsumptionList.size() == 0)
            {
                log.info("getEnergyConsumptionByTimeAndBuildingId return no content!");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(energyConsumptionList, HttpStatus.OK);
        }
        catch (Exception exception)
        {
            log.error("getEnergyConsumptionByTimeAndBuildingId got error {}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

