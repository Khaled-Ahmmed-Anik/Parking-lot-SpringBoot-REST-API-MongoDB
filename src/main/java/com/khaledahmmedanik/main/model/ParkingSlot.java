package com.khaledahmmedanik.main.model;

import java.sql.Time;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ParkingSlot {
	
	@Id
	private int id;
	
	private boolean isBooked;
	
	private Time bookedTime;
	
	private CarInfo bookedCarInfo;

}
