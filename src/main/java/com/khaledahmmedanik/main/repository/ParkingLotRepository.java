package com.khaledahmmedanik.main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.khaledahmmedanik.main.model.ParkingSlot;

@Repository
public interface ParkingLotRepository extends MongoRepository<ParkingSlot, Integer> {

	
	@Query(value = "{}", count = true)
	public int countTotalParkingSlots();

}
