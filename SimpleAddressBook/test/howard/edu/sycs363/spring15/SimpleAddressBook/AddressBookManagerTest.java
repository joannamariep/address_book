package howard.edu.sycs363.spring15.SimpleAddressBook;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class AddressBookManagerTest {

	@Test
	public void testCreateAddressBook() {
		AddressBookManager manager = new AddressBookManager();
		try {
			manager.createAddressBook("Home");
			assertNotEquals("The current address book should not be null", manager.getCurrentAddressBook(), null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
	}

	@Test
	public void testGetCurrentAddressBook() {
		AddressBookManager manager = new AddressBookManager();
		try {
			manager.createAddressBook("Home");
			assertNotEquals("The current address book should not be null", manager.getCurrentAddressBook(), null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
	}

	@Test
	public void testCloseCurrentAddressBook() {
		AddressBookManager manager = new AddressBookManager();
		try {
			manager.createAddressBook("Home");
			manager.closeCurrentAddressBook();
			assertEquals("The current address book should be null", manager.getCurrentAddressBook(), null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
	}

	@Test
	public void testSaveCurrentAddressBook() {
		try
		{
			AddressBookConnection.purgeEntireDb();
			AddressBookManager manager = new AddressBookManager("Home");
			AddressBook addressBook = manager.getCurrentAddressBook();
			
			addressBook.addNewEntry(new Person("Doe", "John", "address0", "city0", "state0", "zip0", "phone0"));
			
			manager.closeCurrentAddressBook(); // internally calls saveCurrentAddressBook
			
			// Opening address book again to load the saved file from disk to ensure that it was indeed written to
			manager.createAddressBook("Home");
			// The list size should be one if the address book was indeed saved to a database
			assertEquals("The list size should be 1", manager.getCurrentAddressBook().getEntry("Doe").size(), 1);
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

}
