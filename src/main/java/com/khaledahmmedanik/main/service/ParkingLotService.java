package com.khaledahmmedanik.main.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;

import com.khaledahmmedanik.main.exception.ParkingSlotCollectionExceptioin;
import com.khaledahmmedanik.main.model.CarInfo;
import com.khaledahmmedanik.main.model.ParkingSlot;

public interface ParkingLotService {
	
	public ParkingSlot addParkingSlot() throws ConstraintViolationException, ParkingSlotCollectionExceptioin;
	
	
	public List<ParkingSlot> getAllParkingSlots();
	
	public ParkingSlot getParkingSlotById(int id) throws ParkingSlotCollectionExceptioin;
	
	public int bookFreeParkingSlot(CarInfo carInfo) throws ParkingSlotCollectionExceptioin;
	
	public int deleteLastParkingSlot() throws ParkingSlotCollectionExceptioin;


	public ParkingSlot carLeavesSlotInfoUpdate(CarInfo carInfo) throws ParkingSlotCollectionExceptioin;
	

}
