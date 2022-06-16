package com.khaledahmmedanik.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.khaledahmmedanik.main.model.ParkingSlot;

@Repository
public interface ParkingLotRepository extends MongoRepository<ParkingSlot, Integer> {

	
	@Query(value="{booked: ?0}", sort = "{id: 1}")
	List<ParkingSlot> getFreeSlotList(boolean b);
	
	
	
	@Query(value = "{}", count = true)
	public int countTotalParkingSlots();
	
	

}
