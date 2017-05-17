package main;

public class ProductButton extends Button {

	private double price;
	private static final int size = 150;
	private int xCord, yCord;

	public ProductButton(String name, int x, int y, double price){
		super(name);
		
		this.xCord = x;
		this.yCord = y;	
		this.price = price;
	}
	
	@Override
	public boolean clicked(int x, int y) {
		if ((x > xCord && x < xCord + size) && (y > yCord && y < yCord + size)) {
			return true;
		}
		return false;
	}
	
	public double getPrice(){
		return price;
	}
	
	public double setPrice(double price){
		return this.price = price;
	}

}
