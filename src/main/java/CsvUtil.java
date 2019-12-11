import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

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
	
	
	/** 
	 * Saves temporary rides, users, bikes, station, and 
	 * completed ride data.
	 */
	public static void saveData() {
		saveOngoingRides();
		saveStationList();
		saveBikeData();
		saveUserData();
	}
	
	
	/* User Data Functions */
	
	/**
	 * Read in all the user data and store in usersMap and userRecords maps 
	 */
	public static void readUserData() {
	    try {
	      String userData = "data-files/user_data.csv";


	      CSVReader userDataReader = new CSVReader(new FileReader(userData));

	      /* to read the CSV data row wise: */
	      List<String[]> allUserEntries = userDataReader.readAll();
	      userDataReader.close();
	      System.out.println("");
	      int counter = 0;
	      for(String[] array : allUserEntries) {
	        if(counter == 0) {

	        } else {
	        Address address = new Address(array[3], array[4], array[5], array[6], array[7]);     
	        User user = new User(Integer.parseInt(array[0]), //id
	              array[1],  //first name
	              array[2],  //last name
	              address,
	              inputUtil.toLocalDate(array[8],"MM/dd/yyyy"), //dob
	              array[9], array[10], array[11], array[12],    //phone, email, pwd, creditCard
	              inputUtil.toLocalDate(array[13], "MM/dd/yyyy"),  //credit card exp date
	              Float.parseFloat(array[14]),  //balance
	              Long.parseLong(array[15]),    //ride time
	              Float.parseFloat(array[16]),  //distance
	              Membership.valueOf(array[17]), //membership type
	              inputUtil.toLocalDate(array[18], "MM/dd/yyyy"),  //membership exp date
	              inputUtil.toBool(array[19]),    //isActive
	              Integer.parseInt(array[20]));   //currentRide

	          ValleyBikeSim.usersMap.put(user.getUserID(), user);
	          ValleyBikeSim.userRecords.put(user.getEmail(), user.getUserID());
	          
	        }
	        counter++;
	      }
	    }
	    catch(Exception e) {
	      System.out.println(e.getMessage());
	    }
	  }	

	
	/**
	 * Save the updated user list to the CSV file, by overwriting all the entries and adding new entries for the new users
	 */
	public static void saveUserData() {
	    try {
	        //overwrites existing file with new data
	        csvWriter = new FileWriter("data-files/user_data.csv");
	        writer = new CSVWriter(csvWriter);
	          String [] record = "ID,FirstName,LastName,Street, City, State, Zip, Country,DOB,Phone, Email, Password, CreditCard, CCExpDate, Balance, TotalRideTime, TotalDistance, Membership, MembershipExpDate, isActive, currentRideId".split(",");

	          writer.writeNext(record);

	        //loops through and saves all the users
	        for (User user : ValleyBikeSim.usersMap.values()) {
	          writer.writeNext(convertUserToCSV(user).split(","));
	        }
	        writer.close();

	    } catch (IOException e) {
	      
	      e.printStackTrace();
	    }

	    
	  }
	  
	/**
	 * Converts a single user object into a string that can be written to CSV
	 * @param user
	 * @return String in csv format
	 */
	private static String convertUserToCSV(User user) {
		String expDate = "12/31/2999";    //if there is no membership expiration date, assume it's permanent
	    String CCexpDate = "12/31/2000";  //if there is no expiration date, assume credit card is expired
	  
	    if (user.getMembershipExpirationDate() != null) {
	      expDate = inputUtil.localDateToString(user.getMembershipExpirationDate(), "MM/dd/yyyy");
	    }
	    if (user.getCreditCard().getExpirationDate() != null) {
	      CCexpDate = inputUtil.localDateToString(user.getCreditCard().getExpirationDate(), "MM/dd/yyyy");
	    }
	    
	    String data = String.join(",",
	        Integer.toString(user.getUserID()), 
	        user.getFirstName(), 
	        user.getLastName(),
	        user.getAddress().getStreet(),
	        user.getAddress().getCity(),
	        user.getAddress().getState(),
	        user.getAddress().getZip(),
	        user.getAddress().getCountry(),
	        inputUtil.localDateToString(user.getDOB(), "MM/dd/yyyy"),
	        user.getPhone(),
	        user.getEmail(),
	        user.getPwd(),
	        user.getCreditCard().getCreditCardNumber(),
	        CCexpDate,
	        Float.toString(user.getBalance()),
	        Long.toString(user.getTotalTime()),
	        Float.toString(user.getTotalDistance()),
	        user.getMembership().name(),
	        expDate,
	        inputUtil.fromBool(user.getIsActive()),
	        Integer.toString(user.getCurrentRideID()));
	    
	    return data;
	}
	

	/* Station Data Functions */
	
	/**
	 * Read in all the station data and store in appropriate data structures. A TreeMap was used so that
	 * when the list of stations is printed to the screen all the IDs for the stations would be ordered.
	 */
	public static void readStationData() {
		try {
			String stationData = "data-files/station-data.csv";

			CSVReader stationDataReader = new CSVReader(new FileReader(stationData));

			/* to read the CSV data row wise: */
			List<String[]> allStationEntries = stationDataReader.readAll();
			System.out.print(allStationEntries.toString());
			stationDataReader.close();
			System.out.println("");
			int counter = 0;
			for(String[] array : allStationEntries) {
				if(counter == 0) {

				} else {
					// the pedelec category of data array[3] is saved in Station.bike 
					Station station = new Station(Integer.parseInt(array[0]), array[1], Integer.parseInt(array[3]), Integer.parseInt(array[4]),
							Integer.parseInt(array[5]), Integer.parseInt(array[6]), inputUtil.toBool(array[7]), array[8],Integer.parseInt(array[9]),
							Integer.parseInt(array[10]));
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
		      String [] record = "ID,Name,Bikes,Pedelacs,Available Docks,Maintainence Request,Capacity,Kiosk,Address,X,Y".split(",");

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
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getX()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(station.getY()));


			csvWriter.append("\n");

			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Ride data functions. */
	
	public static void readOngoingRideData() {
		try {
			String rideData = "data-files/temp-ride-data.csv";

			CSVReader rideDataReader = new CSVReader(new FileReader(rideData));

	        /* to read the CSV data row wise: */
	        List<String[]> allRides = rideDataReader.readAll();
	        rideDataReader.close();
	        System.out.println("");
	        int counter = 0;
	        for(String[] array : allRides) {
	        	if(counter == 0) {

	        	} else {
	        		Ride ride = new Ride(
	        				Integer.parseInt(array[0]), //rideID
	        				Integer.parseInt(array[1]), //userID
	        				Integer.parseInt(array[2]),  //bikeID
	        				Integer.parseInt(array[3]),  //startStationID
	        				Integer.valueOf(-1),		// endStation -- undefined
	        				inputUtil.toLocalDateTime(array[4], inputUtil.LOCAL_DATE_TIME_FORMAT), //startTime
	        				LocalDateTime.MAX
	        		);
	        		ValleyBikeSim.ongoingRides.put(ride.getID(), ride);
	        	}
	        	counter++;
	        }
	    }
	    catch(Exception e) {
	      System.out.println(e.getMessage());
	    }
		  
	}
	
	/** Save rides that havn't been completed into temporary file. */
	public static void saveOngoingRides() {
		String filePath = "data-files/temp-ride-data.csv";
		File file = new File(filePath);
		try {
			  //overwrites existing file with new data
			  csvWriter = new FileWriter(file);
			  writer = new CSVWriter(csvWriter);
		      String [] record = "Ride ID,User ID,Bike ID,Start Station,Start Time".split(",");

		      writer.writeNext(record);
		      
		      for (Ride ride : ValleyBikeSim.ongoingRides.values()) {
		    	  writer.writeNext(convertOngoingRideToCSV(ride).split(","));
		      }
		      
		      writer.close();
		      
		 } catch (IOException e) {
			
			e.printStackTrace();
		}

		//loops through and saves all ongoing rides
		
		
	}
	
	private static String convertOngoingRideToCSV(Ride ride) {
	    
	    String data = String.join(",",
	        Integer.toString(ride.getID()),  //rideID
	        Integer.toString(ride.getUserID()), //userID
	        Integer.toString(ride.getBikeID()), //bikeID
	        Integer.toString(ride.getFromStationID()), //startStation
	        inputUtil.localDateTimeToString(ride.getStartTime(), inputUtil.LOCAL_DATE_TIME_FORMAT) //startTime
	        );
	    
	    return data;
	}

	
	/** Save rides that have been completed into files based on the date.
	 *  It will create a file if a file doesn't exist or append to existing
	 *  file. */
	public static void saveCompletedRides(String dateToSave) {
		String filePath = "data-files/" + dateToSave + ".csv";
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				//overwrites existing file with new data
				csvWriter = new FileWriter(file);
				writer = new CSVWriter(csvWriter);
				String [] record = "Ride ID,User ID,Bike ID,Start Station,End Station,Start Time,End Time".split(",");
					
				writer.writeNext(record);
					
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
			for (Ride ride: ValleyBikeSim.dailyRidesMap.get(dateToSave)) {
				saveRide(ride, filePath);
			}	
	}
	
	/**
	 * Ancillary function to assist the saveOngoingRide() and saveCompletedRides() functions.
	 */
	private static void saveRide(Ride ride, String filePath) {
		
		try {
			csvWriter = new FileWriter(filePath,true);

			//adding all the station details into the csv
			csvWriter.append(Integer.toString(ride.getID()));
		    csvWriter.append(',');
			csvWriter.append(Integer.toString(ride.getUserID()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(ride.getBikeID()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(ride.getFromStationID()));
			csvWriter.append(',');
			csvWriter.append(Integer.toString(ride.getToStationID()));
			csvWriter.append(',');
			
			csvWriter.append(inputUtil.localDateTimeToString(ride.getStartTime(), inputUtil.LOCAL_DATE_TIME_FORMAT));
			csvWriter.append(',');
			
			String endTime = "";
			if (ride.getEndTime() != LocalDateTime.MAX) {
				endTime = inputUtil.localDateTimeToString(ride.getEndTime(), inputUtil.LOCAL_DATE_TIME_FORMAT);
			} 
			csvWriter.append(endTime);
		
			csvWriter.append("\n");

			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Manages to read the ride data, record all the rides in the ridesList and then calculates the average
	 * ride duration and displays the message informing the user how many rides there were and what the average
	 * duration was for the whole ride data.
	 * @param ridesFileName - the string specifying the name and extension of the ride data.
	 */
	public static void resolveRideData(String ridesFileName) {
		String rideData = "data-files/" + ridesFileName;
		//toLocalDateTime returns LocalDateTime.MAX if unable to format appropriately
		// according to inputUti.LOCAL_DATE_TIME_FORMAT
		// TODO(): validate time inputs from file
		// csv rides might contain a duration (in seconds) that could be used instead of the start/end time (.
		try {
			CSVReader rideDataReader = new CSVReader(new FileReader(rideData));
			List<Ride> ridesList = new ArrayList<>();

			List<String[]> allRidesEntries = rideDataReader.readAll();
			rideDataReader.close();
			System.out.println("");

			int counter = 0;
			for(String[] array : allRidesEntries) {
				if(counter == 0) {

				} else {
					//TODO() change what gets read/written in from ride.csv files
					// current the bike id of existing rides is hard coded to 0
					ridesList.add(new Ride(Integer.parseInt(array[0]), 0, Integer.parseInt(array[1]),
							Integer.parseInt(array[2]), inputUtil.toLocalDateTime(array[3], inputUtil.LOCAL_DATE_TIME_FORMAT), inputUtil.toLocalDateTime(array[4],inputUtil.LOCAL_DATE_TIME_FORMAT)));
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
			bikeDataReader.close();
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
