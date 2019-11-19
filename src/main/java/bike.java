
public class bike {
	
	//ID of the current bike object
	private int ID;
	
	//ID of the last station it was at
	private int statID;
	
	//Whether or not the bike is out on a ride
	private boolean onRide;
	
	//Whether or not the bike needs maintenance
	private boolean needsMaintenance;
	
	//Next bike ID that will be assigned for the whole class: Always 1 more than current highest id
	static int nextBikeID;
	
	public bike() {
		//TODO: set up bike constructor
	}
	
	
	/*
	 * Accessor Methods
	 */

	public int getID() {
		return ID;
	}
	
	public int getStatID() {
		return statID;
	}
	
	public boolean getOnRide() {
		return onRide;
	}
	
	public boolean getMaintenance() {
		return needsMaintenance;
	}
	
	/*
	 * Functional methods
	 */
	
	public boolean checkIn(int statID) {
		//TODO: Check bike into station. 
		
		//Check onRide was true and set to false
		//Set StatID to the one given
		//add bike to stationToBikeMap for this station
		
		//return true on no error, false if there was a problem
		return true;
		
	}
	
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
	
	public void setNeedsMaintenance(boolean needsMaintenance) {
		this.needsMaintenance = needsMaintenance;
		
	}
}
