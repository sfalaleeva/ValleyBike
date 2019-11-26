
public class Account {
	
	/**
	 * For the entire class, the highest user ID plus one, to be assigned to the next registering user.
	 */
	static int nextUserID = 1;
	
	/**
	 * Unique integer id of the account
	 */
	private int userID;
	
	
	/**
	 * Associated password of the account
	 */
	protected String password;
	
	/**
	 * Creates a basic account
	 * @param id - unique user id
	 * @param pwd - string password
	 */
	public Account(String pwd) {
		userID = nextUserID;
		password = pwd;
		
		nextUserID++;
	}
	
	/**
	 * Sets the user's password
	 * @param pwd - new password
	 */
	public void setPassword(String pwd) {
		//TODO: needs to match regex
		password = pwd; 
	}
	
	/**
	 * Sets the user ID
	 * @param id - unique user id of account
	 */
	public void setUserID(int id) {
		userID = id;
	}
	
	/**
	 * Gets the user ID
	 * @return id - unique user id of account
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Gets the password
	 * @return password - unique password of account
	 */
	public String getPwd() {
		return password;
	}
	
}
