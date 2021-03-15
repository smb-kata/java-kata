package org.smb.kata.java.entities.serviceEntities;

import lombok.Builder;
import org.smb.kata.java.entities.Consumption;
import org.smb.kata.java.entities.ConsumptionType;

import java.util.Date;

public class ElectricityConsumptionService  extends Consumption {
    @Override
    public ConsumptionType getConsumptionType() {
        return ConsumptionType.ELECTRICITY_CONSUMPTION;
    }

    @Builder
    public ElectricityConsumptionService(Date timeStampUTC, String meterId, double consumptionKwh, String buildingId) {
        super( timeStampUTC, meterId, consumptionKwh,  buildingId);
    }
}
