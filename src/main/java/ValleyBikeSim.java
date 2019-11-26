import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
//import com.sun.tools.javac.code.TypeMetadata.Entry;

public class ValleyBikeSim {

	/** Temporary admin email login bypass */
	private static final String ADMIN = "admin";

	/** Map of station ids to station objects. */
	public static TreeMap<Integer, Station> stationsMap;
	
	/** Map of user ids to user objects. */
	public static TreeMap<Integer, User> usersMap;
	
	/** Map of station ids to a list of bikes at that station. */
	public static HashMap<Integer, ArrayList<Bike>> stationToBikeMap;
	
	/** Map of bike ids to Bike objects */
	public static TreeMap<Integer, Bike> bikesMap;
	
	/** Map of issue id to maintenance requests. */
	public static Map<Integer, Issue> issueMap;
	
	/** Map of date to rides that have't been saved to files yet. */
	public static Map<Date, ArrayList<Ride>> dailyRidesMap;
	
	public static Map<Integer, Ride> ongoingRides;
	
	/** The logged in user id. 
	 * 	-1 if no user. */
	public static int currentUserID;
	
	/** The number of bikes in the system that need maintenance. */
	public static int bikesNeedMaintenance;

	public  static FileWriter csvWriter;
	public static CSVWriter writer;
	
	/* Fields for login process. */
	/** Map of user emails to user ids. */
	public static HashMap<String, Integer> userRecords;
	
	/** Private static instance of class. */
	private static ValleyBikeSim valleyBike = new ValleyBikeSim();
	
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


