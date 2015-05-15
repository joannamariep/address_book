package howard.edu.sycs363.spring15.SimpleAddressBook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Joanna Phillip
 */

/**
 * An AdressBook class object holds information about a particular instance of an address book. The address book maintains/manages 
 * a list of Person objects that constitute an address list. The main responsibilities of this class include adding a new Person 
 * object to the address list, changing “mutable” (all properties except first and last name) information on a Person object, and 
 * deleting person object(s) from the maintained list. It also sorts the maintained address list by last name, and by zip code. 
 * And lastly, it saves the contents of the address list to a database, and or prints to a console output.
 */
public class AddressBook {
	/**
	 * The ways in which an address book can be sorted
	 */
	public enum SortType { LastName, ZipCode };
	
	/**
	 * The ways in which an address book can be printed
	 */
	public enum PrintMode { Database, Console };
	
	/**
	 * Maintains an ordered map of person-hash--to--person objects which comprise of the address list
	 */
	private Map<Integer, Person> addressList = new LinkedHashMap<Integer, Person>();
	
	/**
	 * Abstract queries for database requests
	 */
	private AddressBookConnection addressBookConnection = null;
	
	/**
	 * The unique identifier for the address book that is persisted in the database
	 */
	private String identifier = null;
	
	/**
	 * Initializes/creates an instance of an AddressBook which holds a list of persons maintained in
	 * an address list
	 * 
	 * @param	identifier				identifies the address book in the database that should be loaded
	 * @throws 	SQLException 			can be thrown if there is a sql error while connecting to the db or reading people's information from the database
	 * @throws 	ClassNotFoundException  can be thrown if the MySQL connector driver is not properly referenced
	 */
	public AddressBook(String identifier) throws ClassNotFoundException, SQLException {
		
		this.addressBookConnection = new AddressBookConnection();
		this.identifier = identifier;
		List<Person> people = this.addressBookConnection.getPeopleWithIdentifier(identifier);
		fillDictionaryMap(people);
	}
	
	/**
	 * Edit an entry in the address list
	 * <p>
	 * This method tends to disguise edits by replacement. When a match is found in the map (using the
	 * hash code) the existing mapped value is replaced by the updated person object that is passed as
	 * a parameter. If oldHashCode is not a key is the map, then the function will return gracefully
	 * immediately.
	 * </p>
	 * 
	 * @param	oldHashCode		the hash code of the soon-to-be edited person object in the list
	 * @param	updatedPerson	the replacement person object with the latest edits
	 */
	public void editEntry(int oldHashCode, Person updatedPerson) {
		if (this.addressList.get(oldHashCode) != null) {
			this.addressList.remove(oldHashCode);
			this.addressList.put(updatedPerson.hashCode(), updatedPerson);
		}
	}
	
	/**
	 * Add a new person to the address list
	 * <p>
	 * If the person's hash code already exists as a key in the address list map (in which case, it is 
	 * a duplicate), then a simple "like-for-like" replacement will occur.
	 * </p>
	 * 
	 * @param	person	the new person to be added to the address list
	 */
	public void addNewEntry(Person person) {
		this.addressList.put(person.hashCode(), person);
	}
	
	/**
	 * Delete a person from the address list
	 * <p>
	 * If the person does not exist in the address map, this method will
	 * return gracefully
	 * </p>
	 * 
	 * @param	person	the person to be deleted from the list
	 */
	public void deleteEntry(Person person) {
		this.addressList.remove(person.hashCode());
	}
	
	/**
	 * Gets the Person(s) with the specified last name
	 * <p>
	 * Since lastName cannot be unique, getEntry returns a list of person objects 
	 * with the specified last name. An empty list is returned if there is no match.
	 * </p>
	 * 
	 * @param	lastName	the matching last name of the person(s) in the list
	 * @return				a list of persons that match the given last name
	 */
	public ArrayList<Person> getEntry(String lastName) {
		ArrayList<Person> list = new ArrayList<Person>();
		
		Iterator<Entry<Integer, Person>> it = this.addressList.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, Person> pair = (Map.Entry<Integer, Person>)it.next();
			if (pair.getValue().getLastName().equals(lastName)) {
				list.add(pair.getValue());
			}
		}
		
