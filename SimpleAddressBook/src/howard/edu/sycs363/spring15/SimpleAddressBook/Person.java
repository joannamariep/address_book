package howard.edu.sycs363.spring15.SimpleAddressBook;

import java.util.Objects;

/**
 * @author Joanna Phillip
 */

/**
 * The Person class holds information about a person – first name, last name, address, city, state, zip, and phone number. 
 * It does not expose setting values only for first and last name properties.
 */
public class Person {

	/**
	 * The last name of a person
	 */
	private String lastName;
	
	/**
	 * The first name of a person
	 */
	private String firstName;
	
	/**
	 * The address of a person
	 */
	private String address;
	
	/**
	 * The city of a person
	 */
	private String city;
	
	/**
	 * The state of a person
	 */
	private String state;
	
	/**
	 * The zip code of a person
	 */
	private String zipCode;
	
	/**
	 * The phone number of a person
	 */
	private String phoneNumber;
	
	/**
	 * Initializes/creates a new instance of Person object with a set of mandatory parameters
	 * 
	 * @param	lastName 	the last name of the person
	 * @param	firstName	the first name of the person
	 * @param	address		the address of the person
	 * @param	city		the city of the person
	 * @param	state		the state of the person
	 * @param	zipCode		the zip code of the person
	 * @param	phoneNumber	the phone number of the person
	 */
	public Person(String lastName, String firstName, String address, String city, String state, String zipCode, String phoneNumber)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.setAddress(address);
		this.setCity(city);
		this.setState(state);
		this.setZipCode(zipCode);
		this.setPhoneNumber(phoneNumber);
	}
	
	/**
	 * Gets the last name of a person
	 * 
	 * @return	a person's last name 
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Gets the first name of a person
	 * 
	 * @return	a person's first name 
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Gets the address of a person
	 * 
	 * @return	a person's address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address of a person
	 * 
	 * @param	address	the address of the person
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the city of a person
	 * 
	 * @return	a person's city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city of a person
	 * 
	 * @param	city	the city of the person
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the state of a person
	 * 
	 * @return a person's state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state of a person
	 * 
	 * @param	state	the state of the person
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the zip code of a person
	 * 
	 * @return	a person's zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the zip code of a person
	 * 
	 * @param	zipCode	the zip code of the person
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the phone number of a person
	 * 
	 * @return	a person's phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number of a person
	 * 
	 * @param	phoneNumber	the phone number of the person
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Unique identifier for a person object based on its last name, first name, address, city, state,
	 * zip code, and phone number. This is very important in a uniqueness comparison of two person objects.
	 *  
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(this.lastName, this.firstName, this.address, this.city, this.state, this.zipCode, this.phoneNumber);
	}
}