package howard.edu.sycs363.spring15.SimpleAddressBook.Demo;

import howard.edu.sycs363.spring15.SimpleAddressBook.AddressBook;
import howard.edu.sycs363.spring15.SimpleAddressBook.AddressBook.PrintMode;
import howard.edu.sycs363.spring15.SimpleAddressBook.AddressBook.SortType;
import howard.edu.sycs363.spring15.SimpleAddressBook.AddressBookConnection;
import howard.edu.sycs363.spring15.SimpleAddressBook.AddressBookManager;
import howard.edu.sycs363.spring15.SimpleAddressBook.Person;

import java.sql.SQLException;

public class Driver {	
	public static void createAddressBookWithHomeIdentifierAndPopulate(Boolean purgeDb)
	{
		try {
			if (purgeDb)
			{
				// Clean the db so that we are in a clean state
				AddressBookConnection.purgeEntireDb();
			}
			
			// Create an address book manager which will implicity create an address book
			// that can be identified as "Home"
			AddressBookManager addressBookManager = new AddressBookManager("Home");
			AddressBook addressBook = addressBookManager.getCurrentAddressBook();
			
			addressBook.addNewEntry(new Person("Doe", "John", "344 Woodson Drive", "Seattle", "WA", "76584", "111-111-1111"));
			addressBook.addNewEntry(new Person("Ash", "Mary", "577 Overlake Road", "Arlington", "TX", "32454", "222-222-2222"));
			addressBook.addNewEntry(new Person("Bradford", "Joe", "121 Santa Monica Boulevard", "Los Angeles", "CA", "32565", "333-333-3333"));
			
			addressBookManager.closeCurrentAddressBook();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createAddressBookWithWorkIdentifierAndPopulate(Boolean purgeDb)
	{
		try {
			if (purgeDb)
			{
				// Clean the db so that we are in a clean state
				AddressBookConnection.purgeEntireDb();
			}
			
			// Create an address book manager which will implicity create an address book
			// that can be identified as "Home"
			AddressBookManager addressBookManager = new AddressBookManager("Work");
			AddressBook addressBook = addressBookManager.getCurrentAddressBook();
			
			addressBook.addNewEntry(new Person("Webb", "Mark", "456 Martin Luther King Drive", "Washington", "DC", "65443", "111-111-1111"));
			addressBook.addNewEntry(new Person("Wickam", "Andy", "2213 Alley Way", "Lanham", "MD", "32565", "222-222-2222"));
			addressBook.addNewEntry(new Person("Richardson", "Cecily", "3345 Jacksonville Drive", "Des Moines", "IA", "56433", "333-333-3333"));
			addressBook.addNewEntry(new Person("Gupta", "Sanjay", "225 Redmond Way", "Saint Louis", "MO", "13456", "444-444-4444"));
			
			addressBookManager.closeCurrentAddressBook();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void populateWithHomeAndWorkAddressBooksThenEditWorkAddressBook()
	{
		try {
			Driver.createAddressBookWithHomeIdentifierAndPopulate(true);
			Driver.createAddressBookWithWorkIdentifierAndPopulate(false);
			
			System.out.println("DB Content (Before)");
			System.out.println();
			
			System.out.println("AddressBook with \"Home\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Home");
			System.out.println();
			
			System.out.println("AddressBook with \"Work\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Work");
			System.out.println();
			
			// Create an address book manager which will implicity create an address book
			// that can be identified as "Home"
			AddressBookManager addressBookManager = new AddressBookManager("Work");
			AddressBook addressBook = addressBookManager.getCurrentAddressBook();
			
			Person person = addressBook.getEntry("Richardson").get(0);
			int oldHashCode = person.hashCode();
			person.setAddress("2251 Sherman Ave");
			person.setState("District of Columbia");
			person.setCity("Washington");
			person.setZipCode("20059");
			
			addressBook.editEntry(oldHashCode, person);
			addressBookManager.closeCurrentAddressBook();
			
			System.out.println("DB Content (After)");
			System.out.println();
			
			System.out.println("AddressBook with \"Home\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Home");
			System.out.println();
			
			System.out.println("AddressBook with \"Work\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Work");
			System.out.println();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void populateWithHomeAddressBookThenDeleteAnEntry() {
		try {
			Driver.createAddressBookWithHomeIdentifierAndPopulate(true);
			
			System.out.println("DB Content (Before)");
			System.out.println();
			
			System.out.println("AddressBook with \"Home\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Home");
			System.out.println();
			
			AddressBookManager addressBookManager = new AddressBookManager("Home");
			AddressBook addressBook = addressBookManager.getCurrentAddressBook();
			addressBook.deleteEntry(addressBook.getEntry("Ash").get(0));
			
			addressBookManager.saveCurrentAddressBook();
			
			System.out.println("DB Content (After)");
			System.out.println();
			
			System.out.println("AddressBook with \"Home\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Home");
			System.out.println();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void populateWithHomeAddressBookThenSortByLastName() {
		try {
			Driver.createAddressBookWithHomeIdentifierAndPopulate(true);
			
			System.out.println("DB Content (Before)");
			System.out.println();
			
			System.out.println("AddressBook with \"Home\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Home");
			System.out.println();
			
			AddressBookManager addressBookManager = new AddressBookManager("Home");
			AddressBook addressBook = addressBookManager.getCurrentAddressBook();
			addressBook.sortList(SortType.LastName);
			
			addressBookManager.saveCurrentAddressBook();
			
			System.out.println("DB Content (After)");
			System.out.println();
			
			System.out.println("AddressBook with \"Home\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Home");
			System.out.println();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void populateWithWorkAddressBookThenSortByZipCode() {
		try {
			Driver.createAddressBookWithWorkIdentifierAndPopulate(true);
			
			System.out.println("DB Content (Before)");
			System.out.println();
			
			System.out.println("AddressBook with \"Work\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Work");
			System.out.println();
			
			AddressBookManager addressBookManager = new AddressBookManager("Work");
			AddressBook addressBook = addressBookManager.getCurrentAddressBook();
			addressBook.sortList(SortType.ZipCode);
			
			addressBookManager.saveCurrentAddressBook();
			
			System.out.println("DB Content (After)");
			System.out.println();
			
			System.out.println("AddressBook with \"Work\" Identifier");
			Driver.printCurrentDbContentWithIdentifier("Work");
			System.out.println();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void cleanUpDatabase() {
		try {
			AddressBookConnection.purgeEntireDb();
			System.out.println("Address Book DB has been cleansed");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printCurrentDbContentWithIdentifier(String identifier) {
		try {
			AddressBookManager addressBookManager = new AddressBookManager(identifier);
			addressBookManager.getCurrentAddressBook().printList(PrintMode.Console);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
