package org.smb.kata.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public abstract class Consumption {
    private Date timeStampUTC;
    private String meterId;
    private double consumptionKwh;
    private String buildingId;

    public abstract ConsumptionType getConsumptionType();

    @Override
    public String toString() {
        return "Consumption{" +
                "timeStampUTC=" + timeStampUTC +
                ", meterId='" + meterId + '\'' +
                ", consumptionKwh=" + consumptionKwh +
                ", buildingId='" + buildingId + '\'' +
                '}';
    }
}
