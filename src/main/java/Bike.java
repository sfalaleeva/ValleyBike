
public class Bike {
	
	/**
	 * Unique integer ID of the bike object
	 */
	private int ID;
	/**
	 * ID of the last station it was at
	 */
	private int statID;
	
	/**
	 * Whether or not the bike is out on a ride
	 */
	private boolean onRide;
	
	/**
	 * Whether or not the bike needs maintenance
	 */
	private boolean needsMaintenance;
	
	/**
	 * Next bike ID that will be assigned for the whole class: Always 1 more than current highest id
	 */
	private static int nextBikeID;
	
	/**
	 * Constructs a bike object
	 * @param statID - ID of the station the bike was initialized at
	 */
	public Bike(int statID) {
		ID = nextBikeID;
		this.statID = statID;
		onRide = false;
		needsMaintenance = false;
		
		nextBikeID++;
	}
	
	
	/*
	 * Accessor Methods
	 */

	/**
	 * Gets the bike ID
	 * @return - bike ID
	 */
	public int getID() {
		return ID;
	}
	
	
	/**
	 * Gets the id of the most recent station the bike was at
	 * @return - most recent station ID
	 */
	public int getStatID() {
		return statID;
	}
	
	/**
	 * Gets whether or not the bike is out on a ride
	 * @return - true if checked out, false if at a station
	 */
	public boolean getOnRide() {
		return onRide;
	}
	
	
	/**
	 * Get whether the bike needs maintenance
	 * @return - true if there is a maintenance request, false otherwise
	 */
	public boolean getMaintenance() {
		return needsMaintenance;
	} 
	
	/*
	 * Functional methods
	 */
	
	
	/**
	 * Checks the bike into a station and updates stationToBikeMap.
	 * @param statID - station to be checked into 
	 * @return - true if everything went all right, false if the bike wasn't out on a ride or other error occurred
	 */
	public boolean checkIn(int newStatID) {
		//Check onRide was true and set to false
		if(!onRide) {
			System.out.println("Bike is not on ride");
			return false;
		}
		//Check the station has space
		Station station = ValleyBikeSim.stationsMap.get(newStatID);
		if(station.getAvailableDocks() < 1) {
			System.out.println("We are sorry, that station is currently full.");
			return false;
		}
		
		onRide = false;
		
		//Set StatID to the one given
		this.statID = newStatID;
		
		//add bike to stationToBikeMap for this station
		boolean checkExecution = station.addOneBike(this);
		
		//return true on no error, false if there was a problem
		return checkExecution;
		
	}
	
	
	/**
	 * Checks the bike out of a station
	 * @return - true if no error, false if an error occurred
	 */
	public boolean checkOut() {
		
		//Check that the bike is not out on a ride. It shouldn't be, but check anyways.
		if(onRide) {
			System.out.println("This bike has already been checked out.");
			return false;
		}
		//Check if the bike is in maintenance mode. Deny access if it is.
		else if(needsMaintenance) {
			System.out.println("This bike is damaged and should not be ridden. Please choose another.");
			return false;
		}
		else {
			onRide = true;
		
			// find current station and remove bike
			ValleyBikeSim.stationsMap.get(statID).removeSpecificBike(ID);
			statID = -1; // bike is not at a station anymore
		
			return true;
		}
	}
	
	/**
	 * Sets whether or not the bike needs maintenance
	 * @param needsMaintenance - true for a maintenance request, false otherwise.
	 */
	public void setNeedsMaintenance(boolean needsMaintenance) {
		this.needsMaintenance = needsMaintenance;
		
	}
}
