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
	private String expirationDate;
	
	/**
	 * Create new credit card.
	 * @param cc
	 * @param expirationDate
	 */
	public CreditCard(String cc, String expirationDate) {
		this.creditCardNumber = "";
		this.expirationDate = "";
	}
	
	/* Accessors */
	
	/**
	 * Gets credit card expiration date.
	 * @return expirationDate as string
	 */
	public String getExpirationDate() {
		return expirationDate;
	}
	
	/**
	 * Gets credit card number.
	 * @return credit card number
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	
	/* Setters */
	
	/**
	 * Sets new credit card.
	 * @return true if successful
	 */
	public boolean updateCard(String cc, String expirationDate) {
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

