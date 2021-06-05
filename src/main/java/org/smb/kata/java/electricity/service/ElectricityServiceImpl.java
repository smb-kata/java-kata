package org.smb.kata.java.electricity.service;

import org.smb.kata.java.electricity.model.dto.ElectricityConsumptionView;
import org.smb.kata.java.electricity.model.entitiy.ElectricityConsumption;
import org.smb.kata.java.electricity.model.mapper.ElectricityConsumptionToElectricityConsumptionView;
import org.smb.kata.java.electricity.model.repository.ElectricityConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ElectricityServiceImpl implements ElectricityService {

    @Autowired
    private ElectricityConsumptionRepository repository;

    @Autowired
    private ElectricityConsumptionToElectricityConsumptionView mapper;

    @Override
    public Page<ElectricityConsumptionView> list(String meterId, Pageable pagination) {

        Page<ElectricityConsumption> results;
        if (null == meterId) {
            results = repository.findAll(pagination);
        } else {
            results = repository.findByMeterId(meterId, pagination);
        }

        return results.map(e -> mapper.from(e));
    }
}
