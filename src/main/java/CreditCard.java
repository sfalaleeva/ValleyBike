import java.time.LocalDate;

/**
 * Basic representation of a user's credit card.
 */
public class CreditCard {
	
	/**
	 * Credit card number
	 */
	private String creditCardNumber;
	
	/**
	 * Expiration Date in mm/yyyy format.
	 */
	private LocalDate expirationDate;
	
	/**
	 * Create new credit card.
	 * @param cc
	 * @param expirationDate
	 */
	public CreditCard(String cc, LocalDate expirationDate) {
		this.creditCardNumber = cc;
		this.expirationDate = expirationDate;
	}
	
	/* Accessors */
	
	/**
	 * Gets credit card expiration date.
	 * @return expirationDate as string
	 */
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	
	/**
	 * Gets credit card number.
	 * @return String - credit card number
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	
	/* Setters */
	
	/**
	 * Sets new credit card.
	 * @return true if successful
	 */
	public boolean updateCard(String cc, LocalDate expirationDate) {
			if (!cc.isEmpty() && Payment.validateCardNumber(cc) && Payment.validateCard(cc, expirationDate)) {
				this.creditCardNumber = cc;
				this.expirationDate = expirationDate;
			}
			else if (Payment.validateCard(this.creditCardNumber, this.expirationDate)) {
				// keep existing valid card
			}
			else {
				// neither option is valid and user account is inactive.
				this.creditCardNumber = null;
				this.expirationDate = null;
				return false;
			}
			return true;
	}

}

