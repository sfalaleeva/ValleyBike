import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;

//import com.sun.tools.javac.code.TypeMetadata.Entry;
import java.time.LocalDate;

public class ValleyBikeSim {

	/** Temporary admin email login bypass */
	private static final String ADMIN = "admin";

	/** Map of station ids to station objects. */
	public static TreeMap<Integer, Station> stationsMap;
	
	/** Map of user ids to user objects. */
	public static TreeMap<Integer, User> usersMap;
	
	/** Map of station ids to a list of bikes ids at that station. */
	public static HashMap<Integer, ArrayList<Integer>> stationToBikeMap;
	
	/** Map of bike ids to bike objects. */
	public static TreeMap<Integer, Bike> bikesMap;
	
	/** Map of issue id to maintenance requests. */
	public static Map<Integer, Issue> issueMap;
	
	/** Map of date to rides that have't been saved to files yet. */
	public static Map<String, ArrayList<Ride>> dailyRidesMap;
	
	/** Map of ride ids to rides that have not been ended. */
	public static Map<Integer, Ride> ongoingRides;
	
	public static GraphicUtil graphic = new GraphicUtil();
	
	/** The logged in user id. 
	 * 	-1 if no user. */
	public static int currentUserID;
	
	/** The number of bikes in the system that need maintenance. */
	public static int bikesNeedMaintenance;
	
	/* Fields for login process. */
	/** Map of user emails to user ids. */
	public static HashMap<String, Integer> userRecords;
	
	/** Private static instance of class. */
	private static ValleyBikeSim valleyBike = new ValleyBikeSim();
	
	/**FAKE USER use to check user menu stuff without registering, TODO: delete when useless/before A5 submit */
	private static User fakeUser = new User("Sarah", "Pong", new Address("123 silver st","Holyoke", "MA", "01040", "USA"), 
			LocalDate.now(), "4135555555", "sarah@gmail.com", "Pwd123");
	

	/** 
	 * Private ValleyBike constructor.
	 */
	private ValleyBikeSim() {
		usersMap = new TreeMap<>();
		stationsMap = new TreeMap<>();
		bikesMap = new TreeMap<>();
		stationToBikeMap = new HashMap<>();
		issueMap = new HashMap<>();
		dailyRidesMap = new HashMap<>();
		ongoingRides = new HashMap<>();
		currentUserID = -1;
		bikesNeedMaintenance = 0;
		userRecords = new HashMap<>();
	}

	/*
	 *
	 * ********* HELPER FUNCTIONS START HERE: ***********
	 *
	 */


	/**
	 * Create a list of Stations that still have docks available for parking. Used to assist the
	 * recordRide() function.
	 * @return - list of stations that have available docks.
	 */
	public static List<Station> availableStation() {
		List<Station> stationWithAvailableDocks = new ArrayList<>();

		for (Station station : stationsMap.values()) {
			if (station.getAvailableDocks() > 0) {
				stationWithAvailableDocks.add(station);
			}
		}

		return stationWithAvailableDocks;
	}


	/*
	 *
	 * ********* HELPER FUNCTIONS END HERE: ***********
	 *
	 */

	/**
	 * When the user prompts, print the list of stations.
	 */
	public static void printStationList() {
		
		System.out.println("\nID\tBikes\tAvDocs\tMainReq\t  Cap\tKiosk\tName - Address\n");
		for(Station station: stationsMap.values()) {
			System.out.println(station.getID() + "\t" + station.getBikes()
			+ "\t  " + station.getAvailableDocks() + "\t" +
			+ station.getMaintainenceReq() + "\t  " + station.getCapacity()
			+ "\t" + station.getHasKiosk() + "\t" + station.getName() 
			+ " - " + station.getAddress());
		}
		System.out.println("");

	}

