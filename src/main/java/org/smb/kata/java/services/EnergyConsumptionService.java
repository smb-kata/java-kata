package org.smb.kata.java.services;

import lombok.extern.slf4j.Slf4j;
import org.smb.kata.java.entities.Consumption;
import org.smb.kata.java.entities.repositoriesEntities.ElectricityConsumption;
import org.smb.kata.java.entities.repositoriesEntities.HeatConsumption;
import org.smb.kata.java.entities.serviceEntities.ElectricityConsumptionService;
import org.smb.kata.java.entities.serviceEntities.EnergyConsumption;
import org.smb.kata.java.entities.serviceEntities.HeatConsumptionService;
import org.smb.kata.java.repositories.ElectricityConsumptionRepository;
import org.smb.kata.java.repositories.HeatConsumptionRepository;
import org.smb.kata.java.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnergyConsumptionService {
    @Autowired
    private ElectricityConsumptionRepository electricityConsumptionRepository;

    @Autowired
    private HeatConsumptionRepository heatConsumptionRepository;

    public List<EnergyConsumption> loadConsumptionOrderByTimeAndBuildingId(String buildingId)
    {
        log.info("LoadConsumptionOrderByTimeAndMeter invoked!");

        try {
            List<EnergyConsumption> energyConsumptionsList = new ArrayList<>();
            List<Consumption> electricityConsumptionList = getElectricityConsumptionServiceListByBuildingId(buildingId);
            List<Consumption> heatConsumptionList = getHeatConsumptionServiceListByBuildingId(buildingId);
            Comparator<Consumption> consumptionComparator = Comparator
                    .comparing(Consumption::getTimeStampUTC);

            heatConsumptionList = heatConsumptionList.stream().sorted(consumptionComparator)
                    .collect(Collectors.toList());
            Map<Date, Consumption> map = electricityConsumptionList.stream().collect(Collectors.toMap(Consumption::getTimeStampUTC, t -> t));
            for (Consumption heatConsumption : heatConsumptionList) {
                Consumption electricityConsumption = map.get(DateUtils.FloorHour(heatConsumption.getTimeStampUTC()));
                energyConsumptionsList.add(EnergyConsumption.builder()
                        .energyConsumptionInKWH((electricityConsumption == null ? 0 : electricityConsumption.getConsumptionKwh() / 4) + heatConsumption.getConsumptionKwh())
                        .timeStampUTC(heatConsumption.getTimeStampUTC())
                        .build());
            }
            return buildConsolidatedConsumption(energyConsumptionsList);
        }
        catch (Exception exception)
        {
            log.error("LoadConsumptionOrderByTimeAndBuildingId got error {}", exception.getMessage());
            throw exception;
        }
    }

    private List<Consumption> getElectricityConsumptionServiceListByBuildingId(String buildingId)
    {
        log.info("getElectricityConsumptionServiceListByBuildingId invoked!");

        try {
            List<ElectricityConsumption> electricityConsumptionList = electricityConsumptionRepository.getElectricityConsumptionList();
            return electricityConsumptionList.stream()
                    .filter(electricityConsumption -> electricityConsumption.getBuildingId().equals(buildingId))
                    .map(electricityConsumption -> ElectricityConsumptionService.builder()
                            .timeStampUTC(electricityConsumption.getTimeStampUTC())
                            .meterId(electricityConsumption.getMeterId())
                            .consumptionKwh(electricityConsumption.getConsumptionKwh())
                            .buildingId(electricityConsumption.getBuildingId()).build())
                    .collect(Collectors.toList());
        }
        catch (Exception exception)
        {
            log.error("getElectricityConsumptionServiceListByBuildingId got error {}", exception.getMessage());
            throw exception;
        }
    }

    private List<Consumption> getHeatConsumptionServiceListByBuildingId(String buildingId)
    {
        log.info("getHeatConsumptionServiceListByBuildingId invoked!");

        try {
            List<HeatConsumption> heatConsumptionList = heatConsumptionRepository.getHeatConsumptionList();

            return heatConsumptionList.stream()
                    .filter(heatConsumption -> heatConsumption.getBuildingId().equals(buildingId))
                    .map(heatConsumption -> HeatConsumptionService.builder()
                            .timeStampUTC(heatConsumption.getTimeStampUTC())
                            .meterId(heatConsumption.getMeterId())
                            .consumptionKwh(heatConsumption.getConsumptionKwh())
                            .buildingId(heatConsumption.getBuildingId()).build())
                    .collect(Collectors.toList());
        }
        catch (Exception exception)
        {
            log.error("getHeatConsumptionServiceListByBuildingId got error {}", exception.getMessage());
            throw exception;
        }
    }

    private List<EnergyConsumption> buildConsolidatedConsumption(List<EnergyConsumption> consumptionList)
    {
        log.info("buildConsolidatedConsumption invoked!");

        try {
            List<EnergyConsumption> energyConsumption;

            energyConsumption = consumptionList.stream()
                    .map(consumption -> EnergyConsumption.builder()
                            .energyConsumptionInKWH(consumption.getEnergyConsumptionInKWH())
                            .timeStampUTC(consumption.getTimeStampUTC())
                            .build())
                    .collect(Collectors.toList());

            return energyConsumption;
        }
        catch (Exception exception)
        {
            log.error("buildConsolidatedConsumption got error {}", exception.getMessage());
            throw exception;
        }

    }

}
