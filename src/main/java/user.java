import java.util.ArrayList;

public class user {
	
	static int nextUserID;
	
	private String password;
	
	private boolean isActive;
	
	private String firstName;
	
	private String lastName;
	
	private int userID;
	
	private String email;
	
	//TODO: Set up date class
	//private Date dob;
	
	private String phone;
	
	//TODO: Decide on address class?
	private String street;
	private String city;
	private String zip;
	private String country;
	
	private String creditCard;
	
	private float balance;
	
	//TODO: Figure out time class setup?
	//private [Duration?] totalRideTime;
	
	private float totalDistance;
	
	//TODO: Set up enum class
	//private enum membership;
	
	//TODO: date format?
	//private Date membershipExpirationDate;
	
	private ArrayList<Ride> rideHistory;
	
	private int currentRideID;
	
	
	
	
	
	public user() {
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
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public String getEmail() {
		return email;
	}
	
	//TODO: getDOB needs Date
	
	public String getPhone() {
		return phone;
	}
	
	//TODO: Convert this into an address class
	public String getAddress() {
		return "This method needs an address class";
	}
	
	public String getCreditCard() {
		return creditCard;
	}
	
	public float getBalance() {
		return balance;
	}
	
	//TODO: getTotalRideTime needs a duration class
	
	public float getTotalDistance() {
		return totalDistance;
	}
	
	//TODO: getMembership needs the membership enums to be set up
	
	public ArrayList<Ride> getRideHistory() {
		return rideHistory;
	}
	
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
	
	public void setPassword(String pwd) {
		//TODO: needs to match regex
	}
	
	public void setFirstName(String fname) {
		firstName = fname;
	}
	
	public void setLastName(String lname) {
		lastName = lname;
	}
	
	public void setUserID(int id) {
		userID = id;
	}
	
	public void setEmail(String email) {
		//TODO: regex matching for email
	}
	
	/*
	public void setDOB(Date birth) {
		//TODO: Need a date class
	}
	*/
	
	public void setPhone(String phone) {
		//TODO: Regex matching for phone number
	}
	
	/*
	public void setAddress(Address addr) {
		//TODO: Address class
	}
	*/
	
	public void setCreditCard(String cc) {
		/*
		 * validateCard()
		 * update only if it's valid
		 * updateStatus()
		 */
	}
	
	public void addToBalance(float charge) {
		balance += charge;
	}
	
	/*
	public void addToRideTime(Duration time) {
		//TODO: Duration class
	}
	*/
	
	public void addToDistance(float dist) {
		totalDistance += dist;
	}
	
	public void setCurrentRide(int id) {
		currentRideID = id;
	}
	
	
	public void updateMembership() {
		//TODO: Membership enum
		
		/*
		 * If can pass null, membership = null
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
	
	public boolean chargeUser() {
		//TODO: chargeUser
		
		/*
		 * get balance, pretend charge card, set balance to zero
		 */
		
		//return success or failure
		return true;
	}
	
	public void updateStatus() {
		/*
		 * TODO: updatestatus
		 * if creditcard != null and membership != null
		 * then isActive = true
		 */
	}
	
	public void generateUserReport() {
		//TODO: Did we talk about what this does?
	}
	
	
	
	
	
	
	
	
	

}
