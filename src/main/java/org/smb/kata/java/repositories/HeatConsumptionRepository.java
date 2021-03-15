package org.smb.kata.java.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.smb.kata.java.entities.repositoriesEntities.HeatConsumption;
import org.smb.kata.java.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
@Slf4j
public class HeatConsumptionRepository {
    @Value("${HEAT_CONSUMPTION_CSV_FILE_PATH}")
    private String HEAT_CONSUMPTION_CSV_FILE_PATH;

    private List<HeatConsumption> heatConsumptionList;

    public List<HeatConsumption> getHeatConsumptionList() {
        return heatConsumptionList;
    }

    public void loadHeatConsumptionData() throws IOException {

        log.info("loadHeatConsumptionData invoked!");
        try
        {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(DateUtils.getFormatter());

            heatConsumptionList = objectMapper.readValue(new File(HEAT_CONSUMPTION_CSV_FILE_PATH),
                    new TypeReference<>() {
                    });
        }
        catch (Exception exception)
        {
            log.error("loadHeatConsumptionData got error {}", exception.getMessage());
            throw exception;
        }
    }
}
