package org.smb.kata.java.entities.serviceEntities;

import lombok.Builder;
import lombok.Getter;
import org.smb.kata.java.entities.Consumption;
import org.smb.kata.java.entities.ConsumptionType;

import java.util.Date;

@Getter
public class HeatConsumptionService  extends Consumption {
    @Override
    public ConsumptionType getConsumptionType() {
        return ConsumptionType.HEAT_CONSUMPTION;
    }

    @Builder
    public HeatConsumptionService(Date timeStampUTC, String meterId, double consumptionKwh, String buildingId) {
        super( timeStampUTC, meterId, consumptionKwh,  buildingId);
    }
}
