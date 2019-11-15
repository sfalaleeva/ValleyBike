import java.util.*;
import java.util.concurrent.TimeUnit;
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
import com.sun.tools.javac.code.TypeMetadata.Entry;

public class ValleyBikeSim {

	/**
	 * Fields related to stations.
	 */
	public static List<Station> stationsList;
	public static TreeMap<Integer, Station> stationsMap;
	public static List<String[]> allStationEntries;

	public static List<Station> stationWithAvailableDocks;
	public static List<Integer> stationId;


	public  static FileWriter csvWriter;
	public static CSVWriter writer;


	/**
	 * Fields related to rides.
	 */
	public static List<Ride> ridesList;
	public static List<String[]> allRidesEntries;

	/**
	 * the new station instance that gets instantiated every time the addStation() function is run
	 * and the list of new Stations that needs to be instantiated and saved into the CSV file every
	 * time the saveStationList() function is called.
	 */
	public static Station newStation;



	/**
	 * Read in all the data files and store them in appropriate data structures. A TreeMap was used so that
	 * when the list of stations is printed to the screen all the IDs for the stations would be ordered.
	 */
	public static void readData() {
		try {
			String stationData = "data-files/station-data.csv";


			CSVReader stationDataReader = new CSVReader(new FileReader(stationData));


			stationsList = new ArrayList<>();
			stationsMap = new TreeMap<>();

			/* to read the CSV data row wise: */
			allStationEntries = stationDataReader.readAll();
			System.out.println("");
			int counter = 0;
			for(String[] array : allStationEntries) {
				if(counter == 0) {

				} else {
					stationsList.add(new Station((Integer.parseInt(array[0])), array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4]),
						Integer.parseInt(array[5]), Integer.parseInt(array[6]), toBool(array[7]), array[8]));
				}
				counter++;
			}

