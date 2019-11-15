public class Station {
	private int ID;
	private String name;
	private int bikes;
	private int pedelecs;
	private int availableDocks;
	private int mReq;
	private int capacity;
	private boolean hasKiosk;
	private String address;

	public Station(int ID, String name, int bikes, int pedelecs, int aDocks, int mReq, int capacity,
			boolean hasKiosk, String address) {
		this.ID = ID;
		this.name = name;
		this.bikes = bikes;
		this.pedelecs = pedelecs;
		this.availableDocks = aDocks;
		this.mReq = mReq;
		this.capacity = capacity;
		this.hasKiosk = hasKiosk;
		this.address = address;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBikes(int bikes) {
		this.bikes = bikes;
		//TODO: update available docks
	}

	//TODO: write addBikes method, so we can add a number of bikes to whatever is already at the station
	//also will update available docks
	
	public void setPedelecs(int pedelecs) {
		this.pedelecs = pedelecs;
	}

	//TODO: make private, because we should not call this without updating bikes first
	public void setAvailableDocks(int availableDocks) {
		this.availableDocks = availableDocks;
	}

	public void setMaintenanceRequests(int mReq) {
		this.mReq = mReq;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setHasKiosk(boolean hasKiosk) {
		this.hasKiosk = hasKiosk;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public int getID() {
		return this.ID;
	}

	public String getName() {
		return this.name;
	}

	public int getBikes() {
		return this.bikes;
	}

	public int getPedelecs() {
		return this.pedelecs;
	}

	public int getAvailableDocks() {
		return this.availableDocks;
	}

	public int getMaintainenceReq() {
		return this.mReq;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public boolean getHasKiosk() {
		return this.hasKiosk;
	}

	public String getAddress() {
		return this.address;
	}


	public void printStation() {
		System.out.println(this.ID + "\t" + this.bikes + "\t" + this.pedelecs + "\t"
				+ this.availableDocks + "\t" + this.mReq + "\t" + this.capacity + "\t"
				+ this.hasKiosk + "\t" + this.name + " - " + this.address);
	}


}
