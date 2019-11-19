
public class bike {
	
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
	static int nextBikeID;
	
	public bike() {
		//TODO: set up bike constructor
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
	 * Checks the bike into a station
	 * @param statID - station to be checked into 
	 * @return - true if everything went all right, false if the bike wasn't out on a ride or other error occurred
	 */
	public boolean checkIn(int statID) {
		//TODO: Check bike into station. 
		
		//Check onRide was true and set to false
		//Set StatID to the one given
		//add bike to stationToBikeMap for this station
		
		//return true on no error, false if there was a problem
		return true;
		
	}
	
	
	/**
	 * Checks the bike out of a station
	 * @return - true if no error, false if an error occurred
	 */
	public boolean checkOut() {
		//TODO: Check bike out of station
		
		/*
		 * Check onRide false, set to true
		 * Check no maintenance request, refuse to checkout if needsMaintenance true
		 * Delete bike from current station in stationToBikeMap
		 * return true on no error, false if there was a problem
		 */
		
		return true;
		
	}
	
	/**
	 * Sets whether or not the bike needs maintenance
	 * @param needsMaintenance - true for a maintenance request, false otherwise.
	 */
	public void setNeedsMaintenance(boolean needsMaintenance) {
		this.needsMaintenance = needsMaintenance;
		
	}
}
