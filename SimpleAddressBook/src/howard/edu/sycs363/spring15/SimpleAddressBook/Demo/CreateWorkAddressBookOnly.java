package howard.edu.sycs363.spring15.SimpleAddressBook.Demo;

public class CreateWorkAddressBookOnly {
	public static void main(String[] args) {
		Driver.createAddressBookWithWorkIdentifierAndPopulate(true);
		
		System.out.println("DB Content (After)");
		System.out.println();
		
		System.out.println("AddressBook with \"Work\" Identifier");
		Driver.printCurrentDbContentWithIdentifier("Work");
		System.out.println();
	}
}
