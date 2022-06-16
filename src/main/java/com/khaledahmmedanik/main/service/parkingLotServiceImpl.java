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
		newParkingSlot.setIsbooked(false);
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

	// booking
	@Override
	public void bookParkingSlotById(int id, ParkingSlot parkingSlotUpdatedInfo) throws ParkingSlotCollectionExceptioin {

		Optional<ParkingSlot> foundParkingSlot = parkingLotRepo.findById(id);

		if (foundParkingSlot.isPresent()) {
			ParkingSlot toBeUpdate = foundParkingSlot.get();

			if (toBeUpdate.isIsbooked() == false) {
				// update car info that booked the slot
				toBeUpdate.setBookedCarInfo(parkingSlotUpdatedInfo.getBookedCarInfo());

				// update slot booted status (isBooked=true)
				toBeUpdate.setIsbooked(true);

				// find current time and date
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
				LocalDateTime now = LocalDateTime.now();

				String currentTime = dtf.format(now);

				// update the booking time
				toBeUpdate.setBookedAt(currentTime);

				// update on db
				parkingLotRepo.save(toBeUpdate);

			} else {
				throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.ParkingSlotAlreadyBooked(id));
			}

		} else {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.NotFoundException(id));
		}

	}

	@Override
	public void deleteParkingSlotById(int id) throws ParkingSlotCollectionExceptioin {
		Optional<ParkingSlot> foundParkingSlot = parkingLotRepo.findById(id);

		if (!foundParkingSlot.isPresent()) {
			throw new ParkingSlotCollectionExceptioin(ParkingSlotCollectionExceptioin.NotFoundException(id));
		}
		parkingLotRepo.deleteById(id);
	}

}