		return list;
	}
	
	/**
	 * Gets the Person(s) with the specified last and first names
	 * <p>
	 * Since lastName+firstName cannot be unique, getEntry returns a list of person objects 
	 * with the specified lastName+firstName. An empty list is returned if there is no match.
	 * </p>
	 * 
	 * @param	lastName	the matching last name of the person(s) in the list
	 * @param	firstName	the matching first name of the person(s) in the list
	 * @return				a list of persons that match the given last name and first name
	 */
	public ArrayList<Person> getEntry(String lastName, String firstName) {
		ArrayList<Person> list = new ArrayList<Person>();
		
		Iterator<Entry<Integer, Person>> it = this.addressList.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, Person> pair = (Map.Entry<Integer, Person>)it.next();
			Person person = pair.getValue();
			if (person.getLastName().equals(lastName) && person.getFirstName().equals(firstName)) {
				list.add(pair.getValue());
			}
		}
		
		return list;
	}
	
	/**
	 * Sorts the address list based on the specified sort type
	 * <p>
	 * Sorts the maintained list based on the AddressListSortType enumeration provided – LastName or ZipCode
	 * </p>
	 * 
	 * @param	sortType	The specified sort type to be applied on the list
	 */
	public void sortList(SortType sortType) {
		List<Person> people = new ArrayList<Person>(this.addressList.values());
		
		switch(sortType)
		{
			case LastName:
			{
				Collections.sort(people, new Comparator<Person>() {
					public int compare(Person personA, Person personB) {
						return personA.getLastName().compareTo(personB.getLastName());
					}
				});
			} break;
			case ZipCode:
			{
				Collections.sort(people, new Comparator<Person>() {
					public int compare(Person personA, Person personB) {
						return personA.getZipCode().compareTo(personB.getZipCode());
					}
				});
			} break;
		}
		
		this.addressList.clear();
		
		for(Iterator<Person> it = people.iterator(); it.hasNext(); ) {
		    Person person = it.next();
		    this.addressList.put(person.hashCode(), person);
		}
	}
	
	/**
	 * Prints the address list based on a specified mode
	 * <p>
	 * Prints the maintained address list based on the PrintMode enumeration provided – Database or Console
	 * </p>
	 * 
	 * @param	printMode	The specified mode to print the address list
	 */
	public void printList(PrintMode printMode) {
		switch(printMode)
		{
			case Database:
			{
				try {
					saveToDatabase();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				
			} break;
			case Console:
			{
				printToConsole();
			} break;
		
		}
	}
	
	/**
	 * Gets the identifier of the loaded address book used to query the database
	 * 
	 * @return the identifier of this address book
	 */
	public String getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Delete the contents persisted in the db
	 * 
	 * @throws SQLException	can be thrown if there is a sql error while trying to access people's information in the database
	 */
	public void purgeBackingDbContent() throws SQLException {
		this.addressBookConnection.purgeDbWithIdentifier(identifier);
	}
	
	private void fillDictionaryMap(List<Person> people) {
		for (Iterator<Person> it = people.iterator(); it.hasNext();) {
			Person newPerson = it.next();
			this.addressList.put(newPerson.hashCode(), newPerson);
		}
	}
	
	private void saveToDatabase() throws SQLException {
		
		this.addressBookConnection.deletePeopleWithIdentifier(this.identifier);
		
		List<Person> people = new ArrayList<Person>(this.addressList.values());
		this.addressBookConnection.savePeopleWithIdentifier(people, this.identifier);
	}
	
	private void printToConsole() {
		Iterator<Entry<Integer, Person>> it = this.addressList.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, Person> pair = (Map.Entry<Integer, Person>)it.next();
			
			Person person = pair.getValue();
			System.out.println(person.getLastName() + ", " + person.getFirstName() + ", " +
					     person.getAddress() + ", " + person.getCity() + ", " +
						 person.getState() + ", " + person.getZipCode() + ", " +
					     person.getPhoneNumber());
		}
	}
}

