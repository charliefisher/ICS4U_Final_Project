package database;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class DatabaseElement {

	public DatabaseElement() {

	}

	public abstract boolean load(String fileName);

	public abstract void getInfo() throws FileNotFoundException;

	public abstract void write() throws IOException;
}
