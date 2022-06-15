package com.khaledahmmedanik.main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.khaledahmmedanik.main.model.ParkingSlot;

public interface ParkingLotRepository extends MongoRepository<ParkingSlot, Integer> {

}
