package howard.edu.sycs363.spring15.SimpleAddressBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mysql.jdbc.Statement;

/**
 * @author Joanna Phillip
 */

/**
 * The AddressBookConnection class is an abstraction layer over consumable db queries pertaining to retrieving
 * specific information stored in the address_book database (specifically in the address_info table). Exposed functionality
 * include retrieving, deleting and inserting entries into the address_info table.
 */
public class AddressBookConnection {
	/**
	 * Represents the connection with the underlying MySQL database that holds the ADDRESS_INFO table
	 */
	Connection connection = null;
	
	/**
	 * The class driver representing JDBC API for MySQL
	 */
	String driverName = "com.mysql.jdbc.Driver";
	
	/**
	 * The username required for connection to DB
	 */
	String username = "joannamariep";
	
	/**
	 * The password required for connection to DB
	 */
	String password = "large_scale";
	
	/**
	 * The connection string required for connection to DB
	 */
	String url = "jdbc:mysql://localhost/address_book";
	
	/**
	 * Attempts to make a connection to the db hosting all information for address books. The constructor will
	 * throw an exception if the attempt fails
	 * 
	 * @throws ClassNotFoundException 	can be thrown if the MySQL connector driver is not properly referenced
	 * @throws SQLException				can be thrown if there is a sql error while connecting to the db
	 */
	public AddressBookConnection() throws ClassNotFoundException, SQLException
	{
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
			throw e;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Get a list of people that were identified by the provided identifier
	 * 
	 * @param identifier		identifies address book entries belonging to a unique address book identified by the identifier
	 * @return					the list of people in the address book that have been marked with the identifier. Returns an empty list, if there is no associated entry
	 * @throws SQLException		can be thrown if there is a sql error while trying to access people's information in the database
	 */
	public List<Person> getPeopleWithIdentifier(String identifier) throws SQLException
	{
		List<Person> people = new ArrayList<Person>();
		Statement stmt = null;
		String query = "SELECT * FROM ADDRESS_INFO WHERE IDENTIFIER='"+identifier+"'";
		try {
			stmt = (Statement) connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Person newPerson = new Person(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7));
				
				people.add(newPerson);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		finally {
			stmt.close();
		}
		
		return people;
	}
	
	/**
	 * Delete people that were identified by the provided identifier
	 * 
	 * @param identifier	identifies address book entries belonging to a unique address book identified by the identifier
	 * @throws SQLException	can be thrown if there is a sql error while trying to access people's information in the database
	 */
	public void deletePeopleWithIdentifier(String identifier) throws SQLException
	{
		Statement stmt = null;
		String query = "DELETE FROM ADDRESS_INFO WHERE IDENTIFIER='"+identifier+"'";
		try {
			stmt = (Statement) connection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			stmt.close();
		}
	}
	
	/**
	 * Save the list of people that are identified by the provided identifier.
	 * <p>
	 * NOTE: The existing information, in the DB, of people that can be identified by the provided identifier will be flushed/deleted
	 * before provided list is saved to the DB
	 * </p>
	 * 
	 * @param 	people			list of people that will be saved into the database
	 * @param 	identifier		identifies address book entries belonging to a unique address book identified by the identifier
	 * @throws 	SQLException	can be thrown if there is a sql error while trying to access people's information in the database
	 */
	public void savePeopleWithIdentifier(List<Person> people, String identifier) throws SQLException
	{
		Statement stmt = null;
		try {
			stmt = (Statement) connection.createStatement();
			for (Iterator<Person> it = people.iterator(); it.hasNext();) {
				Person person = it.next();
				String query = "INSERT INTO ADDRESS_INFO "
								+ "VALUES ('"
								+ person.getLastName() + "','" 
								+ person.getFirstName() + "','"
								+ person.getAddress() + "','"
								+ person.getCity() + "','"
								+ person.getState() + "','"
								+ person.getZipCode() + "','"
								+ person.getPhoneNumber() + "','"
								+ identifier + "')";
				stmt.executeUpdate(query);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			stmt.close();
		}
	}
	
	/**
	 * Delete all rows associated with the specified identifier in the address_info table
	 * 
	 * @param identifier	identifies address book entries belonging to a unique address book identified by the identifier
	 * @throws SQLException	can be thrown if there is a sql error while trying to access people's information in the database
	 */
	public void purgeDbWithIdentifier(String identifier) throws SQLException
	{
		Statement stmt = null;
		String query = "DELETE FROM ADDRESS_INFO WHERE IDENTIFIER='"+identifier+"'";
		try {
			stmt = (Statement) connection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			stmt.close();
		}
	}
	
	/**
	 * Delete all rows in the address_info table
	 * 
	 * @throws SQLException				can be thrown if there is a sql error while trying to access people's information in the database
	 * @throws ClassNotFoundException	can be thrown if the MySQL connector driver is not properly referenced
	 */
	public static void purgeEntireDb() throws SQLException, ClassNotFoundException
	{
		try {
			AddressBookConnection connection = new AddressBookConnection();
			connection.purgeEnterDbInternal();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Closes the connection when this object is getting disposed
	 */
	protected void finalize() throws Throwable {
		connection.close();
		super.finalize();
	}
	
	private void purgeEnterDbInternal() throws SQLException
	{
		Statement stmt = null;
		String query = "DELETE FROM ADDRESS_INFO";
		try {
			stmt = (Statement) connection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			stmt.close();
		}
	}
}