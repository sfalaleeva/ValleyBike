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
		
		Address userAddress = getAddress();
		
		System.out.println("Date of Birth [yyyy-MM-dd]:");
		Date dob = inputUtil.getDate();
	
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
		
		System.out.println("Would you like to continue activiating your account?");
		if (inputUtil.getBool()) {
			user = changePayment(user);
			user = changeMembership(user); 
		}
		user.updateStatus();
		return user;
	}
	
	/**
	 * Get the information to create user object.
	 * @return Address
	 */
	public static Address getAddress() {
		System.out.print("Enter your address:\nStreet: ");
		String street = inputUtil.getString();
		System.out.print("City: ");
		String city = inputUtil.getString();
		System.out.println("Zip Code: ");
		String zip = inputUtil.getString();
		System.out.println("Country: ");
		String country = inputUtil.getString();
		return new Address(street, city, zip, country);
	}
	
	/**
	 * Change desired personal information for specific user account
	 * Made to be used by updateAccount() in ValleyBikeSim
	 * @param user - user whose info is being changed 
	 */
	public static void changePersonalInfo(User user) {
		System.out.println("What would you like to change? \n"
				+"0.Go back. \n1.First and last name. \n2.Address. \n3.Date of Birth."
				+"\n4.Phone number. \n5.Email. \n6.Password.");
		
		while(true) {
			String input = inputUtil.getString();
			switch (input) {
				case "0": 
					System.out.println("\n Going back. "); //is this print statement necessary? 
					break;
				case "1":
					//names
					System.out.println("First Name:");
					String fName = inputUtil.getString();
					System.out.println("Last Name:");
					String lName = inputUtil.getString();
					user.setFirstName(fName);
					user.setLastName(lName);
					System.out.println("Info saved! \n");
					break;
				case "2":
					//address
					Address userAddress = getAddress();
					user.setAddress(userAddress);
					System.out.println("Info saved! \n");
					break;
				case "3":
					//DOB
					System.out.println("Date of Birth [yyyy-MM-dd]:");
					Date dob = inputUtil.getDate();
					user.setDOB(dob);
					System.out.println("Info saved! \n");
					break;
				case "4":
					//Phone
					System.out.println("Phone [10 digits, no spaces or extra characters:]");
					String phone = inputUtil.getValidPhone();
					user.setPhone(phone);
					break;
				case "5":
					//email
					System.out.println("Email:");
					String email = inputUtil.getValidEmail();
					user.setEmail(email);
					break;
				case "6":
					//Pwd
					System.out.println("Password:");
					String pwd = inputUtil.getValidPassword();
					System.out.println("Retype Password:");
					
					String pwdRetype = inputUtil.getString();
					// check passwords match
					while (!pwd.equals(pwdRetype)) {
						System.out.println("Passwords do not match." + "\nRetype Password:");
						pwdRetype = inputUtil.getString();
					}
					user.setPassword(pwd);
					break;
				default: 
					System.out.print("\nInvalid input, please select a number within the 0-6 range.\n");
			}
		}
		
	}
	
	/**
	 * Method for getting user membership choice and 
	 * updating the user's membership.
	 * @param user
	 * @return
	 */
	public static User changeMembership(User user) {
		Membership m = selectMembership();
		user.addToBalance(m.getPrice());
		// charge user for their new membership
		if (user.chargeUser()) {
			user.updateMembership(m);
		}else {
			System.out.println("Could not process the payment.");
		}
		return user;
	}
	
	/**
	 * Method for getting user membership choice and 
	 * updating the user's membership.
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
		if (Payment.validateCard(cc)) {
			user.setCreditCard(cc);
		}else{
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
