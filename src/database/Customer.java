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
	
	public Customer(String fileName) {
		this.load(fileName);
	}
	
	@Override
	public boolean load(String fileName) {
		try{
			this.customer = new File("src/database/customers/" + fileName);
			this.getInfo();
		} catch(FileNotFoundException e) {
			return false;
	    }
		
		return true;
	}
	
	public void create(String firstName, String lastName, String phoneNum) throws IOException {
		this.firstName = firstName;
		this.lastName = lastName;
		this.updateUserName();
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
		FileWriter wr = new FileWriter(this.customer);
		
		wr.write(this.firstName + "\n");
		wr.write(this.lastName + "\n");
		String phoneNum = this.phoneNum.substring(1, 4) + this.phoneNum.substring(6, 9) + this.phoneNum.substring(10); 
		wr.write(phoneNum);
	
		wr.close();
	}
	
	private void formatPhoneNum() {
		this.phoneNum = "(" + this.phoneNum.substring(0, 3) + ") " + this.phoneNum.substring(3, 6) + " " + this.phoneNum.substring(6);
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
	
	protected void updateUserName() {
		this.userName = (this.firstName + this.lastName).toLowerCase();
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public static void main(String args[]) throws IOException {
		Customer c = new Customer("frankgrimes");
		
		c.load("frankgrimes");
		
		c.create("Frank", "Grimes", "6475343474");

		
		System.out.println(c);
	}
}
