import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class User extends Account{
	

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
	private Address address;
	
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
	private Membership membership;
	
	/**
	 * The expiration date of the user's membership.
	 */
	private LocalDate membershipExpirationDate;
	
	/**
	 * List of rides the user has taken
	 */
	private ArrayList<Ride> rideHistory;
	
	/**
	 * ID of the ride the user is currently on. -1 if user is not on a ride.
	 */
	private int currentRideID;
	
	/**
	 * Instantiates an inactive user object. 
	 */
	public User(String firstName, String lastName, Address address, Date dob, String phone, String email, String pwd) {
		super(pwd);
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.dob = dob;
		this.phone = phone;
		this.email = email;
		creditCard = "";
		balance = 0f;
		totalRideTime = 0;
		totalDistance = 0;
		membership = Membership.NONE;
		rideHistory = new ArrayList<>();
		currentRideID = -1;
		isActive = false;
	}
	
	/*
	 * 
	 * Accessor Methods
	 * 
	 */
	
	/**
	 * Gets the status of the user account.
	 * @return true if account is active
	 */
	public boolean getIsActive() {
		return isActive;
	}
	
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
	public Address getAddress() {
		return address;
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
	
	/**
	 * Gets total distance user has ridden on the bikes.
	 * @return - total distance of user rides
	 */
	public float getTotalDistance() {
		return totalDistance;
	}
	
	/**
	 * Gets user's membership type
	 * @return - membership type enum
	 */
	public Membership getMembership() {
		return membership;
	}
	
	/**
	 * Gets user's membership expiration date.
	 * @return - date
	 */
	public LocalDate getMembershipExpirationDate() {
		return membershipExpirationDate;
	}
	
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
	 * SETTER METHODS
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
		if (inputUtil.validateEmail(email)) {
			this.email = email;
		}
	}
	
	/**
	 * Sets user's date of birth
	 * @param birth - user's date of birth
	 */
	public void setDOB(Date birth) {
		dob = birth;
	}
	
	/**
	 * Sets user's phone number.
	 * @param phone - phone number
	 */
	public void setPhone(String phone) {
		if (inputUtil.validatePhone(phone)) {
			this.phone = phone;
		}
	}
	
	/**
	 * Sets the user's address
	 * @param addr - Address object.
	 */
	public void setAddress(Address addr) {
		this.address = addr;
	}
	
	/**
	 * Sets the user's credit card number. If invalid and the previous
	 * credit card is not valid, the credit card is set to null.
	 * @param cc - credit card number
	 */
	public void setCreditCard(String cc) {
		if (!cc.isEmpty() && Payment.validateCardNumber(cc) && Payment.validateCard(cc)) {
			this.creditCard = cc;
		}
		else if (Payment.validateCard(this.creditCard)) {
			// keep existing valid card
		}
		else {
			// neither option is valid and user account is inactive.
			this.creditCard = null;
		}
		updateStatus();
	}
	
	/**
	 * Adds a charge to the user's balance.
	 * @param charge - amount charged to the account
	 */
	public void addToBalance(float charge) {
		balance += charge;
	}
	
	/**
	 * Adds a ride duration to the total time sum.
	 * @param time - duration of ride being added
	 */
	
	public void addToRideTime(Duration time) {
		//TODO: Duration class
	}
	
	
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
	 * Update or change user's membership details. 
	 * Returns false if credit card isn't valid.
	 * @param membership - the membership enum
	 * @return boolean - success of membership update
	 */
	public boolean updateMembership(Membership membership) {
			this.membershipExpirationDate = null; // resets expiration date
			this.membership = membership; // sets user membership
			
		if (!membership.equals(Membership.NONE)) {
			// check if user's payment information is valid and activates user
			updateStatus();
			if (this.isActive) {
				addToBalance(membership.getPrice()); // update what user owes
				
				LocalDate now = LocalDate.now(); // immutable object
				
				switch (membership) {
					case MONTH:
						this.membershipExpirationDate = now.plusMonths(1);
						break;
					case DAY:
						this.membershipExpirationDate = now.plusDays(1);
						break;
					default: // PAY_PER_RIDE, YEAR, FOUNDER last 1 year
						this.membershipExpirationDate = now.plusYears(1);
				}
			}
			else {
				// if the account is not validated, the update fails.
				this.membership = Membership.NONE;
				return false;
			}
		}
		return true;
	}
	
	
	/*
	 * 
	 * OTHER METHODS
	 * 
	 */
	
	
	/**
	 * Charge user money for a ride taken, set balance to zero
	 * @return true for card successfully charged, false for any error or failure.
	 */
	public boolean chargeUser() {	
		if (Payment.chargeCard(this.creditCard)) {
			this.balance = 0;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * cancels user's membership. 
	 */
	public void cancelMembership() {
		this.membership = Membership.NONE;
	}
	
	/**
	 * Checks whether the user should be considered active
	 * and updates.
	 */
	public void updateStatus() {
		if (!creditCard.isEmpty() && !this.membership.equals(Membership.NONE)) {
			this.isActive = true;
		}
	}
	
	/**
	 * prints out user's details
	 */
	public void printInfo() {
		//TODO: create censoring helper in InputUtil ?
		//public static String censorString( String str , Integer numOfCharToCensor ) { ... return censoredString } 
		
		String censoredPwd = String.format("%.2s", this.password);  //gets first 2 characters of pwd
		for (int i = 0; i < this.password.length()-2; i++) { 		//replace rest of characters with *
			censoredPwd += "*";
		}
		String format = "%-18s%s%n";
		System.out.println("");
		System.out.printf(format, "Name:", this.firstName + " " + this.lastName);
		System.out.printf(format, "Email:", this.email);
		System.out.printf(format, "Password:", censoredPwd);
		System.out.printf(format, "Address:", this.address.getAddress());
		System.out.printf(format, "Date of Birth:", this.dob);
		System.out.printf(format, "Phone number:", this.phone);
		System.out.printf(format, "Membership:", this.membership);
		//System.out.printf(format, "Payment Info:", this.email);
		
	}
	
	/**
	 * Generate a report of the user's balance, ride time and history, etc
	 */
	public void generateUserReport() {
		//TODO: Did we talk about what this does?
	}
	
	
	
	
	
	
	
	
	

}
