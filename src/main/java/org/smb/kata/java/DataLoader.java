package org.smb.kata.java;

import org.smb.kata.java.electricity.service.ElectricityConsumptionLoader;
import org.smb.kata.java.heat.service.HeatConsumptionLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataLoader {

    @Autowired
    private ElectricityConsumptionLoader electricityConsumptionLoader;

    @Autowired
    private HeatConsumptionLoader heatConsumptionLoader;

    @PostConstruct
    public void init(){
       electricityConsumptionLoader.loadCsvToDB();
    }
}
