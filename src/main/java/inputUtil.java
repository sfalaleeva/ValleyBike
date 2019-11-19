import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handles all functionality related to collecting,
 * formatting, and validating inputs.
 */
public class inputUtil {
	
	/*
	 *
	 * ********* FORMATING FUNCTIONS ***********
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
