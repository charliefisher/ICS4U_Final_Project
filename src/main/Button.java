package main;

public class Button {
	// declare instance variables
	private String name = "";
	protected int xCord, yCord;
	protected int xSize, ySize;

	// default constructor
	public Button() {
	}

	// constructor that initializes all instances variables
	public Button(String name, int xCord, int yCord, int xSize, int ySize) {
		// initialize instance variables
		this.name = name;
		this.xCord = xCord;
		this.yCord = yCord;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	// return the name of the button
	public String getName() {
		return name;
	}

	// set the name of the button to a new value
	public String setName(String name) {
		return this.name = name;
	}

	// returns true or false based on whether or not x and y are inside the
	// button's bounds
	public boolean clicked(int x, int y) {
		// if x greater than x min and less than x max and y is greater than y
		// min and less than y max, return true
		// (the button is clicked)
		// else, the button has not been clicked (return false)
		if ((x > xCord && x < (xCord + xSize)) && (y > yCord && y < (yCord + ySize))) {
			return true;
		}
		return false;
	}
}
