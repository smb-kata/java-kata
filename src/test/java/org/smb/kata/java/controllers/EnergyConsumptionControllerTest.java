package org.smb.kata.java.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smb.kata.java.entities.serviceEntities.EnergyConsumption;
import org.smb.kata.java.services.EnergyConsumptionService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class EnergyConsumptionControllerTest {
    @Mock
    private EnergyConsumptionService energyConsumptionService;

    @InjectMocks
    private EnergyConsumptionController energyConsumptionController;

    //When Request param is null or empty
    @Test
    public void getEnergyConsumptionByTimeAndBuildingId_When_Request_Param_Is_Null_Then_Returns_Bad_Request()
    {
        String buildingId = null;
        List<EnergyConsumption> energyConsumption = new ArrayList<>();
        energyConsumption.add(EnergyConsumption.builder().energyConsumptionInKWH(10).timeStampUTC(Date.from(Instant.now())).build());
        energyConsumption.add(EnergyConsumption.builder().energyConsumptionInKWH(12).timeStampUTC(Date.from(Instant.now())).build());
        energyConsumption.add(EnergyConsumption.builder().energyConsumptionInKWH(14).timeStampUTC(Date.from(Instant.now())).build());

        Mockito.when(energyConsumptionService.loadConsumptionOrderByTimeAndBuildingId(Mockito.anyString())).thenReturn(energyConsumption);
        ResponseEntity<List<EnergyConsumption>> responseEntity =   energyConsumptionController.getEnergyConsumptionByTimeAndBuildingId(buildingId);
        Assertions.assertEquals(responseEntity.getStatusCodeValue(),400);
    }


    //When Null From Service
    @Test
    public void getEnergyConsumptionByTimeAndBuildingId_When_Service_Return_Null_Then_Returns_No_Content()
    {
        String buildingId = "123";
        List<EnergyConsumption> energyConsumption = null;
        Mockito.when(energyConsumptionService.loadConsumptionOrderByTimeAndBuildingId(Mockito.anyString())).thenReturn(energyConsumption);
        ResponseEntity<List<EnergyConsumption>> responseEntity =   energyConsumptionController.getEnergyConsumptionByTimeAndBuildingId(buildingId);
        Assertions.assertEquals(responseEntity.getStatusCodeValue(),204);
    }


    //When Returns data
    @Test
    public void getConsumptionByTimeAndMeter_When_Service_Return_Data_Then_Returns_Data()
    {
        String buildingId = "123";
        List<EnergyConsumption> energyConsumption = new ArrayList<>();
        energyConsumption.add(EnergyConsumption.builder().energyConsumptionInKWH(10).timeStampUTC(Date.from(Instant.now())).build());
        energyConsumption.add(EnergyConsumption.builder().energyConsumptionInKWH(12).timeStampUTC(Date.from(Instant.now())).build());
        energyConsumption.add(EnergyConsumption.builder().energyConsumptionInKWH(14).timeStampUTC(Date.from(Instant.now())).build());

        Mockito.when(energyConsumptionService.loadConsumptionOrderByTimeAndBuildingId(Mockito.anyString())).thenReturn(energyConsumption);
        ResponseEntity<List<EnergyConsumption>> responseEntity =   energyConsumptionController.getEnergyConsumptionByTimeAndBuildingId(buildingId);
        Assertions.assertEquals(responseEntity.getStatusCodeValue(),200);
        Assertions.assertEquals(responseEntity.getBody().size(),3);
    }

    //When Exception From Service
    @Test
    public void getConsumptionByTimeAndMeter__When_Service_Return_Exception_Then_Returns_Internal_Server_Error()
    {
        String buildingId = "123";
        Mockito.when(energyConsumptionService.loadConsumptionOrderByTimeAndBuildingId(Mockito.anyString())).thenThrow(new NullPointerException("Null Pointer Exception"));
        ResponseEntity<List<EnergyConsumption>> responseEntity =   energyConsumptionController.getEnergyConsumptionByTimeAndBuildingId(buildingId);
        Assertions.assertEquals(responseEntity.getStatusCodeValue(),500);
    }
}
