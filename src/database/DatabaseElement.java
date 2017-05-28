package database;

import java.io.FileNotFoundException;
import java.io.IOException;

// an abstract class that is extended by all database elements in this application (customer, transactions)
public abstract class DatabaseElement {

	// default constructor
	public DatabaseElement() {
	}

	// tries to load a file and returns a boolean based on whether or not the
	// file exists
	public abstract boolean load(String fileName);

	// instantiates instance variables from reading the database file
	public abstract void getInfo() throws FileNotFoundException;

	// writes all instance variables to the file (updates changes in the
	// database)
	public abstract void write() throws IOException;
}
