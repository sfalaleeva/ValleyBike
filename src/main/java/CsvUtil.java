import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * @author G6
 * Handles all functionality related to reading from
 * and saving to CSV files. This helper class is final
 * because it should never be instantiated.
 */
public final class CsvUtil {
	
	/**
	 * For use in writing data to files.
	 */
	private  static FileWriter csvWriter;
	
	/**
	 * For writing data to CSV files.
	 */
	private static CSVWriter writer;
	

	/* Station Data Functions */
	
	/**
	 * Read in all the station data and store in appropriate data structures. A TreeMap was used so that
	 * when the list of stations is printed to the screen all the IDs for the stations would be ordered.
	 */
	public static void readStationData() {
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
					ValleyBikeSim.stationsMap.put(station.getID(), station);
				}
				counter++;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Save the updated station list to the CSV file, by overwriting all the entries and adding new entries for the new stations.
	 * Also involves call to saveBikeData to ensure consistency in stored data.
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
		for (Station station : ValleyBikeSim.stationsMap.values()) {
			saveStation(station);
		}
		saveBikeData();
	}
	
	/**
	 * Ancillary function to assist the saveStationList() function.
	 */
	private static void saveStation(Station station) {
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
	
	/* Ride data functions. */
	
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

	/* Bike data functions. */
	
	/**
	 * Read in the bike.csv to reinitialize all system bike objects and
	 * reestablish relationships to stations.
	 */
	public static ArrayList<Bike> readBikeData() {
		ArrayList<Bike> bikesList = new ArrayList<>();
		
		try {
			String bikeData = "data-files/bike-data.csv";
			CSVReader bikeDataReader = new CSVReader(new FileReader(bikeData));

			/* to read the CSV data row wise: */
			List<String[]> allBikeEntries = bikeDataReader.readAll();
			System.out.println("");
			int counter = 0;
			for(String[] array : allBikeEntries) {
				if(counter == 0) {
				} else {
					Bike bike = new Bike(Integer.parseInt(array[0]),Integer.parseInt(array[1]),inputUtil.toBool(array[2]),
							inputUtil.toBool(array[3]));
					bikesList.add(bike);
				}
				counter++;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return bikesList;
	}
	
	/**
	 * Saves the bike list into a CSV file.
	 */
	public static void saveBikeData() {
		//TODO(): complete
		
		try {
			  //overwrites existing file with new data
			  csvWriter = new FileWriter("data-files/bike-data.csv");
			  writer = new CSVWriter(csvWriter);
		      String [] record = "Bike ID,Station ID,Is On Ride,Needs Maintenance".split(",");

		      writer.writeNext(record);

		      writer.close();

		} catch (IOException e) {
			
			e.printStackTrace();
		}

		//loops through and saves all stations
		for (Bike bike : ValleyBikeSim.bikesMap.values()) {
			saveBike(bike);
		}
	}
	
	/**
	 * Ancillary function to assist the saveBikeData() function.
	 */
	private static void saveBike(Bike bike) {
		try {
			csvWriter = new FileWriter("data-files/bike-data.csv",true);

			//adding all the bike details into the csv
			csvWriter.append(Integer.toString(bike.getID()));
		    csvWriter.append(',');
			csvWriter.append(Integer.toString(bike.getStatID()));
			csvWriter.append(',');
			csvWriter.append(inputUtil.fromBool(bike.getOnRide()));
			csvWriter.append(',');
			csvWriter.append(inputUtil.fromBool(bike.getMaintenance()));
			csvWriter.append("\n");

			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
