import java.time.LocalDate;

/**
 * Abstraction of payment related functionality
 * such as validating a credit card and a credit 
 * card number.
 * @author G6
 *
 */
public final class Payment {

	private Payment() {}
	
	/**
	 * Validates a string that is a credit card number.
	 * @param creditCard
	 * @return true if string is a valid credit card number
	 */
	public static boolean validateCardNumber(String creditCard) {
		if (creditCard.matches("\\d{16}")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Abstraction of credit card validation. Check if card has expired. Returns 
	 * true 99% of the time.
	 * @param creditCard
	 * @return true if valid credit card
	 */
	public static boolean validateCard(String creditCard, LocalDate expirationDate) {
		if (expirationDate != null && LocalDate.now().isBefore(expirationDate) && Math.random() < 0.99) {
			return true;
		}
		return false;
	}

	/**
	 * Abstraction for charging a credit card if it is valid.
	 * @param creditCard
	 * @return true if charge succeeds.
	 */
	public static boolean chargeCard(CreditCard card) {
		return validateCard(card.getCreditCardNumber(), card.getExpirationDate());
	}
}