	/**
	 * Create a station as prompted by the user.
	 */
	public static void addStation() {
		Station newStation = new Station(0, null, 0, 0, 0, 0, false, null,-1,-1);
		
		// Determines station id based on the current largest station id.
		int highestID = stationsMap.lastKey();
		newStation.setID(highestID + 1);
		
		System.out.println("\nYou are about to add a new station. Please specify the following details for the new station:\n");

		System.out.println("Station Name: ");
		newStation.setName(inputUtil.getString());

		System.out.println("Specify the capacity for the new station (range: 0-20):");			
		int inputCapacity = inputUtil.getIntInRange(5, 27, "capacity");
		newStation.setCapacity(inputCapacity);

		System.out.println("Enter the number of total bikes at this station (range: 0-" + newStation.getCapacity() + "): ");
		int bikesParsed = inputUtil.getIntInRange(0, newStation.getCapacity(), "number of bikes entered");
		
		// creates bike objects and updates Station and stationToBikeMap
		newStation.setBikes(bikesParsed);
		
		// Available docks are set by the setBike function.

		System.out.println("\nDoes the station have a kiosk?");
		Boolean hasKiosk = inputUtil.getBool();
		newStation.setHasKiosk(hasKiosk);
		
		System.out.println("Please enter the address for the new station:\n");
		newStation.setAddress(inputUtil.getString());
		
		System.out.println("Please enter the coordinates for the map. If you do not know, write -1.\n");
		int x = inputUtil.getIntInRange(-1, 600, "value");
		int y = inputUtil.getIntInRange(-1, 400, "value");
		newStation.setXY(x*2,y*2);
		

		// Assumes a new station doesn't require maintenance requests and hence setting them to 0.

		/*
		* Printing all the specifications of the station designed by the user:
		*/
		System.out.println("This new station was added to the list:\n" + "\nID: " + newStation.getID() + "\nName: " +
				newStation.getName() + "\nCapacity: " + newStation.getCapacity() +
				"\nNumber of Bikes: " + newStation.getBikes() + "\nNumber of Available Docks: " +
				newStation.getAvailableDocks() + "\nNumber of Maintenance Requests: " + newStation.getMaintainenceReq() + "\nHas a kiosk: " +
				newStation.getHasKiosk() + "\nAddress: " + newStation.getAddress() + "\n");

		/*
		 * Save this newly created station to the stations map.
		 */
		stationsMap.put(newStation.getID(), newStation);
		System.out.println("Station successfully added. Choose 'View station list' in menu to view the station.\n");
	}

	
	
	/**
	 * Given a bike to check out, this function checks out a bike and 
	 * starts a ride for the logged in user. After the ride is added, it is 
	 * added to the temporary file for temp-ride-data.csv for ongoing rides.
	 * @param bikeID
	 */
	public static void startRide() {
	
		User currentUser = usersMap.get(currentUserID);
		if (!currentUser.getIsActive()) {
			System.out.println("You must have active membership and valid credit card information to check out a bike.");
			return;
		}
		if (currentUser.getCurrentRideID() != -1) {
			System.out.println("It looks like you already have a bike checked out. Only one bike per person is allowed.");
			return;
		}

		Integer bikeID = inputUtil.getValidBikeIdAtStation();
		if (bikeID == -1) {
			return;
		}
		Bike bike = bikesMap.get(bikeID);
		
		currentUser.addToBalance(currentUser.getMembership().getPricePerRide()); //charge per ride according to membership
		Ride ride = new Ride(currentUserID, bikeID, bike.getStatID(), -1, new Date(), null);
		currentUser.setCurrentRide(ride.getID());
		ongoingRides.put(ride.getID(), ride);
		
		bike.checkOut(); // updates station to -1 and bike object
		System.out.println("Your ride has been started successfully!");
		
		CsvUtil.saveOngoingRides();
	}
	
