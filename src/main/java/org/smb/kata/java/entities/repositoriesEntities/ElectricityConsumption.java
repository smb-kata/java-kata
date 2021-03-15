package org.smb.kata.java.entities.repositoriesEntities;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ElectricityConsumption {
    private Date timeStampUTC;
    private String meterId;
    private double consumptionKwh;
    private String buildingId;

    @Override
    public String toString() {
        return "ElectricityConsumption{" +
                "timeStampUTC=" + timeStampUTC +
                ", meterId='" + meterId + '\'' +
                ", consumptionKwh=" + consumptionKwh +
                ", buildingId='" + buildingId + '\'' +
                '}';
    }
}