	/**
	 * Read in all the data files and store them in appropriate data structures. A TreeMap was used so that
	 * when the list of stations is printed to the screen all the IDs for the stations would be ordered.
	 */
	public static void readData() {
		try {
			String stationData = "data-files/station-data.csv";


			CSVReader stationDataReader = new CSVReader(new FileReader(stationData));


			List<Station> stationsList = new ArrayList<>();

			/* to read the CSV data row wise: */
			List<String[]> allStationEntries = stationDataReader.readAll();
			System.out.println("");
			int counter = 0;
			for(String[] array : allStationEntries) {
				if(counter == 0) {

				} else {
					// the pedelec category of data array[3] is saved in Station.bike 
					Station station = new Station(Integer.parseInt(array[0]), array[1], Integer.parseInt(array[3]), Integer.parseInt(array[4]),
							Integer.parseInt(array[5]), Integer.parseInt(array[6]), inputUtil.toBool(array[7]), array[8]);
					stationsMap.put(station.getID(), station);
				}
				counter++;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
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
		Station newStation = new Station(0, null, 0, 0, 0, 0, false, null);
		
		// Determines station id based on the current largest station id.
		int highestID = stationsMap.lastKey();
		newStation.setID(highestID + 1);
		
		System.out.println("\nYou are about to add a new station. Please specify the following details for the new station:\n");

		System.out.println("Station Name: ");
		newStation.setName(inputUtil.getString());

		System.out.println("Specify the capacity for the new station (range: 0-20):");			
		int inputCapacity = inputUtil.getIntInRange(0, 20, "capacity");
		newStation.setCapacity(inputCapacity);

		System.out.println("Enter the number of total bikes at this station (range: 0-" + newStation.getCapacity() + "): ");
		int bikesParsed = inputUtil.getIntInRange(0, newStation.getCapacity(), "number of bikes entered");
		newStation.setBikes(bikesParsed);
		
		// Available docks are set by the setBike function.

		System.out.println("\nDoes the station have a kiosk?");
		Boolean hasKiosk = inputUtil.getBool();
		newStation.setHasKiosk(hasKiosk);
		
		System.out.println("Lastly, please enter the address for the new station:\n");
		newStation.setAddress(inputUtil.getString());

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
	 * Save the updated station list to the CSV file, by overwriting all the entries and adding new entries for the new stations.
	 */
	public static void saveStationList() {

		try {
			  //overwrites existing file with new data
			  csvWriter = new FileWriter("data-files/station-data.csv");
			  writer = new CSVWriter(csvWriter);
		      String [] record = "ID,Name,Bikes,Pedelacs,Available Docks,Maintainence Request,Capacity,Kiosk,Address".split(",");

		      writer.writeNext(record);

		      writer.close();

		} catch (IOException e) {
			
			e.printStackTrace();
		}

		//loops through and saves all stations
		for (Station station : stationsMap.values()) {
			saveAll(station);
		}
	}

	/**
	 * Ancillary function to assist the saveStationList() function.
	 */
	private static void saveAll(Station station) {
		try {
			csvWriter = new FileWriter("data-files/station-data.csv",true);

			//adding all the station details into the csv
			csvWriter.append(Integer.toString(station.getID()));
		    csvWriter.append(',');
			csvWriter.append(station.getName());
			csvWriter.append(',');
			// there are no bikes in the ValleyBike system
			csvWriter.append(Integer.toString(0));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getBikes()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getAvailableDocks()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getMaintainenceReq()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getCapacity()));
			csvWriter.append(',');
			csvWriter.append(inputUtil.fromBool(station.getHasKiosk()));
			csvWriter.append(',');
			csvWriter.append(station.getAddress());


			csvWriter.append("\n");



			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
			}
	
	/**
	 * Given a bike to check out, this function checks out a bike and starts a ride for the logged in user
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
		bike.checkOut();
		
		currentUser.addToBalance(currentUser.getMembership().getPricePerRide()); //charge per ride according to membership
		Ride ride = new Ride(currentUserID, bikeID, bike.getStatID(), -1, new Date(), null);
		currentUser.setCurrentRide(ride.getID());
		ongoingRides.put(ride.getID(), ride);
		System.out.println("Your ride has been started successfully!");
	}
	
	/**
	 * Function gets called when user wants to end their ride and provides the ID of the station,
	 * which they want to return their bike to
	 * @param endStationID
	 */
	public static void endRide() {
		User currentUser = usersMap.get(currentUserID);
		if (currentUser.getCurrentRideID() == -1) {
			System.out.println("It looks like you are not currently on a ride");
		}
		Integer endStationID = inputUtil.getRideEndStationID();
		Ride currentRide = ongoingRides.get(currentUser.getCurrentRideID());
		Bike bike = bikesMap.get(currentRide.getBikeID());
		if (!bike.checkIn(endStationID)) {
			return;
		};
		//update ride object now that it's complete, remove from ongoing rides
		currentRide.setEndTime(new Date());
		currentRide.setToStationID(endStationID);
		ongoingRides.remove(currentRide.getID());
		
		//calculate the charge for the ride and charge the user if they've ridden longer than their membership allows for free
		float overtime = currentRide.getRideDuration() - currentUser.getMembership().getFreeRideDuration();
		if (overtime > 0) {
			currentUser.addToBalance(Membership.overtimePrice * overtime);
		}
		currentUser.chargeUser();
		currentUser.addRideToHistory(currentRide);
		currentUser.setCurrentRide(-1);
		System.out.println("Your ride was ended successfully! We hope you ride again soon!");
	}

	/**
	 * Manages to read the ride data, record all the rides in the ridesList and then calculates the average
	 * ride duration and displays the message informing the user how many rides there were and what the average
	 * duration was for the whole ride data.
	 * @param ridesFileName - the string specifying the name and extension of the ride data.
	 */
	public static void resolveRideData(String ridesFileName) {
		String rideData = "data-files/" + ridesFileName;

		try {
			CSVReader rideDataReader = new CSVReader(new FileReader(rideData));
			List<Ride> ridesList = new ArrayList<>();

			List<String[]> allRidesEntries = rideDataReader.readAll();
			System.out.println("");

			int counter = 0;
			for(String[] array : allRidesEntries) {
				if(counter == 0) {

				} else {
					//TODO() change what gets read/written in from ride.csv files
					// current the bike id of existing rides is hard coded to 0
					ridesList.add(new Ride(Integer.parseInt(array[0]), 0, Integer.parseInt(array[1]),
							Integer.parseInt(array[2]), inputUtil.toDate(array[3]), inputUtil.toDate(array[4])));
				}
				counter++;

			}
			int totalDuration = 0;
			for(Ride ride : ridesList) {
				totalDuration += ride.getRideDuration();
			}
			int averageDuration = totalDuration / ridesList.size();
			System.out.println("The ride list contains " + ridesList.size() + " rides and the average ride time is " + averageDuration + " minutes.\n");


		}

		catch (Exception e) {
			System.out.println("\n" + e.getMessage());
			System.out.println("Please enter a valid data file path.\n");
			//e.printStackTrace();
		}
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
		
		int bikesLeftUnassigned = totalBikes; //keeps track of how many bikes have yet to be distributed
		for (Station station : stationsMap.values()) {
			int bikes = station.getBikes();
			int capacity = station.getCapacity();
			// totalBikes/totalCapacity = percentage of the docks that should be filled at each station
			// multiply by each station's capacity to get an exact number of bikes that should be at that station
			bikes = (int) Math.floor(capacity * totalBikes / totalCapacity);
			station.setBikes(bikes);
			bikesLeftUnassigned -= bikes;
		}

		//what if after equalizing, we still have bikes left over

		// TODO: revisit using list of station IDs and then retrieving stations from the map instead of
		// just keeping a list of Station objects to begin with
		if (bikesLeftUnassigned != 0) {
			for (Integer stationID: stationIdsSortedByCapacity) {
				Station station = stationsMap.get(stationID);
				if (bikesLeftUnassigned > 0) {
					int bikes = station.getBikes() + 1; // delete
					station.setBikes(bikes); // change setBikes to addBikes
				}
				bikesLeftUnassigned--;
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
		
		//I added a getPwd() method to Account class, not sure if we want 
		//to keep forever for security reasons? Or if it should be in User class? 
		
		System.out.println("Please enter your email: ");
		String inputEmail = inputUtil.getString();
		
		// if the user email is not in the system, the user will
		// be given the option to register instead.
		if (userRecords.get(inputEmail) == null ) {
			System.out.println("There is no registered account with this email.");
			System.out.println("Would you like to register?");
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
	 * Initializes our Valley Bike Simulator.
	 */
	public static void main(String[] args) {
		// add admin to map of emails to user ids.
		userRecords.put(ADMIN, 0);
		
		//TODO:() initialize an admin object

		System.out.println("Welcome to the ValleyBike Simulator.");
		currentUserID = -1; // no user is logged in to start
		readData();
		initializeBikes();
		Scanner userInput = new Scanner(System.in);
		String input = "";
		
		//TODO: for quit program options, create quit() method 
		//save all relevant info to CSVs when quit
		try {
			while(true) {	
				if (currentUserID > 0) {
					printUserMenu();
					System.out.println("\nPlease enter a number (0-7): ");
					input = userInput.nextLine();
					switch (input) {
						case "0": 
							System.out.println("\nThank you for using Valley Bike Simulator!");
							break;
						case "1":
							printStationList();
							break;
						case "2":
							startRide();
							break;
						case "3":
							endRide();
							break;
						case "4":
							//TODO(): reportIssue();
							break;
						case "5": 
							//TODO(): updateAccount();
							// includes potential call to UserModifier.changeMembership();
							// includes potential call to UserModifier.changePayment();
							break;
						case "6":
							//TODO(): viewUserReport();
							break;
						case "7":
							logout();
							break;
						default: 
							System.out.print("\nInvalid input, please select a number within the 0-7 range.\n");
					}
				}
				// assume admin has default id 0
				else if (currentUserID == 0) {
					printAdminMenu();
					System.out.println("\nPlease enter a number (0-8): ");
					input = userInput.nextLine();
					switch (input) {
						case "0": 
							System.out.println("\nThank you for using Valley Bike Simulator!");
							break;
						case "1":
							printStationList();
							break;
						case "2":
							addStation();
							break;
						case "3":
							saveStationList();
							System.out.println("\nSuccessfylly saved station list!");
							break;
						case "4":
							System.out.println("\nEnter the file name (including extension) of the file located in data-files:");
							String rideFile = userInput.nextLine();
							resolveRideData(rideFile);
							break;
						case "5": 
							equalizeStations();
							break;
						case "6":
							//TODO(): updateAccount();
							break;
						case "7":
							resolveIssues();
							break;
						case "8":
							logout();
							break;
						default: 
							System.out.print("\nInvalid input, please select a number within the 0-7 range.\n");
					}
				}
				else {
					printMainMenu();
					System.out.println("\nPlease enter a number (0-3): ");
					input = userInput.nextLine();
					switch (input) {
						case "0": 
							System.out.println("\nThank you for using Valley Bike Simulator!");
							System.exit(0);
							break;
						case "1":
							printStationList();
							break;
						case "2":
							login();
							break;
						case "3":
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
				+ "0. Quit Program.\n1. View station list.\n2. Login.\n3. Register.");
				}
	
	/**
	 * Prints the user menu for the Valley Bike Simulator to the console.
	 */
	public static void printUserMenu() {
		System.out.println("\nPlease choose from one of the following menu options:\n"
				+ "0. Quit Program.\n1. View station list.\n2. Unlock Bike.\n3. End Ride.\n"
				+ "4. Report Issue.\n5. Update Account.\n6. View User Report.\n7. Log Out.");
	}
	
	/**
	 * Prints the admin menu for the Valley Bike Simulator to the console.
	 */
	public static void printAdminMenu() {
		System.out.println("\nPlease choose from one of the following menu options:\n"
				+ "0. Quit Program.\n1. View station list.\n2. Add station.\n3. Save station list.\n"
				+ "4. Resolve ride data.\n5. Equalize stations.\n6. Update Account.\n7. Resolve Issues.\n8. Log Out.");
	}
	
	/**
	 * Initializes all the bikes at the stations.
	 */
	private static void initializeBikes() {
		
		for (Station station : stationsMap.values()) {
			int numBikes = station.getBikes();
			ArrayList<Bike> bikes = new ArrayList<>();
			// initialize all bikes at this station
			while (numBikes > 0) {
				Bike bike = new Bike(station.getID());
				bikes.add(bike);
				bikesMap.put(bike.getID(), bike);
				numBikes--;
			}
			stationToBikeMap.put(station.getID(), bikes);
		}

	}
	
	
	private static void resolveIssues() {
		System.out.println("Please select an issue type:\n" + 
				"1. Station is empty.\n2. Station is full.\n3. A bike is broken or needs maintenance.\n" + 
				"4. Modify account details.\n5. Other issue.");
		int menuItem = inputUtil.getIntInRange(1, 5, "number");
		Issue.TypeIssue typeissue = null;
		switch(menuItem) {
			case(1):
				typeissue = Issue.TypeIssue.STATION_EMPTY;
				break;
			case(2):
				typeissue = Issue.TypeIssue.STATION_FULL;
				break;
			case(3):
				typeissue = Issue.TypeIssue.BIKE_MAINTENANCE;
				break;
			case(4):
				typeissue = Issue.TypeIssue.ACCOUNT;
				break;
			case(5):
				typeissue = Issue.TypeIssue.OTHER;
				break;
				
		}
		
		System.out.println("Please describe your issue.\n");
		String description = inputUtil.getString();
		switch(typeissue) {
			case STATION_EMPTY:
				equalizeStations();
				System.out.println("Balancing stations, thank you for your report!\n");
				break;
			case STATION_FULL:
				equalizeStations();
				System.out.println("Balancing stations, thank you for your report!\n");
				break;
			case BIKE_MAINTENANCE:
				Issue newIssue = new Issue(currentUserID,description,typeissue);
				System.out.println("ID of the damaged bike? \n");
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
			case ACCOUNT:
				System.out.println("Select an option:\n1. Change Membership Type.\n2. Change payment details.\n");
				int input = inputUtil.getIntInRange(1,2, "selection");
				if(input == 1) {
					UserModifier.changeMembership(usersMap.get(currentUserID));
				}
				if(input == 2) {
					UserModifier.changePayment(usersMap.get(currentUserID));
				}
				break;
			case OTHER:
				System.out.println("Your issue details are being forwarded to a Customer Service representative.\n Thank you for your report.");
				break;
		}
		
		
	}
	
	public static void sendMaintenanceDriver() {
		for(Bike bike: bikesMap.values()) {
			bike.setNeedsMaintenance(false);
			
		}
		issueMap.clear();
	}


}