	/**
	 * Function gets called when user wants to end their ride and provides the ID of the station,
	 * which they want to return their bike to. When the ride is completed, the csv file for the
	 * day that ride started is appended to.
	 * @param endStationID
	 */
	public static void endRide() {
		User currentUser = usersMap.get(currentUserID);
		if (currentUser.getCurrentRideID() == -1) {
			System.out.println("It looks like you are not currently on a ride");
		}
		int endStationID = inputUtil.getRideEndStationID();
		Ride currentRide = ongoingRides.get(currentUser.getCurrentRideID());
		Bike bike = bikesMap.get(currentRide.getBikeID());
		if (!bike.checkIn(endStationID)) { 
			return;
		};
		//update ride object now that it's complete, remove from ongoing rides
		currentRide.setEndTime(new Date());
		currentRide.setToStationID(endStationID);
		
		System.out.println("The end station: " + endStationID);
		
		ongoingRides.remove(currentRide.getID());
		
		addCurrentRideToMap(currentRide);
		
		//calculate the charge for the ride and charge the user if they've ridden longer than their membership allows for free
		float overtime = currentRide.getRideDuration() - currentUser.getMembership().getFreeRideDuration();
		if (overtime > 0 && overtime < 1440) {
			currentUser.addToBalance(Membership.overtimePrice * overtime);
		} else if (overtime >= 1440) { //1440 minutes = 24 hours 
			//system checks for bike being out over 24 hours both here when user ends ride and 
			//periodically with checkOver24Hours() in case it is never returned 
			currentUser.addToBalance(2000); //TODO: 2000 + overtime price or just 2000? 
			
		}
		currentUser.chargeUser();
		currentUser.addRideToHistory(currentRide);
		currentUser.setCurrentRide(-1);
		System.out.println("Your ride was ended successfully! We hope you ride again soon!");
		
		// save rides for that day
		String dateKey = inputUtil.dateToString(currentRide.getStartTime(), inputUtil.RIDE_DATE_FORMAT);
		CsvUtil.saveCompletedRides(dateKey);
	}
	
	/**
	 * Helper for adding a completed ride to the dailyRideMap.
	 * @param the ride to add
	 */
	public static void addCurrentRideToMap(Ride ride) {
		// gets date string without time
		String dateString = inputUtil.dateToString(ride.getStartTime(), inputUtil.RIDE_DATE_FORMAT);
		// if it exists, we append the new ride
		if (dailyRidesMap.containsKey(dateString)) {
			dailyRidesMap.get(dateString).add(ride);
		} else {
			ArrayList<Ride> rides = new ArrayList<>();
			rides.add(ride);
			dailyRidesMap.put(dateString, rides);
		}
	}
	
	/**
	 * Prints out a report on total duration, distance and number of rides the user has taken
	 */
	public static void viewUserReport() {
		System.out.println(usersMap.get(currentUserID).getUserReport());
	}

	/**
	 * This function manages to equally divide all the vehicles among all the stations to avoid some stations to be
	 * over-occupied and some stations to be under-occupied.
	 */
	public static void equalizeStations() {
		//sort stations by capacity to help with distributing left over bikes to stations with higher capacity instead of by ID
		List<Integer> stationIdsSortedByCapacity = stationsMap.values().stream().sorted(
				Comparator.comparingInt(Station::getCapacity).reversed()
				).map(s -> s.getID()).collect(Collectors.toList());
		
        int totalBikes = 0;
        int totalCapacity = 0;
        
        for (Station station : stationsMap.values()) {
			totalBikes += station.getBikes();
			totalCapacity += station.getCapacity();
		}
        
        // keeps track of bikes that are in transit between stations
        List<Bike> bikesInTransit = new ArrayList<>();
        // keeps track of already visited stations that still need bikes
        HashMap<Integer, Integer> stationsNeedBikesMap = new HashMap<>();
       
        // visits each station, updates number of bikes, and moves bikes between
        // station and bikes in transit or from bikes in transit to that station.
		for (Station station : stationsMap.values()) {
			ArrayList<Integer> bikeIDsAtStation = ValleyBikeSim.stationToBikeMap.get(station.getID());
		
			int capacity = station.getCapacity();
			// totalBikes/totalCapacity = percentage of the docks that should be filled at each station
			// multiply by each station's capacity to get an exact number of bikes that should be at that station
			int bikes = (int) Math.floor(capacity * totalBikes / totalCapacity);
			
			// update stored data connecting bikes to specific stations
			// if the station had too many bikes
			while (bikes < bikeIDsAtStation.size()) {
				// remove the first bike from the station and add to bikesInTransit
				bikesInTransit.add(station.removeOneBike());
			}
			// if the station needs bikes
			while (bikes > bikeIDsAtStation.size()) {
				if (!bikesInTransit.isEmpty()) {
					// bike transfered from in transit to new station
					station.addOneBike(bikesInTransit.remove(0));
				}
				else {
					// if there are currently no available bikes in transit, keep track
					// of the number of bikes this station still needs.
					int remainingBikes = bikes - bikeIDsAtStation.size();
					stationsNeedBikesMap.put(station.getID(),remainingBikes);
					break;
				}
			}
		}
		
		// place bikes at stations that still need bikes based on first visit
		for (int id: stationsNeedBikesMap.keySet()) {
			int bikesNeeded = stationsNeedBikesMap.get(id);
			while (bikesNeeded > 0 && !bikesInTransit.isEmpty()) {
					stationsMap.get(id).addOneBike(bikesInTransit.remove(0));					
					bikesNeeded--;
			}
		}
			
		// Deal with bikes left over after initial equalize process by adding to the largest stations.
		if (!bikesInTransit.isEmpty()) {
			Station station;
			for (Integer stationID: stationIdsSortedByCapacity) {
				station = stationsMap.get(stationID);
				if (!bikesInTransit.isEmpty()) {
					station.addOneBike(bikesInTransit.remove(0));
				}
			}
		}

		System.out.println("\n" + "Equalization completed! Choose 'View station list' in menu to view the station.\n");
	}
	
