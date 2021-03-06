package com.khaledahmmedanik.main.model;



import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ParkingSlot {
	
	@NotNull(message="Id can not be null")
	@Id
	private int id;
	
	
	private boolean booked;
	
	private String bookedAt;
	
	private String bookFreeFrom;
	
	private CarInfo bookedCarInfo;

	
	
	

}
