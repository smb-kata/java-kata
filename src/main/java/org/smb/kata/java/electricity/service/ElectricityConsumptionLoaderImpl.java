package org.smb.kata.java.electricity.service;

import org.smb.kata.java.electricity.execption.LoadCsvFileException;
import org.smb.kata.java.electricity.model.entitiy.ElectricityConsumption;
import org.smb.kata.java.electricity.model.repository.ElectricityConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElectricityConsumptionLoaderImpl implements ElectricityConsumptionLoader {

    private static final String COMMA_DELIMITER = ";";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ElectricityConsumptionRepository repository;

    @Override
    public void loadCsvToDB() {
        this.persistData(this.readCsvFile());
    }

    @Transactional
    private void persistData(List<ElectricityConsumption> electricityList) {
        repository.saveAll(electricityList);
    }

    private List<ElectricityConsumption> readCsvFile() {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        List<ElectricityConsumption> electricityList = new ArrayList<>();
        try {
            var file = ResourceUtils.getFile("classpath:data/electricity-consumption.csv");
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            br = new BufferedReader(isr);

            var line = br.readLine();
            while(br.readLine() != null) {
                line = br.readLine();
                electricityList.add(this.setValues(line.split(COMMA_DELIMITER)));
            }
        } catch (IOException ex) {
            throw new LoadCsvFileException();
        } finally {
            try {
                br.close();
                fis.close();
                isr.close();
            } catch (Exception ex) {
                // TODO: deal with the exceptions
            }
        }

        return electricityList;
    }

    private ElectricityConsumption setValues(String[] values) {
        // "timestamp_utc";"meter_id";"consumption_kwh";"building_id"
        var electricity = new ElectricityConsumption();
        electricity.setTimestamp(LocalDateTime.parse(values[0].replaceAll("\"",""), DATE_TIME_FORMATTER));
        electricity.setMeterId(values[1].replaceAll("\"",""));
        electricity.setConsumptionInKwh(Double.parseDouble(values[2]));
        electricity.setBuildingId(values[3].replaceAll("\"",""));

        return electricity;
    }
}
