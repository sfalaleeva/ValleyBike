import java.util.ArrayList;
import java.util.Date;

public class user extends account{
	
	
	
	/**
	 * Whether or not the user has a registered membership and card
	 */
	private boolean isActive;
	
	
	/**
	 * User's first name
	 */
	private String firstName;
	
	
	/**
	 * User's last name
	 */
	private String lastName;
	
	
	
	/**
	 * User's email address
	 */
	private String email;
	
	
	/**
	 * User's date of birth: Must be over eighteen to ride.
	 */
	private Date dob;
	
	
	/**
	 * User's phone number
	 */
	private String phone;
	
	
	/**
	 * User's home address, billing address
	 */
	//TODO: Decide on address class?
	private String street;
	private String city;
	private String zip;
	private String country;
	
	
	/**
	 * User's credit card number for billing purposes
	 */
	private String creditCard;
	
	
	/**
	 * User's outstanding balance 
	 */
	private float balance;
	
	
	/**
	 * Total duration of all rides user has taken
	 */
	private long totalRideTime;
	
	
	/**
	 * Total distance of rides user has taken
	 */
	private float totalDistance;
	
	
	/**
	 * User's membership type
	 */
	//TODO: Set up enum class
	//private enum membership;
	
	
	/**
	 * The expiration date of the user's membership
	 */
	private Date membershipExpirationDate;
	
	
	/**
	 * List of rides the user has taken
	 */
	private ArrayList<Ride> rideHistory;
	
	
	/**
	 * ID of the ride the user is currently on. Null if user is not on a ride.
	 */
	private int currentRideID;
	
	
	
	
	/**
	 * Instantiates a user object. 
	 */
	public user(String pwd) {
		super(pwd);
		//TODO: add user class constructor here
	}
	
	
	
	/*
	 * 
	 * 
	 * 
	 * Accessor Methods
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	/**
	 * Gets user's first name
	 * @return - first name of the user
	 */
	public String getFirstName() {
		return firstName;
	}
	
	
	/**
	 * Gets user's last name
	 * @return - last name of user
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Gets user email address
	 * @return - email address of user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets date of birth
	 * @return - date of birth of user
	 */
	public Date getDOB() {
		return dob;
	}
	
	
	/**
	 * Gets user phone number
	 * @return - phone number of user
	 */
	public String getPhone() {
		return phone;
	}
	
	
	/**
	 * Gets the address of the user, whether as a list of fields or compiled into an address class
	 * @return - user's address
	 */
	//TODO: Convert this into an address class
	public String getAddress() {
		return "This method needs an address class";
	}
	
	
	/**
	 * Gets the user's credit card number
	 * @return - credit card number
	 */
	public String getCreditCard() {
		return creditCard;
	}
	
	
	/**
	 * Gets user's current balance
	 * @return - current balance
	 */
	public float getBalance() {
		return balance;
	}
	
	/**
	 * Gets total length of time user has ridden on the bikes
	 * @return - total ride time
	 */
	public long getTotalTime() {
		return totalRideTime;
	}
	
	public float getTotalDistance() {
		return totalDistance;
	}
	
	
	/**
	 * Gets user's membership type
	 * @return - membership type enum
	 */
	//TODO: getMembership needs the membership enums to be set up
	
	/**
	 * Gets the user's entire ride history
	 * @return - list of ride objects the user has taken
	 */
	public ArrayList<Ride> getRideHistory() {
		return rideHistory;
	}
	
	/**
	 * Gets the id of the ride the user is currently on
	 * @return - id of current ride, or null if no current ride exists.
	 */
	public int getCurrentRideID() {
		return currentRideID;
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * SETTER METHODS
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	/**
	 * Sets the user's name
	 * @param fname - first name of user
	 */
	public void setFirstName(String fname) {
		firstName = fname;
	}
	
	/**
	 * Sets the user's last name
	 * @param lname - last name of user
	 */
	public void setLastName(String lname) {
		lastName = lname;
	}
	
	/**
	 * Sets the user's email address
	 * @param email - email address of user
	 */
	public void setEmail(String email) {
		//TODO: regex matching for email
	}
	
	/**
	 * Sets user's date of birth
	 * @param birth - user's date of birth
	 */
	public void setDOB(Date birth) {
		dob = birth;
	}
	
	
	
	/**
	 * Sets user's phone number
	 * @param phone - phone number
	 */
	public void setPhone(String phone) {
		//TODO: Regex matching for phone number
	}
	
	/**
	 * Sets the user's address
	 * @param addr - either list of string objects or an address class
	 */
	/*
	public void setAddress(Address addr) {
		//TODO: Address class
	}
	*/
	
	/**
	 * Sets the user's credit card number
	 * @param cc - credit card number
	 */
	public void setCreditCard(String cc) {
		/*
		 * validateCard()
		 * update only if it's valid
		 * updateStatus()
		 */
	}
	
	
	/**
	 * Adds a charge to the user's balance
	 * @param charge - amount charged to the account
	 */
	public void addToBalance(float charge) {
		balance += charge;
	}
	
	
	/**
	 * Adds a ride duration to the total time sum
	 * @param time - duration of ride being added
	 */
	/*
	public void addToRideTime(Duration time) {
		//TODO: Duration class
	}
	*/
	
	/**
	 * Adds a ride distance to the total distance sum
	 * @param dist - distance of ride being added
	 */
	public void addToDistance(float dist) {
		totalDistance += dist;
	}
	
	
	/**
	 * Sets the current ride id
	 * @param id - id of current ride object
	 */
	public void setCurrentRide(int id) {
		currentRideID = id;
	}
	
	
	/**
	 * Update or change user's membership details
	 */
	public void updateMembership() {
		//TODO: Membership enum
		
		/*
		 * Can pass null ->  membership = null
		 * membership != null, update expirationdate
		 * call updateStatus()
		 * addToBalance() whatever is owed
		 */
	}
	
	
	/*
	 * 
	 * 
	 * OTHER METHODS
	 * 
	 * 
	 */
	
	
	/**
	 * Charge user money for a ride taken, set balance to zero
	 * @return true for card successfully charged, false for any error or failure.
	 */
	public boolean chargeUser() {
		//TODO: chargeUser
		
		/*
		 * get balance, pretend charge card, set balance to zero
		 */
		
		//return success or failure
		return true;
	}
	
	
	/**
	 * Checks whether the user should be considered active. 
	 */
	public void updateStatus() {
		/*
		 * TODO: updatestatus
		 * if creditcard != null and membership != null
		 * then isActive = true
		 */
	}
	
	
	/**
	 * Generate a report of the user's balance, ride time and history, etc
	 */
	public void generateUserReport() {
		//TODO: Did we talk about what this does?
	}
	
	
	
	
	
	
	
	
	

}
