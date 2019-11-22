import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles all functionality related to collecting,
 * formatting, and validating inputs. This helper class
 * is final because it should never be instantiated.
 */
public final class inputUtil {
	
	/** Regex for validating email strings. */
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	/** Scanner for obtaining user input.*/
	private static Scanner userInput = new Scanner(System.in);
	
	/** private parameter-less constructor; this class is never instantiated. **/
	private inputUtil() {}
	
	/*
	 * ********* INPUT FUNCTIONS ***********
	 */
	
	/**
	 * Gets a date from the user in format
	 * yyyy-mm-dd.
	 * @return Date
	 */
	//TODO(): Works strangely. Ex. 1999-5-2654 passes. But it does ensure 
	// there are three dashes.
	public static Date getDate() {
		Date date = new Date();
		while(true) {
			String dateString = getString();
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				break;
			}
			catch(ParseException e){
				System.out.println("Please enter valid date [yyyy-MM-dd].");
				continue;
			}
		}
		return date;
	}
	
	/**
	 * Gets a string from the user.
	 * @return string
	 */
	public static String getString() {
		return userInput.nextLine();
	}
	
	/**
	 * gets boolean from user.
	 * @return boolean
	 */
	public static boolean getBool() {
		while (true) {
			String input = userInput.nextLine();
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
			String input = userInput.nextLine();
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
	/*
	 * ********* FORMATING FUNCTIONS ***********
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
	 * Convert a string to Date java object.
	 * @throws ParseException
	 */
	public static Date toDate(String s) throws ParseException {
		Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
		return dateTime;
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
			if (validatePhone(phone)) {
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
	 * True if string is a valid phone number.
	 * @param phone
	 * @return boolean
	 */
	public static boolean validatePhone(String phone) {
		return phone.matches("\\d{10}");
	}

	/**
	 * Get valid user email. 
	 * @return String
	 */
	public static String getValidEmail() {
		String email = "";
		while(true) {
			email = getString();
			if (validateEmail(email)) {
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
	 * True if string is a valid email.
	 * @param regex Matcher for email
	 * @return boolean
	 */
	public static boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
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
			if (validatePwd(pwd)) {
				break;
			}
			else {
				System.out.println("Invalid password.");
				continue;
			}
		}
		return pwd;
	}
	
	/**
	 * True if string is a valid email.
	 * @param regex Matcher for email
	 * @return boolean
	 */
	public static boolean validatePwd(String pwd) {
		return pwd.matches(".{8}");
	}
	
}