	/**
	 * Creates a new user and adds to system.
	 */
	private static void addUser() {
		
		User user = UserModifier.register();
		
		usersMap.put(user.getUserID(), user);
		
		// for use when logging in
		//TODO(): maintain when account is deleted or changed
		userRecords.put(user.getEmail(), user.getUserID());
		
		currentUserID = user.getUserID();
		System.out.println("Your account has been created and you have been logged in.");
		
		if (user.getIsActive()) {
			System.out.println("Your account is active - have fun on your first ride.");
		}
		else {
			System.out.println("You havn't selected a membership or payment method yet. "
					+ "\nUpdate your account to start riding.");
		}
	}
	/**
	 * Logs out a user that is currently logged in
	 */
	public static void logout() {
		currentUserID = -1; //no user logged in 
		System.out.println("");
		System.out.println("You have been logged out.");
	}
	
	/**
	 * Logs in a user based on correct email and password info
	 */
	public static void login() {
		//Needs to be case sensitive for pswd and not for email. 
		
		System.out.println("Please enter your email: ");
		String inputEmail = inputUtil.getString();
		
		// if the user email is not in the system, the user will
		// be given the option to register instead.
		if (userRecords.get(inputEmail) == null ) {
			System.out.println("There is no registered account with this email.");
			System.out.println("Would you like to register? Enter y/n");
			boolean wantToRegister = inputUtil.getBool();
			if (wantToRegister) {
				addUser();
			}
		}
		// if there is an entry for the given email and the id isn't 0
		// (admin) the system will prompt the user for a password.
		else {
			int userID = userRecords.get(inputEmail);
			if (userID != 0) { // id is 0 if the admin bypass was entered
				// other wise, a valid use is attempting to login.
				User user = usersMap.get(userID);
				System.out.println("Please enter your password: ");
				String inputPwd = inputUtil.getString();
				
				if (inputPwd.equals(user.getPwd())) {
					currentUserID = user.getUserID();
					System.out.println("\nYou have been logged in.");
				}
				else { // if invalid password
					System.out.println("Login unsuccessful." 
							+ "\nPlease try logging in again with a correct email and password.");
				}
			}
			// if the id was 0, log the admin in.
			else {
				currentUserID = 0;
			}
		}
	}
	
