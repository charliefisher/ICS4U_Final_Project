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

//a transaction is a database element (inherits properties such as reading and writing to a text file)
public class Transaction extends DatabaseElement {
	// declare and instantiate the tax rate for all transactions
	private static final double TAX_RATE = 0.13;

	// declare instance variables
	private Customer customer;
	private String date;
	private double subtotal;
	private double tax;
	private double total;
	private int numProducts;
	
	// store the list of products purchased in this transaction
	private ArrayList<String> products = new ArrayList<String>();
	
	// declare a file for this transaction
	private File transaction;
	// declare a file that stores info about transactions (in general, not actual transaction data)
	private File settings;

	// declare a static instance variable that stores the transcation number (in the history of this My Checkout)
	private static int transactionNum;

	// default constructor
	public Transaction() {
	}
	
	public Transaction(Customer customer) throws IOException {
		// "opens the register" (get transaction number)
		this.registerOpen();

		// declare the file path to the directory for the transaction files
		File dir = new File("src/database/transactions/");
		// make/locate the appropriate directory for the transaction files
		dir.mkdirs();
		// instantiate the transaction file to the name T and the transaction number (T1, T2, T3, ...)
		this.transaction = new File(dir, "T" + Transaction.transactionNum);
		// create  a permanent file for the transaction
		transaction.createNewFile();

		// reference the customer
		this.customer = customer;

		// instantiate the date to the current date
		this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	private void registerOpen() throws FileNotFoundException {
		// instantiate the settings file
		this.settings = new File("src/database/transactions/transaction_settings");
		// declare and instantiate a scanner for the settings file
		Scanner sc = new Scanner(this.settings);
		// instantiate the transaction number (must add one as the file stores the most recent transaction)
		// transaction number has a value of 0 before the application is used
		Transaction.transactionNum = sc.nextInt() + 1;
		// close the scanner (finished reading)
		sc.close();
	}

	public void registerClose() throws IOException {
		// declare and instantiate a file writer for the settings file
		FileWriter wr = new FileWriter(this.settings);
		// write the updated transaction number to the settings file
		wr.write(new Integer(Transaction.transactionNum).toString());
		// close the filewriter (finished writing)
		wr.close();
	}

	// load the transaction file
	// is unused but will be implemented when the application allows you to access past transaction data 
	// (other than through the physical text file)
	@Override
	public boolean load(String fileName) {
		try {
			// load the transaction file
			this.transaction = new File("src/database/transactions/" + fileName);
			// instantiate instance variables (read from file)
			this.getInfo();
		} catch (FileNotFoundException e) {
			// if the transaction is not in the database, return false
			return false;
		}

		// the transaction has been successfully loaded, return true
		return true;
	}

	// instantiate instance variables with the values in the transaction
	// is unused but will be implemented when the application allows you to access past transaction data 
	// (other than through the physical text file)
	@Override
	public void getInfo() throws FileNotFoundException {
		// declare and instantiate a scanner for the transaction file
		Scanner sc = new Scanner(this.transaction);

		// instantiate instance variables
		this.customer = new Customer(sc.next());
		this.date = sc.next();
		this.numProducts = sc.nextInt();

		// read all purcahsed products
		for (int i = 0; i < numProducts; i++) {
			products.add(sc.next());
		}
		// instantiate instance variables
		this.subtotal = sc.nextDouble();
		this.tax = sc.nextDouble();
		this.total = sc.nextDouble();

		// close the scanner (finished reading)
		sc.close();
	}

	// update the values in the file to the instance variables of the current transaction
	@Override
	public void write() throws IOException {
		// declare and instantiate a filewriter to write to the transaction's file
		FileWriter wr = new FileWriter(this.transaction);
		// write the customers username (first and last name)
		wr.write(customer.getUserName() + "\n");
		// write the date the transaction occured on
		wr.write(this.date + "\n");
		// write the number of products purchased
		wr.write(new Integer(products.size()).toString() + "\n");

		// write all of the products purchased
		for (int i = 0; i < numProducts; i++) {
			wr.write(this.products.get(i) + "\n");
		}
		// write the cost of the transaction (subtotal, tax, total)
		wr.write(new Double(Math.round(this.subtotal * 100.0) / 100.0).toString() + "\n");
		wr.write(new Double(Math.round(this.tax * 100.0) / 100.0).toString() + "\n");
		wr.write(new Double(Math.round(this.total * 100.0) / 100.0).toString());

		// close the writer (finished writing)
		wr.close();
	}

	// return a string containing the customer bought X products on the date ____
	public String toString() {
		return this.customer + " bought " + this.numProducts + " products on " + date;
	}

	// adds a product to the transaction
	public void addToSubtotal(ProductButton product, String undefined) throws IOException {
		// adds the price of the product to the subtotal
		this.subtotal += product.getPrice();
		// updates the tax for the transaction
		this.tax = subtotal * TAX_RATE;
		// updates the total for this transaction
		this.total = subtotal + tax;
		// if the product has a name, add the name to the list of products
		// if the product does not have a name, add "Empty Name" to the list of products
		if (!product.getName().equals(undefined)) {
			this.products.add(product.getName());
		} else {
			this.products.add("Empty Name");
		}	
		// update the number of products to the size of the list of products
		this.numProducts = this.products.size();
	}

	// returns a string with information about the order
	public String getOrderSummary() {		
		// return a string containing all of the info for the transaction
		// ? is used as reference to program where to draw new line
		return "Date: " + this.date + "?Customer: " + this.getCustomer() + "?Subtotal: $" + this.getSubtotal()
				+ "?Tax: $" + this.getTax() + "?Total: $" + this.getTotal() + "?";
	}

	// returns a string of the transaction number (T1, T2, T3, ...)
	public String getTransactionNum() {
		return "T" + Transaction.transactionNum;
	}

	// returns a string of the date of purchase
	public String getDate() {
		return this.date;
	}

	// returns the first and last name
	public String getCustomer() {
		String temp;

		// if the customer has a last name (capitalize the first letter) and store the value in temp
		// if the customer has no last name store "" (blank string) in temp
		if (!this.customer.getLastName().equals(Customer.EMPTY_LAST_NAME)) {
			temp = this.customer.getLastName().toUpperCase().charAt(0)
					+ this.customer.getLastName().substring(1).toLowerCase();
		} else {
			temp = "";
		}

		// retuns a string of the customers first and last name (with the first letter of each capitalized)
		return this.customer.getFirstName().toUpperCase().charAt(0)
				+ this.customer.getFirstName().substring(1).toLowerCase() + " " + temp;
	}

	// returns a string of the subtotal for the transaction with 2 decimal places	
	public String getSubtotal() {
		return "$" + String.format("%.2f", this.subtotal);
	}

	// returns a string of the tax for the transaction with 2 decimal places
	public String getTax() {
		return String.format("%.2f", this.tax);
	}

	// returns a string of the total for the transaction with 2 decimal places
	public String getTotal() {
		return String.format("%.2f", this.total);
	}
}
