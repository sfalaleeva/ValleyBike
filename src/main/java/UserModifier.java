import java.time.LocalDate;

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
		
		Address userAddress = inputUtil.getAddress();
		
		LocalDate dob = getDOB();
	
		System.out.println("Phone [10 digits, no spaces or extra characters:]");
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
		
		User user = new User(fName, lName, userAddress, dob, phone, email, pwd);
		user.setUserID(ValleyBikeSim.usersMap.lastKey()+1);
		
		System.out.println("Would you like to enter payment information and select a membership? Enter y/n");
		if (inputUtil.getBool()) {
			changePayment(user);
			changeMembership(user); 
		}
		// the status is automatically updated when user.getIsActive() is called.
		return user;
	}
	
	/**
	 * Gets DOB from user.
	 * @return DOB
	 */
	private static LocalDate getDOB() {
		System.out.println("Date of Birth [yyyy-[m]m-[d]d]:");
		String dobString = inputUtil.getValidDateString();
		return inputUtil.toLocalDate(dobString, inputUtil.LOCAL_DATE_FORMAT);
	}
	
	/**
	 * Change desired personal information for specific user account
	 * Made to be used by updateAccount() in ValleyBikeSim
	 * @param user - user whose info is being changed 
	 */
	public static void changePersonalInfo(User user) {
		System.out.println("What would you like to change? \n"
				+"0. Go back\t\t4. Phone number"
				+ "\n1. First and last name\t5. Email"
				+ "\n2. Address\t\t6. Password"
				+ "\n3. Date of Birth.");
		
		while(true) {
			int input = inputUtil.getIntInRange(0,6, "menu option");
			switch (input) {
				case 0: 
					System.out.println("\n Going back. "); 
					break;
				case 1:
					//names
					System.out.println("New First Name:");
					String fName = inputUtil.getString();
					System.out.println("New Last Name:");
					String lName = inputUtil.getString();
					user.setFirstName(fName);
					user.setLastName(lName);
					System.out.println("Info saved! \n");
					break;
				case 2:
					//address
					Address userAddress = inputUtil.getAddress();
					user.setAddress(userAddress);
					System.out.println("Info saved! \n");
					break;
				case 3:
					//DOB
					LocalDate dob = getDOB();
					user.setDOB(dob);
					System.out.println("Info saved! \n");
					break;
				case 4:
					//Phone
					System.out.println("New Phone [10 digits, no spaces or extra characters:]");
					String phone = inputUtil.getValidPhone();
					user.setPhone(phone);
					break;
				case 5:
					//email
					System.out.println("New Email:");
					String email = inputUtil.getValidEmail();
					user.setEmail(email);
					break;
				case 6:
					//Pwd
					System.out.println("New Password:");
					String pwd = inputUtil.getValidPassword();
					System.out.println("Retype New Password:");
					
					String pwdRetype = inputUtil.getString();
					// check passwords match
					while (!pwd.equals(pwdRetype)) {
						System.out.println("Passwords do not match." + "\nRetype New Password:");
						pwdRetype = inputUtil.getString();
					}
					user.setPassword(pwd);
					break;
				default: 
					System.out.print("\nInvalid input, please select a number within the 0-6 range.\n");
			}	
			break;
		}
		
	}
	
	/**
	 * Method for getting user membership choice and 
	 * updating the user's membership.
	 * @param user
	 * @return
	 */
	public static void changeMembership(User user) {
		Membership m = selectMembership();
		user.addToBalance(m.getPrice());
		// if able to charge user for their new membership, we update the membership
		if (user.chargeUser()) {
			user.updateMembership(m); // also updates expiration date
		}else {
			System.out.println("Could not process the payment. Membership not changed.");
			user.refundToBalance(m.getPrice());
		}
	}
	
	/**
	 * Method for getting user payment details.
	 * @param user
	 * @return
	 */
	public static User changePayment(User user) {
		System.out.println("Enter valid credit card number (16 digits):");
		String cc = inputUtil.getString();
		while (!Payment.validateCardNumber(cc)) {
			System.out.println("Invalid. Enter valid credit card number.");
			cc = inputUtil.getString();
		}
		System.out.println("Enter credit card expiration date. [yyyy-[m]m]");
		String expirationString = inputUtil.getValidExpirationDateString();
		LocalDate expirationDate = inputUtil.toLocalDate(expirationString, inputUtil.LOCAL_DATE_FORMAT);
		if (!user.setCreditCard(cc, expirationDate)) {
			System.out.println("Card validation failed.");
		}
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
			while(true) {
				int selection = inputUtil.getIntInRange(0,4,"membership option");
				switch (selection) {
					case 0:
						return Membership.PAY_PER_RIDE;
					case 1:
						return Membership.DAY;
					case 2:
						return Membership.MONTH;
					case 3:
						return Membership.YEAR;
					case 4:
						return Membership.FOUNDER;
					default: 
						System.out.println("Enter number in range [0-4]");
				}
			}
		}
}
