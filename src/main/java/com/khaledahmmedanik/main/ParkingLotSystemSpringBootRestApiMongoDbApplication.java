package com.khaledahmmedanik.main;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingLotSystemSpringBootRestApiMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingLotSystemSpringBootRestApiMongoDbApplication.class, args);
		
		
		/*
		 * DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss\nyyyy/MM/dd ");
		 * LocalDateTime now = LocalDateTime.now();
		 * 
		 * String currentTime = dtf.format(now);
		 * 
		 * System.out.println(currentTime);
		 * 
		 * 
		 */
	}

}
