package com.khaledahmmedanik.main.exception;

public class ParkingSlotCollectionExceptioin extends Exception {

	
		private static final long serialVersionUID=1L;
		
		
		public ParkingSlotCollectionExceptioin(String message) {
			super(message);
		}
		
		public static String NotFoundException(int id) {
			
			return "Parking slot "+ id + " not found";
		}
		
		
		public static String ParkingSlotAlreadyExists() {
			return "Paking Slot with given paking slot Id already exists";
		}
		
		public static String newParkingSlotIdNull() {
			return "Id can not be null/0";
		}
		
		public static String newParkingSlotIsBookedtrue() {
			return "IsBooked can not be True when add a new Parking Slot";
		}
		
		public static String ParkingSlotAlreadyBooked(int id) {
			
			return "Parking slot "+ id + " is already Booked";
		}
		
		
		
}