	/**
	 * Updates account details for either a user's own account or 
	 * for a user specified by the admin. This gives the options to 
	 * change a user's membership, payment info, personal info, or cancel membership.
	 */
	public static void updateAccount() {
		int userID = 0; // id is 0 when admin is logged in
		User userChange; 
		
		//check if admin is logged in
		while(userID == 0) {
			if (currentUserID == 0) {
				System.out.println("Please enter email of user whose account info you'd like to change: ");
				String userEmail = inputUtil.getString();
				if (userRecords.get(userEmail) == null ) {
					System.out.println("There is no registered account with this email.");
				} else {
					userID = userRecords.get(userEmail);
					System.out.println("For the user you selected--");
				}
				
			} else if (currentUserID > 0) {
				userID = currentUserID;
			}
		}
		
		userChange = usersMap.get(userID);
		
		//print user account details before menu options
		userChange.printInfo();
		
		System.out.println("\nPlease choose from one of the following menu options:\n"
				+ "0. Back to Main Menu\t3. Change personal info"
				+ "\n1. Change Membership\t4. Cancel Membership" 
				+"\n2. Change payment info\t5. Delete Account");
		System.out.println("Enter a number (0-5): \n");
		
		while(true) {
			String input = inputUtil.getString();
			
			switch (input) {
				case "0": 
					//exits to main account menu
					System.out.println("\nGoing back. "); 
					break;
				case "1":
					//check they have card info before allow to change membership
					if (userChange.hasValidCardInfo() == false) {
						System.out.print("\nYou need to provide valid card info before you can pick a membership."
								+ "\nNavigate to the 'Change payment info' option to do this.\n");
					} else {
						//TODO: check why changeMembership by itself allows user with no card info to to choose a membership? 
						UserModifier.changeMembership(userChange);	
					}
					break;
				case "2":
					//add payment info
					UserModifier.changePayment(userChange);
					break;
				case "3":
					//change personal info
					UserModifier.changePersonalInfo(userChange);
					break;
				case "4":
					//cancel membership
					userChange.cancelMembership();
					break;
				case "5":
					//delete acct
					deleteAcct(userChange);
					break;
				default: 
					System.out.print("Invalid input, please select a number within the 0-5 range.\n");
					continue;
						
			}
			break; //exit out of while loop
		}
	}
	
	/**
	 * Initializes our Valley Bike Simulator.
	 */
	public static void main(String[] args) {
		// add admin to map of emails to user ids.
		userRecords.put(ADMIN, 0);
		
		//TODO: delete these when fakeUser is no longer needed 
		usersMap.put(fakeUser.getUserID(), fakeUser);
		userRecords.put(fakeUser.getEmail(), fakeUser.getUserID());
		
		//TODO:() initialize an admin object

		System.out.println("Welcome to the ValleyBike Simulator.");
		currentUserID = -1; // no user is logged in to start
		CsvUtil.readStationData();
		
		if (CsvUtil.readBikeData().isEmpty()) {
			initializeBikes();
		}
		else {
			reinitializeBikes(CsvUtil.readBikeData());
		}
	
		int input;
		
		//TODO: for quit program options, create quit() method 
		//save all relevant info to CSVs when quit
		try {
			while(true) {	
				if (currentUserID > 0) {
					checkOver24Hours();
					printUserMenu();
					System.out.println("\nPlease enter a number (0-8): ");
					input = inputUtil.getIntInRange(0, 8, "menu option");
					switch (input) {
						case 0: 
							System.out.println("\nThank you for using Valley Bike Simulator!");
							break;
						case 1:
							printStationList();
							break;
						case 2:
							startRide();
							sendMapInfo();
							break;
						case 3:
							endRide();
							sendMapInfo();
							break;
						case 4:
							reportIssues();
							break;
						case 5: 
							updateAccount();
							break;
						case 6:
							viewUserReport();
							break;
						case 7:
							logout();
							break;
						case 8:
							displayMap();
							break;
						default: 
							System.out.print("\nInvalid input, please select a number within the 0-7 range.\n");
					}
				}
				// assume admin has default id 0
				else if (currentUserID == 0) {
					checkOver24Hours();
					printAdminMenu();
					System.out.println("\nPlease enter a number (0-8): ");
					input = inputUtil.getIntInRange(0,8, "menu option");
					switch (input) {
						case 0: 
							System.out.println("\nThank you for using Valley Bike Simulator!");
							break;
						case 1:
							printStationList();
							break;
						case 2:
							addStation();
							sendMapInfo();
							break;
						case 3:
							// also saves bike data for consistency
							CsvUtil.saveStationList();
							System.out.println("\nSuccessfully saved station and bike list!");
							break;
						case 4:
							System.out.println("\nEnter the file name (including extension) of the file located in data-files:");
							String rideFile = inputUtil.getString();
							CsvUtil.resolveRideData(rideFile);
							break;
						case 5: 
							equalizeStations();
							sendMapInfo();
							break;
						case 6:
							updateAccount();
							break;
						case 7:
							logout();
							break;
						case 8: 
							displayMap();
							break;
						default: 
							System.out.print("\nInvalid input, please select a number within the 0-7 range.\n");
					}
				}
				else {
					printMainMenu();
					System.out.println("\nPlease enter a number (0-3): ");
					input = inputUtil.getIntInRange(0,3, "menu option");
					switch (input) {
						case 0: 
							System.out.println("\nThank you for using Valley Bike Simulator!");
							System.exit(0);
							break;
						case 1:
							printStationList();
							break;
						case 2:
							login();
							break;
						case 3:
							addUser();
							break;
						default: 
							System.out.print("\nInvalid input, please select a number within the 0-3 range.\n");
					}
				}
			}
		} catch(Exception e){

		}
	}


