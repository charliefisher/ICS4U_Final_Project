package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Customer extends DatabaseElement{
	private String firstName;
	private String lastName;
	private String userName;
	
	private File customer;
	
	private String phoneNum;
	
	public Customer() {
		
	}
	
	public Customer(String fileName) {
		this.load(fileName);
	}
	
	@Override
	public boolean load(String customerName) {
		this.formatCustomerName(customerName);
		
		try{
			this.customer = new File("src/database/customers/" + this.userName);
			this.getInfo();
		} catch(FileNotFoundException e) {
			return false;
	    }
		
		return true;
	}
	
	public void create(String customerName, String phoneNum) throws IOException {
		this.formatCustomerName(customerName);
		this.phoneNum = phoneNum;
		this.formatPhoneNum();
		
		File dir = new File("src/database/customers/");
		dir.mkdirs();
		this.customer = new File(dir, userName);
		customer.createNewFile();
		
		this.write();
	}
	
	@Override
	public void getInfo() throws FileNotFoundException {
		System.out.println(this.toString());
		
		Scanner sc = new Scanner(this.customer);
		
		while(sc.hasNextLine()) {
			this.firstName = sc.next();
			this.lastName = sc.next();
			this.updateUserName();
			this.phoneNum = sc.next();
			this.formatPhoneNum();
		}
		
		sc.close();	
	}
	
	@Override
	public void write() throws IOException {
		System.out.println("Writing");
		
		FileWriter wr = new FileWriter(this.toString());
		
		wr.write(this.firstName + "\n");
		wr.write(this.lastName + "\n");
		String phoneNum = this.phoneNum.substring(1, 4) + this.phoneNum.substring(6, 9) + this.phoneNum.substring(10); 
		wr.write(phoneNum);
	
		wr.close();
	}
	
	private void formatPhoneNum() {
		this.phoneNum = "(" + this.phoneNum.substring(0, 3) + ") " + this.phoneNum.substring(3, 6) + " " + this.phoneNum.substring(6);
	}
	
	private void formatCustomerName(String customerName) {
		if(customerName.contains(" ")) {
			this.firstName = customerName.substring(0, customerName.indexOf(" ")).toLowerCase();
			this.lastName = customerName.substring(customerName.indexOf(" ") + 1).toLowerCase();
		}
		else {
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
