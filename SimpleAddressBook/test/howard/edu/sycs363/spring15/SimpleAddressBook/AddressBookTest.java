package howard.edu.sycs363.spring15.SimpleAddressBook;

import static org.junit.Assert.*;

import java.sql.SQLException;

import howard.edu.sycs363.spring15.SimpleAddressBook.AddressBook.PrintMode;

import org.junit.Test;

public class AddressBookTest {

	@Test
	public void testEditEntry() {
		try
		{
			AddressBookConnection.purgeEntireDb();
			AddressBook addressBook = new AddressBook("Home");
			
			addressBook.addNewEntry(new Person("Smith", "Jane", "address1", "city1", "state1", "zip1", "phone1"));
			
			//Now, editing Jane's information:
			Person jane = addressBook.getEntry("Smith").get(0);
			int oldJaneHash = jane.hashCode();
			jane.setAddress("address2");
			jane.setCity("city2");
	
			addressBook.editEntry(oldJaneHash, jane);
			
			// Retrieve Jane's entry in the address book again
			jane = addressBook.getEntry("Smith").get(0);
			
			assertEquals("New address of Jane should be 'address2'", jane.getAddress(), "address2");
			assertEquals("New city of Jane should be 'city2'", jane.getCity(), "city2");	
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		finally {
			// clean up test mess
			try {
				AddressBookConnection.purgeEntireDb();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testAddNewEntry() {
		try
		{
			AddressBookConnection.purgeEntireDb();
			AddressBook addressBook = new AddressBook("Home");
			
			addressBook.addNewEntry(new Person("Doe", "John", "address0", "city0", "state0", "zip0", "phone0"));
			// Retrieve John's entry in the address book
			Person john = addressBook.getEntry("Doe").get(0);
			
			assertEquals("The last name of the person should be 'Doe'", john.getLastName(), "Doe");
			assertEquals("The first name of the person should be 'John'", john.getFirstName(), "John");
			assertEquals("The address of the person should be 'address0'", john.getAddress(), "address0");
			assertEquals("The city of the person should be 'city0'", john.getCity(), "city0");
			assertEquals("The address of the person should be 'state0'", john.getState(), "state0");
			assertEquals("The address of the person should be 'zip0'", john.getZipCode(), "zip0");
			assertEquals("The address of the person should be 'phone0'", john.getPhoneNumber(), "phone0");
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		finally {
			// clean up test mess
			try {
				AddressBookConnection.purgeEntireDb();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testDeleteEntry() {
		try
		{
			AddressBookConnection.purgeEntireDb();
			AddressBook addressBook = new AddressBook("Home");
			
			addressBook.addNewEntry(new Person("Doe", "John", "address0", "city0", "state0", "zip0", "phone0"));
			
			// Retrieve John's entry in the address book
			Person john = addressBook.getEntry("Doe").get(0);
			// Delete from address list
			addressBook.deleteEntry(john);
			
			assertEquals("The list should be empty", addressBook.getEntry("Doe").size(), 0);
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		finally {
			// clean up test mess
			try {
				AddressBookConnection.purgeEntireDb();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testGetEntryLastName() {
		try
		{
			AddressBookConnection.purgeEntireDb();
			AddressBook addressBook = new AddressBook("Home");
			
			addressBook.addNewEntry(new Person("Doe", "John", "address0", "city0", "state0", "zip0", "phone0"));
			assertEquals("The list size should be 1", addressBook.getEntry("Doe").size(), 1);
			addressBook.addNewEntry(new Person("Harris", "John", "address1", "city1", "state1", "zip1", "phone1"));
			assertEquals("The list size should be 1", addressBook.getEntry("Doe").size(), 1);
			// After 'Jane Doe' is added, the size of the list returned should be 2
			addressBook.addNewEntry(new Person("Doe", "Jane", "address2", "city2", "state2", "zip2", "phone2"));
			assertEquals("The list size should be 2", addressBook.getEntry("Doe").size(), 2);
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		// clean up test mess
		finally {
			try {
				AddressBookConnection.purgeEntireDb();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testGetEntryLastAndFirstName() {
		try
		{
			AddressBookConnection.purgeEntireDb();
			AddressBook addressBook = new AddressBook("Home");
			
			addressBook.addNewEntry(new Person("Doe", "John", "address0", "city0", "state0", "zip0", "phone0"));
			assertEquals("The list size should be 1", addressBook.getEntry("Doe", "John").size(), 1);
			addressBook.addNewEntry(new Person("Doe", "Harris", "address1", "city1", "state1", "zip1", "phone1"));
			assertEquals("The list size should be 1", addressBook.getEntry("Doe", "John").size(), 1);
			// After another 'John Doe' is added (with different address, city, etc.), the size of the list returned should be 2
			addressBook.addNewEntry(new Person("Doe", "John", "address2", "city2", "state2", "zip2", "phone2"));
			assertEquals("The list size should be 2", addressBook.getEntry("Doe", "John").size(), 2);
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		// clean up test mess
		finally {
			try {
				AddressBookConnection.purgeEntireDb();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testPrintList() {
		// No way to test with PrintMode.Console, but the theory is the same with PrintMode.Database
		try
		{
			AddressBookConnection.purgeEntireDb();
			AddressBook addressBook = new AddressBook("Home");
			
			addressBook.addNewEntry(new Person("Doe", "John", "address0", "city0", "state0", "zip0", "phone0"));
			addressBook.addNewEntry(new Person("Doe", "Harris", "address1", "city1", "state1", "zip1", "phone1"));
			addressBook.addNewEntry(new Person("Doe", "John", "address2", "city2", "state2", "zip2", "phone2"));
			
			addressBook.printList(PrintMode.Database);
			
			// Opening another address book to load the saved info from database to ensure that it was indeed written to
			AddressBook anotherAddressBook = new AddressBook("Home");
			// The list size should be one if the address book was indeed saved to disk
			assertEquals("The list size should be 3", anotherAddressBook.getEntry("Doe").size(), 3);
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		// clean up test mess
		finally {
			try {
				AddressBookConnection.purgeEntireDb();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
