package local_database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LocalDataBase {
	
	private File playerList; 
	private ArrayList<Player> database;
	
	
	public LocalDataBase() throws FileNotFoundException  {
		this.playerList = new File("src/local_database/Players");
		this.database = new ArrayList<Player>();
		this.read();
	}

	public void read() throws FileNotFoundException {
		Scanner sc = new Scanner(this.playerList);
		
		for(int i = 0; sc.hasNextLine(); i++) {
			this.database.add(new Player());
			this.database.get(i).initialize(sc.next(), sc.nextInt(), sc.nextDouble(), sc.nextLong(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextLong(), sc.nextLong(), sc.nextByte());
		}
		
		sc.close();	
	}
	
	public Player search(String name) {
		for (int i = 0; i < this.database.size(); i++) {
			if (this.database.get(i).getName().equals(name)) {
				return this.database.get(i);
			}
		}
		
		return null;
	}
	
	public void write() throws IOException {
		FileWriter writer = new FileWriter(this.playerList);
		
		for(int i = 0; i < this.database.size(); i++) {
			writer.write(this.database.get(i).getName() + "\n");
			writer.write(new Integer(this.database.get(i).getHighscore()).toString() + "\n");
			writer.write(new Double(this.database.get(i).getMeanScore()).toString() + "\n");
			writer.write(new Long(this.database.get(i).getGamesPlayed()).toString() + "\n");
			writer.write(new Integer(this.database.get(i).getS1()).toString() + "\n");
			writer.write(new Integer(this.database.get(i).getS2()).toString() + "\n");
			writer.write(new Integer(this.database.get(i).getS3()).toString() + "\n");
			writer.write(new Integer(this.database.get(i).getS4()).toString() + "\n");
			writer.write(new Integer(this.database.get(i).getS5()).toString() + "\n");
			writer.write(new Long(this.database.get(i).getBulletsHit()).toString() + "\n");
			writer.write(new Long(this.database.get(i).getBulletsShot()).toString() + "\n");
			writer.write(new Byte(this.database.get(i).getCarLevel()).toString());
			
			if (i < this.database.size() - 1) {
				writer.write("\n");
			}
		}
		
		while (this.database.size() > 0) {
			this.database.remove(0);
		}
		
		writer.close();
	}
	
	public void update() throws IOException {
		this.write();
		this.read();
	}
	
	public String toString() {
		return database.toString();
	}
	
	public File getPlayerList() {
		return this.playerList;
	}
	
	public static void main(String args[]) throws IOException {
		LocalDataBase m = new LocalDataBase();
		
		System.out.println(m.toString());
		
		m.write();
		System.out.println(m.toString());
		m.read();
		System.out.println(m.toString());
	}
}