			for(Station station : stationsList) {
				stationsMap.put(station.getID(), station);
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
	 * Interpret the boolean given as argument and return a number.
	 * @param b - a boolean argument to be interpreted as a number
	 * @return a number: 0 if boolean is false or 1 if boolean is true
	 */
	public static int boolToInt(boolean b) {
		if(b) {
			return 1;
		} else {
			return 0;
		}
	}


	/**
	 * Convert a string to Date java object.
	 * @throws ParseException
	 */
	private static Date toDate(String s) throws ParseException {
		Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
		return dateTime;
	}


	/**
	 * Helper function to pass the String values of "0" and "1" as arguments
	 * and return boolean values of true and false respectively.
	 */
	private static boolean toBool(String s) {
		if(s.equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Helper function to pass a boolean and change it to a string of "0" if false
	 * and "1" if true respectively so that it can be used to make a new station into a corresponding
	 * row to be saved to the CSV. used the helper function in saveStationList() method.
	 * @param b - boolean
	 * @return - corresponding string of either "0" or "1".
	 */
	public static String fromBool(Boolean b) {
		if(b == true) {
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * Create a list of all Station IDs. Used to assist the recordRide() function.
	 * @return - a list of all Station IDs.
	 */
	public static List<Integer> stationID() {
		stationId = new ArrayList<>();

		for (Station station : stationsList) {
			stationId.add(station.getID());
		}

		return stationId;
	}

	/**
	 * Create a list of Stations that still have docks available for parking. Used to assist the
	 * recordRide() function.
	 * @return - list of stations that have available docks.
	 */
	public static List<Station> availableStation() {
		stationWithAvailableDocks = new ArrayList<>();

		for (Station station : stationsList) {
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
	 * Prints the main menu for the Valley Bike Simulator to the console.
	 */
	public static void printMainMenu() {
		System.out.println("Please choose from one of the following menu options:\n"
				+ "0. Quit Program.\n1. View station list.\n2. Add station.\n3. Save station list.\n"
				+ "4. Record ride.\n5. Resolve ride data.\n6. Equalize stations.");
	}

	/**
	 * When the user prompts, print the list of stations.
	 */
	public static void printStationList() {
		System.out.println("\nID\tBikes\tPedelecs AvDocs\tMainReq\t  Cap\tKiosk\tName - Address\n");
		for(Map.Entry<Integer, Station> entry: stationsMap.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue().getBikes() + "\t" + entry.getValue().getPedelecs()
			+ "\t  " + entry.getValue().getAvailableDocks() + "\t" + entry.getValue().getMaintainenceReq() + "\t  " + entry.getValue().getCapacity()
			+ "\t" + entry.getValue().getHasKiosk() + "\t" + entry.getValue().getName() + " - " + entry.getValue().getAddress());
		}
		System.out.println("");

	}

	/**
	 * Create a station as prompted by the user.
	 */
	public static void addStation() {
		newStation = new Station(0, null, 0, 0, 0, 0, 0, false, null);
		System.out.println("\nYou are about to add a new station. Please specify the following details for the new station:\n");
		while(true) {
			System.out.println("Station ID (00-99):");
			Scanner userInput = new Scanner(System.in);
			String inputID = userInput.nextLine();
			try {
				int parsedID = Integer.parseInt(inputID);
				if(!stationsMap.containsKey(parsedID)) {
					if(parsedID < 00 | parsedID > 99) {
						System.out.println("\nInvalid input ID: out of range (00-99). Please start over.");
						continue;
					} else {
						newStation.setID(parsedID);
					}
				}
				else {
					System.out.println("Invalid ID number: A station with that ID already exists. Please enter a valid ID.");
					continue;
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
				continue;
			}

			System.out.println("Station Name: ");
			String inputName = userInput.nextLine();
			newStation.setName(inputName);




			System.out.println("Specify the capacity for the new station (range: 0-20):");
			String inputCapacity = userInput.nextLine();
			try {
				int capacityParsed = Integer.parseInt(inputCapacity);
				if(capacityParsed > 20 | capacityParsed < 0) {
					System.out.println("Invalid capacity specified. Please start over.");
					continue;
				} else {
					newStation.setCapacity(capacityParsed);
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
				continue;
			}




			System.out.println("Enter the number of total bikes at this station (range: 0-" + newStation.getCapacity() + "): ");
			String inputBikes = userInput.nextLine();
			try {
				int bikesParsed = Integer.parseInt(inputBikes);
				if(bikesParsed > newStation.getCapacity()) {
					System.out.println("The number of bikes specified exceeds the capacity of the station. Please start over.\n");
					continue;
				} else if(bikesParsed < 0) {
					System.out.println("Invalid number of bikes entered. Please start over.\n");
					continue;
				} else {
					newStation.setBikes(bikesParsed);
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
				continue;
			}


			System.out.println("Enter the number of pedelecs (range: 0-" + (newStation.getCapacity() - newStation.getBikes()) +"): ");
			String inputPedelecs = userInput.nextLine();
			try {
				int pedelecsParsed = Integer.parseInt(inputPedelecs);
				if(pedelecsParsed > newStation.getCapacity() - newStation.getBikes()) {
					System.out.println("The number of pedelecs specified exceeds the available docks. Please start over.\n");
					continue;
				} else if(pedelecsParsed < 0) {
					System.out.println("Invalid number of pedelecs entered. Please start over.\n");
					continue;
				} else {
					newStation.setPedelecs(pedelecsParsed);
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
				continue;
			}

			newStation.setAvailableDocks(newStation.getCapacity() - (newStation.getBikes() + newStation.getPedelecs()));



			System.out.println("\nDoes the station have a kiosk?");
			String inputKiosk = userInput.nextLine();
			if(inputKiosk.equals("Y") || inputKiosk.equals("yes") || inputKiosk.equals("Yes") || inputKiosk.equals("YES")) {
				newStation.setHasKiosk(true);
			} else if(inputKiosk.equals("N") || inputKiosk.equals("no") || inputKiosk.equals("No") || inputKiosk.equals("NO")) {
				newStation.setHasKiosk(false);
			} else {
				System.out.println("\nInvalid input. Please start over.");
				continue;
			}


			System.out.println("Lastly, please enter the address for the new station:\n");
			String inputAddressString = userInput.nextLine();
			newStation.setAddress(inputAddressString);


			/*
			 * Assuming a new station doesn't require maintenance requests and hence setting them to 0.
			 */


			/*
			 * Printing all the specifications of the station designed by the user:
			 */
			System.out.println("This new station was added to the list:\n" + "\nID: " + newStation.getID() + "\nName: " +
			newStation.getName() + "\nCapacity: " + newStation.getCapacity() +
				"\nNumber of Bikes: " + newStation.getBikes() + "\nNumber of Pedelecs: " + newStation.getPedelecs() + "\nNumber of Available Docks: " +
			newStation.getAvailableDocks() + "\nNumber of Maintenance Requests: " + newStation.getMaintainenceReq() + "\nHas a kiosk: " +
				newStation.getHasKiosk() + "\nAddress: " + newStation.getAddress() + "\n");


			/*
			 * Save this newly created station to the stations list.
			 */
			stationsList.add(newStation);
			stationsMap.put(newStation.getID(), newStation);
			System.out.println("Station successfully added. Choose 'View station list' in menu to view the station.\n");

			break;
		}
		return;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//loops through and saves all stations
		for (Station station : stationsList) {
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
			csvWriter.append(Integer.toString(station.getBikes()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getPedelecs()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getAvailableDocks()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getMaintainenceReq()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getCapacity()));
			csvWriter.append(',');
			csvWriter.append(fromBool(station.getHasKiosk()));
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
	 * When the user tries to record a new ride, do the following:
	 * 1. Make sure that the ride is valid by accounting all exceptions.
	 * 2. Ask the user which station did they take the ride from, what vehicle they used, and which station was their end destination.
	 * 3. Update the values of the stations in stationsList after these rides were recorded.
	 * (Note: this is different from saving the station list, which manages to write to the CSV file itself.)
	 */
	public static void recordRide() {
		Scanner input = new Scanner(System.in);
		boolean error = true;
		int start = 0;
		Station startStation = new Station(0, null, 0, 0, 0, 0, 0, false, null);
		String transportation = null;
		int end = 0;
		Station endStation = new Station(0, null, 0, 0, 0, 0, 0, false, null);
		stationId = stationID();

		//what's the start station
		while (error) {
			System.out.println("Which station did you start from (station ID)? ");
			try {
				start = Integer.parseInt(input.nextLine());
				error = false;
			}
			catch (NumberFormatException nfe) {
				System.out.println("Please enter a valid integer ID.");
			}

		}

		while (! stationId.contains(start)) {
			try {
				System.out.println("Please enter an existing station ID: ");
				start = Integer.parseInt(input.nextLine());
			}
			catch (NumberFormatException nfe) {
				System.out.println(nfe.getMessage());
			}
		}

		startStation = stationsMap.get(start);

		//what's the transportation
		error = true;
		while (error) {
			System.out.println("Which transportation did you use (bike or pedelec)? ");
			transportation = input.nextLine();

			if (transportation.toLowerCase().equals("bike")) {
				if (startStation.getBikes() <= 0) {
					System.out.println("There's no bike at the start station. Please start over and enter the correct start station ID and the transportation.");
					while (error) {
						System.out.println("Which station did you start from (station ID)? ");
						try {
							start = Integer.parseInt(input.nextLine());
							error = false;
						}
						catch (NumberFormatException nfe) {
							System.out.println("Please enter a valid integer ID.");
						}

					}

					while (! stationId.contains(start)) {
						try {
							System.out.println("Please enter an existing station ID: ");
							start = Integer.parseInt(input.nextLine());
						}
						catch (NumberFormatException nfe) {
							System.out.println(nfe.getMessage());
						}
					}

					startStation = stationsMap.get(start);
					error = true;
					continue;
				}
				startStation.setBikes(startStation.getBikes()-1);
				startStation.setAvailableDocks(startStation.getAvailableDocks()+1);
				error = false;
			}

			else if (transportation.toLowerCase().equals("pedelec")) {
				if (startStation.getPedelecs() <= 0) {
					System.out.println("There's no pedelec at the start station. Please start over and enter the correct start station ID and the transportation you used.");
					while (error) {
						System.out.println("Which station did you start from (station ID)? ");
						try {
							start = Integer.parseInt(input.nextLine());
							error = false;
						}
						catch (NumberFormatException nfe) {
							System.out.println("Please enter a valid integer ID.");
						}

					}

					while (! stationId.contains(start)) {
						try {
							System.out.println("Please enter an existing station ID: ");
							start = Integer.parseInt(input.nextLine());
						}
						catch (NumberFormatException nfe) {
							System.out.println(nfe.getMessage());
						}
					}

					startStation = stationsMap.get(start);
					error = true;
					continue;
				}
				startStation.setPedelecs(startStation.getPedelecs() - 1);
				startStation.setAvailableDocks(startStation.getAvailableDocks()+1);
				error = false;

			}

			else {
				System.out.println("Please enter either 'bike' or 'pedelec'.");
			}
		}

		//what's the destination
		error = true;
		while (error) {
			System.out.println("Where's your destination (station ID)? ");
			try {
				end = Integer.parseInt(input.nextLine());
				error = false;
			}
			catch (NumberFormatException nfe) {
				System.out.println("Please enter a valid integer ID.");
			}

		}

		while (! stationId.contains(end)) {
			try {
				System.out.println("Please enter an existing station ID: ");
				end = Integer.parseInt(input.nextLine());
			}
			catch (NumberFormatException nfe) {
				System.out.println(nfe.getMessage());
			}
		}

		endStation = stationsMap.get(end);

		while (endStation.getAvailableDocks() <= 0) {
			System.out.println("Sorry, there's no available dock for you to return.");
			System.out.println("\n" + "Here's a list of stations that have available docks: ");
			System.out.println("ID" + "\t" + "Bikes" + "\t" + "Pedelecs" + "\t" + "AvDocs"
	    			+ "\t" + "MainReq" + "\t" + "Cap" + "\t" + "Kiosk" + "\t" + "Name - Address");

			stationWithAvailableDocks = availableStation();
			for (Station station : stationWithAvailableDocks) {
				station.printStation();
			}

			error = true;
			while (error) {
				System.out.println("Please choose an available station to return (station ID): ");
				try {
					end = Integer.parseInt(input.nextLine());
					error = false;
				}
				catch (NumberFormatException nfe) {
					System.out.println("Please enter a valid integer ID.");
				}
			}

			while (!stationId.contains(end)) {
				try {
					System.out.println("Please enter an existing station ID: ");
					end = Integer.parseInt(input.nextLine());
				}
				catch (NumberFormatException nfe) {
					System.out.println(nfe.getMessage());
				}
			}

			endStation = stationsMap.get(end);
		}

		System.out.println("You've recorded your ride successfully!\n");
		if (transportation.toLowerCase().equals("bike")) {
			endStation.setBikes(endStation.getBikes()+1);
		} else {
			endStation.setPedelecs(endStation.getPedelecs()+1);
		}
		endStation.setAvailableDocks(endStation.getAvailableDocks()-1);

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
			ridesList = new ArrayList<>();

			allRidesEntries = rideDataReader.readAll();
			System.out.println("");

			int counter = 0;
			for(String[] array : allRidesEntries) {
				if(counter == 0) {

				} else {
					ridesList.add(new Ride(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
							Integer.parseInt(array[2]), toDate(array[3]), toDate(array[4])));
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

		//TODO: get rid of the bikes all together
		// compress all of the 3 for-loops to 1

		//TODO: change all stationList references to stationsMap

		//TODO: sort stations by capacity and store them in a list at the beginning of the FUNCTION
		// this will help with distributing left over bikes to stations with higher capacity instead of by ID
		// potentially only have station ids and reference them by ID in the TreeMap
        int totalBikes = 0;
		for (Station station : stationsList) {
			totalBikes += station.getBikes();
		}

        int totalPedelecs = 0;
		for (Station station : stationsList) {
			totalPedelecs += station.getPedelecs();
		}

        int totalCapacity = 0;
		for (Station station : stationsList) {
			totalCapacity += station.getCapacity();
		}

		for (Station station : stationsList) {
			int bikes = station.getBikes();
			int capacity = station.getCapacity();
			bikes = Math.round(capacity * totalBikes / totalCapacity);
			//there should be a comment that describes what we are doing here in plain English
			station.setBikes(bikes);
		}

		for (Station station : stationsList) {
			int pedelecs = station.getPedelecs();
			int capacity = station.getCapacity();
			// use Math.floor function instead of round, so we don't assign more bikes than we have
			pedelecs = Math.round(capacity * totalPedelecs / totalCapacity);
			station.setPedelecs(pedelecs);
		}

		//what if after equalizing, the number of bikes isn't the same
		int nowTotalBikes = 0;
		for (Station station : stationsList) {
			nowTotalBikes += station.getBikes();
		}

		if (nowTotalBikes != totalBikes) {
			int difference = totalBikes - nowTotalBikes; //can calculate nowTotalBikesLeftOver as we reassign them
			for (int i = 0; i < difference; i++) {
				int bikes = stationsList.get(i).getBikes() + 1; //delete
				stationsList.get(i).setBikes(bikes);  //change setBikes to addBikes
			}
		}

		//what if the number of pedelecs isn't the same
		int nowTotalPedelecs = 0;
		for (Station station : stationsList) {
			nowTotalPedelecs += station.getPedelecs();
		}

		if (nowTotalPedelecs != totalPedelecs) {
			int difference = totalPedelecs - nowTotalPedelecs;
			for (int i = 0; i < difference; i++) {
				int pedelecs = stationsList.get(i).getPedelecs() + 1;
				stationsList.get(i).setPedelecs(pedelecs);
			}
		}

		//TODO: delete
		for (Station station : stationsList) {
			int aDocs = station.getAvailableDocks();
			aDocs = station.getCapacity() - station.getBikes() - station.getPedelecs();
			station.setAvailableDocks(aDocs);
		}

		System.out.println("\n" + "Equalization completed! Choose 'View station list' in menu to view the station.\n");
	}


	/**
	 * Initializes our Valley Bike Simulator.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the ValleyBike Simulator.");
		readData();
		Scanner userInput = new Scanner(System.in);
		try {
			while(true) {
				printMainMenu();
				System.out.println("\nPlease enter a number (0-6): ");
				String input = userInput.nextLine();

				if(input.compareTo("0") == 0) {
					System.out.println("\nThank you for using Valley Bike Simulator!");
					break;
				} else if(input.compareTo("1") == 0) {
					printStationList();
				} else if(input.equals("2")) {
					addStation();
				} else if(input.equals("3")) {
					saveStationList();
					System.out.println("\nStations successfully added to the csv data file!\n");
				} else if(input.equals("4")) {
					recordRide();
				} else if(input.equals("5")) {
					System.out.println("\nEnter the file name (including extension) of the file located in data-files:");
					String rideFile = userInput.nextLine();
					resolveRideData(rideFile);
				} else if(input.equals("6")) {
					equalizeStations();
				} else {
					System.out.println("\nInvalid input, please select a number within the 0-6 range.\n");
				}
			}
		} catch(Exception e){

		}

	}


}