	/**
	 * Prints the main menu for the Valley Bike Simulator to the console
	 * before any accounts are logged in.
	 */
	public static void printMainMenu() {
		System.out.println("\nPlease choose from one of the following menu options:\n"
				+ "0. Quit Program\t\t3. Register\n1. View station list\n2. Login");
				}
	
	/**
	 * Prints the user menu for the Valley Bike Simulator to the console.
	 */
	public static void printUserMenu() {
		System.out.println("\nPlease choose from one of the following menu options:\n"
				+ "0. Quit Program\t\t5. View Account Details and Update Account"
				+ "\n1. View station list\t6. View User Report"
				+ "\n2. Unlock Bike\t\t7. Log Out"
				+ "\n3. End Ride\t\t8. Show station map."
				+ "\n4. Report Issue"
				+ "\n5. Show station map");
	}
	
	/**
	 * Prints the admin menu for the Valley Bike Simulator to the console.
	 */
	public static void printAdminMenu() {
		System.out.println("\nPlease choose from one of the following menu options:\n"
				+ "0. Quit Program\t\t5. Equalize stations"
				+ "\n1. View station list\t6. Update Account"
				+ "\n2. Add station\t\t7. Log Out"
				+ "\n3. Save station and bike list"
				+ "\n4. Resolve ride data\t\t8. Show station map.");
	}
	
	/**
	 * Adds bikes from CSV to bikesMap, adds to stationToBikeMap, and updates the
	 * highestBikeID so that new bikes can be added.
	 * @param a list of bike from CsvUtil.readBikeData().
	 */
	private static void reinitializeBikes(ArrayList<Bike> bikes) {
		
		for (Bike bike: bikes) {
			// store newly reconstructed bike object
			bikesMap.put(bike.getID(), bike);
		
			// add new bike to stationToBikeMap if at a station
			if(bike.getStatID() >= 20) {
				if (!stationToBikeMap.containsKey(bike.getStatID()) ) {
					stationToBikeMap.put(bike.getStatID(), new ArrayList<>());
				}
				stationToBikeMap.get(bike.getStatID()).add(bike.getID());
			}
		}
		
		// ensures the next bike id is updated 
		Bike.setNextBikeID();
	}
	
	/**
	 * Creates new bikes objects based on station data if there is not existing
	 * bike data.
	 */
	private static void initializeBikes() {
 		for (Station station : stationsMap.values()) {
 			ArrayList<Integer> bikes = new ArrayList<>();

 			int numBikes = station.getBikes();
 			Bike bike;
 			// initialize all bikes at this station
 			while (numBikes > 0) {
 				bike = new Bike(station.getID());
 				bikes.add(bike.getID());
 				// add bike to map
				bikesMap.put(bike.getID(), bike);
 				numBikes--;
 			}
 			stationToBikeMap.put(station.getID(), bikes);
 		}
	}
	
	
	/**
	 * deletes account
	 * @param user - user account to be deleted
	 */
	private static void deleteAcct(User user) {
		usersMap.remove(user.getUserID());
		userRecords.remove(user.getEmail());
		System.out.println("Deleted");
		currentUserID = -1;
	}

