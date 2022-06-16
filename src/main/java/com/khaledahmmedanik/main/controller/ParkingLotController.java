package com.khaledahmmedanik.main.controller;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

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
		
		
		
		
		// find single parking slot info by ID:
		
		@GetMapping("parkingSlots/{id}")
		public ResponseEntity<?> getParkingSlotById(@PathVariable("id") int id){
			
			Optional<ParkingSlot> foundParkingSlot=parkingLotRepo.findById(id);
			
			if(foundParkingSlot.isPresent()) {
				return new ResponseEntity<ParkingSlot>(foundParkingSlot.get(),HttpStatus.OK);
			}else {
				return new ResponseEntity<>("There is no parking slot with id: "+id,HttpStatus.NOT_FOUND);
			}
			
		}
		
		
		
		
		// book a parking slot   by ID: (update parking slot info)
		
		@PutMapping("/parkingSlots/book/{id}")
		public ResponseEntity<?> bookParkingSlotById(@PathVariable("id") int id, @RequestBody ParkingSlot parkingSlotWithUpdatedInfo){
			
			Optional<ParkingSlot> foundParkingSlot=parkingLotRepo.findById(id);
			
			if(foundParkingSlot.isPresent()) {
				ParkingSlot toBeUpdate=foundParkingSlot.get();
				
				if(toBeUpdate.isBooked()==false) {
					//update car info that booked the slot
					toBeUpdate.setBookedCarInfo(parkingSlotWithUpdatedInfo.getBookedCarInfo());
					
					//update slot booted status (isBooked=true)
					toBeUpdate.setBooked(true);

					
					
					//find current time and date
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");  
					LocalDateTime now = LocalDateTime.now();  
					
					String currentTime = dtf.format(now);
					 
					
					
					//update the booking time
					toBeUpdate.setBookedAt(currentTime);
					
					parkingLotRepo.save(toBeUpdate);
					
					return new ResponseEntity<ParkingSlot>(foundParkingSlot.get(),HttpStatus.OK);
					
				}else {
					return new ResponseEntity<>("Parking slot "+id+" is already booked...",HttpStatus.OK);
				}
				
//				return new ResponseEntity<ParkingSlot>(parkingSlotWithUpdatedInfo,HttpStatus.OK);
			}else {
				return new ResponseEntity<>("There is no parking slot with id: "+id,HttpStatus.NOT_FOUND);
			}
		}
				
		
		
		
		
		
}
