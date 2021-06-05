package org.smb.kata.java.electricity.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ElectricityConsumptionView {

    private String meterId;
    private String buildingId;
    private Double kwh;
    private LocalDateTime time;
}
