package howard.edu.sycs363.spring15.SimpleAddressBook;

import howard.edu.sycs363.spring15.SimpleAddressBook.AddressBook.PrintMode;

import java.sql.SQLException;

/**
 * @author Joanna Phillip
 */

/**
 * The AddressBookManager orchestrates/manages the core operations of the application. It is responsible for creating, opening, saving and 
 * closing address book(s) – essentially, it manages the lifecycle of all address books.
 */
public class AddressBookManager {
	/**
	 * The address book that is currently managed
	 */
	private AddressBook currentAddressBook = null;
	
	/**
	 * Initializes/creates an instance of an AddressBookManager (default)
	 */
	public AddressBookManager() {
		
	}
	
	/**
	 * Initializes/creates an instance of an AddressBookManager
	 * 
	 * @param	identifier				identifies the address book in the database that should be loaded
	 * @throws 	SQLException 			can be thrown if there is a sql error while connecting to the db or reading people's information from the database
	 * @throws 	ClassNotFoundException  can be thrown if the MySQL connector driver is not properly referenced
	 */
	public AddressBookManager(String identifier) throws ClassNotFoundException, SQLException {
		createAddressBook(identifier);
	}
	
	/**
	 * Creates an address book
	 * <p>
	 * Tries to create or open an address book which is identified by a unique identifier. If the current
	 * address book is identified by the specified identifier, then the call returns right away.
	 * 
	 * If this method is called while another different address book is opened, the opened address book 
	 * will be saved, and closed, before the newly created address book will be opened.
	 * </p>
	 * 
	 * @param	identifier				the path of the address book to be created on disk
	 * @throws 	SQLException 			can be thrown if there is a sql error while connecting to the db or reading people's information from the database
	 * @throws 	ClassNotFoundException 	can be thrown if the MySQL connector driver is not properly referenced
	 */
	public void createAddressBook(String identifier) throws ClassNotFoundException, SQLException {
		this.setCurrentAddressBook(identifier);
	}
	
	/**
	 * Gets the currently managed address book
	 * 
	 * @return	AddressBook	the currently managed address book
	 */
	public AddressBook getCurrentAddressBook() {
		return this.currentAddressBook;
	}
	
	/**
	 * Close the currently managed address book
	 * <p>
	 * This method internally calls saveAddressBook before in-memory information about the closing address book is cleaned up
	 * </p>
	 */
	public void closeCurrentAddressBook() {
		saveCurrentAddressBook();
		this.currentAddressBook = null;
	}
	
	/**
	 * Save the currently managed address book
	 * <p>
	 * Saves the currently opened address book’s in-memory content to a database. This will call the 
	 * printList method on the AddressBook passing PrintMode.Database option.
	 * </p>
	 */
	public void saveCurrentAddressBook() {
		if (this.currentAddressBook != null) {
			this.currentAddressBook.printList(PrintMode.Database);
		}		
	}
	
	private void setCurrentAddressBook(String identifier) throws ClassNotFoundException, SQLException {
		if (this.currentAddressBook != null) {
			if (this.currentAddressBook.getIdentifier().equals(identifier))
			{
				return;
			}
			
			closeCurrentAddressBook();
		}
		
		this.currentAddressBook = new AddressBook(identifier);
	}
}
