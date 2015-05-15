package howard.edu.sycs363.spring15.SimpleAddressBook.Demo;

public class CreateHomeAddressBookOnly {
	public static void main(String[] args) {
		Driver.createAddressBookWithHomeIdentifierAndPopulate(true);
		
		System.out.println("DB Content (After)");
		System.out.println();
		
		System.out.println("AddressBook with \"Home\" Identifier");
		Driver.printCurrentDbContentWithIdentifier("Home");
		System.out.println();
	}
}
