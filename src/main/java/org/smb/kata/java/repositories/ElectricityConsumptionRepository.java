package org.smb.kata.java.repositories;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.smb.kata.java.entities.repositoriesEntities.ElectricityConsumption;
import org.smb.kata.java.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ElectricityConsumptionRepository {
    @Value("${Electricity_CSV_FILE_PATH}")
    String Electricity_CSV_FILE_PATH;

    private Reader reader;
    private CSVParser csvParser;
    private List<ElectricityConsumption> electricityConsumptionList;
    public List<ElectricityConsumption> getElectricityConsumptionList() {
        return electricityConsumptionList;
    }

    public ElectricityConsumptionRepository()
    {
        electricityConsumptionList = new ArrayList<>();
    }

    public void loadElectricityConsumptionData() throws IOException, ParseException {
        log.info("loadElectricityConsumptionData invoked!");
        try {
            reader = Files.newBufferedReader(Paths.get(Electricity_CSV_FILE_PATH));
            csvParser = new CSVParser(reader, CSVFormat.newFormat(';').withQuote('"').withHeader("timestamp_utc","meter_id","consumption_kwh","building_id").withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            ElectricityConsumption electricityConsumption;

            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                electricityConsumptionList.add(
                        ElectricityConsumption.builder()
                                .consumptionKwh(Double.parseDouble(csvRecord.get("consumption_kwh")))
                                .buildingId(csvRecord.get("building_id"))
                                .meterId(csvRecord.get("meter_id"))
                                .timeStampUTC(DateUtils.getFormatter().parse(csvRecord.get("timestamp_utc")))
                                .build()
                );
            }
        }
        catch (Exception exception)
        {
            log.error("loadElectricityConsumptionData got error {}", exception.getMessage());
            throw exception;
        }
    }
}
