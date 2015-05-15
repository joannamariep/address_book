package howard.edu.sycs363.spring15.SimpleAddressBook.Demo;

import java.sql.SQLException;

import howard.edu.sycs363.spring15.SimpleAddressBook.AddressBookConnection;

public class TestDBConnection {
	public static void main(String[] args)
	{
		try {
			@SuppressWarnings("unused")
			AddressBookConnection connection = new AddressBookConnection();
			System.out.println("Connection to MySQL DB was successful!");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not connect to MySQL DB! Tip: Ensure mysqld.exe is running process on this machine");
		}
	}
}