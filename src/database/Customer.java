package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// a customer is a database element (inherits properties such as reading and writing to a text file)
public class Customer extends DatabaseElement {
	// declare instance variables to store our customers information
	private String firstName;
	private String lastName;
	private String userName;
	private String phoneNum;
	
	// store the list of transactions that the customer has taken part in
	private ArrayList<String> transactions = new ArrayList<String>();

	// default file path for all cutomer files
	private static final String FILE_PATH = "src/database/customers/";
	// create a file for the customer
	private File customer;

	// default constructor
	public Customer() {
	}

	// the customer will automatically load the customer's information from the necessary file
	public Customer(String fileName) {
		this.load(fileName);
	}

	// load the customers file and information
	@Override
	public boolean load(String customerName) {
		// remove the space from the customer's name and store their name
		this.formatCustomerName(customerName);
		
		try {
			// load the customer file
			this.customer = new File(FILE_PATH + this.userName);
			// instantiate instance variables (read from file)
			this.getInfo();
		} catch (FileNotFoundException e) {
			// if the customer is not in the database, return false
			return false;
		}

		// the customer has been successfully loaded, return true
		return true;
	}

	// create a new customer in the database
	public void create(String customerName, String phoneNum) throws IOException {
		// remove the space from the customer's name and store their name
		this.formatCustomerName(customerName);
		// store the customer's phone number
		this.phoneNum = phoneNum;
		// format the customer's phone number (add parenthesis around the area code and add spaces)
		this.formatPhoneNum();

		// declare the file path to the directory for the customer files
		File dir = new File(FILE_PATH);
		// make/locate the appropriate directory for the customer files
		dir.mkdirs();
		// instantiate the customer file to the customer's name
		this.customer = new File(dir, userName);
		// create  a permanent file for the new customer
		customer.createNewFile();

		// write all local instance variables to the customer's file
		this.write();
	}

	// instantiate instance variables with the values in the customer's file
	@Override
	public void getInfo() throws FileNotFoundException {
		// declare and instantiate a scanner for the customers file
		Scanner sc = new Scanner(this.customer);

		// instantiate the customer's first name
		this.firstName = sc.next();
		// if the next line is blank (no last name), then instantiate the last name to "" (empty string)
		// else, the customer has a last name, then instantiate the customer's last name to the name in the file
		if (sc.nextLine().isEmpty()) {
			this.lastName = "";
		}
		else {
			this.lastName = sc.next();
		}
		
		// instantiate instance variables to the values in the customer's file and format all fields properly
		this.updateUserName();
		this.phoneNum = sc.next();
		this.formatPhoneNum();

		// load all transaction numbers
		while (sc.hasNextLine()) {
			this.transactions.add(sc.next());
		}

		// close the scanner (finished reading)
		sc.close();
	}

	// update the values in the file to the instance variables of the customer
	@Override
	public void write() throws IOException {
		// declare and instantiate a filewriter to write to the customer's file
		FileWriter wr = new FileWriter(this.customer);

		// write the customers first and last name (seperate lines)
		wr.write(this.firstName + "\n");
		wr.write(this.lastName + "\n");
		// write the customers phone number (remove the parenthesis around the area code and spaces)
		String phoneNum = this.phoneNum.substring(1, 4) + this.phoneNum.substring(6, 9) + this.phoneNum.substring(10);
		wr.write(phoneNum + "\n");

		// write all of the transaction numbers
		for (int i = 0; i < this.transactions.size(); i++) {
			wr.write(this.transactions.get(i) + "\n");
		}

		// close the filewriter (finished writing)
		wr.close();
	}
	
	// update the values in the file to the instance variables of the customer, and draw the most recent transaction number
	public void write(String transactionNum) throws IOException {
		// write all of the instance variables
		this.write();

		// create a filewriter that appends to the customer file (rather than over writes it)
		FileWriter wr = new FileWriter(this.customer, true);
		// write the transaction number to the customer file
		wr.write(transactionNum);
		// close the writer (finished writing)
		wr.close();
	}


	// add parenthesis around the customer's area code and add spaces to the phone number
	private void formatPhoneNum() {
		this.phoneNum = "(" + this.phoneNum.substring(0, 3) + ") " + this.phoneNum.substring(3, 6) + " "
				+ this.phoneNum.substring(6);
	}

	// remove spaces from the customer name
	private void formatCustomerName(String customerName) {
		// if the customer has a first and last name, separate them and instantiate the respective instance variable
		// if the customer only has a first name, declare the last name to "" (blank string)
		if (customerName.contains(" ")) {
			this.firstName = customerName.substring(0, customerName.indexOf(" ")).toLowerCase();
			this.lastName = customerName.substring(customerName.indexOf(" ") + 1).toLowerCase();
		} else {
			this.firstName = customerName.toLowerCase();
			this.lastName = "";
		}

		// update the customer's username
		this.updateUserName();
	}

	// returns a string containing the first and last name of the customer and their phone number
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + " " + this.phoneNum;
	}

	// updates the first name of the customer to a new name
	// does not have implementation but will be used when the application has a screen to edit customer's files
	public void changeFirstName(String newName) throws IOException {
		// update the customer's first name instance variable
		this.firstName = newName;
		// write the changes to the customer's file
		this.write();
		// update the custome's user name
		this.updateUserName();
	}

	// updates the last name of the customer to a new name
	// does not have implementation but will be used when the application has a screen to edit customer's files
	public void changeLastName(String newName) throws IOException {
		// update the customer's last name instance variable
		this.lastName = newName;
		// write the changes to the customer's file
		this.write();
		// update the custome's user name
		this.updateUserName();
	}

	// updates the customer's user name to be the concatenation of their first and last name (in lower case)
	private void updateUserName() {
		this.userName = (this.firstName + this.lastName).toLowerCase();
	}

	// returns the customer's user name
	public String getUserName() {
		return this.userName;
	}

	// returns the customer's first name
	public String getFirstName() {
		return this.firstName;
	}

	// returns the customer's last name
	public String getLastName() {
		return this.lastName;
	}
	
	// add a new transaction to the list of the customer's transactions
	public void addTransaction(String transactionNum) {
		this.transactions.add(transactionNum);	
	}
}
