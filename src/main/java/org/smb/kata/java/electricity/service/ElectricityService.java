package org.smb.kata.java.electricity.service;

import org.smb.kata.java.electricity.model.dto.ElectricityConsumptionView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElectricityService {

    Page<ElectricityConsumptionView> list(String meterId, Pageable pagination);
}
