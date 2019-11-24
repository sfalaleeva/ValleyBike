import java.util.Date;

/**
 * Contains methods for creating, editing, and
 * deleting user objects.
 * @author G6
 */
public final class UserModifier {

	/*
	 * Publicly facing methods.
	 */
	
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
		
		System.out.println("Date of Birth [yyyy-MM-dd]:");
		Date dob = inputUtil.getDate();
	
		System.out.println("Phone [no spaces or extra characters:]");
		String phone = inputUtil.getValidPhone();
		System.out.println("Email:");
		String email = inputUtil.getValidEmail();
		
		System.out.println("Password:");
		String pwd = inputUtil.getValidPassword();
		System.out.println("Retype Password:");
		
		String pwdRetype = inputUtil.getString();
		// check passwords match
		while (!pwd.equals(pwdRetype)) {
			System.out.println("Passwords do not match." + "\nRetype Password:");
			pwdRetype = inputUtil.getString();
		}
		
		User user = new User(fName, lName, dob, phone, email, pwd);
		
		System.out.println("Would you like to continue activiating your account?");
		if (inputUtil.getBool()) {
			user = changePayment(user);
			user = changeMembership(user); 
		}
		user.updateStatus();
		return user;
	}
	
	/**
	 * Method for getting user membership choice and 
	 * updating the user's membership.
	 * @param user
	 * @return
	 */
	public static User changeMembership(User user) {
		Membership m = selectMembership();
		user.updateMembership(m);
		return user;
	}
	
	/**
	 * Method for getting user membership choice and 
	 * updating the user's membership.
	 * @param user
	 * @return
	 */
	public static User changePayment(User user) {
		System.out.println("Enter valid credit card number.");
		String cc = inputUtil.getString();
		while (!Payment.validateCardNumber(cc)) {
			System.out.println("Invalid. Enter valid credit card number.");
			cc = inputUtil.getString();
		}
		user.setCreditCard(cc);
		return user;
	}

	/*
	 * Internal Method
	 */
	
	/**
	 * private method for obtaining membership choice, called by changeMembership
	 * @return Membership enum
	 */
	private static Membership selectMembership() {		
		int counter = 0;
		for (Membership m: Membership.values()) {
			if (m.equals(Membership.NONE)) {
			}
			else {
			System.out.println(counter + ". " + m + " - $" + m.getPrice() +" every " + m.getDuration() + " days. $" + m.getPricePerRide() + " per " 
					+ m.getFreeRideDuration() + " minute ride.");
			counter++;
			}
		}
		
			System.out.println("Pick a membership.");
			String selection = "";
			while(true) {
				selection = inputUtil.getString();
				switch (selection) {
					case "0":
						return Membership.PAY_PER_RIDE;
					case "1":
						return Membership.DAY;
					case "2":
						return Membership.MONTH;
					case "3":
						return Membership.YEAR;
					case "4":
						return Membership.FOUNDER;
					default: 
						System.out.println("Enter number in range [0-4]");
				}
			}
		}
}
