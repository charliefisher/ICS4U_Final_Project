package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import main.ProductButton;

public class Transaction extends DatabaseElement{
	private static final double TAX_RATE = 0.13;
	
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
	
	public Transaction(Customer customer) throws IOException {
		this.registerOpen();
		
		File dir = new File("src/database/transactions/");
		dir.mkdirs();
		this.transaction = new File(dir, "T" + this.transactionNum);
		transaction.createNewFile();
		
		this.customer = customer;
			
		Transaction.transactionNum++;
		this.registerClose();
		
		this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	private void registerOpen() throws FileNotFoundException {
		this.settings = new File("src/database/transactions/transaction_settings");
		Scanner sc = new Scanner(this.settings);
		Transaction.transactionNum = sc.nextInt();
		sc.close();
	}
	
	private void registerClose() throws IOException {
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
			wr.write(customer.getUserName() + "\n");
			wr.write(this.date + "\n");
			wr.write(new Integer(products.size()).toString() + "\n");
			
			for (int i = 0; i < numProducts; i++) {
				wr.write(this.products.get(i) + "\n");
			}
			
			wr.write(new Double(Math.round(this.subtotal * 100.0) / 100.0).toString() + "\n");
			wr.write(new Double(Math.round(this.tax * 100.0) / 100.0).toString() + "\n");
			wr.write(new Double(Math.round(this.total * 100.0) / 100.0).toString());
			
			wr.close();
	}
	
	public String toString() {
		return this.customer + " bought " + this.numProducts + " products on " + date;
	}
	
	public void addToSubtotal(ProductButton product) throws IOException {
		this.subtotal += product.getPrice();
		this.tax = subtotal * TAX_RATE;
		this.total = subtotal + tax;
		this.products.add(product.getName());
		this.numProducts = this.products.size();
	}
	
	public String getSubTotal(){
		return "$" + String.format("%.2f", subtotal);
	}
	
	public String getOrderSummary() {
		String temp;
		
		if (this.customer.getLastName().length() > 1) {
			temp = this.customer.getLastName().toUpperCase().charAt(0) + this.customer.getLastName().substring(1).toLowerCase();
		}
		else {
			temp = "";
		}
		
		return "Date: " + this.date 
				+ "?Customer: " + this.customer.getFirstName().toUpperCase().charAt(0) + this.customer.getFirstName().substring(1).toLowerCase() + " " + temp 
				+ "?Subtotal: $" + String.format("%.2f", this.subtotal) 
				+ "?Tax: $" + String.format("%.2f", this.tax) 
				+ "?Total: $" + String.format("%.2f", this.total) +"?"; //? used as reference to program when to draw new line
	}
	
	public String getTransactionNum() {
		return "T" + (Transaction.transactionNum - 1);
	}
}
