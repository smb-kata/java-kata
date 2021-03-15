package org.smb.kata.java.entities.serviceEntities;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class EnergyConsumption {

    private double energyConsumptionInKWH;
    private Date timeStampUTC;

    @Override
    public String toString() {
        return "EnergyConsumption{" +
                "EnergyConsumptionInKWH=" + energyConsumptionInKWH +
                ", timeStamp_UTC=" + timeStampUTC +
                '}';
    }
}
