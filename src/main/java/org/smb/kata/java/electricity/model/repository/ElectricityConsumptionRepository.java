package org.smb.kata.java.electricity.model.repository;

import org.smb.kata.java.electricity.model.entitiy.ElectricityConsumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityConsumptionRepository extends JpaRepository<ElectricityConsumption, Long> {

    Page<ElectricityConsumption> findByMeterId(String meterId, Pageable pagination);
}
