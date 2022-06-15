package com.khaledahmmedanik.main.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CarInfo {
	
	@Id
	private int id;
	
	private String carModel;
	
	private String carBrand;
	
	private String carOwnerName;
	
	
}
