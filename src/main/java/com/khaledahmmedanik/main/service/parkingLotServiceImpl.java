package com.khaledahmmedanik.main.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ParameterOutOfBoundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.khaledahmmedanik.main.exception.ParkingSlotCollectionExceptioin;
import com.khaledahmmedanik.main.model.CarInfo;
import com.khaledahmmedanik.main.model.ParkingSlot;
import com.khaledahmmedanik.main.repository.ParkingLotRepository;

@Service
public class parkingLotServiceImpl implements ParkingLotService {

	@Autowired
	private ParkingLotRepository parkingLotRepo;

	// create new parking slot , Id : 1,2,3,4,5........n
	@Override
	public ParkingSlot addParkingSlot() throws ConstraintViolationException, ParkingSlotCollectionExceptioin {

		int id = parkingLotRepo.countTotalParkingSlots() + 1;

		ParkingSlot newParkingSlot = new ParkingSlot();

		newParkingSlot.setId(id);
		newParkingSlot.setBooked(false);
		newParkingSlot.setBookedAt(null);
		newParkingSlot.setBookedCarInfo(null);
		parkingLotRepo.save(newParkingSlot);

		return newParkingSlot;
	}

	// read all parking slots info
	@Override
	public List<ParkingSlot> getAllParkingSlots() {
		List<ParkingSlot> parkingSlots = parkingLotRepo.findAll();

		if (parkingSlots.size() > 0) {
			return parkingSlots;
		} else {
			return new ArrayList<ParkingSlot>();
		}

	}

	// read single parking slot info
	@Override
	public ParkingSlot getParkingSlotById(int id) throws ParkingSlotCollectionExceptioin {
		Optional<ParkingSlot> parkingSlotOptional = parkingLotRepo.findById(id);

		if (!parkingSlotOptional.isPresent()) {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.NotFoundException(id));
		}

		return parkingSlotOptional.get();
	}

	// book slot , slot will be selected based on free and smaller id
	@Override
	public int bookFreeParkingSlot(CarInfo carInfo) throws ConstraintViolationException, ParkingSlotCollectionExceptioin {

		Optional<?> foundParkingSlot = parkingLotRepo.getTheSlotId(carInfo.getVin());
		if (foundParkingSlot.isPresent()) {
			ParkingSlot tempInfo = (ParkingSlot) foundParkingSlot.get();
			throw new ParkingSlotCollectionExceptioin(
					ParkingSlotCollectionExceptioin.carAlreadyInLot(carInfo.getVin(), tempInfo.getId()));
		}

		int id = getSlotIdReadyToBeBooked();

		Optional<ParkingSlot> foundParkingSlot1 = parkingLotRepo.findById(id);
		ParkingSlot toBeUpdate = foundParkingSlot1.get();

		// update car info that booked the slot
		toBeUpdate.setBookedCarInfo(carInfo);

		// update slot booted status (isBooked=true)
		toBeUpdate.setBooked(true);

		// find current time and date
		String currentTime = getCurretTimeDate();

		// update the booking time
		toBeUpdate.setBookedAt(currentTime);
		
		// update the leaving time to null
		toBeUpdate.setBookFreeFrom(null);

		// update on db
		parkingLotRepo.save(toBeUpdate);
		
		return id;

	}

	@Override
	public int deleteLastParkingSlot() throws ParkingSlotCollectionExceptioin {
		
		int id = parkingLotRepo.countTotalParkingSlots();
		
		
		//if there is no parking slot
		if (id==0) {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.NoSlotToDelete());
		}
		
		Optional<ParkingSlot> parkingSlotOptional = parkingLotRepo.findById(id);
		
		//if Last parking slot still booked 
		if(parkingSlotOptional.get().isBooked()) {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.SlotCanNotBeBooked());
		}
		
		parkingLotRepo.deleteById(id);

		return id;
	}

	//find which slot should be booked next
	public int getSlotIdReadyToBeBooked() throws ParkingSlotCollectionExceptioin {
		List<ParkingSlot> searchParkingSlot = parkingLotRepo.getFreeSlotList(false);


		if (searchParkingSlot.size() > 0) {
			return searchParkingSlot.get(0).getId();
		} else {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.AllSlotBooked());
		}

	}
	
	

	public String getCurretTimeDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();

		return dtf.format(now);
	}

	// car leave from parking lot, 1 parking slot get free
	@Override
	public ParkingSlot carLeavesSlotInfoUpdate(CarInfo carInfo) throws ParkingSlotCollectionExceptioin {

		Optional<?> foundParkingSlot = parkingLotRepo.getTheSlotId(carInfo.getVin());
		if (foundParkingSlot.isPresent()) {
			ParkingSlot toBeUpdated = (ParkingSlot) foundParkingSlot.get();

			// update leving time
			toBeUpdated.setBookFreeFrom(getCurretTimeDate());

			// set Booked as False as car is leaving and slot get free
			toBeUpdated.setBooked(false);

			// car info -> null , car is not associate anymore with this particular slot
			toBeUpdated.setBookedCarInfo(null);

			parkingLotRepo.save(toBeUpdated);

			return toBeUpdated;

		} else {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.carNotFound());
		}
	}

	@Override
	public void deleteAllParkingSlot() throws ParkingSlotCollectionExceptioin {
		
		int id = parkingLotRepo.countTotalParkingSlots();
		
		if (id==0) {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.NoSlotToDelete());
		}
		
		int totalBooked = parkingLotRepo.getNumberOfSltoBooked(true);
		
		if (totalBooked!=0) {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.RemoveAllBooked());
		}
	
		parkingLotRepo.deleteAll();
	}

}
