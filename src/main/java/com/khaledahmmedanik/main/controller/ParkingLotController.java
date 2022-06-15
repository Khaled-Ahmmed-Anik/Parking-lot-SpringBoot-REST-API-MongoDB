package com.khaledahmmedanik.main.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.khaledahmmedanik.main.model.ParkingSlot;
import com.khaledahmmedanik.main.repository.ParkingLotRepository;

@RestController
public class ParkingLotController {

	
		@Autowired
		private ParkingLotRepository parkingLotRepo;
		
		
		
		
		
		//to get all the parking slots
		
		@GetMapping("/parkingSlots")
		public ResponseEntity<?> getAllParkingSlots(){
			List<ParkingSlot> parkingSlots=parkingLotRepo.findAll();
			
			if(parkingSlots.size()>0) {
				return new ResponseEntity<List<ParkingSlot>>(parkingSlots,HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Any Parking Slots is not Exits",HttpStatus.NOT_FOUND);
			}
			
		}
		
		
		
		
		// to add new parking slot or space to the database (extra space add to the parking lot)
		
		@PostMapping("/parkingSlots")
		public ResponseEntity<?> addNewParkingSlot(@RequestBody ParkingSlot newParkingSlot){
			
			try {
				parkingLotRepo.save(newParkingSlot);
				return new ResponseEntity<ParkingSlot>(newParkingSlot,HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
}