	/**
	 * Enters the user dialog for reporting problems
	 */
	private static void reportIssues() {
		System.out.println("Please select an issue type:\n" + 
				"1. Station is empty\t4. Modify account details"
				+ "\n2. Station is full\t5. Other issue"
				+ "\n3. A bike is broken or needs maintenance");
		int menuItem = inputUtil.getIntInRange(1, 5, "number");
		Issue.TypeIssue typeissue = null;
		
		System.out.println("Please describe your issue.\n");
		String description = inputUtil.getString();
		
		switch(menuItem) {
			case 1:
				typeissue = Issue.TypeIssue.STATION_EMPTY;
				equalizeStations();
				System.out.println("Balancing stations, thank you for your report!\n");
				break;
			case 2:
				typeissue = Issue.TypeIssue.STATION_FULL;
				equalizeStations();
				System.out.println("Balancing stations, thank you for your report!\n");
				break;
			case 3:
				typeissue = Issue.TypeIssue.BIKE_MAINTENANCE;
				Issue newIssue = new Issue(currentUserID,description,typeissue);
				System.out.println("ID of the damaged bike? [0,"+ bikesMap.size() + "]\n");
				int bikeID = inputUtil.getIntInRange(1,bikesMap.size(),"id");
				Bike bike = bikesMap.get(bikeID);
				bike.setNeedsMaintenance(true);
				newIssue.setBikeID(bikeID);
				
				issueMap.put(newIssue.getIssueID(),newIssue);
				if(issueMap.size() > 10) {
					sendMaintenanceDriver();
				}
				System.out.println("Thank you for your report!");
				break;
			case 4:
				//TODO() Redundant with update account functionality?
				typeissue = Issue.TypeIssue.ACCOUNT;
				System.out.println("Select an option:\n1. Change Membership Type.\t2. Change payment details.\n");
				int input = inputUtil.getIntInRange(1,2, "selection");
				if(input == 1) {
					UserModifier.changeMembership(usersMap.get(currentUserID));
				}
				if(input == 2) {
					UserModifier.changePayment(usersMap.get(currentUserID));
				}
				break;
			case 5:
				typeissue = Issue.TypeIssue.OTHER;
				System.out.println("Your issue details are being forwarded to a Customer Service representative.\n Thank you for your report.");
				break;
		}	
		
	}
	/**
	 * Shows the map of the stations
	 */
	public static void displayMap() {
		sendMapInfo();
		JFrame frame = new JFrame("Valley Bike Map");
		frame.add(graphic);
		frame.setVisible(true);
		frame.setSize(600,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		graphic.repaint();
		
		
	}
	
	/**
	 * Sends updated information to the visual map and repaints it if it is running
	 */
	public static void sendMapInfo() {
		HashMap<Integer, int[]> inputMap = new HashMap<>();
		for(Map.Entry<Integer, Station> entry: stationsMap.entrySet()) {
			int ID = entry.getKey();
			Station station = entry.getValue();
			int bikenum = station.getBikes();
			int docknum = station.getAvailableDocks();
			int numArray[] = {bikenum,docknum};
			inputMap.put(ID, numArray);
			int[] xy = {station.getX(), station.getY()};
			GraphicUtil.setRawLocation(ID, xy);
			
		}
		GraphicUtil.setParams(inputMap);
		if(GraphicUtil.running) {
			graphic.repaint();
		}
	}
	
	/**
	 * check if within list of ongoing rides there's one that's been going 
	 * on for >=24 hours. If so, charge the user $2000
	 */
	public static void checkOver24Hours() {
		//TODO: notify user they've been charged?
		for (Map.Entry<Integer, Ride> rideEntry: ongoingRides.entrySet()) {
			Ride ride = rideEntry.getValue();
			
			if (ride.calculateOngoingRide() >= 1440) { //1440 min = 24 hrs
				User userToCharge = usersMap.get(ride.getUserID());
				userToCharge.addToBalance(2000);
			}
		}
	}
	
	/**
	 * Models the process of fixing all the bikes with issues 
	 * in the system.
	 */
	public static void sendMaintenanceDriver() {
		for(Bike bike: bikesMap.values()) {
			bike.setNeedsMaintenance(false);
			
		}
		issueMap.clear();
		bikesNeedMaintenance = 0;
	}
}
