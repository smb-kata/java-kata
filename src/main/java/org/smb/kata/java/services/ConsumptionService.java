package org.smb.kata.java.services;

import lombok.extern.slf4j.Slf4j;
import org.smb.kata.java.entities.Consumption;
import org.smb.kata.java.entities.repositoriesEntities.ElectricityConsumption;
import org.smb.kata.java.entities.repositoriesEntities.HeatConsumption;
import org.smb.kata.java.entities.serviceEntities.ConsolidatedConsumption;
import org.smb.kata.java.entities.serviceEntities.ElectricityConsumptionService;
import org.smb.kata.java.entities.serviceEntities.HeatConsumptionService;
import org.smb.kata.java.repositories.ElectricityConsumptionRepository;
import org.smb.kata.java.repositories.HeatConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConsumptionService {
    @Autowired
    private ElectricityConsumptionRepository electricityConsumptionRepository;

    @Autowired
    private HeatConsumptionRepository heatConsumptionRepository;

    public ConsolidatedConsumption loadConsumptionOrderByTimeAndMeter()
    {
        log.info("LoadConsumptionOrderByTimeAndMeter invoked!");
        try {
            List<Consumption> consumptionList = new ArrayList<>();
            consumptionList.addAll(getElectricityConsumptionServiceList());
            consumptionList.addAll(getHeatConsumptionServiceList());

            Comparator<Consumption> consumptionComparator = Comparator
                    .comparing(Consumption::getTimeStampUTC)
                    .thenComparing(Consumption::getMeterId);

            consumptionList = consumptionList.stream().sorted(consumptionComparator)
                    .collect(Collectors.toList());

            return buildConsolidatedConsumption(consumptionList);
        }
        catch (Exception exception)
        {
            log.error("LoadConsumptionOrderByTimeAndMeter got error {}", exception.getMessage());
            throw exception;
        }
    }

    public ConsolidatedConsumption loadConsumptionOrderByTimeAndMeterId(String meterId)
    {
        log.info("LoadConsumptionOrderByTimeAndMeterId invoked using {}", meterId );

        try {
            List<Consumption> consumptionList = new ArrayList<>();
            consumptionList.addAll(getElectricityConsumptionServiceListByMeterId(meterId));
            consumptionList.addAll(getHeatConsumptionServiceListByMeterId(meterId));

            Comparator<Consumption> consumptionComparator = Comparator
                    .comparing(Consumption::getTimeStampUTC);

            consumptionList = consumptionList.stream().sorted(consumptionComparator)
                    .collect(Collectors.toList());
            return buildConsolidatedConsumption(consumptionList);
        }
        catch (Exception exception)
        {
            log.error("LoadConsumptionOrderByTimeAndMeterId got error {}", exception.getMessage());
            throw exception;
        }
    }

    private List<Consumption> getElectricityConsumptionServiceListByMeterId(String meterId)
    {
        try {
            List<ElectricityConsumption> electricityConsumptionList = electricityConsumptionRepository.getElectricityConsumptionList();
            return electricityConsumptionList.stream()
                    .filter(electricityConsumption -> electricityConsumption.getMeterId().equals(meterId))
                    .map(electricityConsumption -> ElectricityConsumptionService.builder()
                            .timeStampUTC(electricityConsumption.getTimeStampUTC())
                            .meterId(electricityConsumption.getMeterId())
                            .consumptionKwh(electricityConsumption.getConsumptionKwh())
                            .buildingId(electricityConsumption.getBuildingId()).build())
                    .collect(Collectors.toList());
        }
        catch (Exception exception)
        {
            log.error("getElectricityConsumptionServiceListByMeterId got error {}", exception.getMessage());
            throw exception;
        }
    }

    private List<Consumption> getHeatConsumptionServiceListByMeterId(String meterId)
    {
        try {
            List<HeatConsumption> heatConsumptionList = heatConsumptionRepository.getHeatConsumptionList();

            return heatConsumptionList.stream()
                    .filter(heatConsumption -> heatConsumption.getMeterId().equals(meterId))
                    .map(heatConsumption -> HeatConsumptionService.builder()
                            .timeStampUTC(heatConsumption.getTimeStampUTC())
                            .meterId(heatConsumption.getMeterId())
                            .consumptionKwh(heatConsumption.getConsumptionKwh())
                            .buildingId(heatConsumption.getBuildingId()).build())
                    .collect(Collectors.toList());
        }
        catch (Exception exception)
        {
            log.error("getHeatConsumptionServiceListByMeterId got error {}", exception.getMessage());
            throw exception;
        }
    }

    private List<Consumption> getElectricityConsumptionServiceList()
    {
        try
        {
            List<ElectricityConsumption> electricityConsumptionList = electricityConsumptionRepository.getElectricityConsumptionList();
            return electricityConsumptionList.stream()
                    .map(electricityConsumption -> ElectricityConsumptionService.builder()
                            .timeStampUTC(electricityConsumption.getTimeStampUTC())
                            .meterId(electricityConsumption.getMeterId())
                            .consumptionKwh(electricityConsumption.getConsumptionKwh())
                            .buildingId(electricityConsumption.getBuildingId()).build())
                    .collect(Collectors.toList());
        }
        catch (Exception exception)
        {
            log.error("getElectricityConsumptionServiceList got error {}", exception.getMessage());
            throw exception;
        }
    }

    private List<Consumption> getHeatConsumptionServiceList()
    {
        try {
            List<HeatConsumption> heatConsumptionList = heatConsumptionRepository.getHeatConsumptionList();
            return heatConsumptionList.stream()
                    .map(heatConsumption -> HeatConsumptionService.builder()
                            .timeStampUTC(heatConsumption.getTimeStampUTC())
                            .meterId(heatConsumption.getMeterId())
                            .consumptionKwh(heatConsumption.getConsumptionKwh())
                            .buildingId(heatConsumption.getBuildingId()).build())
                    .collect(Collectors.toList());
        }
        catch (Exception exception)
        {
            log.error("getHeatConsumptionServiceList got error {}", exception.getMessage());
            throw exception;
        }
    }

    private ConsolidatedConsumption buildConsolidatedConsumption(List<Consumption> consumptionList)
    {
        ConsolidatedConsumption consolidatedConsumption = new ConsolidatedConsumption();
        consolidatedConsumption.consumptionList = consumptionList;
        return consolidatedConsumption;

    }

}
