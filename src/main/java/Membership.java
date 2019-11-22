/**
 * Defines the types of memberships that a user can have.
 * @author G6
 */
public enum Membership {
	NONE (0,0f,0f,0),
	PAY_PER_RIDE (365,0.0f,2.0f,30),
	DAY (1, 6.0f, 0.0f, 30),
	MONTH (30, 20.0f, 0.0f, 45),
	YEAR (365,80.0f,0.0f,45),
	FOUNDER (365, 90.0f, 0.0f, 60);
	
	/** Length of the membership in days. */
	private final int duration; 
	
	/** Initial price. */
	private final float price;

	/** Price per ride. */
	private final float pricePerRide;
	
	/** Length of free ride in minutes. */
	private final int freeRideDuration;

	/** Overtime free for 1 minute. */
	private static final float overtime = 0.15f;
	
	/** Create enum membership constants. */
	Membership(int duration, float price, float pricePerRide,
			int freeRideDuration) {
		this.duration = duration;
		this.price = price;
		this.pricePerRide = pricePerRide;
		this.freeRideDuration = freeRideDuration;
	}
	
	/*
	 * Accessors
	 */
	
	/** Gets membership duration.
	 *  @return duration
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Gets membership price.
	 * @return price 
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * Gets price per ride.
	 * @return price per ride 
	 */
	public float getPricePerRide() {
		return pricePerRide;
	}
	
	/**
	 * Gets length of free ride in minutes.
	 * @return freeRideDuration
	 */
	public float getFreeRideDuration() {
		return freeRideDuration;
	}
			
}
