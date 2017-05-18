package main;

public class ProductButton extends Button {

	private double price;
	private static final int size = 150;

	public ProductButton(String name, double price, int x, int y){
		super(name, x, y, size, size);
		this.price = price;
	}
	
	public double getPrice(){
		return price;
	}
	
	public double setPrice(double price){
		return this.price = price;
	}

}
