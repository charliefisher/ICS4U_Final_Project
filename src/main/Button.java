package main;

public class Button {
	
	private String name = "";
	protected int xCord, yCord;
	protected int xSize, ySize;
	

	public Button(String name, int xCord, int yCord, int xSize, int ySize){
		this.name = name;
		this.xCord = xCord;
		this.yCord = yCord;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	
	public String getName(){
		return name;
	}
	
	public String setName(String name){
		return this.name = name;
	}
	
	public boolean clicked(int x, int y) {
		if ((x > xCord && x < xCord + xSize) && (y > yCord && y < yCord + ySize)) {
			return true;
		}
		return false;
	}
}
