package org.smb.kata.java.entities.repositoriesEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeatConsumption {
    @JsonProperty("ts")
    private Date timeStampUTC;
    @JsonProperty("mid")
    private String meterId;
    @JsonProperty("v")
    private double consumptionKwh;
    @JsonProperty("bid")
    private String buildingId;

    @Override
    public String toString() {
        return "HeatConsumption{" +
                "timeStampUTC=" + timeStampUTC +
                ", meterId='" + meterId + '\'' +
                ", consumptionKwh=" + consumptionKwh +
                ", buildingId='" + buildingId + '\'' +
                '}';
    }
}
