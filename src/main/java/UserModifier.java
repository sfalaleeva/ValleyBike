import java.util.Date;

/**
 * Contains methods for creating, editing, and
 * deleting user objects.
 * @author G6
 */
public final class UserModifier {

	/**
	 * Obtains the information about the user required to register,
	 * adds the user to the system.
	 */
	public static User register() {
		System.out.println("Please enter the following information to register.");
		
		System.out.println("First Name:");
		String fName = inputUtil.getString();
		System.out.println("Last Name:");
		String lName = inputUtil.getString();
		
		//TODO(): Get address inputs
		//System.out.println("Address:");
		
		System.out.println("Date of Birth:");
		//TODO(): implement getDate() yyyy-mm-dd
		Date dob = new Date();
		// = inputUtil.getDate();
		
		System.out.println("Phone:");
		String phone = "";
		//TODO(): = inputUtil.getValidPhone();
		System.out.println("Email:");
		String email = "";
		//TODO(): = inputUtil.getValidEmail();
		System.out.println("Password:");
		String pwd = "";
		//TODO(): = inputUtil.getValidPassword();
		System.out.println("Retype Password:");
		//TODO(): check passwords match
		while (!pwd.equals(inputUtil.getString())) {
			System.out.println("Passwords do not match.");
			inputUtil.getString();
		}
		
		User user = new User(fName, lName, dob, phone, email, pwd);
		user.updateStatus();
		
		System.out.println("Would you like to continue activiating your account?");
		if (inputUtil.getBool()) {
			//TODO(): addMembership(); which will use updateMembership()
			//TODO(): addPayment();
		}
		
		return user;
	}
	
}
