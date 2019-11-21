import java.util.ArrayList;

public class Station {
	
	/**
	 * Static across all stations, the next ID to be assigned to a new station
	 */
	static int nextStationID;
	
	
	/**
	 * Unique integer ID of this station
	 */
	private int ID;
	
	
	/**
	 * Station name
	 */
	private String name;
	
	
	/**
	 * Number of bikes at the station
	 */
	private int bikes;
	
	
	/**
	 * Number of docks the station has
	 */
	private int availableDocks;
	
	
	/**
	 * Number of maintenance requests at this station
	 */
	private int mReq;
	
	
	/**
	 * Total capacity of the station
	 */
	private int capacity;
	
	
	/**
	 * Whether or not the station has a kiosk
	 */
	private boolean hasKiosk;
	
	
	/**
	 * Address of the station
	 */
	private String address;

	/**
	 * Creates a station
	 * @param ID - Station ID
	 * @param name - Station name
	 * @param bikes - # bikes
	 * @param aDocks - available docks
	 * @param mReq - # maintenance requests
	 * @param capacity - Total capacity of the station
	 * @param hasKiosk - Whether or not it has a kiosk
	 * @param address - Address of the station
	 */
	public Station(int ID, String name, int bikes, int aDocks, int mReq, int capacity,
			boolean hasKiosk, String address) {
		this.ID = ID;
		this.name = name;
		this.bikes = bikes;
		this.availableDocks = aDocks;
		this.mReq = mReq;
		this.capacity = capacity;
		this.hasKiosk = hasKiosk;
		this.address = address;
	}

	/**
	 * Sets the station ID
	 * @param ID - Unique station ID
	 */
	public void setID(int ID) {
		this.ID = ID;
	}

	
	/**
	 * Sets the station name
	 * @param name - station name
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Sets the number of bikes at the station
	 * @param bikes - # bikes at the station
	 */
	public void setBikes(int bikes) {
		this.bikes = bikes;
		//TODO: update available docks
	}

	
	/**
	 * Adds the number of bikes to the total station tally
	 * @param bikesAdded - list of bike objects added
	 * @return true if operation completed successfully, false otherwise.
	 */
	public boolean addBikes(ArrayList<Bike> bikesAdded) {
		//Checks if enough docks are available, returns false if not
		if(availableDocks <= bikesAdded.size()) {
			return false;
		}
		
		//Adds each bike object to the station map list
		ArrayList<Bike> currentBikesList = ValleyBikeSim.stationToBikeMap.get(ID);
		for(Bike b: bikesAdded) {
			currentBikesList.add(b);
			bikes ++;
		}
		if(bikes != currentBikesList.size()) {
			System.out.println("Something went wrong. Bike number mismatch in station " + ID);
			return false;
		}
		//updates available docks and the station map
		setAvailableDocks(availableDocks - bikesAdded.size());
		ValleyBikeSim.stationToBikeMap.put(ID, currentBikesList);
		
		return true;
	}
	
	/**
	 * Adds one single bike to a station
	 * @param bike - the bike object added
	 * @return true: operation completed successfully, false if there was a problem
	 */
	public boolean addOneBike(Bike b) {
		if(availableDocks < 1) {
			return false;
		}
		
		ArrayList<Bike> currentBikes = ValleyBikeSim.stationToBikeMap.get(ID);
		currentBikes.add(b);
		ValleyBikeSim.stationToBikeMap.put(ID, currentBikes);
		bikes ++;
		availableDocks --;
		return true;
	}

	/**
	 * Removes a certain number of bikes from a station and returns them as a list
	 * @param numBikes - number of bikes to remove
	 * @return - list of bikes removed from the station
	 */
	public ArrayList<Bike> removeBikes(int numBikes) {
		if(bikes < numBikes) {
			return null;
		}
		ArrayList<Bike> removed = new ArrayList<>();
		ArrayList<Bike> current = ValleyBikeSim.stationToBikeMap.get(ID);
		for(int i = 0; i < numBikes; i ++) {
			removed.add(current.remove(0));
		}
		ValleyBikeSim.stationToBikeMap.put(ID, current);
		bikes = current.size();
		return removed;
	}
	
	
	public boolean removeOneBike(Bike b) {
		ArrayList<Bike> currentBikes = ValleyBikeSim.stationToBikeMap.get(ID);
		boolean checkExecution = currentBikes.remove(b);
		
		bikes = currentBikes.size();
		ValleyBikeSim.stationToBikeMap.put(ID, currentBikes);
		
		return checkExecution;
	}
	
	/**
	 * Sets the number of docks available at the station
	 * @param availableDocks - number of docks available
	 */
	private void setAvailableDocks(int availableDocks) {
		this.availableDocks = availableDocks;
	}

	
	/**
	 * Sets number of maintenance requests currently at the station
	 * @param mReq - Number of maintenance requests
	 */
	public void setMaintenanceRequests(int mReq) {
		this.mReq = mReq;
	}

	
	/**
	 * Sets the station capacity
	 * @param capacity - new station capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	
	/**
	 * Set whether or not the station has a kiosk
	 * @param hasKiosk - whether or not a kiosk exists
	 */
	public void setHasKiosk(boolean hasKiosk) {
		this.hasKiosk = hasKiosk;
	}

	
	/**
	 * Sets the station address
	 * @param address - String address of the physical station
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * Get the station id
	 * @return - station id
	 */
	public int getID() {
		return this.ID;
	}

	
	/**
	 * Get the station name
	 * @return - station name
	 */
	public String getName() {
		return this.name;
	}

	
	/**
	 * Get the number of bikes at the station currently
	 * @return - number of bikes
	 */
	public int getBikes() {
		return this.bikes;
	}


	/**
	 * Get the number of available docks at the station
	 * @return - number of docks available
	 */
	public int getAvailableDocks() {
		return this.availableDocks;
	}

	
	/**
	 * Get number of maintenance requests at the station
	 * @return - 
	 */
	public int getMaintainenceReq() {
		return this.mReq;
	}

	
	/**
	 * Gets the station capacity
	 * @return - Total station capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}

	
	/**
	 * Get whether the station has a kiosk or not
	 * @return - whether or not the station has a kiosk
	 */
	public boolean getHasKiosk() {
		return this.hasKiosk;
	}

	
	/**
	 * Gets the station address
	 * @return - station address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Prints out the station data to the console
	 */
	public void printStation() {
		System.out.println(this.ID + "\t" + this.bikes + "\t" 
				+ this.availableDocks + "\t" + this.mReq + "\t" + this.capacity + "\t"
				+ this.hasKiosk + "\t" + this.name + " - " + this.address);
	}


}
