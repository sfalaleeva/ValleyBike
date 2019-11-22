import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Handles all functionality related to collecting,
 * formatting, and validating inputs. This helper class
 * is final because it should never be instantiated.
 */
public final class inputUtil {
	
	/** Scanner for obtaining user input.*/
	private static Scanner userInput = new Scanner(System.in);
	
	/** private parameter-less constructor; this class is never instantiated. **/
	private inputUtil() {}
	
	/*
	 * ********* INPUT FUNCTIONS ***********
	 */
	
	public static String getString() {
		return userInput.nextLine();
	}
	
	public static int getInt() {
		String input = userInput.nextLine();
		return Integer.parseInt(input);
	}
	
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
	
	/** Helper function generates number in a specified range. */
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
	
}
