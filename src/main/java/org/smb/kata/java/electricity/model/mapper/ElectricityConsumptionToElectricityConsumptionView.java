package org.smb.kata.java.electricity.model.mapper;

import org.smb.kata.java.common.Mapper;
import org.smb.kata.java.electricity.model.dto.ElectricityConsumptionView;
import org.smb.kata.java.electricity.model.entitiy.ElectricityConsumption;
import org.springframework.stereotype.Component;

@Component
public class ElectricityConsumptionToElectricityConsumptionView
        implements Mapper<ElectricityConsumption, ElectricityConsumptionView> {

    @Override
    public ElectricityConsumptionView from(ElectricityConsumption source) {
        ElectricityConsumptionView target = new ElectricityConsumptionView();
        map(source, target);
        return target;
    }

    @Override
    public void map(ElectricityConsumption source, ElectricityConsumptionView target) {
        target.setMeterId(source.getMeterId());
        target.setBuildingId(source.getBuildingId());
        target.setKwh(source.getConsumptionInKwh());
        target.setTime(source.getTimestamp());
    }
}
