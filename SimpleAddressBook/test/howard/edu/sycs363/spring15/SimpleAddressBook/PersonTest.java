package howard.edu.sycs363.spring15.SimpleAddressBook;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testGetLastName() {
		Person person = new Person("Doe", "John", "", "", "", "", "");
		assertEquals("The last name of the person should be 'Doe'", person.getLastName(), "Doe");
	}

	@Test
	public void testGetFirstName() {
		Person person = new Person("Doe", "John", "", "", "", "", "");
		assertEquals("The first name of the person should be 'John'", person.getFirstName(), "John");
	}

	@Test
	public void testGetAddress() {
		Person person = new Person("Doe", "John", "address ADDRESS address", "", "", "", "");
		assertEquals("The returned address is incorrect", person.getAddress(), "address ADDRESS address");
	}

	@Test
	public void testSetAddress() {
		Person person = new Person("Doe", "John", "address ADDRESS address", "", "", "", "");
		assertEquals("The returned address is incorrect", person.getAddress(), "address ADDRESS address");
		person.setAddress("ADDRESS address ADDRESS");
		assertEquals("The returned address is incorrect", person.getAddress(), "ADDRESS address ADDRESS");
	}

	@Test
	public void testGetCity() {
		Person person = new Person("Doe", "John", "", "city", "", "", "");
		assertEquals("The city of the person should be 'city'", person.getCity(), "city");
	}

	@Test
	public void testSetCity() {
		Person person = new Person("Doe", "John", "", "city", "", "", "");
		assertEquals("The city of the person should be 'city'", person.getCity(), "city");
		person.setCity("CITY");
		assertEquals("The city of the person should be 'CITY'", person.getCity(), "CITY");
	}

	@Test
	public void testGetState() {
		Person person = new Person("Doe", "John", "", "", "state", "", "");
		assertEquals("The state of the person should be 'state'", person.getState(), "state");
	}

	@Test
	public void testSetState() {
		Person person = new Person("Doe", "John", "", "", "state", "", "");
		assertEquals("The state of the person should be 'state'", person.getState(), "state");
		person.setState("STATE");
		assertEquals("The state of the person should be 'STATE'", person.getState(), "STATE");
	}

	@Test
	public void testGetZipCode() {
		Person person = new Person("Doe", "John", "", "", "", "00000", "");
		assertEquals("The zip code of the person should be '00000'", person.getZipCode(), "00000");
	}

	@Test
	public void testSetZipCode() {
		Person person = new Person("Doe", "John", "", "", "", "00000", "");
		assertEquals("The zip code of the person should be '00000'", person.getZipCode(), "00000");
		person.setZipCode("99999");
		assertEquals("The zip code of the person should be '99999'", person.getZipCode(), "99999");
	}

	@Test
	public void testGetPhoneNumber() {
		Person person = new Person("Doe", "John", "", "", "", "", "000-000-0000");
		assertEquals("The phone number of the person should be '000-000-0000'", person.getPhoneNumber(), "000-000-0000");
	}

	@Test
	public void testSetPhoneNumber() {
		Person person = new Person("Doe", "John", "", "", "", "", "000-000-0000");
		assertEquals("The phone number of the person should be '000-000-0000'", person.getPhoneNumber(), "000-000-0000");
		person.setPhoneNumber("999-999-9999");
		assertEquals("The zip code of the person should be '999-999-9999'", person.getPhoneNumber(), "999-999-9999");
	}

}
