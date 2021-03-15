package org.smb.kata.java.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smb.kata.java.entities.serviceEntities.ConsolidatedConsumption;
import org.smb.kata.java.entities.serviceEntities.ElectricityConsumptionService;
import org.smb.kata.java.services.ConsumptionService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
public class ConsumptionControllerTest {
    @Mock
    private ConsumptionService consumptionService;

    @InjectMocks
    private ConsumptionController consumptionController;

    //When Response From Service
    @Test
    public void getConsumptionByTimeAndMeter_When_Service_Response_With_Data_Then_Returns_Data()
    {
        ConsolidatedConsumption consolidatedConsumption = new ConsolidatedConsumption();
        consolidatedConsumption.consumptionList = new ArrayList<>();
        consolidatedConsumption.consumptionList.add(ElectricityConsumptionService.builder().meterId("1").buildingId("1").consumptionKwh(10).timeStampUTC(Date.from(Instant.now())).build());
        consolidatedConsumption.consumptionList.add(ElectricityConsumptionService.builder().meterId("2").buildingId("1").consumptionKwh(12).timeStampUTC(Date.from(Instant.now())).build());
        consolidatedConsumption.consumptionList.add(ElectricityConsumptionService.builder().meterId("3").buildingId("2").consumptionKwh(8).timeStampUTC(Date.from(Instant.now())).build());

        Mockito.when(consumptionService.loadConsumptionOrderByTimeAndMeter()).thenReturn(consolidatedConsumption);
        ResponseEntity<ConsolidatedConsumption> responseEntity =   consumptionController.getConsumptionByTimeAndMeter();
        Assertions.assertEquals(responseEntity.getStatusCodeValue(),200);
        Assertions.assertEquals(responseEntity.getBody().consumptionList.size(),3);
    }

    //When Null From Service
    @Test
    public void getConsumptionByTimeAndMeter_When_Service_Response_With_Null_Then_Returns_No_Content()
    {
        ConsolidatedConsumption consolidatedConsumption = new ConsolidatedConsumption();
        consolidatedConsumption.consumptionList = null;
        Mockito.when(consumptionService.loadConsumptionOrderByTimeAndMeter()).thenReturn(consolidatedConsumption);
        ResponseEntity<ConsolidatedConsumption> consolidatedConsumptionResponseEntity =  consumptionController.getConsumptionByTimeAndMeter();
        Assertions.assertEquals(consolidatedConsumptionResponseEntity.getStatusCodeValue(), 204);
    }


    //When Exception From Service
    @Test
    public void getConsumptionByTimeAndMeter_When_Service_Response_With_Exception_Then_Returns_Internal_Server_Error()
    {
        Mockito.when(consumptionService.loadConsumptionOrderByTimeAndMeter()).thenThrow(new NullPointerException("Null Pointer Exception"));
        ResponseEntity<ConsolidatedConsumption> consolidatedConsumptionResponseEntity =  consumptionController.getConsumptionByTimeAndMeter();
        Assertions.assertEquals(consolidatedConsumptionResponseEntity.getStatusCodeValue(), 500);
    }
}
