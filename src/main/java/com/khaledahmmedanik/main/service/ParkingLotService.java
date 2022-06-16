package com.khaledahmmedanik.main.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;

import com.khaledahmmedanik.main.exception.ParkingSlotCollectionExceptioin;
import com.khaledahmmedanik.main.model.ParkingSlot;

public interface ParkingLotService {
	
	public ParkingSlot addParkingSlot() throws ConstraintViolationException, ParkingSlotCollectionExceptioin;
	
	
	public List<ParkingSlot> getAllParkingSlots();
	
	public ParkingSlot getParkingSlotById(int id) throws ParkingSlotCollectionExceptioin;
	
	public void bookParkingSlotById(int id, ParkingSlot parkingSlotUpdatedInfo) throws ParkingSlotCollectionExceptioin;
	
	public void deleteParkingSlotById(int id) throws ParkingSlotCollectionExceptioin;
	

}
