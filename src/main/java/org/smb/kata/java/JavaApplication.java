package org.smb.kata.java;

import lombok.extern.slf4j.Slf4j;
import org.smb.kata.java.repositories.ElectricityConsumptionRepository;
import org.smb.kata.java.repositories.HeatConsumptionRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Slf4j
public class JavaApplication {

	public static void main(String[] args) {

		try {
			ApplicationContext applicationContext = SpringApplication.run(JavaApplication.class, args);
			ElectricityConsumptionRepository electricityConsumptionRepository = applicationContext.getBean(ElectricityConsumptionRepository.class);
			HeatConsumptionRepository heatConsumptionRepository = applicationContext.getBean(HeatConsumptionRepository.class);
			electricityConsumptionRepository.loadElectricityConsumptionData();
			heatConsumptionRepository.loadHeatConsumptionData();
		}
		catch (Exception exception)
		{
			log.error("main method got error {}", exception.getMessage());
		}

	}

	@EventListener(ApplicationReadyEvent.class)
	public void printGreetings() {
		System.out.println("Hello world.");
	}

}
