import java.awt.Point;

/**
 * Represents an user or station address.
 * @author G6
 */
public class Address {
	/**
	 * String for address street.
	 */
	private String street;
	
	/**
	 * String for address city.
	 */
	private String city;
	
	/**
	 * String for address state.
	 */
	private String state;
	
	/**
	 * String for address zip code.
	 */
	private String zip;
	
	/**
	 * String for address country
	 */
	private String country;
	
	/**
	 * String for address coordinates for use in station map.
	 */
	private Point coordinates;
	
	
	/**
	 * Constructor for address.
	 * @param street
	 * @param city
	 * @param zip
	 * @param state
	 * @param country
	 * @param country2 
	 */
	public Address(String street, String city, String state, String zip, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}
	
	/*
	 * Accessor Methods
	 */

	/**
	 * Gets street.
	 * @return - street
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Gets city.
	 * @return - city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Gets state.
	 * @return - state
	 */
	public String getState() {
		return state;
	}
	
	
	/**
	 * Gets zip code.
	 * @return - zip code
	 */
	public String getZip() {
		return zip;
	}
	
	/**
	 * Gets country.
	 * @return - country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Gets address coordinates.
	 * @return - coordinates
	 */
	public Point getCoordinates() {
		return coordinates;
	}
	
	/**
	 * returns string of the address
	 * @return - string of address
	 */
	public String getAddress() {
		String addr = this.street +", " + this.city +", " + this.state +", " + this.zip +", " + this.country;
		return addr;
	}
	
	/*
	 * Setters
	 */
	
	/**
	 * Sets the street.
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * Sets the city.
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Sets the state.
	 * @param city
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Sets the zip.
	 * @param zip
	 */
	public void setZip(String zip) {
		if (inputUtil.validateWithRegex(zip, inputUtil.VALID_ZIPCODE_REGEX)) {
			this.zip = zip;	
		}
	}
	
	/**
	 * Sets the country.
	 * @param country.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Sets the issue's street.
	 * @param street.
	 */
	public void setCoordinates(int x, int y) {
		this.coordinates.setLocation(x,y);
	}
	
	/**
	 * OTHER METHODS
	 * 
	 */
	
	/**
	 * prints address in reasonable format
	 */
	
	public void printAddress() {
		System.out.print(this.street +", " + this.city +", " + this.state +", " + this.zip +", " + this.country);
	}
	
	
}
