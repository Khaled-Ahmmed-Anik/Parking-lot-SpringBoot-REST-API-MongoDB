package com.khaledahmmedanik.main.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.khaledahmmedanik.main.exception.ParkingSlotCollectionExceptioin;
import com.khaledahmmedanik.main.model.CarInfo;
import com.khaledahmmedanik.main.model.ParkingSlot;
import com.khaledahmmedanik.main.service.ParkingLotService;

@RestController
public class ParkingLotController {
		
		@Autowired ParkingLotService parkingLotService;
		
		
		
		//to get all the parking slots
		
		@GetMapping("/parkingSlots")
		public ResponseEntity<?> getAllParkingSlots(){
			List<ParkingSlot> parkingSlots=parkingLotService.getAllParkingSlots();
			return new ResponseEntity<>(parkingSlots,(parkingSlots.size()>0)?HttpStatus.OK:HttpStatus.NOT_FOUND);
		}
		
		
		
		
		// to add new parking slot or space to the database (extra space add to the parking lot, )
		
		@PostMapping("/parkingSlots/add")
		public ResponseEntity<?> addNewParkingSlot() throws ParkingSlotCollectionExceptioin{
			
			try {
				ParkingSlot newParkingSlot= parkingLotService.addParkingSlot();
				return new ResponseEntity<ParkingSlot>(newParkingSlot,HttpStatus.OK);
			} catch (ConstraintViolationException e) {
				return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			} catch (ParkingSlotCollectionExceptioin e) {
				return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
			}
			
		}
		
		
		
		
		// find single parking slot info by ID:
		
		@GetMapping("parkingSlots/{id}")
		public ResponseEntity<?> getParkingSlotById(@PathVariable("id") int id){
			
			try {
				return new ResponseEntity<>( parkingLotService.getParkingSlotById(id),HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>( e.getMessage(),HttpStatus.NOT_FOUND);
			}
			
		}
		
		
		
		
		// book a parking slot   by ID: (update parking slot info)
		
		@PutMapping("/parkingSlots/book/{id}")
		public ResponseEntity<?> bookParkingSlotById(@PathVariable("id") int id, @RequestBody CarInfo carInfo){
			
			try {
				parkingLotService.bookParkingSlotById(id,carInfo);
				return new ResponseEntity<>("Parking slot "+id+" get booked successfully",HttpStatus.OK);
			}catch (ParkingSlotCollectionExceptioin e) {
				return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
			}
		}
		
		
		
		
		
		
		@DeleteMapping("/parkingSlots/delete/{id}")
		public ResponseEntity<?> deleteParkingSlotById(@PathVariable("id") int id){
			
			try {
				parkingLotService.deleteParkingSlotById(id);
				return new ResponseEntity<>("Parking slot "+id+" deleted successfully", HttpStatus.OK);
			}catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
			}
			
		}
				
		
		@PutMapping("/parkingSlots/leave")
		public ResponseEntity<?> parkingSlotWithCarInfoGetFree(@RequestBody CarInfo carInfo){
			
			try {
				ParkingSlot updatedSlot= parkingLotService.carLeavesSlotInfoUpdate(carInfo);
				return new ResponseEntity<>("Car "+carInfo.getVin()+" was in slot"+updatedSlot.getId()+" from: "+updatedSlot.getBookedAt()+" to: "+updatedSlot.getBookFreeFrom(),HttpStatus.OK);
			}catch (ParkingSlotCollectionExceptioin e) {
				return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
			}
		}
		
		
		
		
		
}
