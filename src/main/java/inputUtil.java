import java.text.ParseException;

import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//Cite: https://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/

/**
 * Handles all functionality related to collecting,
 * formatting, and validating inputs. This helper class
 * is final because it should never be instantiated.
 */
public final class inputUtil {
	
	/** Regex for validating email strings. */
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	/** Regex for validating password strings. 
	 * Specifies at least 1 of each: lower case, upper case, digit, and then length somewhere from 6-16
	 **/
	public static final Pattern VALID_PASSWORD_REGEX = 
			Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16})");
	
	/** Regex for validating zipcode strings. */
	public static final Pattern VALID_ZIPCODE_REGEX = Pattern.compile("^\\d{5}(?:[-\\s]\\d{4})?$");
	
	/** Regex for validating phone number. */
	public static final Pattern VALID_PHONE_REGEX = Pattern.compile("\\d{10}");
	
	/** Scanner for obtaining user input.*/
	private static Scanner userInput = new Scanner(System.in);
	
	/** private parameter-less constructor; this class is never instantiated. **/
	private inputUtil() {}
	
	/*
	 * ********* INPUT FUNCTIONS ***********
	 */

	/**
	 * Gets a non-empty string from the user.
	 * @return string
	 */
	public static String getString() {
		String input = "";
		while(true) {
			input = userInput.nextLine();
			if (input.isEmpty()) {
				System.out.println("Please enter a response.");
				continue;
			}
			break;
		}
		return input;
	}
	
	/**
	 * Returns valid date string.
	 * @return valid string in format yyyy-MM-dd.
	 */
	public static String getValidDateString() {		
		String pattern = "^[1-2]\\d\\d\\d[-](0[1-9]|[1-9]|1[0-2])[-]([1-9]|[0-2][0-9]|3[0-1])";
		String dateString = getString();
		while(true) {
			if (dateString.matches(pattern)) {
				//restrict DOB year to after 1900
				Integer year = Integer.valueOf(dateString.substring(0, 4)); 
				if (year > 1900) {    
					return dateString;
				}
			}
			System.out.println("Please enter valid date [yyyy-[m]m-[d]d].");
			dateString = getString();
			continue;
		}
	}
	
	/**
	 * Returns valid expiration date string.
	 * @return valid string in format yyyy-[m]m.
	 */
	public static String getValidExpirationDateString() {
		String dateString = getString();
		boolean valid = isValidExpirationDate(dateString);
		while(!valid) {
			System.out.println("Please enter valid expiration date [yyyy-[m]m].");
			dateString = getString();
			valid = isValidExpirationDate(dateString);
		}
		return dateString + "-01"; // day needed to parse local date string
	}
	
	public static boolean isValidExpirationDate(String expirationDate) {
		String pattern = "^([2]\\d\\d\\d)[-](0[1-9]|[1-9]|1[0-2])";
		if (expirationDate.matches(pattern)) {
			//restrict expiration data to a future date
			Integer year = Integer.valueOf(expirationDate.substring(0,4)); 
			Integer month = Integer.valueOf(expirationDate.substring(5));
			if (year > LocalDate.now().getYear() || (year == LocalDate.now().getYear() && month > LocalDate.now().getMonthValue())) { 
				return true;
			}
		}
		return false;
	}
	
	/**
	 * gets boolean from user.
	 * @return boolean
	 */
	public static boolean getBool() {
		while (true) {
			String input = getString();
			input = input.toLowerCase();
			if(input.charAt(0) == 'y') {
				return true;
			} else if(input.charAt(0) == 'n') {
				return false;
			} else {
				System.out.println("\nInvalid input. Please enter y/n.");
				continue;
			}
		}
	}
	
	/** 
	 * Helper function generates number in a specified range.
	 * @return int
	 */
	public static int getIntInRange(int min, int max, String desiredInput) {
		while(true) {
			String input = getString();
			try {
				int parsedInput = Integer.parseInt(input);
				if(parsedInput < min | parsedInput > max) {
					System.out.printf("\nInvalid " + desiredInput + ". Please enter int in range %d-%d.", min, max);
					continue;
				} else {
					return parsedInput;
				}
		} catch(NumberFormatException e) {
			System.out.printf("Invalid input. Please enter int in range %d-%d.", min, max);
			continue;
			}
		}
	}
	
	public static int getIntInList(List<Integer> list, String desiredInput) {
		while(true) {
			String input = getString();
			try {
				int parsedInput = Integer.parseInt(input);
				if(!list.contains(parsedInput)) {
					System.out.printf("\nInvalid " + desiredInput + ". Please enter one of the numbers in the list: ", list);
					continue;
				} else {
					return parsedInput;
				}
		} catch(NumberFormatException e) {
			System.out.printf("Invalid input. Please enter one of the numbers in the list: ", list);
			continue;
			}
		}
	}
	
	
	/**
	 * Get the information to create user object.
	 * @return Address
	 */
	public static Address getAddress() {
		System.out.print("Enter your address.\nStreet: ");
		String street = inputUtil.getString();
		System.out.print("City: ");
		String city = inputUtil.getString();
		System.out.print("State: ");
		String state = inputUtil.getString();
		System.out.print("Zip Code in [xxxxx (xxxx)] format: ");
		String zip = inputUtil.getValidZipCode();
		System.out.print("Country: ");
		String country = inputUtil.getString();
		return new Address(street, city, state, zip, country);
	}
	
	/*
	 * ********* FORMATING FUNCTIONS ***********
	 */

	/**
	 * Turn provided string into date in specified format.
	 * @return Date
	 */
	public static LocalDate toDate(String dateString, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		LocalDate date = LocalDate.parse(dateString, formatter);
		return date;
	}
	
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
	 * Helper function to pass the String values of "0" and "1" as arguments
	 * and return boolean values of true and false respectively.
	 */
	public static boolean toBool(String s) {
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
	 * Returns a list of bike objects
	 * @param station id
	 * @return list of bike objects
	 */
	public static ArrayList<Bike> getBikeListFromIds(Integer stationId) {
		ArrayList<Integer> bikeIDs = ValleyBikeSim.stationToBikeMap.get(stationId);
		ArrayList<Bike> bikes = new ArrayList<>();
		for (int id: bikeIDs) {
			bikes.add(ValleyBikeSim.bikesMap.get(id));
		}
		return bikes;
	}

	/*
	 * User Account Detail Validation
	 */
	
	/**
	 * Gets valid user phone number.
	 * @return String
	 */
	public static String getValidPhone() {
		String phone = "";
		while(true) {
			phone = getString();
			if (validateWithRegex(phone, VALID_PHONE_REGEX)) {
				break;
			}
			else {
				System.out.println("Invalid phone number.");
				continue;
			}
		}
		return phone;
	}

	/**
	 * Get valid zipcode.
	 * @return String
	 */
	public static String getValidZipCode() {
		String zipcode = "";
		while (true) {
			zipcode = getString();
			if (validateWithRegex(zipcode, VALID_ZIPCODE_REGEX)) {
				break;
			}
			else {
				System.out.println("Invalid zipcode. Try [xxxxx (xxxxx)]");
				continue;
			}
		}
		return zipcode;
	}

	/**
	 * Get valid user email. 
	 * @return String
	 */
	public static String getValidEmail() {
		String email = "";
		while(true) {
			email = getString();
			if (validateWithRegex(email, VALID_EMAIL_ADDRESS_REGEX)) {
				break;
			}
			else {
				System.out.println("Invalid email.");
				continue;
			}
		}
		return email;
	}
	
	/**
	 * True if string matches given regex.
	 * @param regex Matcher for string
	 * @return boolean
	 */
	public static boolean validateWithRegex(String s, Pattern regex) {
		Matcher matcher = regex .matcher(s);
		return matcher.find();
	}

	/** 
	 * Get valid user password of at 8 characters
	 * @return String
	 */
	public static String getValidPassword() {
		String pwd = "";
		while(true) {
			pwd = getString();
			if (validateWithRegex(pwd, VALID_PASSWORD_REGEX)) {
				break;
			}
			else {
				System.out.println("Invalid password." 
			+ "\n Must be 6-16 characters long" 
			+ "\n Must have at least 1 lower case, 1 upper case, and 1 digit");
				continue;
			}
		}
		return pwd;
	}
	
	/**
	 * Prompts user for StationID to return their bike to, validates that ID is valid
	 * @return station ID
	 */
	public static Integer getRideEndStationID() {
		System.out.println("Please tell us ID of the station you are returning this bike to: ");
		Integer stationID = getIntInRange(20, ValleyBikeSim.stationsMap.lastKey(), "station ID");
		return stationID;
	}
	
	/**
	 * Function helps the user to pick out a bike they want to unlock by displaying a list of bikes
	 * at a given station and validating that user entered one of the numbers in that list
	 * @return bike ID
	 */
	public static Integer getValidBikeIdAtStation() {
		System.out.println("First, enter the ID of the station you want to start your ride from:");
		int stationID = getIntInRange(20, ValleyBikeSim.stationsMap.lastKey(), "station ID");
		Station station = ValleyBikeSim.stationsMap.get(stationID);
		if(station.getBikes() <= 0) {
			System.out.println("It looks like there are no bikes at this station. You can try looking for a bike at another station.");
			return -1;
		}
		List<Integer> bikeIDs = ValleyBikeSim.stationToBikeMap.get(stationID);
		System.out.println("These are the bikes that are currently at the station: "+ bikeIDs.toString());  
		System.out.println("Please enter the ID of the bike you would like to check out: ");
		Integer bikeID = inputUtil.getIntInList(bikeIDs, "bike ID");
	
		return bikeID;
	}
	
}
