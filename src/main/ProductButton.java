package main;

// ProductButton is a Button (specialized for products)
// extends product button (adds the functionality of price)
public class ProductButton extends Button {
	// declare instance variables
	private double price;
	private static final int size = 120;

	// default constructor
	public ProductButton() {
	}
	
	// constructor that instantiates all instance variables
	public ProductButton(String name, double price, int x, int y){
		// calls the button constructor
		super(name, x, y, size, size);
		// instantiates the price
		this.price = price;
	}
	
	// returns the price of the product
	public double getPrice(){
		return price;
	}
	
	// set the price of the product to a new value
	public double setPrice(double price){
		return this.price = price;
	}

}
