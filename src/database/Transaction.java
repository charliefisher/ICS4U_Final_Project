package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Transaction extends DatabaseElement{
	private File transaction;
	private File settings;
	
	private int numProducts;
	
	private Customer customer;
	
	private String date;
	
	private double subtotal;
	private double tax;
	private double total;
	
	private ArrayList<String> products = new ArrayList<String>();
	
	private static int transactionNum;
	
	public Transaction() {
		
	}
	
	public void registerOpen() throws FileNotFoundException {
		this.settings = new File("src/database/transactions/settings");
		Scanner sc = new Scanner(this.settings);
		Transaction.transactionNum = sc.nextInt();
		sc.close();
	}
	
	public void registerClose() throws IOException {
		FileWriter wr = new FileWriter(this.settings);
		wr.write(new Integer(Transaction.transactionNum).toString());
		wr.close();
	}

	@Override
	public boolean load(String fileName) {
		try{
			this.transaction = new File("src/database/transactions/" + fileName);
			this.getInfo();
		} catch(FileNotFoundException e) {
			return false;
	    }
		
		return true;
	}

	@Override
	public void getInfo() throws FileNotFoundException {
		Scanner sc = new Scanner(this.transaction);
		
		while(sc.hasNextLine()) {
			this.customer = new Customer(sc.next());
			
			this.date = sc.next();
			
			this.numProducts = sc.nextInt();
			
			for (int i = 0; i < numProducts; i++) {
				products.add(sc.next());
			}
			
			this.subtotal = sc.nextDouble();
			this.tax = sc.nextDouble();
			this.total = sc.nextDouble();
		}
		
		sc.close();
	}

	@Override
	public void write() throws IOException {
			FileWriter wr = new FileWriter(this.transaction);
			wr.write(customer.getUserName());
			wr.write(this.date);
			wr.write(new Integer(numProducts).toString());
			
			for (int i = 0; i < numProducts; i++) {
				wr.write(products.get(i));
			}
			
			wr.write(new Double(this.subtotal).toString());
			wr.write(new Double(this.tax).toString());
			wr.write(new Double(this.total).toString());
			
			wr.close();
	}
	
	public String toString() {
		return this.customer + " bought " + this.numProducts + " products on " + date;
	}
	
	public static void main (String args[]) throws IOException {
		Transaction t = new Transaction();
		t.registerOpen();
		t.registerClose();
		t.load("T1");
		
		System.out.println(t);
		
	}
}
