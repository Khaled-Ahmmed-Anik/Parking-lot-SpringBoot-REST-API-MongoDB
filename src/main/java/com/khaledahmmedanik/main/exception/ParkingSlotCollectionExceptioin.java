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
		
		public static String carVinNull() {
			return "Car vin can not be null";
		}
		
		public static String newParkingSlotIsBookedtrue() {
			return "IsBooked can not be True when add a new Parking Slot";
		}
		
		public static String ParkingSlotAlreadyBooked(int id) {
			
			return "Parking slot "+ id + " is already Booked";
		}
		
		
		public static String AllSlotBooked() {
			
			return "All slot are already booked";
		}

		public static String carNotFound(){
			return "This car is not in this parking lot or Given car info is not correct";
		}
		
		public static String carAlreadyInLot(String vin, int id){
			return "Car"+vin+" is already in parking slot "+id ;
		}
		
		public static String NoSlotToDelete(){
			return "ParkingLot has no slot yet to delete" ;
		}
		
		public static String SlotCanNotBeBooked(){
			return "Last Parking slot has to be free befor closed" ;
		}
		
		public static String RemoveAllBooked() {
			return "All Parking slot has to be free befor deteling all Slots" ;
		}
		
}
