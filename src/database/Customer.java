package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends DatabaseElement {
	private String firstName;
	private String lastName;
	private String userName;

	private File customer;
	private String phoneNum;

	private ArrayList<String> transactions = new ArrayList<String>();

	private static final String FILE_PATH = "src/database/customers/";

	public Customer() {
	}

	public Customer(String fileName) {
		this.load(fileName);
	}

	@Override
	public boolean load(String customerName) {
		this.formatCustomerName(customerName);

		try {
			this.customer = new File(FILE_PATH + this.userName);
			this.getInfo();
		} catch (FileNotFoundException e) {
			return false;
		}

		return true;
	}

	public void create(String customerName, String phoneNum) throws IOException {
		this.formatCustomerName(customerName);
		this.phoneNum = phoneNum;
		this.formatPhoneNum();

		File dir = new File(FILE_PATH);
		dir.mkdirs();
		this.customer = new File(dir, userName);
		customer.createNewFile();

		this.write();
	}

	@Override
	public void getInfo() throws FileNotFoundException {
		Scanner sc = new Scanner(this.customer);

		this.firstName = sc.next();
		this.lastName = sc.next();
		this.updateUserName();
		this.phoneNum = sc.next();
		this.formatPhoneNum();

		while (sc.hasNextLine()) {
			this.transactions.add(sc.next());
		}

		sc.close();
	}

	@Override
	public void write() throws IOException {
		FileWriter wr = new FileWriter(this.customer);

		wr.write(this.firstName + "\n");
		wr.write(this.lastName + "\n");
		String phoneNum = this.phoneNum.substring(1, 4) + this.phoneNum.substring(6, 9) + this.phoneNum.substring(10);
		wr.write(phoneNum + "\n");

		for (int i = 0; i < this.transactions.size(); i++) {
			wr.write(this.transactions.get(i) + "\n");
		}

		wr.close();
	}

	public void write(String transactionNum) throws IOException {
		this.write();

		FileWriter wr = new FileWriter(this.customer, true);

		wr.write(transactionNum);

		wr.close();
	}

	private void formatPhoneNum() {
		this.phoneNum = "(" + this.phoneNum.substring(0, 3) + ") " + this.phoneNum.substring(3, 6) + " "
				+ this.phoneNum.substring(6);
	}

	private void formatCustomerName(String customerName) {
		if (customerName.contains(" ")) {
			this.firstName = customerName.substring(0, customerName.indexOf(" ")).toLowerCase();
			this.lastName = customerName.substring(customerName.indexOf(" ") + 1).toLowerCase();
		} else {
			this.firstName = customerName.toLowerCase();
			this.lastName = "";
		}

		this.updateUserName();
	}

	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + " " + this.phoneNum;
	}

	public void changeFirstName(String newName) throws IOException {
		this.firstName = newName;
		this.write();
		this.updateUserName();
	}

	public void changeLastName(String newName) throws IOException {
		this.lastName = newName;
		this.write();
		this.updateUserName();
	}

	private void updateUserName() {
		this.userName = (this.firstName + this.lastName).toLowerCase();
	}

	public String getUserName() {
		return this.userName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}
}
