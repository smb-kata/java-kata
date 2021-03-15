package org.smb.kata.java.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smb.kata.java.entities.repositoriesEntities.ElectricityConsumption;
import org.smb.kata.java.entities.repositoriesEntities.HeatConsumption;
import org.smb.kata.java.entities.serviceEntities.ConsolidatedConsumption;
import org.smb.kata.java.repositories.ElectricityConsumptionRepository;
import org.smb.kata.java.repositories.HeatConsumptionRepository;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ConsumptionServiceTest {


    @Mock
    private ElectricityConsumptionRepository electricityConsumptionRepository;

    @Mock
    private HeatConsumptionRepository heatConsumptionRepository;

    @InjectMocks
    private ConsumptionService consumptionService;

    //When electricity data exception
    @Test
    public void LoadConsumptionOrderByTimeAndMeterTest_When_LoadingData_Return_Exception_Then_Throws_Exception()
    {
        List<ElectricityConsumption> electricityConsumptionList = new ArrayList<>();
        List<HeatConsumption> heatConsumptionList = new ArrayList<>();
        String exceptionMessage = "Null Pointer";

        Mockito.when(electricityConsumptionRepository.getElectricityConsumptionList()).thenThrow(new NullPointerException(exceptionMessage));
        Mockito.when(heatConsumptionRepository.getHeatConsumptionList()).thenReturn(heatConsumptionList);

        Exception exception = Assertions.assertThrows(NullPointerException.class, ()-> consumptionService.loadConsumptionOrderByTimeAndMeter());

        Assertions.assertEquals(exception.getMessage(),exceptionMessage);
    }

    //When electricity data returns
    @Test
    public void LoadConsumptionOrderByTimeAndMeterTest_When_LoadingData_Return_Data_Then_Returns_Data_Sorted()
    {
        List<ElectricityConsumption> electricityConsumptionList = new ArrayList<>();
        List<HeatConsumption> heatConsumptionList = new ArrayList<>();

        electricityConsumptionList.add(ElectricityConsumption.builder()
                .consumptionKwh(10)
                .buildingId("1")
                .meterId("1")
                .timeStampUTC(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5)))
                .build());

        electricityConsumptionList.add(ElectricityConsumption.builder()
                .consumptionKwh(10)
                .buildingId("1")
                .meterId("3")
                .timeStampUTC(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(7)))
                .build());

        electricityConsumptionList.add(ElectricityConsumption.builder()
                .consumptionKwh(10)
                .buildingId("1")
                .meterId("2")
                .timeStampUTC(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2)))
                .build());


        Mockito.when(electricityConsumptionRepository.getElectricityConsumptionList()).thenReturn(electricityConsumptionList);
        Mockito.when(heatConsumptionRepository.getHeatConsumptionList()).thenReturn(heatConsumptionList);

        ConsolidatedConsumption consolidatedConsumption =  consumptionService.loadConsumptionOrderByTimeAndMeter();

        Assertions.assertEquals(consolidatedConsumption.consumptionList.size(),3);
        Assertions.assertEquals(consolidatedConsumption.consumptionList.get(0).getMeterId(), "3");
    }


}
